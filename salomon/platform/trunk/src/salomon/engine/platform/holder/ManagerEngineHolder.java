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

package salomon.engine.platform.holder;

import salomon.engine.plugin.IPluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.task.ITaskManager;

import salomon.engine.platform.IManagerEngine;

/**
 * Holds all other holders.
 * It manges with swithing between connected clients.
 */
public final class ManagerEngineHolder implements IManagerEngine
{
	private IManagerEngine _currentManagerEngine;

	private PluginManagerHolder _pluginManagerHolder;

	private ProjectManagerHolder _projectManagerHolder;

	private SolutionManagerHolder _solutionManagerHolder;

	private TaskManagerHolder _taskManagerHolder;

	/**
	 * 
	 */
	public ManagerEngineHolder(IManagerEngine managerEngine)
	{
		_currentManagerEngine = managerEngine;
		_pluginManagerHolder = new PluginManagerHolder(
				_currentManagerEngine.getPluginManager());
		_projectManagerHolder = new ProjectManagerHolder(
				_currentManagerEngine.getProjectManager());
		_taskManagerHolder = new TaskManagerHolder(
				_currentManagerEngine.getTasksManager());
		_solutionManagerHolder = new SolutionManagerHolder(
				_currentManagerEngine.getSolutionManager());
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManagerHolder;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManagerHolder;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getTasksManager()
	 */
	public ITaskManager getTasksManager()
	{
		return _taskManagerHolder;
	}

	/**
	 * 
	 * TODO: add comment.
	 * @param managerEngine
	 */
	public void setCurrentManager(IManagerEngine managerEngine)
	{
		_currentManagerEngine = managerEngine;
		_pluginManagerHolder.setCurrent(_currentManagerEngine.getPluginManager());
		_projectManagerHolder.setCurrent(_currentManagerEngine.getProjectManager());
		_taskManagerHolder.setCurrent(_currentManagerEngine.getTasksManager());
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getSolutionManager()
	 */
	public ISolutionManager getSolutionManager()
	{
		return _solutionManagerHolder;
	}
}