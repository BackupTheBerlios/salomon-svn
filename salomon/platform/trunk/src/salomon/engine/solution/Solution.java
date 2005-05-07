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

package salomon.engine.solution;

import salomon.engine.database.DBManager;
import salomon.engine.project.IProjectManager;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.DataEngine;
import salomon.engine.platform.ManagerEngine;

/**
 * 
 */
public final class Solution implements ISolution
{

	private DataEngine _dataEngine;

	private ManagerEngine _managerEngine;

	private SolutionInfo _solutionInfo;
	
	private DBManager _dbManager;

	/**
	 * Constructor should be used only by createSolution() in SolutionManager.
	 *  
	 * @param managerEngine
	 */
	protected Solution(ManagerEngine managerEngine, DBManager manager)
	{
		_managerEngine = managerEngine;
		_dbManager = manager;
		//TODO:
		_dataEngine = new DataEngine();
	}

		/**
	 * @see salomon.engine.solution.ISolution#getDataEngine()
	 */
	public IDataEngine getDataEngine() throws PlatformException
	{
		return _dataEngine;
	}

	/**
	 * @see salomon.engine.solution.ISolution#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _managerEngine.getProjectManager();
	}

	public SolutionInfo getInfo()
	{
		return _solutionInfo;
	}

}
