/*
 * Created on 2004-05-21
 *
 */

package salomon.core.plugin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.plugin.IPlugin;

/**
 * @author nico
 * 
 * Class is responsible for dynamic plugin loading.
 */
public final class PluginLoader
{
	private static Logger _logger = Logger.getLogger(PluginLoader.class);

	private static Map _pluginsLoaded = new HashMap();

	public static IPlugin loadPlugin(URL url) throws Exception
	{
		IPlugin plugin = null;
		// creating class loader
		PluginClassLoader classLoader = new PluginLoader().new PluginClassLoader(
				url);
		// loading appropriate plugin (it has to be in *.jar file
		plugin = (IPlugin) classLoader.findMainClass().newInstance();
		//TODO:
		plugin.getDescription().setLocation(url);
		_pluginsLoaded.put(plugin, url);
		return plugin;
	}

	public static URL getPluginLocation(IPlugin plugin)
	{
		return (URL) _pluginsLoaded.get(plugin);
	}

	final class PluginClassLoader extends URLClassLoader
	{
		private URL _url = null;

		PluginClassLoader(URL url) throws MalformedURLException
		{
			super(new URL[]{url});
			_url = url;
		}

		//work around
		public Class findMainClass() throws ClassNotFoundException, IOException
		{
			Class foundClass = null;
			_logger.debug("file: " + _url);
			//JarFile jarFile = new JarFile();
			// reading manifest to find main class
			String className = null;
			if (_url.toString().contains("Average")) {
				className = "pl.edu.agh.icsr.salomon.plugin.averageprice.AveragePrice";
			} else if (_url.toString().contains("Simple")) {
				className = "pl.edu.agh.icsr.salomon.plugin.simpleconsole.SimpleSQLConsole";
			}
			//String className =
			// jarFile.getManifest().getMainAttributes().getValue(
			//"Main-Class");
			_logger.info("Looking for class: " + className);
			foundClass = super.findClass(className);
			_logger.info("Found class: " + foundClass);
			return foundClass;
		}
	}
}