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

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
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

	public IDataSet createEmpty() throws PlatformException
	{
		IDataSet dataSet = new DataSet(_dbManager, _externalDBManager);
		return dataSet;
	}

	public ICondition createEqualsCondition(IColumn column, Object value)
			throws PlatformException
	{
		return new EqualsCondition(column, value);
	}

	public ICondition createGreaterCondition() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method DataSetManager.createGreaterCondition() not implemented yet!");
	}

	public ICondition createLowerCondition() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method DataSetManager.createLowerCondition() not implemented yet!");
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

}