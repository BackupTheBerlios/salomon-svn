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
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.dataset.condition.AbstractCondition;
import salomon.engine.platform.data.dataset.condition.AndCondition;
import salomon.engine.platform.data.dataset.condition.EqualsCondition;
import salomon.engine.platform.data.dataset.condition.GreaterCondition;
import salomon.engine.platform.data.dataset.condition.LowerCondition;
import salomon.engine.platform.data.dataset.condition.OrCondition;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Class manages with datasets.
 */
public final class DataSetManager implements IDataSetManager
{
	private DBManager _dbManager;

	private ExternalDBManager _externalDBManager;

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

	public ICondition createAndCondition(ICondition condition,
			ICondition... conditions) throws PlatformException
	{
		return new AndCondition((AbstractCondition) condition,
				(AbstractCondition[]) conditions);
	}

	//TODO: rename
	public IDataSet createEmpty() throws PlatformException
	{
		IDataSet dataSet = new DataSet(_dbManager, _externalDBManager);
		((DataSetInfo) dataSet.getInfo()).setSolutionID(_solutionInfo.getId());
		return dataSet;
	}

	public ICondition createEqualsCondition(IColumn column, Object value)
			throws PlatformException
	{
		return new EqualsCondition(column, value);
	}

	public ICondition createGreaterCondition(IColumn column, Object value)
			throws PlatformException
	{
		return new GreaterCondition(column, value);
	}

	public ICondition createLowerCondition(IColumn column, Object value)
			throws PlatformException
	{
		return new LowerCondition(column, value);
	}

	public ICondition createOrCondition(ICondition condition,
			ICondition... conditions) throws PlatformException
	{
		return new OrCondition((AbstractCondition) condition,
				(AbstractCondition[]) conditions);
	}

	/**
	 * @see salomon.platform.data.dataset.IDataSetManager#getAll()
	 */
	public IDataSet[] getAll() throws PlatformException
	{
		List<IDataSet> dataSets = new LinkedList<IDataSet>();

		SQLSelect select = this.getDataSetSelect();

		ResultSet resultSet;
		try {
			resultSet = _dbManager.select(select);

			while (resultSet.next()) {
				IDataSet dataSet = this.createEmpty();
				dataSet.getInfo().load(resultSet);
				dataSets.add(dataSet);
			}
			resultSet.close();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e);
		}
		LOGGER.info("DataSet list successfully loaded.");
		IDataSet[] dataSetsArray = new IDataSet[dataSets.size()];
		return dataSets.toArray(dataSetsArray);
	}

	public IDataSet getDataSet(IUniqueId id) throws PlatformException
	{
		IDataSet dataSet = this.createEmpty();

		// selecting DataSet header
		SQLSelect dataSetSelect = new SQLSelect();
		dataSetSelect.addTable(DataSetInfo.TABLE_NAME);
		dataSetSelect.addColumn("dataset_id");
		dataSetSelect.addColumn("dataset_name");
		dataSetSelect.addColumn("info");
		dataSetSelect.addCondition("dataset_id =", id.getId());
		// to ensure solution_id consistency
		dataSetSelect.addCondition("solution_id =",
				((DataSetInfo) dataSet.getInfo()).getSolutionID());

		ResultSet resultSet = null;
		try {
			DataSetInfo dataSetInfo = (DataSetInfo) dataSet.getInfo();

			resultSet = _dbManager.select(dataSetSelect);
			resultSet.next();
			// loading data set header
			dataSetInfo.load(resultSet);

			// one row should be found, if found more, the first is got
			if (resultSet.next()) {
				LOGGER.warn("TOO MANY ROWS");
			}
			resultSet.close();

			// loading items
			SQLSelect dataSetItemsSelect = new SQLSelect();
			dataSetItemsSelect.addTable(DataSetInfo.ITEMS_TABLE_NAME);
			dataSetItemsSelect.addColumn("dataset_id");
			dataSetItemsSelect.addColumn("table_name");
			dataSetItemsSelect.addColumn("condition");
			dataSetItemsSelect.addCondition("dataset_id =", id.getId());

			resultSet = _dbManager.select(dataSetItemsSelect);
			while (resultSet.next()) {
				dataSetInfo.loadItems(resultSet);
			}
			resultSet.close();

		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		}

		LOGGER.info("DataSet successfully loaded.");

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

	private SQLSelect getDataSetItemsSelect(int id)
	{
		SQLSelect select = new SQLSelect();
		select.addTable(DataSetInfo.ITEMS_TABLE_NAME);
		select.addColumn("table_name");
		select.addColumn("condition");
		return select;
	}

	private SQLSelect getDataSetSelect()
	{
		SQLSelect select = new SQLSelect();
		select.addTable(DataSetInfo.TABLE_NAME + " d");
		select.addTable(DataSetInfo.ITEMS_TABLE_NAME + " di");
		select.addColumn("d.dataset_id");
		select.addColumn("d.dataset_name");
		select.addColumn("d.info");
		select.addColumn("di.table_name");
		select.addColumn("di.condition");
		select.addCondition("d.dataset_id = di.dataset_id");
		select.addOrderBy("d.dataset_id");
		return select;
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetManager.class);

}