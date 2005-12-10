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
import org.apache.log4j.PropertyConfigurator;

import salomon.TestObjectFactory;
import salomon.engine.database.DBManager;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.IUniqueId;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

public class DataSetManagerTest extends TestCase
{

	private DataSetManager _dataSetManager;

	protected void setUp() throws Exception
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

	public void testGetDataSet() throws PlatformException
	{
		LOGGER.info("DataSetManagerTest.testGetDataSet()");
		IDataSet dataSet = _dataSetManager.getDataSet(new IUniqueId() {
			public int getId()
			{
				return 13;
			}
		});
		LOGGER.info(dataSet.getInfo());
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetManagerTest.class);

}
