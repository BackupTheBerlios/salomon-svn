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

package salomon.engine.platform.data.dataset.condition;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.solution.ISolution;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;

import salomon.engine.platform.ManagerEngine;
import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.engine.platform.data.dataset.DataSetManager;

abstract class AbstractConditionTest extends TestCase
{
	private AbstractCondition _condition;

	protected abstract AbstractCondition createCondition(IColumn column,
			Object value);

	protected AbstractCondition getCondition()
	{
		return _condition;
	}

	@Override
	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
		ManagerEngine engine = new ManagerEngine();
		ISolution solution = engine.getSolutionManager().getSolutions()[0];
		IDataEngine dataEngine = solution.getDataEngine();
		DataSetManager _dataSetManager = (DataSetManager) dataEngine.getDataSetManager();
		DBManager manager = engine.getDbManager();

		DBTable table = new DBTable("persons");

		IColumn column = new DBColumn(table, "person_id", "INT");
		_condition = createCondition(column, "10");

		LOGGER.info("Connected");
	}

	private static final Logger LOGGER = Logger.getLogger(AbstractConditionTest.class);
}
