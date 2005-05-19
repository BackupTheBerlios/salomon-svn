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

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/** * Class represents class loader used by application to load plugins. It has to * be used to download plugins from remote locations and create an instance of * given plugin. This intstance will be used to execute task. */
final class PluginClassLoader extends URLClassLoader
{
	PluginClassLoader() throws MalformedURLException
	{
		super(new URL[0]);
	}

	// work around
	public Class findMainClass(File file) throws ClassNotFoundException,
			IOException, URISyntaxException
	{
		Class foundClass = null;
		LOGGER.debug("file: " + file);
		JarFile jarFile = new JarFile(file);
		LOGGER.debug("jarFile: " + jarFile);
		// reading manifest to find main class
		String className = jarFile.getManifest().getMainAttributes().getValue(
				"Main-Class");
		LOGGER.info("Looking for class: " + className);
		foundClass = super.loadClass(className);
		LOGGER.info("Found class: " + foundClass);

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
				LOGGER.fatal("", e);
			}
		}

		return _instance;
	}

	/**
	 * 
	 * @uml.property name="_instance"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private static PluginClassLoader _instance;

	private static final Logger LOGGER = Logger.getLogger(PluginClassLoader.class);
}