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

package salomon.engine.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.plugin.IPluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.remote.plugin.PluginManagerProxy;
import salomon.engine.remote.project.ProjectManagerProxy;
import salomon.engine.remote.solution.SolutionManagerProxy;
import salomon.engine.remote.task.TaskManagerProxy;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.task.ITaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * Class is a sever side wrapper of IRemoteManagerEngine object. It implements
 * IManagerEngine interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 * 
 * @see salomon.engine.remote.IRemoteManagerEngine
 */
public final class ManagerEngineProxy implements IManagerEngine
{
	private IPluginManager _pluginManager;

	private IProjectManager _projectManager;

	private ISolutionManager _solutionManager;

	private ITaskManager _taskManager;

	public ManagerEngineProxy(IRemoteManagerEngine remoteManagerEngine)
	{
		try {
			_taskManager = new TaskManagerProxy(
					remoteManagerEngine.getTasksManager());
			_projectManager = new ProjectManagerProxy(
					remoteManagerEngine.getProjectManager());
			_pluginManager = new PluginManagerProxy(
					remoteManagerEngine.getPluginManager());
			_solutionManager = new SolutionManagerProxy(
					remoteManagerEngine.getSolutionManager());
		} catch (PlatformException e) {
			LOGGER.error("", e);
		} catch (RemoteException e) {
			LOGGER.error("", e);
		}

	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManager;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManager;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getSolutionManager()
	 */
	public ISolutionManager getSolutionManager()
	{
		return _solutionManager;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getTasksManager()
	 */
	public ITaskManager getTasksManager()
	{
		return _taskManager;
	}

	private static final Logger LOGGER = Logger.getLogger(ManagerEngineProxy.class);

}