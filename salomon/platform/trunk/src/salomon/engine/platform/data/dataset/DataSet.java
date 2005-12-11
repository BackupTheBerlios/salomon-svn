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

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.dataset.condition.AbstractCondition;
import salomon.platform.IInfo;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.DBException;
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
		_info = new DataSetInfo(dbManager, externalDBManager);
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
		SQLSelect select = new SQLSelect();
		// adding columns to be selected
		if (columns != null) {
			for (IColumn column : columns) {
				select.addColumn(column.getName());
			}
		}

		// setting data set conditions
		AbstractCondition[] dataSetConditions = _info.getConditions();
		for (AbstractCondition condition : dataSetConditions) {
			select.addCondition(condition.toSQL());

			// adding table (tables are kept is set
			// so it doesnt have to be checked if table exists in query
			select.addTable(condition.getColumn().getTable().getName());
		}

		// adding additional conditions
		if (conditions != null) {
			for (ICondition condition : conditions) {
				select.addCondition(((AbstractCondition) condition).toSQL());
				// adding table 
				select.addTable(((AbstractCondition) condition).getColumn().getTable().getName());
			}
		}

		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = _externalDBManager.select(select);
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return new Data(resultSet);
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