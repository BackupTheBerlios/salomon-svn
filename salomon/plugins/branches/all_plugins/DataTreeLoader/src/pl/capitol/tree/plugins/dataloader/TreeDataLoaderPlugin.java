
package pl.capitol.tree.plugins.dataloader;

import pl.capitol.tree.plugins.dataloader.components.TreeDataLoaderResultComponent;
import pl.capitol.tree.plugins.dataloader.components.TreeDataLoaderSettingComponent;
import pl.capitol.tree.plugins.util.TreeDataLoaderResults;

import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.IVariable;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TreeDataLoaderPlugin implements IPlugin
{

    public IResult doJob(IDataEngine eng, IEnvironment env, ISettings settings)
    {
        // TODO 
        ITreeManager trm = eng.getTreeManager();
        try {
            SimpleString table = (SimpleString) settings.getField("table");
            SimpleString decisionedColumn = (SimpleString) settings.getField("decisionedColumn");
            String[] fieldNames = settings.getFieldNames();
            //String [] decisioningColumns = new String [fieldNames.length - 2];
            SimpleInteger firstColumn = (SimpleInteger) settings.getField("firstIndex");
            SimpleInteger lastColumn = (SimpleInteger) settings.getField("lastIndex");

            SimpleString decisioningColumns = (SimpleString) settings.getField("decisioningColumns");
            String[] decisioningColumnsTable = decisioningColumns.getValue().split(
                    "'");

            //SimpleString[] decisioningColumnsTableSS = new SimpleString[decisioningColumnsTable.length];

            //for (int i=0; i<decisioningColumnsTable.length; i++)
            //	decisioningColumnsTableSS[i] = new SimpleString(decisioningColumnsTable[i]);

            /*int j = 0;
             for (int i=0; i<fieldNames.length; i++)
             {
             if (fieldNames[i].contains("decisioningColumn"))
             {
             decisioningColumns[j] = ((SimpleString)settings.getField(fieldNames[i])).getValue();
             j++;
             }
             }
             
             int numberOfDecisioningColumn = 0;
             
             for (int i=0; i<decisioningColumns.length; i++)
             {
             if (decisioningColumns[i]!=null)
             numberOfDecisioningColumn +=1;
             }
             String[] decisioningColumns2 = new String [numberOfDecisioningColumn];
             j=0;
             for (int i=0; i<fieldNames.length; i++)
             {
             if (fieldNames[i].contains("decisioningColumn"))
             {
             decisioningColumns2[j] = ((SimpleString)settings.getField(fieldNames[i])).getValue();
             j++;
             }
             }*/

            /*for (int i=0; i<decisioningColumns.length; i++)
             {
             System.out.println(decisioningColumns[i]);
             }*/

            IDataSource dataS = trm.createTreeDataSource();
            dataS.setInfo("TreeDataSource z informacjami na temat tabeli oraz wybranych kolumn");
            dataS.setName("TreeDataSource");
            dataS.setTableName(table.getValue());
            dataS.setDecisionedColumn(decisionedColumn.getValue());
            dataS.setDecioningColumns(decisioningColumnsTable);
            dataS.setFirstRowIndex(firstColumn.getValue());
            dataS.setLastRowIndex(lastColumn.getValue());

            int dataSId = trm.addTreeDataSource(dataS);
            IVariable variable = env.createEmpty("DATA_SOURCE_ID");
            variable.setValue(new SimpleInteger(dataSId));
            env.add(variable);

        } catch (PlatformException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new TreeDataLoaderResults();
    }

    public IResultComponent getResultComponent()
    {
        return new TreeDataLoaderResultComponent();
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        return new TreeDataLoaderSettingComponent();
    }

}