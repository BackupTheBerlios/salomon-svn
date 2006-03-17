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

package salomon.engine.platform.data;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.platform.data.IColumn;
import salomon.platform.data.ITable;

public class DBMetaDataTest extends TestCase
{

    /*
     * Test method for 'salomon.engine.platform.data.DBMetaData.getTable(String)'
     */
    public void testGetTable() throws Exception
    {
        DBMetaData metaData = TestObjectFactory.getMetaData();
        ITable table = metaData.getTable("datasets");
        assertNotNull(table);
        table = metaData.getTable("DATASETS");
        assertNotNull(table);
        LOGGER.debug("table: " + table.getName());
        IColumn column = table.getColumn("dataset_id");
        assertNotNull(column);
        column = table.getColumn("DATASET_ID");
        assertNotNull(column);
        LOGGER.debug("column: " + column.getName());
    }

    /*
     * Test method for 'salomon.engine.platform.data.DBMetaData.getTables()'
     */
    public void testGetTables() throws Exception
    {
        DBMetaData metaData = TestObjectFactory.getMetaData();
        ITable[] tables = metaData.getTables();
        for (ITable table : tables) {
            LOGGER.debug("table: " + table.getName());
            IColumn[] columns = table.getColumns();
            for (IColumn column : columns) {
                assertTrue(table.getName().equals(column.getTable().getName()));
                LOGGER.debug("column: " + column.getName() + "("
                        + column.getType() + ")");
            }
        }
    }

    public void testEquals()
    {
        LOGGER.info("DBMetaDataTest.testEquals()");
        DBTable table1 = new DBTable("persons");
        DBColumn column1 = new DBColumn(table1, "id", "INT");

        DBTable table2 = new DBTable("persons");
        DBColumn column2 = new DBColumn(table1, "id", "INT");

        // equals
        assertTrue(column1.equals(column2));
        assertTrue(table1.equals(table2));

        // hashcode
        assertTrue(column1.hashCode() == column2.hashCode());
        assertTrue(table1.hashCode() == table2.hashCode());
    }

    private static final Logger LOGGER = Logger.getLogger(DBMetaDataTest.class);
}
