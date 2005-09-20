package pl.capitol.tree.plugins.dataloader;

import pl.capitol.tree.plugins.dataloader.components.TreeDataLoaderSettingComponent;
import pl.capitol.tree.plugins.dataloader.components.TreeDataLoaderResultComponent;
import pl.capitol.tree.plugins.util.TreeDataLoaderResults;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;

public class TreeDataLoaderPlugin implements IPlugin {

	public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings) {
		// TODO 
		ITreeManager trm = eng.getTreeManager();
		try {


			System.out.println("-----------XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX---------------");
			SimpleString table = (SimpleString) settings.getField("table");
			SimpleString decisionedColumn = (SimpleString) settings.getField("decisionedColumn");
			String [] fieldNames = settings.getFieldNames();
			String [] decisioningColumns = new String [fieldNames.length - 2];
			
			int j = 0;
			for (int i=0; i<fieldNames.length; i++)
			{
				if (fieldNames[i].contains("decisioningColumn"))
				{
					decisioningColumns[j] = ((SimpleString)settings.getField(fieldNames[i])).getValue();
					j++;
				}
			}
			
			
			for (int i=0; i<decisioningColumns.length; i++)
			{
				System.out.println(decisioningColumns[i]);
			}
			
			IDataSource dataS = trm.createTreeDataSource();
			dataS.setInfo("TreeDataSource z informacjami na temat tabeli oraz wybranych kolumn");
			dataS.setName("TreeDataSource");
			dataS.setTableName(table.getValue());
			dataS.setDecisionedColumn(decisionedColumn.getValue());
			dataS.setDecioningColumns(decisioningColumns);
				
			int  dataSId = trm.addTreeDataSource(dataS);
			IVariable variable = env.createEmpty("DATA_SOURCE_ID");
			variable.setValue(new SimpleInteger(dataSId));
			env.add(variable);
		} catch (PlatformException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TreeDataLoaderResults();
	}

	public ISettingComponent getSettingComponent() {
		return new TreeDataLoaderSettingComponent();
	}

	public IResultComponent getResultComponent() {
		return new TreeDataLoaderResultComponent(); 
	}
	
}