/*
 * Created on 2004-05-22
 *
 */

package salomon.core.plugin;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.plugin.IPlugin;

/**
 * @author nico Class manager available plugins.
 */
public class PluginManager
{
	private static Logger _logger = Logger.getLogger(PluginLoader.class);

	private static String _pluginsDir = Config.getString("PLUGINS_DIR");

	public File[] getAvailablePlugins()
	{
		File dir = new File(_pluginsDir);
		_logger.info("looking for plugins in: " + dir.getAbsolutePath());
		File[] plugins = dir.listFiles(new PluginFileFilter("jar"));
		_logger.info(Arrays.asList(plugins));
		return plugins;
	}

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