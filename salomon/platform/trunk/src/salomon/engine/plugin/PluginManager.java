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
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;

import salomon.platform.exception.PlatformException;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;

/**
 * Class manager available plugins.
 */
public final class PluginManager implements IPluginManager
{
	/**
	 * Returns collection of LocalPlugins
	 * 
	 * @return plugin descriptions
	 */
	public LocalPlugin[] getPlugins() throws PlatformException
	{
		Collection<Description> result = new LinkedList<Description>();
		SQLSelect select = new SQLSelect();
		select.addTable(Description.TABLE_NAME);
		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = DBManager.getInstance().select(select);
			while (resultSet.next()) {
				Description desc = new Description();
				desc.load(resultSet);
				result.add(desc);
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		LocalPlugin[] plugins = null;
		if (!result.isEmpty()) {
			plugins = new LocalPlugin[result.size()];
			int i = 0;
			for (Description desc : result) {
                plugins[i] = this.createPlugin();
				plugins[i].setDescription(desc);
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
	public boolean removePlugin(IPlugin plugin)
	{
		boolean result = false;
		DBManager dbManager = null;
		try {
			// removing all related tasks
			Description description = plugin.getDescription();
			// TODO: change to Task.TABLE_NAME
			SQLDelete delete = new SQLDelete("tasks");
			delete.addCondition("plugin_id =", description.getPluginID());

			dbManager = DBManager.getInstance();
			dbManager.delete(delete);
			// removing plugin
			description.delete();
			result = true;
			dbManager.commit();
			LOGGER.info("Plugin successfully deleted.");
		} catch (SQLException e) {
			dbManager.rollback();
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			dbManager.rollback();
			LOGGER.fatal("", e);
		}

		return result;
	}

	/**
	 * Saves plugin in database.
	 * 
	 * @return true if successfully saved, false otherwise
	 */
	public boolean savePlugin(IPlugin plugin)
	{
		boolean result = false;
		try {
			plugin.getDescription().save();
			DBManager.getInstance().commit();
			result = true;
			LOGGER.info("Plugin successfully saved.");
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}

		return result;
	}

	private static final Logger LOGGER = Logger.getLogger(PluginLoader.class);

	/**
	 * @see salomon.engine.plugin.IPluginManager#createPlugin()
	 */
	public LocalPlugin createPlugin()
	{
		return new LocalPlugin();
	}

	/**
	 * @see salomon.engine.plugin.IPluginManager#addPlugin(salomon.plugin.IPlugin)
	 */
	public void addPlugin(IPlugin plugin)
	{
		throw new UnsupportedOperationException("Method addPlugin() not implemented yet!");
	}

}