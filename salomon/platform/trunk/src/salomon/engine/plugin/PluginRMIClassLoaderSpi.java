/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.plugin;

import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.rmi.server.RMIClassLoaderSpi;

import org.apache.log4j.Logger;

/**
 * Class required by RMI, whem application uses its own class loader. 
 */
public final class PluginRMIClassLoaderSpi extends RMIClassLoaderSpi
{
	/**
     * 
     *
	 */
	public PluginRMIClassLoaderSpi()
	{
		LOGGER.debug("Creating PluginRMIClassLoaderSpi");
	}

	/**
	 * @see java.rmi.server.RMIClassLoaderSpi#getClassAnnotation(java.lang.Class)
	 */
	public String getClassAnnotation(Class cl)
	{
		return null;
	}

	/**
	 * @see java.rmi.server.RMIClassLoaderSpi#getClassLoader(java.lang.String)
	 */
	public ClassLoader getClassLoader(String codebase)
			throws MalformedURLException
	{
		LOGGER.error("Not implemented!!!");

		return null;
	}

	/**
	 * @see java.rmi.server.RMIClassLoaderSpi#loadClass(java.lang.String,
	 *      java.lang.String, java.lang.ClassLoader)
	 */
	public Class loadClass(String codebase, String name,
			ClassLoader defaultLoader) throws MalformedURLException,
			ClassNotFoundException
	{
		return PluginClassLoader.getInstance().loadClass(name);
	}

	/**
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
    
	private static Logger LOGGER = Logger.getLogger(PluginRMIClassLoaderSpi.class);
}