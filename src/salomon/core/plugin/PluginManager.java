/*
 * Created on 2004-05-22
 *
 */

package salomon.core.plugin;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBTableName;

/**
 * @author nico Class manager available plugins.
 */
public final class PluginManager implements IPluginManager
{
	private static Logger _logger = Logger.getLogger(PluginLoader.class);

	private static String _pluginsDir = Config.getString("PLUGINS_DIR");

	public Collection getAvailablePlugins()
	{
		Collection result = new ArrayList();
		//		File dir = new File(_pluginsDir);
		//		_logger.info("looking for plugins in: " + dir.getAbsolutePath());
		//		File[] plugins = dir.listFiles(new PluginFileFilter("jar"));
		//        for (int i = 0; i < plugins.length; i++) {
		//			try {
		//				result.add(plugins[i].toURL());
		//			} catch (MalformedURLException e) {
		//				_logger.fatal("", e);
		//			}
		//		}
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

	//    /* (non-Javadoc)
	//	 * @see salomon.core.plugin.IPluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IPlugin getPlugin(URL url)
	//	{
	//		PluginLoader.loadPlugin(url);
	//	}

	class PluginFileFilter implements FileFilter
	{
		private String extension;

		private String description;

		public PluginFileFilter()
		{
			super();
		}

		public PluginFileFilter(String extension)
		{
			this();
			this.extension = extension;
		}

		public boolean accept(File file)
		{
			if (file != null) {
				if (file.isDirectory()) {
					return false;
				}
				String extension = getExtension(file);
				if (extension != null) {
					return this.extension.equalsIgnoreCase(extension);
				}
			}
			return false;
		}

		/** @return rozszerzenie pliku (lub null gdy go nie ma) */
		private String getExtension(File f)
		{
			if (f != null) {
				String filename = f.getName();
				int i = filename.lastIndexOf('.');
				if (i > 0 && i < filename.length() - 1) {
					return filename.substring(i + 1).toLowerCase();
				}
			}
			return null;
		}
	}
}