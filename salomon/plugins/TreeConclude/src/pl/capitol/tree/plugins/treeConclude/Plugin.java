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
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class Plugin implements IPlugin {

	private static final Logger LOGGER = Logger.getLogger(Plugin.class);
	
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
				treeId = ((IInteger)treeVar.getValue()).getValue();
				
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
	
	
	private String conclude(INode root,IDataSource dataSource, Object [] row){
		boolean testResult = false;
		String test = root.getParentEdge();
		if ("".equals(test)) testResult = true;
		else {
			
			
		}
		String columnName = getColumnName(test);
		switch (root.getType()) {
		case COLUMN:
			
			break;
		case VALUE:
			
			break;
		}
		
		
		
		return "testttt";
	}
	

	
	private String getColumnName(String test){
		int index;
		index = test.indexOf(">");
		if (index < 0) index = test.indexOf("=");
		if (index < 0) index = test.indexOf("<=");
		String result = test.substring(0,index);
		return result.trim();
	}

	public ISettingComponent getSettingComponent() {
		return new SettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new ResultComponent(); 
	}

}
