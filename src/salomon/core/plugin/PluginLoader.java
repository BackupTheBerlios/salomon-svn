/*
 * Created on 2004-05-21
 *
 */

package salomon.core.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.plugin.IPlugin;

/**
 * @author nico
 * 
 * Class is responsible for dynamic plugin loading.
 */
public final class PluginLoader
{

	public static URL getPluginLocation(IPlugin plugin)
	{
		return (URL) _pluginsLoaded.get(plugin);
	}

	public static IPlugin loadPlugin(URL url) throws Exception
	{
		IPlugin plugin = null;
		File pluginFile = getFromCache(url);
		if (pluginFile == null) {
			pluginFile = downloadFile(url);
		}
		// creating class loader
		PluginClassLoader classLoader = new PluginLoader().new PluginClassLoader(
				pluginFile);
		// loading appropriate plugin (it has to be in *.jar file
		plugin = (IPlugin) classLoader.findMainClass().newInstance();

		plugin.getDescription().setLocation(url);
		_pluginsLoaded.put(plugin, url);
		return plugin;
	}

	private static File downloadFile(URL url) throws IOException
	{
		String fileName = _pluginsDir + Config.FILE_SEPARATOR;
		String tmpFileName = url.getFile();
		_logger.debug("trying to download: " + tmpFileName);
		fileName += tmpFileName.substring(tmpFileName.lastIndexOf("/") + 1);
		File pluginFile = new File(fileName);
		DataInputStream input = new DataInputStream(new BufferedInputStream(
				url.openStream()));
		DataOutputStream output = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream(pluginFile)));
		byte buffer[] = new byte[1024];
		int bytesRead = -1;
		while ((bytesRead = input.read(buffer)) > 0) {
			output.write(buffer, 0, bytesRead);
		}
		input.close();
		output.close();
		_logger.info("downloaded file: " + pluginFile);
		return pluginFile;
	}

	private static File getFromCache(URL url)
	{
		File pluginFile = null;
		String tmpFileName = url.getFile();
		String fileName = tmpFileName.substring(tmpFileName.lastIndexOf("/") + 1);
		File dir = new File(_pluginsDir);
		_logger.info("looking for plugins in: " + dir.getAbsolutePath());
		File[] plugins = dir.listFiles(new PluginLoader().new PluginFileFilter(
				"jar"));
		for (int i = 0; i < plugins.length; i++) {
			if (plugins[i].getName().equals(fileName)) {
				_logger.info("found in cache: " + plugins[i]);
				pluginFile = plugins[i];
				break;
			}
		}
		return pluginFile;
	}

	final class PluginClassLoader extends URLClassLoader
	{
		private File _file = null;

		PluginClassLoader(File file) throws MalformedURLException
		{
			super(new URL[]{file.toURL()});
			_file = file;
		}

		//work around
		public Class findMainClass() throws ClassNotFoundException,
				IOException, URISyntaxException
		{
			Class foundClass = null;
			_logger.debug("file: " + _file);
			JarFile jarFile = new JarFile(_file);
			_logger.debug("jarFile: " + jarFile);
			// reading manifest to find main class
			String className = jarFile.getManifest().getMainAttributes().getValue(
					"Main-Class");
			_logger.info("Looking for class: " + className);
			foundClass = super.findClass(className);
			_logger.info("Found class: " + foundClass);
			return foundClass;
		}
	}

	class PluginFileFilter implements FileFilter
	{

		private String description;

		private String extension;

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

	private static Logger _logger = Logger.getLogger(PluginLoader.class);

	private static String _pluginsDir = Config.getString("PLUGINS_DIR");

	private static Map _pluginsLoaded = new HashMap();
}