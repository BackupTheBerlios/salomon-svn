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
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.engine.remote.plugin.IRemotePluginManager;
import salomon.engine.remote.plugin.RemotePluginManager;
import salomon.engine.remote.project.IRemoteProjectManager;
import salomon.engine.remote.project.RemoteProjectManager;
import salomon.engine.remote.solution.IRemoteSolutionManager;
import salomon.engine.remote.solution.RemoteSolutionManager;
import salomon.engine.remote.task.IRemoteTaskManager;
import salomon.engine.remote.task.RemoteTaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * Class representing remote instance of IManagerEngine.
 * 
 * @see salomon.engine.platform.IManagerEngine
 */
public final class RemoteManagerEngine extends UnicastRemoteObject
		implements IRemoteManagerEngine
{
	private IRemotePluginManager _remotePluginManager;

	private IRemoteProjectManager _remoteProjectManager;

	private IRemoteSolutionManager _remoteSolutionManager;

	private IRemoteTaskManager _remoteTaskManager;

	/**
	 * @throws RemoteException
	 * @pre managerEngine != null
	 * @post $none
	 */
	public RemoteManagerEngine(IManagerEngine managerEngine)
			throws RemoteException
	{
		try {
			_remotePluginManager = new RemotePluginManager(
					managerEngine.getPluginManager());
			_remoteProjectManager = new RemoteProjectManager(
					managerEngine.getProjectManager());
			_remoteTaskManager = new RemoteTaskManager(
					managerEngine.getTasksManager());
			_remoteSolutionManager = new RemoteSolutionManager(
					managerEngine.getSolutionManager());
		} catch (PlatformException e) {
			LOGGER.error("", e);
		}
	}

	/**
	 * @see IRemoteManagerEngine#getPluginManager()
	 */
	public IRemotePluginManager getPluginManager() throws RemoteException
	{
		return _remotePluginManager;
	}

	/**
	 * @see IRemoteManagerEngine#getProjectManager()
	 */
	public IRemoteProjectManager getProjectManager() throws RemoteException
	{
		return _remoteProjectManager;
	}

	/**
	 * @see IRemoteManagerEngine#getSolutionManager()
	 */
	public IRemoteSolutionManager getSolutionManager() throws RemoteException
	{
		return _remoteSolutionManager;
	}

	/**
	 * @see IRemoteManagerEngine#getTasksManager()
	 */
	public IRemoteTaskManager getTasksManager() throws RemoteException
	{
		return _remoteTaskManager;
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteManagerEngine.class);

}