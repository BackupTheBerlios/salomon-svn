/*
 * Created on 2004-05-22
 *
 */

package salomon.platform.plugin;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.platform.data.DBManager;
import salomon.platform.data.common.SQLDelete;
import salomon.platform.data.common.SQLSelect;
import salomon.plugin.Description;

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
	public Collection getAvailablePlugins()
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
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		} catch (MalformedURLException e) {
			_logger.fatal("", e);
		}
		return result;
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
			_logger.info("Plugin successfully deleted.");
		} catch (SQLException e) {
			dbManager.rollback();
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			dbManager.rollback();
			_logger.fatal("", e);
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
			_logger.info("Plugin successfully saved.");
		} catch (SQLException e) {
			_logger.fatal("", e);
		} catch (ClassNotFoundException e) {
			_logger.fatal("", e);
		}
		return result;
	}

	private static Logger _logger = Logger.getLogger(PluginLoader.class);

}