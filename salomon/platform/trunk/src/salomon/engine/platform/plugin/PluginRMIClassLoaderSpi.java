
package salomon.platform.plugin;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoaderSpi;

import org.apache.log4j.Logger;

/**
 *  Class required by RMI, whem application uses its own class loader. 
 * 
 */
public final class PluginRMIClassLoaderSpi extends RMIClassLoaderSpi
{
	
	public PluginRMIClassLoaderSpi()
	{
		_logger.debug("Creating PluginRMIClassLoaderSpi");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RMIClassLoaderSpi#getClassAnnotation(java.lang.Class)
	 */
	public String getClassAnnotation(Class cl)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RMIClassLoaderSpi#getClassLoader(java.lang.String)
	 */
	public ClassLoader getClassLoader(String codebase)
			throws MalformedURLException
	{
		_logger.error("Not implemented!!!");

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RMIClassLoaderSpi#loadClass(java.lang.String,
	 *      java.lang.String, java.lang.ClassLoader)
	 */
	public Class loadClass(String codebase, String name,
			ClassLoader defaultLoader) throws MalformedURLException,
			ClassNotFoundException
	{
		return PluginClassLoader.getInstance().loadClass(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RMIClassLoaderSpi#loadProxyClass(java.lang.String,
	 *      java.lang.String[], java.lang.ClassLoader)
	 */
	public Class loadProxyClass(String codebase, String[] interfaces,
			ClassLoader defaultLoader) throws MalformedURLException,
			ClassNotFoundException
	{
		PluginClassLoader classLoader = PluginClassLoader.getInstance();
		Class[] interfaceClasses = new Class[interfaces.length];
		for (int i = 0; i < interfaces.length; ++i) {
			interfaceClasses[i] = classLoader.loadClass(interfaces[i]);
		}

		Class proxyClass = Proxy.getProxyClass(PluginClassLoader.getInstance(),
				interfaceClasses);

		return proxyClass;
	}
    
	private static Logger _logger = Logger.getLogger(PluginRMIClassLoaderSpi.class);
}