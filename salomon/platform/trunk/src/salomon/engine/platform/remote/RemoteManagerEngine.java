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

package salomon.engine.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.remote.plugin.IRemotePluginManager;
import salomon.engine.platform.remote.plugin.RemotePluginManager;
import salomon.engine.platform.remote.project.IRemoteProjectManager;
import salomon.engine.platform.remote.project.RemoteProjectManager;
import salomon.engine.platform.remote.task.IRemoteTaskManager;
import salomon.engine.platform.remote.task.RemoteTaskManager;

/**
 * Class representing remote instance of IManagerEngine.
 * 
 * @see salomon.engine.platform.IManagerEngine
 */
public final class RemoteManagerEngine extends UnicastRemoteObject
		implements IRemoteManagerEngine
{
	private IManagerEngine _managerEngine;

	private IRemotePluginManager _remotePluginManager;

	private IRemoteProjectManager _remoteProjectManager;

	private IRemoteTaskManager _remoteTaskManager;

	/**
	 * @throws RemoteException
	 * @pre managerEngine != null
	 * @post $none
	 */
	public RemoteManagerEngine(IManagerEngine managerEngine)
			throws RemoteException
	{
		_remotePluginManager = new RemotePluginManager(
				managerEngine.getPluginManager());
		_remoteProjectManager = new RemoteProjectManager(
				managerEngine.getProjectManager());
		_remoteTaskManager = new RemoteTaskManager(
				managerEngine.getTasksManager());
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
	 * @see IRemoteManagerEngine#getTasksManager()
	 */
	public IRemoteTaskManager getTasksManager() throws RemoteException
	{
		return _remoteTaskManager;
	}
}