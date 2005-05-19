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

package salomon.engine.plugin;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

/** * Class manager available plugins. */
public final class PluginManager implements IPluginManager
{

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	private LinkedList<ILocalPlugin> _plugins;

	private void loadPlugins() throws PlatformException
	{
		SQLSelect select = new SQLSelect();
		select.addTable(PluginInfo.TABLE_NAME);
		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(select);
			while (resultSet.next()) {
				LocalPlugin localPlugin = this.createPlugin();
				localPlugin.getInfo().load(resultSet);
				//this.addPlugin(localPlugin);
				_plugins.add(localPlugin);
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	public PluginManager(DBManager manager)
	{
		_dbManager = manager;
		_plugins = new LinkedList<ILocalPlugin>();
	}

	/**
	 * @throws PlatformException 
	 * @see salomon.engine.plugin.IPluginManager#addPlugin(salomon.plugin.IPlugin)
	 */
	public void addPlugin(ILocalPlugin plugin) throws PlatformException
	{
		try {
			plugin.getInfo().save();
			_plugins.add(plugin);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @throws PlatformException 
	 * @see salomon.engine.plugin.IPluginManager#createPlugin()
	 */
	public LocalPlugin createPlugin() throws PlatformException
	{
		LocalPlugin localPlugin = new LocalPlugin(_dbManager);
		return localPlugin;
	}

	public ILocalPlugin getPlugin(IUniqueId id) throws PlatformException
	{
		LocalPlugin localPlugin = null;

		if (_plugins.isEmpty()) {
			loadPlugins();
		}
		// if plugin exists in collection, then it is returned
		for (ILocalPlugin plugin : _plugins) {
			if (plugin.getInfo().getId() == id.getId()) {
				localPlugin = (LocalPlugin) plugin;
				break;
			}
		}
		return localPlugin;
	}

	/**
	 * Returns collection of LocalPlugins
	 * 
	 * @return plugin descriptions
	 */
	public ILocalPlugin[] getPlugins() throws PlatformException
	{
		if (_plugins.isEmpty()) {
			loadPlugins();
		}

		ILocalPlugin[] plugins = null;
		if (!_plugins.isEmpty()) {
			plugins = new LocalPlugin[_plugins.size()];
			int i = 0;
			for (ILocalPlugin plugin : _plugins) {
				plugins[i] = plugin;
				i++;
			}
		}
		return plugins;
	}

	/**
	 * Removes from data base information about given plugin.
	 * 
	 * @param plugin to remove
	 * @return true if successfully removed, false otherwise
	 */
	public boolean removePlugin(ILocalPlugin plugin)
	{
		boolean result = false;
		try {
			// removing all related tasks
			PluginInfo pluginInfo = ((LocalPlugin) plugin).getInfo();
			// TODO: change to Task.TABLE_NAME
			SQLDelete delete = new SQLDelete("tasks");
			delete.addCondition("plugin_id =", pluginInfo.getPluginID());

			_dbManager.delete(delete);
			// removing plugin
			pluginInfo.delete();
			_plugins.remove(plugin);
			result = true;
			_dbManager.commit();
			LOGGER.info("Plugin successfully deleted.");
		} catch (Exception e) {
			_dbManager.rollback();
			LOGGER.fatal("", e);
		}
		return result;
	}

	/**
	 * Saves plugin in database.
	 * 
	 * @return true if successfully saved, false otherwise
	 */
	public boolean savePlugin(ILocalPlugin plugin)
	{
		boolean result = false;
		try {
			plugin.getInfo().save();
			_dbManager.commit();
			result = true;
			LOGGER.info("Plugin successfully saved.");
		} catch (Exception e) {
			LOGGER.fatal("", e);
		}
		return result;
	}

	private static final Logger LOGGER = Logger.getLogger(PluginLoader.class);

	public boolean removeAll() throws PlatformException
	{
		throw new UnsupportedOperationException("Method removeAll() not implemented yet!");
	}

	public void clearPluginList() throws PlatformException
	{
		_plugins.clear();
	}

    public DBManager getDBManager() {
        return _dbManager;
    }
}