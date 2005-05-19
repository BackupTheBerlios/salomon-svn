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

import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.IPluginManager;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;

/** * Holds pluginManager of current client. * It is used by ManagerEngineHolder to switch between connected clients. */
final class PluginManagerHolder implements IPluginManager
{

	/**
	 * 
	 * @uml.property name="_currentPluginManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IPluginManager _currentPluginManager;

	PluginManagerHolder(IPluginManager pluginManager)
	{
		_currentPluginManager = pluginManager;
	}

	/**
	 * @throws PlatformException 
	 * @see salomon.engine.plugin.IPluginManager#addPlugin(salomon.plugin.IPlugin)
	 */
	public void addPlugin(ILocalPlugin plugin) throws PlatformException
	{
		_currentPluginManager.addPlugin(plugin);
	}

	/**
	 * @throws PlatformException 
	 * @see salomon.engine.plugin.IPluginManager#createPlugin()
	 */
	public ILocalPlugin createPlugin() throws PlatformException
	{
		return _currentPluginManager.createPlugin();
	}

	/**
	 * @see IPluginManager#getPlugins()
	 */
	public ILocalPlugin[] getPlugins() throws PlatformException
	{
		return _currentPluginManager.getPlugins();
	}

	/**
	 * @see IPluginManager#removePlugin(IPlugin)
	 */
	public boolean removePlugin(ILocalPlugin description)
			throws PlatformException
	{
		return _currentPluginManager.removePlugin(description);
	}

	/**
	 * @see IPluginManager#savePlugin(IPlugin)
	 */
	public boolean savePlugin(ILocalPlugin plugin) throws PlatformException
	{
		return _currentPluginManager.savePlugin(plugin);
	}

	/**
	 * @pre pluginManager != null
	 * @post $none
	 */
	void setCurrent(IPluginManager pluginManager)
	{
		_currentPluginManager = pluginManager;
	}

	public ILocalPlugin getPlugin(IUniqueId id) throws PlatformException
	{
		return _currentPluginManager.getPlugin(id);
	}

	public boolean removeAll() throws PlatformException
	{
		return _currentPluginManager.removeAll();
	}

	public void clearPluginList() throws PlatformException
	{
		_currentPluginManager.clearPluginList();
	}

}