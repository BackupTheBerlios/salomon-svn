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

public class SQLInsertTest extends TestCase
{
    //    public void testGetQuery1()
    //    {
    //        boolean success = false;
    //
    //        SQLInsert insert = new SQLInsert("plugins");
    //        insert.addValue("plugin_name", "plugin_testowy");
    //        insert.addValue("location", "http://www.gdzies.pl");
    //        insert.addValue("info", "max id + 1");
    //        
    //        try {
    //            _manager.insert(insert, "plugin_id");
    //            success = true;
    //            _manager.commit();
    //        } catch (SQLException e) {
    //            e.printStackTrace();
    //            success = false;
    //            _manager.rollback();
    //        }
    //        assertTrue(success);
    //    }
    /*
     public void testGetQuery2()
     {
     _logger.debug("SQLInsertTest.testGetQuery2()");
     boolean success = false;

     SQLInsert insert = new SQLInsert("plugins");
     insert.addValue("plugin_name", "plugin_testowy");
     insert.addValue("location", "http://www.gdzies.pl");
     insert.addValue("info", "id z generatora");
     try {
     _manager.rollback() ;
     _manager.insert(insert, "plugin_id", "gen_plugin_id");
     success = true;
     _manager.commit();
     } catch (SQLException e) {
     e.printStackTrace();
     success = false;
     _manager.rollback();
     }
     assertTrue(success);
     }
     *//*
     public void testGetQuery3()
     {
     _logger.debug("SQLInsertTest.testGetQuery3()");
     boolean success = false;

     SQLInsert insert = new SQLInsert("plugins");
     insert.addValue("plugin_name", "plugin_testowy");
     insert.addValue("location", "http://www.gdzies.pl");
     insert.addValue("info", "id z generatora");
     insert.addValue("lm_date", new Date(System.currentTimeMillis()));

     try {
     _manager.insert(insert, "plugin_id", "gen_plugin_id");
     success = true;
     _manager.commit();
     } catch (SQLException e) {
     e.printStackTrace();
     success = false;
     _manager.rollback();
     }
     assertTrue(success);
     }
     *//*
     public void testGetQuery4()
     {
     _logger.debug("SQLInsertTest.testGetQuery4()");
     boolean success = false;

     SQLInsert insert = new SQLInsert("plugins");
     insert.addValue("plugin_name", "plugin_testowy");
     insert.addValue("location", "http://www.gdzies.pl");
     insert.addValue("info", "id z generatora");
     insert.addValue("lm_date", new Timestamp(System.currentTimeMillis()));

     try {
     _manager.insert(insert, "plugin_id", "gen_plugin_id");
     success = true;
     _manager.commit();
     } catch (SQLException e) {
     e.printStackTrace();
     success = false;
     _manager.rollback();
     }
     assertTrue(success);
     }
     */
    /*
     * @see TestCase#setUp()
     */
    public void testGetQuery5() throws Exception, SQLException
    {

    }

    @Override
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$      
        //_manager = DBManager.getInstance();
    }

    private static Logger _logger = Logger.getLogger(SQLInsertTest.class);

}
