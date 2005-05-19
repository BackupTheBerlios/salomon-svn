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

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;

public class SQLUpdateTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="_manager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _manager;

    public void testGetQuery1()
    {
        _logger.debug("SQLUpdateTest.testGetQuery1()");
        SQLUpdate update = new SQLUpdate("plugins");
        update.addValue("plugin_name", "Jakis plugin - updated");
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        update.addCondition("plugin_id =", 5);
        boolean success = false;
        try {
            _manager.update(update);
            success = true;
            _manager.commit();
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            _manager.rollback();
        }
        assertTrue(success);
    }

    public void testGetQuery2()
    {
        _logger.debug("SQLUpdateTest.testGetQuery2()");
        SQLUpdate update = new SQLUpdate("plugins");
        update.addValue("plugin_name", "Jakis plugin - updated 2");
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        update.addCondition("plugin_id in (8, 12)");
        boolean success = false;
        try {
            _manager.update(update);
            success = true;
            _manager.commit();
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            _manager.rollback();
        }
        assertTrue(success);
    }
    public void testGetQuery3()
    {
        _logger.debug("SQLUpdateTest.testGetQuery3()");
        SQLUpdate update = new SQLUpdate("tasks");        
        update.addValue("task_name", "Jakis task - updated 2");
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        update.addValue("stop_time", new Time(System.currentTimeMillis()));
        update.addCondition("task_id = ", 3);
        boolean success = false;
        try {
            _manager.update(update);
            success = true;
            _manager.commit();
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            _manager.rollback();
        }
        assertTrue(success);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$        
        _manager = DBManager.getInstance();
    }

    private static Logger _logger = Logger.getLogger(SQLUpdateTest.class);

}
