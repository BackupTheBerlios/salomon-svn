/*
 * Copyright (C) 2005 Salomon Team
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

import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.database.queries.SQLUpdate;
import salomon.platform.IInfo;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.data.dataset.condition.AbstractCondition;

public final class DataSetInfo implements IInfo
{
	/**
	 * Conditions determinating data set. key is table name, value - list of
	 * conditions corresponding to given table
	 */
	private Set<AbstractCondition> _conditions;

	private int _datasetID;

	private DBManager _dbManager;

	private String _info;

	private String _name;

	private int _solutionID;

	DataSetInfo(DBManager dbManager)
	{
		_dbManager = dbManager;
		_conditions = new HashSet<AbstractCondition>();
	}

	public void addCondition(ICondition condition)
	{
		_conditions.add((AbstractCondition) condition);
	}

	public boolean delete() throws SQLException, ClassNotFoundException
	{
		SQLDelete delete = new SQLDelete();
		// deleting items
		delete.setTable(TABLE_NAME);
		delete.addCondition("dataset_id = ", _datasetID);
		int rows = _dbManager.delete(delete);
		LOGGER.debug("rows deleted: " + rows);
		return (rows > 0);
	}

	public Date getCreationDate() throws PlatformException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getId()
	{
		return _datasetID;
	}

	public String getInfo() throws PlatformException
	{
		return _info;
	}

	public Date getLastModificationDate() throws PlatformException
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the solutionID.
	 */
	public int getSolutionID()
	{
		return _solutionID;
	}

	public void loadItems(ResultSet resultSet) throws SQLException {
		LOGGER.info("DataSetInfo.loadItems(): TODO: ");
		String tableName = resultSet.getString("table_name");
		String condition = resultSet.getString("condition");
		//AbstractCondition condition = facory.createCondition(table, conditon)
		AbstractCondition abstractCondition = null;
		_conditions.add(abstractCondition);
	}
	
	public void load(ResultSet resultSet) throws MalformedURLException,
			SQLException
	{
		// do not load solution_id - it is set while creating dataset
		_datasetID = resultSet.getInt("dataset_id");		
		_name = resultSet.getString("dataset_name");
		_info = resultSet.getString("info");
	}

	/**
	 * @see salomon.platform.IInfo#save()
	 */
	public int save() throws SQLException, ClassNotFoundException
	{
		//removing old items
		SQLDelete delete = new SQLDelete();
		delete.setTable(ITEMS_TABLE_NAME);
		delete.addCondition("dataset_id = ", _datasetID);
		int rows = _dbManager.delete(delete);
		LOGGER.debug("rows deleted: " + rows);

		//saving header
		SQLUpdate update = new SQLUpdate(TABLE_NAME);
		if (_name != null) {
			update.addValue("dataset_name", _name);
		}
		if (_info != null) {
			update.addValue("dataset_info", _info);
		}
		update.addValue("solution_id", _solutionID);
		update.addValue("lm_date", new Date(System.currentTimeMillis()));

		_datasetID = _dbManager.insertOrUpdate(update, "dataset_id",
				_datasetID, GEN_NAME);

		// saving items
		for (AbstractCondition condition : _conditions) {
			SQLInsert insert = new SQLInsert(ITEMS_TABLE_NAME);
			insert.addValue("dataset_id", _datasetID);
			insert.addValue("table_name",
					condition.getColumn().getTable().getName());
			insert.addValue("condition", condition.toSQL());
			LOGGER.debug("insert: " + insert.getQuery());
			_dbManager.insert(insert, "dataset_item_id");
		}

		return _datasetID;
	}

	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	@Override
	public String toString()
	{
		String info = _datasetID + " " + _solutionID; 
		info +=	(_name == null ? "" : _name);
		info += (_name == null ? "" : " " + _info);
		info += _conditions.toString();
		return info;
	}

	/**
	 * @param solutionID The solutionID to set.
	 */
	void setSolutionID(int solutionID)
	{
		_solutionID = solutionID;
	}
	
	public ICondition[] getConditions()
	{
		ICondition[] conditions = new ICondition[_conditions.size()];
		return _conditions.toArray(conditions);
	}

	public static final String ITEMS_TABLE_NAME = "dataset_items";

	public static final String TABLE_NAME = "datasets";

	private static final String GEN_NAME = "gen_dataset_id";

	private static final Logger LOGGER = Logger.getLogger(DataSet.class);

}
