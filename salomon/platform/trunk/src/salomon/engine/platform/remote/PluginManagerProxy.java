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

import org.apache.log4j.Logger;

import salomon.platform.plugin.IPluginManager;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;

/**
 * Class is a sever side wrapper of IRemotePluginManager object. It implements
 * IPluginManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 *  
 */
public final class PluginManagerProxy implements IPluginManager
{
	private IRemotePluginManager _remotePluginManager;

	/**
	 * @pre $none
	 * @post $none
	 */
	public PluginManagerProxy(IRemotePluginManager remotePluginManager)
	{
		_remotePluginManager = remotePluginManager;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.plugin.IPluginManager#getPlugins()
	 */
	public IPlugin[] getPlugins()
	{
		IPlugin[] result = null;
		try {
			result = _remotePluginManager.getPlugins();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
        
		return result;
	}

	private static final Logger LOGGER = Logger.getLogger(PluginManagerProxy.class);

	/**
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.plugin.IPluginManager#addPlugin(salomon.plugin.Description)
	 */
	public boolean savePlugin(Description description)
	{
		boolean result = false;
		try {
			result = _remotePluginManager.savePlugin(description);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.plugin.IPluginManager#removePlugin(salomon.plugin.Description)
	 */
	public boolean removePlugin(Description description)
	{

		boolean result = false;
		try {
			result = _remotePluginManager.removePlugin(description);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return result;
	}

	//	/* (non-Javadoc)
	//	 * @see salomon.engine.platform.plugin.IPluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IPlugin getPlugin(URL url)
	//	{
	//		return PluginLoader.loadPlugin(_remotePluginManager.getPlugin();
	//	}

}