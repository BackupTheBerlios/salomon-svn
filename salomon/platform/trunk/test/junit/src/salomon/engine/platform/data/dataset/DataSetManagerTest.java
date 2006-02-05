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
import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

public class DataSetManagerTest extends TestCase
{

	private DataSetManager _dataSetManager;
	
	public DataSetManagerTest() throws PlatformException
	{
		ISolution solution = TestObjectFactory.getSolution("Persons");
		IDataEngine dataEngine = solution.getDataEngine();
		_dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		LOGGER.info("Connected");		
	}


	public void testGetAll() throws PlatformException
	{
		LOGGER.info("DataSetManagerTest.testGetAll()");
		IDataSet[] dataSets;
		dataSets = _dataSetManager.getAll();
		for (IDataSet dataSet : dataSets) {
			LOGGER.info(dataSet.getInfo());
		}
	}

	public void testAdd() throws PlatformException
	{
		LOGGER.info("DataSetManagerTest.testAdd()");
		DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();		

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		ICondition[] conditions = new ICondition[3];
		conditions[0] = _dataSetManager.createEqualsCondition(column,
				new Integer(10));
		column = new DBColumn(table, "first_name", "VARCHAR");
		conditions[1] = _dataSetManager.createEqualsCondition(column,
				"Nikodem");
		column = new DBColumn(table, "last_name", "VARCHAR");
		conditions[2] = _dataSetManager.createEqualsCondition(column,
				"Jura");
		IDataSet dataSet = mainDataSet.createSubset(conditions);
		((DataSetInfo) dataSet.getInfo()).setName("test add");
		_dataSetManager.add(dataSet);
	}
	
	public void testRemove() throws PlatformException
	{
		LOGGER.info("DataSetManagerTest.testRemove()");
		DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();
		

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		ICondition[] conditions = new ICondition[1];
		conditions[0] =_dataSetManager.createEqualsCondition(column,
				new Integer(10));
		IDataSet dataSet = mainDataSet.createSubset(conditions);
		((DataSetInfo) dataSet.getInfo()).setName("test remove");
		_dataSetManager.add(dataSet);
		_dataSetManager.remove(dataSet);
	}
	
	
	public void testGetDataSet() throws PlatformException
	{
		DataSet mainDataSet = (DataSet) _dataSetManager.getMainDataSet();			

		DBTable table = new DBTable("persons");
		// column type is not important
		IColumn column = new DBColumn(table, "id", "INT");
		ICondition[] conditions = new ICondition[3];
		conditions[0] = _dataSetManager.createEqualsCondition(column,
				new Integer(10));
		column = new DBColumn(table, "first_name", "VARCHAR");
		conditions[1] = _dataSetManager.createEqualsCondition(column,
				"Nikodem");
		column = new DBColumn(table, "last_name", "VARCHAR");
		conditions[2] = _dataSetManager.createEqualsCondition(column,
				"Jura");
		IDataSet dataSet = mainDataSet.createSubset(conditions);
		((DataSetInfo) dataSet.getInfo()).setName("second");
		
		final int dataSetID = dataSet.getInfo().save();		
		
		IDataSet loadedDataSet = _dataSetManager.getDataSet(new IUniqueId() {
			public int getId()
			{
				return dataSetID;
			}

		});
		assertNotNull(loadedDataSet);
		LOGGER.info(loadedDataSet.getInfo());
		
		loadedDataSet = _dataSetManager.getDataSet("second");
		assertNotNull(loadedDataSet);
		LOGGER.info(loadedDataSet.getInfo());
		
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetManagerTest.class);

}
