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

package salomon.engine.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.queries.SQLSelect;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.util.gui.Utils;

public class DBManagerTest extends TestCase
{

	private DBManager manager;

	private DBManager personManager;

	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
		manager = new DBManager();
		personManager = new DBManager();
	}

	public void testConnect()
	{
		LOGGER.info("DBManagerTest.testConnect()");
		try {
			manager.connect("", "\\db\\salomon.gdb", "sysdba", "masterkey");
			LOGGER.info("CONNECTED");
			
			SQLSelect select = new SQLSelect();
			select.addTable("projects");			
			ResultSet resultSet = manager.select(select);
			Utils.getDataFromResultSet(resultSet);			
			resultSet.close();			
			
			personManager.connect("", "\\db\\persons.gdb", "sysdba",
					"masterkey");
			LOGGER.info("CONNECTED to PERSONS");
			select = new SQLSelect();
			select.addTable("persons");
			//resultSet = personManager.select(select);
			resultSet = personManager.select(select);
			Utils.getDataFromResultSet(resultSet);
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		} finally {
			try {
				manager.disconnect();
			} catch (SQLException e) {
				LOGGER.fatal("", e);
			}
			try {
				personManager.disconnect();
			} catch (SQLException e) {
				LOGGER.fatal("", e);
			}
		}
	}
	
	public void testGetMetaData()
	{
		LOGGER.info("DBManagerTest.testGetMetaData()");
		try {
			manager.connect("", "\\db\\salomon.gdb", "sysdba", "masterkey");
			LOGGER.info("CONNECTED");
			
			IMetaData metaData = manager.getMetaData();
			
			ITable[] tables = metaData.getTables();
			for (ITable table : tables) {
				LOGGER.debug("table: " + table);
				IColumn[] columns = table.getColumns();
				for (int i = 0; i < columns.length; ++i) {
					LOGGER.debug(columns[i]);
				}
			}
			
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		} finally {
			try {
				manager.disconnect();
			} catch (SQLException e) {
				LOGGER.fatal("", e);
			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(DBManagerTest.class);
}
