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

	private ManagerEngine _engine;

	private DBManager _manager;

	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
		_engine = new ManagerEngine();
		ISolution solution = _engine.getSolutionManager().getSolutions()[0];
		IDataEngine dataEngine = solution.getDataEngine();
		_dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		_manager = _engine.getDbManager();
		LOGGER.info("Connected");
	}

	public void testGetAll()
	{
		LOGGER.info("DataSetManagerTest.testGetAll()");
		boolean success = false;
		IDataSet[] dataSets;
		try {
			dataSets = _dataSetManager.getAll();
			for (IDataSet dataSet : dataSets) {
				LOGGER.info(dataSet.getInfo());
			}
			success = true;
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}

		assertTrue(success);
	}

	public void testGetDataSet()
	{
		LOGGER.info("DataSetManagerTest.testGetDataSet()");
		boolean success = false;
		try {
			IDataSet dataSet = _dataSetManager.getDataSet(new IUniqueId() {
				public int getId()
				{
					return 12;
				}
			});
			LOGGER.info(dataSet.getInfo());
			success = true;
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
		assertTrue(success);
	}

	private static final Logger LOGGER = Logger.getLogger(DataSetManagerTest.class);

}
