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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

/**
 * 
 * !!! IMPORTANT !!!
 * 
 * DO NOT SORT MEMBERS
 * 
 * STATIC INITIALIZERS MUST STAY IN CORRECT ORDER
 */
public class DataSetTest extends TestCase
{
	private static final Logger LOGGER;	
	
	static {
		LOGGER = Logger.getLogger(DataSetTest.class);
		try {			
			init();
		} catch (Exception e) {
			LOGGER.fatal("", e);
		}
	}

	public void testDelete() throws PlatformException
	{
		LOGGER.info("DataSetTest.testDelete()");
		DataSet dataSet = (DataSet) _dataSetManager.getAll()[0];
		assertNotNull(dataSet);
		dataSet.getInfo().delete();
		_manager.rollback();
	}

	public void testIntersection() throws PlatformException, SQLException
	{
		LOGGER.info("DataSetTest.testIntersection()");

		IDataSet intersestion = _dataSet1.intersection(_dataSet2);
		ICondition[] cond = intersestion.getConditions();

		Set<ICondition> intersectionConditions = new HashSet<ICondition>();
		intersectionConditions.add(_cond3ds2);

		assertTrue(compareCondtitions(cond, intersectionConditions));
	}

	public void testMinus() throws PlatformException, SQLException
	{
		LOGGER.info("DataSetTest.testMinus()");

		IDataSet minus = _dataSet1.minus(_dataSet2);
		ICondition[] cond = minus.getConditions();

		Set<ICondition> minusConditions = new HashSet<ICondition>();
		minusConditions.add(_cond1ds1);
		minusConditions.add(_cond2ds1);

		assertTrue(compareCondtitions(cond, minusConditions));
	}

	public void testSave1() throws PlatformException
	{
		LOGGER.info("DataSetTest.testSave1()");
		
			
		String dataSetName = "test_" + System.currentTimeMillis(); 
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

		((DataSetInfo) dataSet.getInfo()).setName(dataSetName);
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
		ITable table = _metaData.getTable("persons");
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

	public void testUnion() throws PlatformException, SQLException
	{
		LOGGER.info("DataSetTest.testUnion()");

		IDataSet union = _dataSet1.union(_dataSet2);
		ICondition[] cond = union.getConditions();

		Set<ICondition> unionConditions = new HashSet<ICondition>();
		unionConditions.add(_cond1ds1);
		unionConditions.add(_cond2ds1);
		unionConditions.add(_cond3ds1);
		unionConditions.add(_cond1ds2);
		unionConditions.add(_cond2ds2);
		unionConditions.add(_cond3ds2);

		assertTrue(compareCondtitions(cond, unionConditions));
	}

	private boolean compareCondtitions(ICondition[] dataSetCond,
			Set<ICondition> expected)
	{
		boolean result = false;
		Set<ICondition> conditions = new HashSet<ICondition>();
		conditions.addAll(Arrays.asList(dataSetCond));

		// they must have the same size
		if (conditions.size() == expected.size()) {
			conditions.removeAll(expected);
			// and the same elements
			result = (conditions.size() == 0);
		}
		return result;
	}

	private static void init() throws Exception
	{
		LOGGER.info("DataSetTest.init()");
		ISolution solution = TestObjectFactory.getSolution("Persons");
		IDataEngine dataEngine = solution.getDataEngine();
		_metaData = dataEngine.getMetaData();
		_dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		_manager = TestObjectFactory.getDbManager();
		LOGGER.info("Connected");
		initCondtitions();
		prepareTestData();
	}

	private static void initCondtitions() throws PlatformException
	{
		LOGGER.info("DataSetTest.initCondtitions()");
		

		// dataset 1
		ITable table = _metaData.getTable("persons");
		IColumn[] columns = new IColumn[4];
		columns[0] = table.getColumn("id");
		columns[1] = table.getColumn("first_name");
		columns[2] = table.getColumn("email");
		columns[3] = table.getColumn("last_name");

		// id < 3
		_cond1ds1 = _dataSetManager.createLowerCondition(columns[0],
				new Integer(3));
		// first_name = 'Nikodem'
		_cond2ds1 = _dataSetManager.createEqualsCondition(columns[1], "Nikodem");
		// last_name = 'Jura'
		_cond3ds1 = _dataSetManager.createEqualsCondition(columns[3], "Jura");

		// dataset 2
		// id < 10
		_cond1ds2 = _dataSetManager.createLowerCondition(columns[0],
				new Integer(10));
		// email = 'ernie'
		_cond2ds2 = _dataSetManager.createEqualsCondition(columns[2], "ernie");
		// last_name = 'Jura'
		_cond3ds2 = _dataSetManager.createEqualsCondition(columns[3], "Jura");
	}

	private static void prepareTestData() throws PlatformException,
			SQLException
	{
		LOGGER.info("DataSetTest.prepareTestData()");
		String dataSetName = "operations1";
		SQLSelect select = new SQLSelect();
		select.addColumn("dataset_id");
		select.addTable(DataSetInfo.TABLE_NAME);
		select.addCondition("dataset_name =", dataSetName);
		// if not exists, then adding test data set
		if (!_manager.existsSelect(select)) {
			LOGGER.debug("Adding test dataset:" + dataSetName);
			DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

			ICondition[] conditions = new ICondition[3];
			conditions[0] = _cond1ds1;
			conditions[1] = _cond2ds1;
			conditions[2] = _cond3ds1;
			// column type is not important
			_dataSet1 = mainDataSet.createSubset(conditions);
			((DataSetInfo) _dataSet1.getInfo()).setName(dataSetName);
			_dataSetManager.add(_dataSet1);
			_manager.commit();
		} else {
			ResultSet resultSet = _manager.select(select);
			assertTrue(resultSet.next());
			final int dataSetID = resultSet.getInt("dataset_id");
			_dataSet1 = _dataSetManager.getDataSet(new IUniqueId() {
				public int getId()
				{
					return dataSetID;
				}
			});
		}

		dataSetName = "operations2";
		select = new SQLSelect();
		select.addColumn("dataset_id");
		select.addTable(DataSetInfo.TABLE_NAME);
		select.addCondition("dataset_name =", dataSetName);
		// if not exists, then adding test data set
		if (!_manager.existsSelect(select)) {
			LOGGER.debug("Adding test dataset:" + dataSetName);
			DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

			ICondition[] conditions = new ICondition[3];
			conditions[0] = _cond1ds2;
			conditions[1] = _cond2ds2;
			conditions[2] = _cond3ds2;
			// column type is not important
			_dataSet2 = mainDataSet.createSubset(conditions);
			((DataSetInfo) _dataSet2.getInfo()).setName(dataSetName);
			_dataSetManager.add(_dataSet2);
		} else {
			ResultSet resultSet = _manager.select(select);
			assertTrue(resultSet.next());
			final int dataSetID = resultSet.getInt("dataset_id");
			_dataSet2 = _dataSetManager.getDataSet(new IUniqueId() {
				public int getId()
				{
					return dataSetID;
				}
			});
		}
	}

	private static ICondition _cond1ds1;

	private static ICondition _cond1ds2;

	private static ICondition _cond2ds1;

	private static ICondition _cond2ds2;

	private static ICondition _cond3ds1;

	private static ICondition _cond3ds2;

	private static IDataSet _dataSet1;

	private static IDataSet _dataSet2;
	
	private static IMetaData _metaData;

	/**
	 * 
	 * @uml.property name="_manager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private static DataSetManager _dataSetManager;

	private static DBManager _manager;

}
