
package salomon.core.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * Class represents class loader used by application to load plugins. It has to
 * be used to download plugins from remote locations and create an instance of
 * given plugin. This intstance will be used to execute task.
 *  
 */
final class PluginClassLoader extends URLClassLoader
{
	PluginClassLoader() throws MalformedURLException
	{
		super(new URL[0]);
	}

	//work around
	public Class findMainClass(File file) throws ClassNotFoundException,
			IOException, URISyntaxException
	{
		Class foundClass = null;
		_logger.debug("file: " + file);
		JarFile jarFile = new JarFile(file);
		_logger.debug("jarFile: " + jarFile);
		// reading manifest to find main class
		String className = jarFile.getManifest().getMainAttributes().getValue(
				"Main-Class");
		_logger.info("Looking for class: " + className);
		foundClass = super.loadClass(className);
		_logger.info("Found class: " + foundClass);

		return foundClass;
	}

	void addUrl(URL url)
	{
		super.addURL(url);
	}

	static PluginClassLoader getInstance()
	{
		if (_instance == null) {
			try {
				_instance = new PluginClassLoader();
			} catch (MalformedURLException e) {
				_logger.fatal("", e);
			}
		}

		return _instance;
	}

	private static PluginClassLoader _instance;

	private static Logger _logger = Logger.getLogger(PluginClassLoader.class);
}