/** Java class "DataSetManager.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package ks.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ks.core.DBManager;

/**
 *  
 */
public class DataSetManager
{
	private final static String DATASET_TABLE = "datasets";
	private final static String DATASET_ITEMS = "dataset_items";
	
	public DataSet getDataSetForName(String name) throws SQLException, ClassNotFoundException {
		DataSet dataSet = null;
		ResultSet resultSet = getDataSetItems(name);
		List conditions = new LinkedList();
		
		//getting conditions
		while (resultSet.next()) {
			String tableName = resultSet.getString("table_name");
			String condition = resultSet.getString("condition");
			conditions.add(new DBCondition(tableName, condition));
		}
		DBCondition[] conditionsArray = new DBCondition[conditions.size()];
		dataSet = new DataSet(0);
		dataSet.setConditions((DBCondition[]) conditions.toArray(conditionsArray));
		return dataSet;
	}
	
	private ResultSet getDataSetItems(String dataSetName) throws SQLException, ClassNotFoundException {
		DBTableName[] tableNames = {new DBTableName("datasets"),
				new DBTableName("dataset_items")				
		};
		DBColumnName[] columnNames = {new DBColumnName(tableNames[1], "table_name"),				
									new DBColumnName(tableNames[1], "condition")
					};
		DBCondition[] conditions = {				
				new DBCondition(new DBColumnName(tableNames[0], "dataset_id"), DBCondition.REL_EQ, new DBColumnName(tableNames[1], "dataset_id"), DBCondition.JOIN),
				new DBCondition(new DBColumnName(tableNames[0], "dataset_name"), DBCondition.REL_EQ, dataSetName, DBCondition.TEXT)
				};
		
		DBManager connector = DBManager.getInstance();
		 
		return connector.selectData(columnNames, tableNames, conditions);
	}
}
