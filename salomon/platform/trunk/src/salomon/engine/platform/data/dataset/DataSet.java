/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.platform.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;


import salomon.platform.data.dataset.IDataSet;

/**
 * Class represents data set. Data set is a subset of data stored in tables, its
 * conditions specifies how to get this subset.
 */
class DataSet implements IDataSet
{
	/**
	 * Conditions determinating data set. If conditions are empty it means that
	 * data set includes all data
	 */
	private Collection<String> _conditions;

	private Collection<String> _tableNames;

	/**
	 * Method returns the conditions determinating data set.
	 * 
	 * @return conditions determinating data set.
	 */
	public Collection<String> getConditions()
	{
		return _conditions;
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSet#getName()
	 */
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method selects data basing on given parameters. It takes into account
	 * conditions determinating data set - conditions passed as the arguments of
	 * method are concatenated to them. TODO: reimplement it
	 * 
	 * @param select SELECT query
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public ResultSet selectData(SQLSelect select) throws SQLException,
			ClassNotFoundException
	{
		// Collection<String> queryConditions = null;
		//        
		// if (_conditions != null) {
		// //
		// //preparing conditions
		// //
		// List conditonList = getConditionsByTable(tableNames);
		// _logger.info("DataSet.selectData(): conditonList = " + conditonList);
		// if (conditonList.size() > 0) {
		// //
		// //adding conditions determinating data set
		// //
		// queryConditions = new DBCondition[conditonList.size()
		// + conditions.length];
		// conditonList.addAll(Arrays.asList(conditions));
		// queryConditions = (DBCondition[])
		// conditonList.toArray(queryConditions);
		// }
		// } else {
		// queryConditions = conditions;
		// }
		// DBManager connector = DBManager.getInstance();
		return DBManager.getInstance().select(select);
	}

	/**
	 * Method returns ResultSet basing on given query. Query is modified - all
	 * conditions determinating data set are concatenated to the query; TODO:
	 * reimplement it
	 * 
	 * @param query SQL query to be executed
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ResultSet selectData(String query) throws SQLException,
			ClassNotFoundException
	{
		LOGGER.info("given query: " + query);
		String finalQuery = query.trim().toLowerCase();
		//
		// If there are some conditions determinating data set
		// they are added to query
		//
		// String dataSetCond = null;
		// if (_conditions != null) {
		// for (int i = 0; i < _conditions.length; i++) {
		// // to first condition "and" is not added
		// // it will be added later, if necessary
		// if (i > 0) {
		// dataSetCond += " and ";
		// }
		// dataSetCond += _conditions[i].toString();
		// }
		// }
		// _logger.debug("dataSetCond" + dataSetCond);
		// //
		// // If there are any conditions, given query has to be modified
		// //
		// if (dataSetCond != null) {
		// //
		// // removing semicolon if exists
		// //
		// if (finalQuery.endsWith(";")) {
		// finalQuery = finalQuery.substring(0, finalQuery.length() - 1);
		// }
		// //
		// // if there is where clause,
		// // then adding conditions at the end of query
		// // else adding where and conditions list
		// //
		// if (finalQuery.indexOf("where") != -1) {
		// finalQuery += " where ";
		// } else {
		// finalQuery += " and " + dataSetCond;
		// }
		// }
		// _logger.info("finalQuery: " + finalQuery);
		// DBManager connector = DBManager.getInstance();
		// connector.executeQuery(finalQuery);
		// return connector.getResultSet();
		return null;
	}

	/**
	 * Method sets conditions determinating data set.
	 * 
	 * @param conditions to set
	 */
	public void setConditions(Collection<String> conditions)
	{
		_conditions = conditions;
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSet#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param tableNames The tableNames to set.
	 */
	public void setTableNames(Collection<String> tableNames)
	{
		_tableNames = tableNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String dataSet = "[";
		for (String condition : _conditions) {
			dataSet += condition + ",";
		}
		dataSet += "]";
		return dataSet;
	}

	/**
	 * Returns conditions which refers to tables specified by tableNames. Data
	 * set is a subset of data stored in tables, conditions specifies how to get
	 * this subset. Conditions got from plugin has to be added to condition list
	 * determinating data set.
	 * TODO: reimplement it
	 * @param tableNames
	 * @return
	 */
	private List<String> getConditionsByTable(Collection<String> tableNames)
	{
		// List conditionList = new LinkedList();
		// // Checking which condition determinating data set
		// // should be added to specified condition list
		// // If conditions table name is the same as tableName[i]
		// // then it should be added
		// for (int i = 0; i < _conditions.length; i++) {
		// for (int j = 0; j < tableNames.length; j++) {
		// if (tableNames[j].equals(_conditions[i].getTableName())) {
		// conditionList.add(_conditions[i]);
		// }
		// }
		// }
		// return conditionList;
		return null;
	}

	private static final Logger LOGGER = Logger.getLogger(DataSet.class);
}