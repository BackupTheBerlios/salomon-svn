
package salomon.core.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;

/**
 *  Class manages with datasets.
 */
public class DataSetManager
{
	private final static String DATASET_TABLE = "datasets";

	private final static String DATASET_ITEMS = "dataset_items";

	public DataSet getDataSetForName(String name) throws SQLException,
			ClassNotFoundException
	{
		DataSet dataSet = null;
		ResultSet resultSet = getDataSetItems(name);
		List conditions = new LinkedList();
		//
		//getting conditions
		//
		while (resultSet.next()) {
			String tableName = resultSet.getString("table_name");
			String condition = resultSet.getString("condition");
			conditions.add(new DBCondition(tableName, condition));
		}
		dataSet = new DataSet();
		DBCondition[] conditionsArray = null;
		//
		// if there is no conditions, data set includes all data
		// and its conditions list remains null
		//
		if (!conditions.isEmpty()) {
			conditionsArray = new DBCondition[conditions.size()];
			dataSet.setConditions((DBCondition[]) conditions.toArray(conditionsArray));
		}
		return dataSet;
	}

	private ResultSet getDataSetItems(String dataSetName) throws SQLException,
			ClassNotFoundException
	{
		DBTableName[] tableNames = {new DBTableName("datasets"),
				new DBTableName("dataset_items")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[1], "table_name"),
				new DBColumnName(tableNames[1], "condition")};
		DBCondition[] conditions = {
				new DBCondition(new DBColumnName(tableNames[0], "dataset_id"),
						DBCondition.REL_EQ, new DBColumnName(tableNames[1],
								"dataset_id"), DBCondition.JOIN),
				new DBCondition(
						new DBColumnName(tableNames[0], "dataset_name"),
						DBCondition.REL_EQ, dataSetName, DBCondition.TEXT)};
		DBManager connector = DBManager.getInstance();
		return connector.select(columnNames, tableNames, conditions);
	}
}