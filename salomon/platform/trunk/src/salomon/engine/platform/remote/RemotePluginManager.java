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

import salomon.platform.plugin.IPluginManager;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;

/**
 * Class representing remote instance of IPluginManager.
 */
public final class RemotePluginManager extends UnicastRemoteObject
		implements IRemotePluginManager
{
	private IPluginManager _pluginManager;

	/**
     * @pre pluginManager != null
     * @post $none
     */
	public RemotePluginManager(IPluginManager pluginManager)
			throws RemoteException
	{
		_pluginManager = pluginManager;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see salomon.engine.platform.IRemotePluginManager#getAvailablePlugins()
	 */
	public IPlugin[] getPlugins() throws RemoteException
	{
		return _pluginManager.getPlugins();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemotePluginManager#addPlugin(salomon.plugin.Description)
	 */
	public boolean savePlugin(Description description) throws RemoteException
	{
		return _pluginManager.savePlugin(description);		
	}

	/**
	 * @see salomon.engine.platform.remote.IRemotePluginManager#removePlugin(salomon.plugin.Description)
	 */
	public boolean removePlugin(Description description) throws RemoteException
	{		
		return _pluginManager.removePlugin(description);
	}

	//	/* (non-Javadoc)
	//	 * @see salomon.engine.platform.remote.IRemotePluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IRemotePlugin getPlugin(URL url) throws RemoteException
	//	{
	//		return _pluginManager.get;
	//	}

}