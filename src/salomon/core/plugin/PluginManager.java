/*
 * Created on 2004-05-22
 *
 */

package salomon.core.plugin;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBTableName;

/**
 * @author nico Class manager available plugins.
 */
public final class PluginManager implements IPluginManager
{

	public Collection getAvailablePlugins()
	{
		Collection result = new ArrayList();
		DBTableName[] tableNames = {new DBTableName("plugins")};
		DBColumnName[] columnNames = {new DBColumnName(tableNames[0],
				"location")};
		// executing query
		ResultSet resultSet = null;
		//TODO: change it
		Collection stringCollection = new ArrayList();
		try {
			resultSet = DBManager.getInstance().select(columnNames, tableNames,
					null);
			while (resultSet.next()) {
				String location = resultSet.getString("location");
				_logger.info("plugin: " + location);
				if (!stringCollection.contains(location)) {
					stringCollection.add(location);
					result.add(new URL(location));
				}

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

	private static Logger _logger = Logger.getLogger(PluginLoader.class);
}