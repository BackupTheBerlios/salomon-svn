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

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;

/**
 * Class manager available plugins.
 */
public final class PluginManager implements IPluginManager
{
	/**
	 * Returns collection of plugin descriptions
	 * 
	 * @return plugin descriptions
	 */
	public IPlugin[] getPlugins()
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
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		} catch (MalformedURLException e) {
			LOGGER.fatal("", e);
		}
		//FIXME
		throw new UnsupportedOperationException(
				"Method union() not implemented yet!");
	}

	/**
	 * Removes from data base information about given plugin.
	 * 
	 * @param description description of plugin to remove
	 * @return true if successfully removed, false otherwise 
	 */
	public boolean removePlugin(Description description)
	{
		boolean result = false;
		DBManager dbManager = null;
		try {
			// removing all related tasks
			//TODO: change to Task.TABLE_NAME
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
	public boolean savePlugin(Description description)
	{
		boolean result = false;
		try {
			description.save();
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

}