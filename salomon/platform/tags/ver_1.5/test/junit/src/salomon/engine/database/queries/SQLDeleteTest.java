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

import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;

public class SQLDeleteTest extends TestCase
{

    /**
     * 
     * @uml.property name="_manager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _manager;

    public void testGetQuery1()
    {
        _logger.debug("SQLDeleteTest.testGetQuery1()");
        SQLDelete delete = new SQLDelete("plugins");
        delete.addCondition("plugin_id =", 5);
        boolean success = false;
        try {
            _manager.rollback();
            _manager.delete(delete);
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
        _logger.debug("SQLDeleteTest.testGetQuery2()");
        SQLDelete delete = new SQLDelete("plugins");
        delete.addCondition("plugin_id in (8, 10)");
        boolean success = false;
        try {
            _manager.delete(delete);
            success = true;
            _manager.commit();
        } catch (SQLException e) {
            success = false;
            e.printStackTrace();
            _manager.rollback();
        }
        assertTrue(success);
    }

    @Override
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$        
        _manager = new DBManager();
        _manager.connect();
    }

    private static Logger _logger = Logger.getLogger(SQLDeleteTest.class);
}
