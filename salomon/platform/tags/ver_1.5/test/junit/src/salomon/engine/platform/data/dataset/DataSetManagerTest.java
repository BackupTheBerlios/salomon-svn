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

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.database.DBManager;
import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

public class DataSetManagerTest extends TestCase
{
    private static final Logger LOGGER = Logger.getLogger(DataSetManagerTest.class);

    private static final String SOLUTION_NAME = "Example";

    private static final String TABLE_NAME = "candidates";

    private DataSetManager _dataSetManager;

    private DBManager _dbManager;

    public DataSetManagerTest() throws PlatformException
    {
        ISolution solution = TestObjectFactory.getSolution(SOLUTION_NAME);
        IDataEngine dataEngine = solution.getDataEngine();
        _dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
        _dbManager = TestObjectFactory.getDbManager();
        LOGGER.info("Connected");
    }

    public void testAdd() throws PlatformException
    {
        LOGGER.info("DataSetManagerTest.testAdd()");
        DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

        DBTable table = new DBTable(TABLE_NAME);
        // column type is not important
        IColumn column = new DBColumn(table, "id", "INT");
        ICondition[] conditions = new ICondition[3];
        conditions[0] = _dataSetManager.createEqualsCondition(column,
                new Integer(10));
        column = new DBColumn(table, "age", "INT");
        conditions[1] = _dataSetManager.createLowerCondition(column, 25);
        column = new DBColumn(table, "sex", "VARCHAR");
        conditions[2] = _dataSetManager.createEqualsCondition(column, "m");
        IDataSet dataSet = mainDataSet.createSubset(conditions);
        ((DataSetInfo) ((DataSet) dataSet).getInfo()).setName("First");
        _dataSetManager.add(dataSet);
        // e.g. to test adding dataset with the same name twice
        _dbManager.commit();
    }

    public void testGetAll() throws PlatformException
    {
        LOGGER.info("DataSetManagerTest.testGetAll()");
        IDataSet[] dataSets;
        dataSets = _dataSetManager.getAll();
        for (IDataSet dataSet : dataSets) {
            LOGGER.info(((DataSet) dataSet).getInfo());
        }
    }

    public void testGetDataSet() throws PlatformException
    {
        DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

        DBTable table = new DBTable(TABLE_NAME);
        // column type is not important
        IColumn column = new DBColumn(table, "id", "INT");
        ICondition[] conditions = new ICondition[3];
        conditions[0] = _dataSetManager.createEqualsCondition(column,
                new Integer(10));
        column = new DBColumn(table, "age", "INT");
        conditions[1] = _dataSetManager.createLowerCondition(column, "20");
        column = new DBColumn(table, "sex", "VARCHAR");
        conditions[2] = _dataSetManager.createEqualsCondition(column, "m");
        IDataSet dataSet = mainDataSet.createSubset(conditions);
        ((DataSetInfo) ((DataSet) dataSet).getInfo()).setName("Second");

        final int dataSetID = ((DataSet) dataSet).getInfo().save();

        IDataSet loadedDataSet = _dataSetManager.getDataSet(dataSetID);
        assertNotNull(loadedDataSet);
        LOGGER.info(((DataSet) loadedDataSet).getInfo());

        loadedDataSet = _dataSetManager.getDataSet("Second");
        assertNotNull(loadedDataSet);
        LOGGER.info(((DataSet) loadedDataSet).getInfo());
        _dataSetManager.remove(loadedDataSet);
    }

    public void testRemove() throws PlatformException
    {
        LOGGER.info("DataSetManagerTest.testRemove()");
        DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();

        DBTable table = new DBTable(TABLE_NAME);
        // column type is not important
        IColumn column = new DBColumn(table, "id", "INT");
        ICondition[] conditions = new ICondition[1];
        conditions[0] = _dataSetManager.createEqualsCondition(column,
                new Integer(10));
        IDataSet dataSet = mainDataSet.createSubset(conditions);
        ((DataSetInfo) ((DataSet) dataSet).getInfo()).setName("To remove");
        _dataSetManager.add(dataSet);
        _dataSetManager.remove(dataSet);
    }
}
