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
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;

/**
 * 
 */
public class DataSetTest extends TestCase
{
	DBManager _manager;
	/*
	 * Class under test for ResultSet selectData(SQLSelect)
	 */
	public void testSelectDataSQLSelect1()
	{
		LOGGER.info("DataSetTest.testSelectDataSQLSelect1()");
		boolean success = false;

		DataSet dataSet = new DataSet("testName");
		dataSet.addCondition("project", "project_id > 10");
		dataSet.addCondition("project", "project_name <> 'project_1'");
		dataSet.addCondition("task", "task_name  'task1'");
		SQLSelect select = new SQLSelect();
		select.addTable("projects");
		select.addTable("tasks");
		select.addCondition("task_id >", 10);
		select.addCondition("task_name =", "task2");

		String selectBefore = select.getQuery();
		LOGGER.debug("select: " + selectBefore);
		try {
			ResultSet result = dataSet.selectData(select);
            result.close();
            _manager.rollback();
			ResultSet result2 = dataSet.selectData(select);	
			result2.close();
            
			success = true;
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
		_manager.rollback();
		assertTrue(success);
		String selectAfter = select.getQuery();
		LOGGER.debug("select: " + selectAfter);
		assertEquals(selectBefore, selectAfter);			
	}
	

	public void testSelectDataSQLSelect2()
	{
		LOGGER.info("DataSetTest.testSelectDataSQLSelect2()");
		boolean success = false;

		DataSet dataSet = new DataSet("testName");
		dataSet.addCondition("projects p", "p.project_id > 10");
		dataSet.addCondition("projects p", "p.project_name <> 'project_1'");

		SQLSelect select = new SQLSelect();
		select.addTable("projects");
		select.addTable("tasks");
		select.addCondition("task_id >", 10);
		select.addCondition("task_name =", "task2");

		String selectBefore = select.getQuery();
		LOGGER.debug("select: " + selectBefore);
		try {
			ResultSet result = dataSet.selectData(select);
			result.close();			
			success = true;			
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
		_manager.rollback();
		assertTrue(success);
		String selectAfter = select.getQuery();
		LOGGER.debug("select: " + selectAfter);
		assertEquals(selectBefore, selectAfter);
	}

	public void testSelectDataSQLSelect3()
	{
		LOGGER.info("DataSetTest.testSelectDataSQLSelect2()");
		boolean success = false;

		DataSet dataSet = new DataSet("testName");

		SQLSelect select = new SQLSelect();
		select.addTable("projects");
		select.addTable("tasks");
		select.addCondition("task_id >", 10);
		select.addCondition("task_name =", "task2");

		String selectBefore = select.getQuery();
		LOGGER.debug("select: " + selectBefore);
		try {
			ResultSet result = dataSet.selectData(select);
			result.close();
			success = true;
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
		_manager.rollback();
		assertTrue(success);
		String selectAfter = select.getQuery();
		LOGGER.debug("select: " + selectAfter);
		assertEquals(selectBefore, selectAfter);
	}

	/*
	 * Class under test for ResultSet selectData(String)
	 */
	public void testSave1()
	{
		LOGGER.info("DataSetTest.testSave1()");
		boolean success = false;
		DataSet dataSet = new DataSet("testName");
		dataSet.addCondition("projects", "project_id > 10");
		dataSet.addCondition("projects", "project_name <> 'project_1'");
		try {
			dataSet.save();
			success = true;
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
		_manager.rollback();
		assertTrue(success);
	}
	
	public void testSave2()
	{
		LOGGER.info("DataSetTest.testSave2()");
		boolean success = false;
		DataSet dataSet = new DataSet("testName2");
		dataSet.addCondition("projects", "project_id > 10");
		dataSet.addCondition("projects", "project_name <> 'project_1'");
		dataSet.addCondition("tasks", "task_id = 2");
		try {
			dataSet.save();
			success = true;
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
		_manager.rollback();
		assertTrue(success);
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   
		_manager = DBManager.getInstance(); 
	}
	private static final Logger LOGGER = Logger.getLogger(DataSetTest.class);

}
