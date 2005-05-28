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

package salomon.engine.holder;

import org.apache.log4j.Logger;

import salomon.engine.plugin.IPluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.task.ITaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * Holds all other holders.
 * It manges with swithing between connected clients.
 */
public final class ManagerEngineHolder implements IManagerEngine
{

	/**
	 * 
	 * @uml.property name="_currentManagerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _currentManagerEngine;

	/**
	 * 
	 * @uml.property name="_pluginManagerHolder"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginManagerHolder _pluginManagerHolder;

	/**
	 * 
	 * @uml.property name="_projectManagerHolder"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ProjectManagerHolder _projectManagerHolder;

	/**
	 * 
	 * @uml.property name="_solutionManagerHolder"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SolutionManagerHolder _solutionManagerHolder;

	/**
	 * 
	 * @uml.property name="_taskManagerHolder"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskManagerHolder _taskManagerHolder;

	/**
	 * 
	 */
	public ManagerEngineHolder(IManagerEngine managerEngine)
	{
		_currentManagerEngine = managerEngine;
		try {
			_pluginManagerHolder = new PluginManagerHolder(
					_currentManagerEngine.getPluginManager());
			_projectManagerHolder = new ProjectManagerHolder(
					_currentManagerEngine.getProjectManager());
			_taskManagerHolder = new TaskManagerHolder(
					_currentManagerEngine.getTasksManager());
			_solutionManagerHolder = new SolutionManagerHolder(
					_currentManagerEngine.getSolutionManager());
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
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
	 * @see salomon.engine.platform.IManagerEngine#getSolutionManager()
	 */
	public ISolutionManager getSolutionManager()
	{
		return _solutionManagerHolder;
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
		try {
			_pluginManagerHolder.setCurrent(_currentManagerEngine.getPluginManager());
			_projectManagerHolder.setCurrent(_currentManagerEngine.getProjectManager());
			_taskManagerHolder.setCurrent(_currentManagerEngine.getTasksManager());
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(ManagerEngineHolder.class);
}