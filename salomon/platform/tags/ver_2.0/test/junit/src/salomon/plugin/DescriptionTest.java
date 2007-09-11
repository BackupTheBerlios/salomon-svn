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

package salomon.plugin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.plugin.PluginInfo;

import junit.framework.TestCase;

/**
 * TODO: add comment.
 */
public class DescriptionTest extends TestCase
{

    /**
     * 
     * @uml.property name="_manager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _manager;

    public void testDelete()
    {
        LOGGER.debug("DescriptionTest.testDelete()");
        boolean success = false;
        //PluginInfo desc = new PluginInfo();
        //desc.setPluginID(40);
        try {
            //desc.delete();
            success = true;
            _manager.commit();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            _manager.rollback();
        }
        assertTrue(success);
    }

    public void testLoad()
    {
        LOGGER.debug("DescriptionTest.testLoad()");
        SQLSelect select = new SQLSelect();
        select.addTable(PluginInfo.TABLE_NAME);
        select.addCondition("plugin_id =", 20);
        ResultSet resultSet = null;
        boolean success = false;
        try {
            resultSet = _manager.select(select);
            assertNotNull(resultSet);
            success = true;
        } catch (SQLException e) {
            LOGGER.fatal("", e);
        }
        assertTrue(success);
        success = false;
        //PluginInfo desc = new PluginInfo();
        try {
            if (resultSet.next()) {
                //desc.load(resultSet);
                success = true;
            } else {
                LOGGER.debug("No data found");
                success = true;
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
        } finally {
            try {
                _manager.closeResultSet(resultSet);
            } catch (SQLException ex) {
                LOGGER.fatal("", ex);
            }
        }
        assertTrue(success);
        //LOGGER.debug(desc);
    }

    public void testSave()
    {
        LOGGER.debug("DescriptionTest.testSave()");
        boolean success = false;
        //PluginInfo desc = new PluginInfo();
        //desc.setName("test_plugin");
        //try {
        //desc.setLocation(new URL("http://www.test_description.pl"));
        //	success = true;
        //} catch (MalformedURLException e) {
        //	LOGGER.fatal("", e);
        //}
        assertTrue(success);
        success = false;
        try {
            //desc.save();
            _manager.commit();
            success = true;
        } catch (Exception e) {
            LOGGER.fatal("", e);
            _manager.rollback();
        }
        assertTrue(success);
    }

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   
        _manager = new DBManager();
        _manager.connect();
    }

    private static final Logger LOGGER = Logger.getLogger(DescriptionTest.class);

}
