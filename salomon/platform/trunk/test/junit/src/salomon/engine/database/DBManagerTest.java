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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.Config;
import salomon.engine.platform.data.DBMetaData;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;

public class DBManagerTest extends TestCase
{
    private static final String DB_PASSWORD = "masterkey";

    private static final String DB_PATH = "\\db\\salomon.gdb";

    private static final String DB_USER = "SYSDBA";

    // change to "localhost" to test server mode
    private static final String HOSTNAME = "";

    private static final Logger LOGGER = Logger.getLogger(DBManagerTest.class);

    static {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
        Config.readConfiguration();
    }

    private DBManager _manager;

    public DBManagerTest()
    {
        _manager = new DBManager();
    }

    public void testGetDistinctValues() throws Exception
    {
        LOGGER.info("DBManagerTest.testGetDistinctValues()");
        _manager.connect("", DB_PATH, DB_USER, DB_PASSWORD);

        DBMetaData metaData = _manager.getMetaData();
        ITable table = metaData.getTables()[0];
        String[] values = metaData.getDistinctValues(table.getColumns()[2]);

        for (String value : values) {
            LOGGER.info(value);
        }
    }

    public void testGetMetaData() throws Exception
    {
        LOGGER.info("DBManagerTest.testGetMetaData()");
        IMetaData metaData = _manager.getMetaData();

        ITable[] tables = metaData.getTables();
        for (ITable table : tables) {
            LOGGER.debug("table: " + table);
            IColumn[] columns = table.getColumns();
            for (IColumn column : columns) {
                LOGGER.debug(column);
            }
        }
    }

    @Override
    protected void setUp() throws Exception
    {
        _manager.connect(HOSTNAME, DB_PATH, DB_USER, DB_PASSWORD);
    }

    @Override
    protected void tearDown() throws Exception
    {
        _manager.disconnect();
    }
}
