/*
 * Created on 2004-05-22
 *
 */

package salomon.core.plugin;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;
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
		Collection result = new ArrayList();
		DBTableName[] tableNames = {new DBTableName(Description.TABLE_NAME)};

		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = DBManager.getInstance().select(null, tableNames, null);
			while (resultSet.next()) {
				Description desc = new Description();
				desc.init(resultSet);
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
			// remving all related tasks
			DBTableName[] tableNames = {new DBTableName("tasks")};
			DBCondition[] conditions = {new DBCondition(new DBColumnName(
					tableNames[0], "plugin_id"), DBCondition.REL_EQ,
					new Integer(description.getPluginID()),
					DBCondition.NUMBERIC)};
			dbManager = DBManager.getInstance();
			dbManager.delete(conditions);
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