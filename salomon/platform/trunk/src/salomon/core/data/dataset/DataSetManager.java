
package salomon.core.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import salomon.core.data.DBManager;
import salomon.core.data.common.SQLSelect;

/**
 * Class manages with datasets.
 */
public class DataSetManager
{

	public DataSet getDataSetForName(String name) throws SQLException,
			ClassNotFoundException
	{
		DataSet dataSet = null;
		ResultSet resultSet = getDataSetItems(name);
		Collection<String> conditions = new HashSet<String>();
		Collection<String> tableNames = new HashSet<String>();

        //getting conditions
		while (resultSet.next()) {
			String tableName = resultSet.getString("table_name");
			String condition = resultSet.getString("condition");			
            tableNames.add(tableName);
            conditions.add(condition);            
		}
		dataSet = new DataSet();
        
        dataSet.setTableNames(tableNames);
		dataSet.setConditions(conditions);        
		
		return dataSet;
	}

	private ResultSet getDataSetItems(String dataSetName) throws SQLException,
			ClassNotFoundException
	{		
        SQLSelect select = new SQLSelect();
        select.addColumn("table_name");
        select.addColumn("condition");
        select.addTable(DATASETS + " d");
        select.addTable(DATASET_ITEMS + " di");
        select.addCondition("d.dataset_id = di.dataset_id");
        select.addCondition("dataset_name =", dataSetName);        
        
		DBManager connector = DBManager.getInstance();
		return connector.select(select);
	}

	private final static String DATASET_ITEMS = "dataset_items";
	private final static String DATASETS = "datasets";
}