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

package salomon.engine.project;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.plugin.DescriptionTest;

public class ProjectTest extends TestCase
{
	private DBManager _manager;

	public void testDelete()
	{
		LOGGER.debug("ProjectTest.testDelete()");
		boolean success = false;
		Project project = new Project();
		project.setProjectID(2);
		try {
			project.delete();
			success = true;
			// _manager.commit();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			_manager.rollback();
		}
		assertTrue(success);
	}

	public void testLoad()
	{
		LOGGER.debug("ProjectTest.testLoad()");
		boolean success = false;
		SQLSelect select = new SQLSelect();
		select.addTable(Project.TABLE_NAME);
		select.addCondition("project_id =", 3);
		ResultSet resultSet = null;
		try {
			resultSet = _manager.select(select);
			assertNotNull(resultSet);
			success = true;
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		}
		assertTrue(success);
		success = false;

		Project project = new Project();
		try {
			if (resultSet.next()) {
				project.load(resultSet);
				success = true;
			} else {
				LOGGER.debug("No data found");
				success = true;
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException ex) {
				LOGGER.fatal("", ex);
			}
		}
		assertTrue(success);
		LOGGER.debug(project);
	}

	public void testSave()
	{
		LOGGER.debug("ProjectTest.testSave()");
		boolean success = false;
		Project project = new Project();
		project.setName("test_project");
		try {
			project.save();
			_manager.commit();
			success = true;
		} catch (Exception e) {
			LOGGER.fatal("", e);
			_manager.rollback();
		}
		assertTrue(success);
	}

	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   
        
        try {
            DBManager.getInstance();
        } catch (SQLException e) {
            LOGGER.fatal("", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("", e);
        }
        
		_manager = DBManager.getInstance();
	}

	private static Logger LOGGER = Logger.getLogger(DescriptionTest.class);

}