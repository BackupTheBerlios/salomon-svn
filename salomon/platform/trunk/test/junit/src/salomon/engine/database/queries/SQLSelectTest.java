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

package salomon.engine.database.queries;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.util.gui.Utils;

public class SQLSelectTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="_manager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _manager;

    public void testGetQuery1()
    {
        _logger.debug("SQLSelectTest.testGetQuery1()");
        boolean success = false;

        SQLSelect sel = new SQLSelect();
        sel.addTable("tasks");
        
        try {
            
            ResultSet resultSet = _manager.select(sel);
            assertNotNull(resultSet);
            resultSet.close();
            success = true;        
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        _manager.rollback() ;
        assertTrue(success);
    }

    public void testGetQuery2()
    {
        _logger.debug("SQLSelectTest.testGetQuery2()");
        boolean success = false;

        SQLSelect sel = new SQLSelect();
        sel.addColumn("t.task_id");
        sel.addColumn("t.task_name");
        sel.addTable("tasks t");
        sel.addTable("plugins p");
        sel.addCondition("t.task_name =", "task testowy");
        sel.addCondition("p.plugin_name =", "SQLConsole");
        sel.addCondition("t.plugin_id = p.plugin_id");
        sel.addCondition("task_name =", "Jacks' task''");

        try {
            
            ResultSet resultSet = _manager.select(sel);
            assertNotNull(resultSet);
            resultSet.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        
        _manager.rollback() ;
        assertTrue(success);
    }

    public void testGetQuery3()
    {
        _logger.debug("SQLSelectTest.testGetQuery3()");
        boolean success = false;

        SQLSelect sel = new SQLSelect();
        sel.addTable("tasks");
        sel.addCondition("task_id >", 10);
        try {
            ResultSet resultSet = _manager.select(sel);
            assertNotNull(resultSet);            
            resultSet.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        _manager.rollback() ;
        assertTrue(success);
    }
    
    public void testGetQuery4()
    {
        _logger.debug("SQLSelectTest.testGetQuery4()");
        boolean success = false;

        SQLSelect sel = new SQLSelect();
        sel.addColumn("plugin_id id");
        sel.addColumn("plugin_name name");        
        sel.addTable("plugins");        
        try {
            ResultSet resultSet = _manager.select(sel);
            assertNotNull(resultSet);
            // just to print results
            Utils.getDataFromResultSet(resultSet);            
            resultSet.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }
        assertTrue(success);
    }

    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$      
        _manager = new DBManager();
        _manager.connect() ;
    }

    private static Logger _logger = Logger.getLogger(SQLSelectTest.class);

}
