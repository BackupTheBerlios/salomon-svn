/** Java class "DataSet.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;

import org.apache.log4j.Logger;

/**
 *  
 */
public class DataSet
{
	private int _datasetId = 0;
	
	private DBCondition[] _conditions = null;	
	private DBTableName[] _tableNames = null;
	
	private static Logger _logger = Logger.getLogger(DataSet.class);
	/**
	 * @param dataSetId (niepotrzebne chyba)
	 */
	public DataSet(int dataSetId)
	{
		_datasetId = dataSetId;
	}	
	
	public ResultSet selectData(DBColumnName[] columnNames,
			DBTableName[] tableNames, DBCondition[] conditions) throws SQLException, ClassNotFoundException
	{
		//preparing conditions
		List conditonList = getConditionsByTable(tableNames);
		DBCondition[] queryConditions = null;
		_logger.info("DataSet.selectData(): conditonList = " + conditonList);
		
		if (conditonList.size() > 0) {			
			//adding conditions determinating data set
			queryConditions = new DBCondition[conditonList.size() + conditions.length];
			conditonList.addAll(Arrays.asList(conditions));
			queryConditions = (DBCondition[]) conditonList.toArray(queryConditions);			
		} else {
			queryConditions = conditions;
		}	 
		
		DBManager connector = DBManager.getInstance();
		 
		return connector.selectData(columnNames, tableNames, queryConditions);		 
	}
	/**
	 * @return Returns the _conditions.
	 */
	public DBCondition[] getConditions()
	{
		return _conditions;
	}
	/** Returns conditions which refers to tables specified by tableNames
	 * Data set is a subset of data stored in tables, conditions specifies
	 * how to get this subset. Conditions got from plugin has to be added 
	 * to condition list determinating data set.   
	 * 
	 * @param tableNames
	 * @return
	 */
	private List getConditionsByTable(DBTableName[] tableNames) {
		List conditionList = new LinkedList();
		// Checking which condition determinating data set
		// should be added to specified condition list
		// If conditions table name is the same as tableName[i]
		// then it should be added
		for (int i = 0; i < _conditions.length; i++) {
			for (int j = 0; j < tableNames.length; j++) {
				if (tableNames[j].equals(_conditions[i].getTableName())) {
					conditionList.add(_conditions[i]);
				}
			}
		}
		return conditionList;
	}
	
	public void setConditions(DBCondition[] conditions)
	{
		_conditions = conditions;		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String dataSet = "[";
		for (int i = 0; i < _conditions.length; i++) {
			dataSet += _conditions[i].toString() + ", ";
		}
		dataSet += "]";
		return dataSet;
	}
}
