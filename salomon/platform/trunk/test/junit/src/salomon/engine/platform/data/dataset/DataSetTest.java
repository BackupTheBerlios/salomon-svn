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

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

public class DataSetTest extends TestCase
{
	/**
	 * 
	 * @uml.property name="_manager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	DataSetManager _dataSetManager;

	DBManager _manager;

	public DataSetTest() throws Exception
	{
		ISolution solution = TestObjectFactory.getSolution("Persons");
		IDataEngine dataEngine = solution.getDataEngine();
		_dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		_manager = TestObjectFactory.getDbManager();
		LOGGER.info("Connected");
	}

	public void testDelete() throws PlatformException
	{
		LOGGER.info("DataSetTest.testDelete()");
		DataSet dataSet = (DataSet) _dataSetManager.getAll()[0];
		assertNotNull(dataSet);
		dataSet.getInfo().delete();
		_manager.rollback();

	}

	public void testSave1() throws PlatformException
	{
		LOGGER.info("DataSetTest.testSave1()");
		DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		ICondition[] condition = new ICondition[3];
		condition[0] = _dataSetManager.createEqualsCondition(column,
				new Integer(10));
		column = new DBColumn(table, "first_name", "VARCHAR");
		condition[1] = _dataSetManager.createEqualsCondition(column, "Nikodem");
		column = new DBColumn(table, "last_name", "VARCHAR");
		condition[2] = _dataSetManager.createEqualsCondition(column, "Jura");

		IDataSet dataSet = mainDataSet.createSubset(condition);

		((DataSetInfo) dataSet.getInfo()).setName("first");
		dataSet.getInfo().save();

		_manager.commit();
	}

	public void testSelect() throws SQLException, PlatformException
	{
		LOGGER.info("DataSetTest.testSelect()");
		SQLSelect select = new SQLSelect();
		select.addTable(DataSetInfo.TABLE_NAME);
		select.addCondition("dataset_name =", "test select");

		// if not exists, then adding test data set
		if (!_manager.existsSelect(select)) {
			LOGGER.debug("Adding test dataset");
			DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

			DBTable table = new DBTable("persons");
			// column type is not important
			IColumn column = new DBColumn(table, "id", "INT");
			ICondition[] conditions = new ICondition[1];
			conditions[0] = _dataSetManager.createLowerCondition(column,
					new Integer(4));
			
			IDataSet dataSet = mainDataSet.createSubset(conditions);
			((DataSetInfo) dataSet.getInfo()).setName("test select");
			_dataSetManager.add(dataSet);
		}

		ResultSet resultSet = _manager.select(select);
		assertTrue(resultSet.next());

		final int dataSetID = resultSet.getInt("dataset_id");
		resultSet.close();

		IDataSet dataSet = _dataSetManager.getDataSet(new IUniqueId() {
			public int getId()
			{
				return dataSetID;
			}
		});

		IData data = dataSet.selectData(null, null);

		while (data.next()) {
			Object[] dataArray = data.getData();
			StringBuilder dataString = new StringBuilder();
			for (Object d : dataArray) {
				dataString.append("|");
				dataString.append(d);
			}
			LOGGER.debug(dataString);
		}
		data.close();

		// adding some conditions
		IMetaData metaData = TestObjectFactory.getSolution("Persons").getDataEngine().getMetaData();
		ITable table = metaData.getTable("persons");
		IColumn[] columns = new IColumn[2];
		columns[0] = table.getColumn("first_name");
		columns[1] = table.getColumn("email");

		IColumn idColumn = table.getColumn("id");

		ICondition[] conditions = new ICondition[1];
		conditions[0] = _dataSetManager.createLowerCondition(idColumn,
				new Integer(3));

		data = dataSet.selectData(columns, conditions);

		while (data.next()) {
			Object[] dataArray = data.getData();
			StringBuilder dataString = new StringBuilder();
			for (Object d : dataArray) {
				dataString.append("|");
				dataString.append(d);
			}
			LOGGER.debug(dataString);
		}
		data.close();

	}
	
	public void testUnion()
	{
		
	}
	
	public void testIntersection()
	{
		
	}
	
	public void testMinus()
	{
		
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetTest.class);

}
