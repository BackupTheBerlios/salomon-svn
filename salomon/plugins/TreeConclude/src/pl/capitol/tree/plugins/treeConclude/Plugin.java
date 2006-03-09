package pl.capitol.tree.plugins.treeConclude;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import pl.capitol.tree.plugins.treeConclude.components.ResultComponent;
import pl.capitol.tree.plugins.treeConclude.components.SettingComponent;
import pl.capitol.tree.plugins.treeConclude.util.Results;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;
import salomon.platform.serialization.IInteger;
import salomon.platform.serialization.IString;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * G³ówna klasa realizujaca wnioskowanie.
 * 
 * @author Mateusz Nowakowski
 *
 */
public class Plugin implements IPlugin {

	private static final Logger LOGGER = Logger.getLogger(Plugin.class);
	
	/**
	 * Metoda wykonuje wnioskowanie. W przypadku okno ustawieñ ustawi³o zmienn¹ <i>isAlone</i> wówczas
	 * pobierana jest wartosæ zmiennej <i>treeId</i> z okna ustawieñ, jako identyfikator drzewa do wnioskowania.
	 * W przeciwnym razie sprawdzana jest zmienna œrodowiskowa <i>tree_name</i>. Test nastepuje na pozosta³ych
	 * wierszach , które nie by³y u¿yte do jego tworzenia drzewa.
	 */
	/* (non-Javadoc)
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.IDataEngine, salomon.platform.IEnvironment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		
		Results results = null;
		try {
			int treeId;
			results = new Results();
			
			//popranie z settingsow lub enviromentu id drzewa
			if (((IInteger)settings.getField("isAlone")).getValue() == 1) { //isAlone == true
				treeId = ((IInteger)settings.getField("treeId")).getValue();
			} else {
				IVariable treeVar = env.getVariable("tree_name");
				if (treeVar == null) {
					results.setErrorMessage("Brak drzewa w srodowisku.");
					return results;
				}
				treeId = Integer.parseInt(((IString)treeVar.getValue()).getValue());
				
			}
			
			ITree tree = eng.getTreeManager().getTree(treeId);
			IDataSource dataSource = tree.getDataSource();
			Collection<Object []> list = new ArrayList<Object []>();
			List<Object []> data = eng.getTreeManager().getRestTreeDataSourceRows(dataSource);
			int positiveTests = 0;
			
			//pierwszy wiersz
			ArrayList<Object> row = new ArrayList<Object>();
			row.add("Nr wiersza");
			row.add("Wynik");
			row.add("Oczekiwany wynik: "+dataSource.getDecisionedColumn());
			Collections.addAll(row,dataSource.getDecioningColumns());
			list.add(row.toArray());
			
			for (Object [] rowData : data) {
				String result = conclude(tree.getRoot(),dataSource,rowData);
				if (!rowData[1].toString().equals(result)){
					row =new ArrayList<Object>();
					Collections.addAll(row,rowData);
					row.add(1,result);
					list.add(row.toArray());		
				}else positiveTests++;
			}
			
			results.setTreeName(tree.getName()+" ("+tree.getId()+")");
			results.setAllTests(data.size());
			results.setPositiveTests(positiveTests);
			results.setInvalidRows(list);
			results.setSuccess(true);			

			
		} catch (Exception e){
			LOGGER.error("Error in TreeConcludePlugin : "+e.getMessage());
			e.printStackTrace();
			results = new Results();
			results.setErrorMessage("Wystapil nieoczekiwany blad: \n"+e.getMessage());
		}
		return results;
	}
	
	
	private String conclude(INode root,IDataSource dataSource, Object [] row) throws Exception{
		boolean testResult = false;
		String test = root.getParentEdge();
		if (test == null) test = "";
		if ("".equals(test)) testResult = true;
		else {
			String columnName = getColumnName(test);
			String operator = getOperator(test);
			String value = getValue(test);
			
			int index = getColumnIndex(columnName,dataSource);
			String testValue = row[index].toString();
			if (operator.equals("=")) {
				testResult = testValue.equals(value);
			} else {
				double val1 = Double.parseDouble(testValue);
				double val2 = Double.parseDouble(value);
				
				
				if (operator.equals(">")) testResult = (val1 > val2);
				if (operator.equals("<=")) testResult = (val1 <= val2);
			}
		}
		
		switch (root.getType()) {
		case COLUMN:
			if (testResult){
				String result = null;
				for (INode node : root.getChildren()) {
					result = conclude(node,dataSource,row);
					if (result != null) return result;
				}
				return null;
			} else return null;
		case VALUE:
			if (testResult) return root.getValue();
			else return null;
		}

		return null;
	}
	

	
	private String getColumnName(String test){
		int index;
		index = test.indexOf(">");
		if (index < 0) index = test.indexOf("=");
		if (index < 0) index = test.indexOf("<=");
		String result = test.substring(0,index);
		return result.trim();
	}

	private int getColumnIndex(String columnName,IDataSource dataSource) throws Exception{
		String [] columns = dataSource.getDecioningColumns();
		for (int i=0;i< columns.length;i++){
			String name = columns[i];
			if (name.equals(columnName)) return i+2; 
		}
		throw new Exception("Cannot find column name "+columnName+"in dataSource "+dataSource.getId());
	}
	
	
	private String getOperator(String test) throws Exception{
		int index;
		index = test.indexOf(">");
		if (index < 0) index = test.indexOf("="); else return ">";
		if (index < 0) index = test.indexOf("<="); else return "=";
		if (index >= 0) return "<=";
		else throw new Exception("Cannot find supported operator.");
	}
	
	private String getValue(String test)throws Exception{
		int index;
		int opLen = 1;
		index = test.indexOf(">");
		if (index < 0) index = test.indexOf("=");
		if (index < 0) {
			index = test.indexOf("<=");
			if (index >= 0) opLen =2;
			else throw new Exception("Cannot find supported operator.");
		}
		return test.substring(index+opLen,test.length()).trim();
	}
	
	/**
	 * Zwraca komponent definicyjny
	 */
	public ISettingComponent getSettingComponent() {
		return new SettingComponent();
	}

	/**
	 * Zwraca komponent z rezultatami
	 */
	public IResultComponent getResultComponent() {
		return new ResultComponent(); 
	}

}
