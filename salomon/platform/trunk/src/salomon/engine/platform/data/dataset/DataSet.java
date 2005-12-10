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
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.platform.IInfo;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

/**
 * Class represents data set. Data set is a subset of data stored in tables, its
 * conditions specifies how to get this subset.
 * 
 * TODO: add join between tables support
 */
public class DataSet implements IDataSet
{
	private DBManager _dbManager;

	private ExternalDBManager _externalDBManager;

	private DataSetInfo _info;

	/**
	 * Creates data set. This constructor can be used only from DataSetManager.
	 */
	protected DataSet(DBManager dbManager, ExternalDBManager externalDBManager)
	{
		_dbManager = dbManager;
		_externalDBManager = externalDBManager;
		_info = new DataSetInfo(dbManager);
	}

	/**
	 * Method adds condition corresponding to given table name.
	 *
	 * @param condition
	 */
	public void addCondition(ICondition condition)
	{
		_info.addCondition(condition);
	}

	public ICondition[] getConditions()
	{
		return _info.getConditions();
	}

	public IInfo getInfo() throws PlatformException
	{
		return _info;
	}

	public IDataSet intersection(IDataSet dataSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method intersection() not implemented yet!");
	}

	public IDataSet intersection(IDataSet dataSet, IUniqueId id)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method intersection() not implemented yet!");
	}

	public IDataSet minus(IDataSet dataSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method minus() not implemented yet!");
	}

	public IDataSet minus(IDataSet dataSet, IUniqueId id)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method minus() not implemented yet!");
	}

	public IData selectData(IColumn[] columns, ICondition[] conditions)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method DataSet.selectData() not implemented yet!");
	}

	/**
	 * Method selects data basing on given parameters. It takes into account
	 * conditions determinating data set - conditions passed as the arguments of
	 * method are concatenated to them. TODO: reimplement it
	 * 
	 * @param select SELECT query
	 * @throws PlatformException
	 */
	//	public ResultSet selectData(SQLSelect select) throws PlatformException
	//
	//	{
	//		SQLSelect selectCopy = (SQLSelect) select.clone();
	//		// getting all tables used in select and _tableNames
	//		// (intersection of these sets)
	//		// These tables are needed to add conditions determinating dataset
	//		Collection<String> commonTables = selectCopy.getTables();
	//		commonTables.retainAll(_conditions.keySet());
	//
	//		if (_conditions.size() > 0) {
	//			// preparing conditions
	//			for (String commonTable : commonTables) {
	//				for (String condition : _conditions.get(commonTable)) {
	//					selectCopy.addCondition(condition);
	//				}
	//			}
	//		}
	//		// adding tables from original select
	//		for (String tableName : select.getTables()) {
	//			selectCopy.addTable(tableName);
	//		}
	//
	//		LOGGER.debug("selectCopy: " + selectCopy.getQuery());
	//		return DBManager.getInstance().select(selectCopy);
	//FIXME: use conditions
	//		ResultSet resultSet = null;
	//		try {
	//			resultSet = _externalDBManager.select(select);
	//		} catch (SQLException e) {
	//			LOGGER.fatal("", e);
	//			throw new PlatformException(e.getLocalizedMessage());
	//		}
	//		return resultSet;
	//	}
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
		throw new UnsupportedOperationException(
				"Method selectData() not implemented yet!");
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _info.toString();
	}

	public IDataSet union(IDataSet dataSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method union() not implemented yet!");
	}

	public IDataSet union(IDataSet dataSet, IUniqueId id)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method union() not implemented yet!");
	}

	private static final Logger LOGGER = Logger.getLogger(DataSet.class);
}