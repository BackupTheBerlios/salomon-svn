
package salomon.core.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;

/**
 * Class represents data set. Data set is a subset of data stored in tables, its
 * conditions specifies how to get this subset.
 * 
 * @author nico
 */
public class DataSet
{
	/**
	 * Conditions determinating data set. If conditions are null it means that
	 * data set includes all data
	 */
	private DBCondition[] _conditions = null;

	private DBTableName[] _tableNames = null;

	private static Logger _logger = Logger.getLogger(DataSet.class);

	/**
	 * Method selects data basing on given parameters. It takes into account
	 * conditions determinating data set - conditions passed as the arguments of
	 * method are concatenated to them.
	 * 
	 * @param columnNames column names to select
	 * @param tableNames tables from which data should be selected
	 * @param conditions conditions of query
	 * @return @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet selectData(DBColumnName[] columnNames,
			DBTableName[] tableNames, DBCondition[] conditions)
			throws SQLException, ClassNotFoundException
	{
		DBCondition[] queryConditions = null;
		if (_conditions != null) {
			//
			//preparing conditions
			//
			List conditonList = getConditionsByTable(tableNames);
			_logger.info("DataSet.selectData(): conditonList = " + conditonList);
			if (conditonList.size() > 0) {
				//
				//adding conditions determinating data set
				//
				queryConditions = new DBCondition[conditonList.size()
						+ conditions.length];
				conditonList.addAll(Arrays.asList(conditions));
				queryConditions = (DBCondition[]) conditonList.toArray(queryConditions);
			}
		} else {
			queryConditions = conditions;
		}
		DBManager connector = DBManager.getInstance();
		return connector.select(columnNames, tableNames, queryConditions);
	}

	/**
	 * Method returns ResultSet basing on given query. Query is modified - all
	 * conditions determinating data set are concatenated to the query;
	 * 
	 * @param query SQL query to be executed
	 * @return @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ResultSet selectData(String query) throws SQLException,
			ClassNotFoundException
	{
		_logger.info("given query: " + query);
		String finalQuery = query.trim().toLowerCase();
		//
		// If there are some conditions determinating data set
		// they are added to query
		//
		String dataSetCond = null;
		if (_conditions != null) {
			for (int i = 0; i < _conditions.length; i++) {
				// to first condition "and" is not added
				// it will be added later, if necessary
				if (i > 0) {
					dataSetCond += " and ";
				}
				dataSetCond += _conditions[i].toString();
			}
		}
		_logger.debug("dataSetCond" + dataSetCond);
		//
		// If there are any conditions, given query has to be modified
		//
		if (dataSetCond != null) {
			//
			// removing semicolon if exists
			//
			if (finalQuery.endsWith(";")) {
				finalQuery = finalQuery.substring(0, finalQuery.length() - 1);
			}
			//
			// if there is where clause,
			// then adding conditions at the end of query
			// else adding where and conditions list
			//			
			if (finalQuery.indexOf("where") != -1) {
				finalQuery += " where ";
			} else {
				finalQuery += " and " + dataSetCond;
			}
		}
		_logger.info("finalQuery: " + finalQuery);
		DBManager connector = DBManager.getInstance();
		connector.executeQuery(finalQuery);
		return connector.getResultSet();
	}

	/**
	 * Method returns the conditions determinating data set.
	 * 
	 * @return conditions determinating data set.
	 */
	public DBCondition[] getConditions()
	{
		return _conditions;
	}

	/**
	 * Returns conditions which refers to tables specified by tableNames. Data
	 * set is a subset of data stored in tables, conditions specifies how to get
	 * this subset. Conditions got from plugin has to be added to condition list
	 * determinating data set.
	 * 
	 * @param tableNames
	 * @return
	 */
	private List getConditionsByTable(DBTableName[] tableNames)
	{
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

	/**
	 * Method sets conditions determinating data set.
	 * 
	 * @param conditions to set
	 */
	public void setConditions(DBCondition[] conditions)
	{
		_conditions = conditions; 
	}

	/*
	 * (non-Javadoc)
	 * 
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