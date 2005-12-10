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

import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.TestObjectFactory;
import salomon.engine.database.DBManager;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
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

	//TODO: commented out until DataSet is implemented

	/*
	 * Class under test for ResultSet selectData(SQLSelect)
	 */
	//	public void testSelectDataSQLSelect1()
	//	{
	//		LOGGER.info("DataSetTest.testSelectDataSQLSelect1()");
	//		boolean success = false;
	//
	//		DataSet dataSet = 
	//		dataSet.addCondition("project", "project_id > 10");
	//		dataSet.addCondition("project", "project_name <> 'project_1'");
	//		dataSet.addCondition("task", "task_name  'task1'");
	//		SQLSelect select = new SQLSelect();
	//		select.addTable("projects");
	//		select.addTable("tasks");
	//		select.addCondition("task_id >", 10);
	//		select.addCondition("task_name =", "task2");
	//
	//		String selectBefore = select.getQuery();
	//		LOGGER.debug("select: " + selectBefore);
	//		try {
	//			ResultSet result = dataSet.selectData(select);
	//            result.close();
	//            _manager.rollback();
	//			ResultSet result2 = dataSet.selectData(select);	
	//			result2.close();
	//            
	//			success = true;
	//		} catch (SQLException e) {
	//			LOGGER.fatal("", e);
	//		} catch (ClassNotFoundException e) {
	//			LOGGER.fatal("", e);
	//		}
	//		_manager.rollback();
	//		assertTrue(success);
	//		String selectAfter = select.getQuery();
	//		LOGGER.debug("select: " + selectAfter);
	//		assertEquals(selectBefore, selectAfter);			
	//	}
	//	
	//
	//	public void testSelectDataSQLSelect2()
	//	{
	//		LOGGER.info("DataSetTest.testSelectDataSQLSelect2()");
	//		boolean success = false;
	//
	//		DataSet dataSet = new DataSet("testName");
	//		dataSet.addCondition("projects p", "p.project_id > 10");
	//		dataSet.addCondition("projects p", "p.project_name <> 'project_1'");
	//
	//		SQLSelect select = new SQLSelect();
	//		select.addTable("projects");
	//		select.addTable("tasks");
	//		select.addCondition("task_id >", 10);
	//		select.addCondition("task_name =", "task2");
	//
	//		String selectBefore = select.getQuery();
	//		LOGGER.debug("select: " + selectBefore);
	//		try {
	//			ResultSet result = dataSet.selectData(select);
	//			result.close();			
	//			success = true;			
	//		} catch (SQLException e) {
	//			LOGGER.fatal("", e);
	//		} catch (ClassNotFoundException e) {
	//			LOGGER.fatal("", e);
	//		}
	//		_manager.rollback();
	//		assertTrue(success);
	//		String selectAfter = select.getQuery();
	//		LOGGER.debug("select: " + selectAfter);
	//		assertEquals(selectBefore, selectAfter);
	//	}
	//
	//	public void testSelectDataSQLSelect3()
	//	{
	//		LOGGER.info("DataSetTest.testSelectDataSQLSelect2()");
	//		boolean success = false;
	//
	//		DataSet dataSet = new DataSet("testName");
	//
	//		SQLSelect select = new SQLSelect();
	//		select.addTable("projects");
	//		select.addTable("tasks");
	//		select.addCondition("task_id >", 10);
	//		select.addCondition("task_name =", "task2");
	//
	//		String selectBefore = select.getQuery();
	//		LOGGER.debug("select: " + selectBefore);
	//		try {
	//			ResultSet result = dataSet.selectData(select);
	//			result.close();
	//			success = true;
	//		} catch (SQLException e) {
	//			LOGGER.fatal("", e);
	//		} catch (ClassNotFoundException e) {
	//			LOGGER.fatal("", e);
	//		}
	//		_manager.rollback();
	//		assertTrue(success);
	//		String selectAfter = select.getQuery();
	//		LOGGER.debug("select: " + selectAfter);
	//		assertEquals(selectBefore, selectAfter);
	//	}
	//
	/*
	 * Class under test for ResultSet selectData(String)
	 */
	public void testSave1() throws PlatformException, SQLException, ClassNotFoundException
	{
		LOGGER.info("DataSetTest.testSave1()");
		DataSet dataSet = (DataSet) _dataSetManager.createEmpty();
		((DataSetInfo) dataSet.getInfo()).setName("first");

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				new Integer(10)));
		column = new DBColumn(table, "first_name", "VARCHAR");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				"Nikodem"));
		column = new DBColumn(table, "last_name", "VARCHAR");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				"Jura"));

		dataSet.getInfo().save();

		_manager.commit();
	}

	public void testDelete() throws PlatformException, SQLException,
			ClassNotFoundException
	{
		LOGGER.info("DataSetTest.testDelete()");
		DataSet dataSet = (DataSet) _dataSetManager.getAll()[0];
		assertNotNull(dataSet);
		dataSet.getInfo().delete();
		_manager.rollback();

	}

	public void testLoad() throws PlatformException, SQLException, ClassNotFoundException
	{
		LOGGER.info("DataSetTest.testLoad()");
		
		DataSet dataSet = (DataSet) _dataSetManager.createEmpty();
		((DataSetInfo) dataSet.getInfo()).setName("second");

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				new Integer(10)));
		column = new DBColumn(table, "first_name", "VARCHAR");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				"Nikodem"));
		column = new DBColumn(table, "last_name", "VARCHAR");
		dataSet.addCondition(_dataSetManager.createEqualsCondition(column,
				"Jura"));

		final int dataSetID = dataSet.getInfo().save();		
		
		IDataSet loadedDataSet = _dataSetManager.getDataSet(new IUniqueId() {
			public int getId()
			{
				return dataSetID;
			}

		});
		assertNotNull(loadedDataSet);
		LOGGER.info(loadedDataSet.getInfo());
	}

	//	
	//	public void testSave2()
	//	{
	//		LOGGER.info("DataSetTest.testSave2()");
	//		boolean success = false;
	//		DataSet dataSet = new DataSet("testName2");
	//		dataSet.addCondition("projects", "project_id > 10");
	//		dataSet.addCondition("projects", "project_name <> 'project_1'");
	//		dataSet.addCondition("tasks", "task_id = 2");
	//		try {
	//			dataSet.save();
	//			success = true;
	//		} catch (SQLException e) {
	//			LOGGER.fatal("", e);
	//		} catch (ClassNotFoundException e) {
	//			LOGGER.fatal("", e);
	//		}
	//		_manager.rollback();
	//		assertTrue(success);
	//	}
	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		ISolution solution = TestObjectFactory.getSolution("Persons");
		IDataEngine dataEngine = solution.getDataEngine();
		_dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		_manager = TestObjectFactory.getDbManager();
		LOGGER.info("Connected");
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetTest.class);

}
