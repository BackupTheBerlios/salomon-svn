/*
 * Created on 2004-05-21
 *
 */

package salomon.core.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.plugin.IPlugin;

/**
 * @author nico
 * 
 * Class is responsible for dynamic plugin loading.
 */
public class PluginLoader
{
	private static Logger _logger = Logger.getLogger(PluginLoader.class);

	public static IPlugin loadPlugin(File pluginFile) throws Exception
	{
		IPlugin plugin = null;
		// creating class loader
		PluginClassLoader classLoader = new PluginLoader().new PluginClassLoader(
				pluginFile);
		// loading appropriate plugin (it has to be in *.jar file
		plugin = (IPlugin) classLoader.findMainClass().newInstance();
		return plugin;
	}
	
	public static IPlugin loadPlugin(String pluginFileName) throws Exception
	{
		return loadPlugin(new File(pluginFileName));
	}

	class PluginClassLoader extends URLClassLoader
	{
		private File _file = null;

		PluginClassLoader(File file) throws MalformedURLException
		{
			super(new URL[]{file.toURL()});
			_file = file;
		}

		public Class findMainClass() throws ClassNotFoundException, IOException
		{
			Class foundClass = null;
			_logger.debug("file: " + _file.getAbsolutePath());
			JarFile jarFile = new JarFile(_file);
			// reading manifest to find main class
			String className = jarFile.getManifest().getMainAttributes()
					.getValue("Main-Class");
			_logger.info("Looking for class: " + className);
			foundClass = super.findClass(className);
			_logger.info("Found class: " + foundClass);
			return foundClass;
		}
	}
}