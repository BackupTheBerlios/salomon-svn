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
import salomon.engine.solution.ShortSolutionInfo;

import salomon.platform.IUniqueId;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;

/**
 * Class manages with datasets.
 */
public final class DataSetManager implements IDataSetManager
{
	private ExternalDBManager _externalDBManager;

	private DBManager _dbManager;
	
	private ShortSolutionInfo _solutionInfo;

	public DataSetManager(DBManager dbManager, ShortSolutionInfo solutionInfo,
			ExternalDBManager externalDBManager)
	{
		_dbManager = dbManager;
		_externalDBManager = externalDBManager;
		_solutionInfo = solutionInfo;
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#add(salomon.platform.data.dataset.IDataSet)
	 */
	public void add(IDataSet dataSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method add() not implemented yet!");
	}

	public IDataSet createEmpty() throws PlatformException
	{
		IDataSet dataSet = new DataSet(_dbManager, _externalDBManager);
		return dataSet;
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#getAll()
	 */
	public IDataSet[] getAll() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getDataSets() not implemented yet!");
	}

	public IDataSet getDataSet(IUniqueId id) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getDataSet() not implemented yet!");
	}

	public IDataSet getDataSet(String name) throws PlatformException
	{
		DataSet dataSet = null;
		ResultSet resultSet = null;
		try {
			resultSet = getDataSetItems(name);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		// getting conditions
		try {
			dataSet = (DataSet) this.createEmpty();
			dataSet.setName(name);
			while (resultSet.next()) {
				String tableName = resultSet.getString("table_name");
				String condition = resultSet.getString("condition");
				dataSet.addCondition(tableName, condition);
			}
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		return dataSet;
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#intersection(salomon.platform.data.dataset.IDataSet, salomon.platform.data.dataset.IDataSet)
	 */
	public IDataSet intersection(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method intersection() not implemented yet!");
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#minus(salomon.platform.data.dataset.IDataSet, salomon.platform.data.dataset.IDataSet)
	 */
	public IDataSet minus(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method minus() not implemented yet!");
	}

	public void remove(IDataSet dataSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method remove() not implemented yet!");
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#union(salomon.platform.data.dataset.IDataSet,
	 *      salomon.platform.data.dataset.IDataSet)
	 */
	public IDataSet union(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method union() not implemented yet!");
	}

	private ResultSet getDataSetItems(String dataSetName) throws SQLException,
			ClassNotFoundException
	{
		//		SQLSelect select = new SQLSelect();
		//		select.addColumn("table_name");
		//		select.addColumn("condition");
		//		select.addTable(DATASETS + " d");
		//		select.addTable(DATASET_ITEMS + " di");
		//		select.addCondition("d.dataset_id = di.dataset_id");
		//		select.addCondition("dataset_name =", dataSetName);
		//
		//		DBManager connector = DBManager.getInstance();
		//		return connector.select(select);
		throw new UnsupportedOperationException(
				"Method enclosing_method() not implemented yet!");
	}

	private static final String DATASET_ITEMS = "dataset_items";

	private static final String DATASETS = "datasets";

	private static final Logger LOGGER = Logger.getLogger(DataSetManager.class);

	public ICondition createEqualsCondition() throws PlatformException
	{
		throw new UnsupportedOperationException("Method DataSetManager.createEqualsCondition() not implemented yet!");
	}

	public ICondition createGreaterCondition() throws PlatformException
	{
		throw new UnsupportedOperationException("Method DataSetManager.createGreaterCondition() not implemented yet!");
	}

	public ICondition createLowerCondition() throws PlatformException
	{
		throw new UnsupportedOperationException("Method DataSetManager.createLowerCondition() not implemented yet!");
	}
}