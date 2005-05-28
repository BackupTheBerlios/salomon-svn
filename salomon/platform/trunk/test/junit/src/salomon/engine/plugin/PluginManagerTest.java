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

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;

import salomon.engine.platform.ManagerEngine;

public class PluginManagerTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="pluginManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginManager pluginManager;

	public void testGetPlugins()
	{
		LOGGER.info("PluginManagerTest.testGetPlugins()");

		IPlugin[] plugins = null;
		try {
			plugins = pluginManager.getPlugins();
			assertFalse(plugins.length == 0);
			//for (int i = 0; i < plugins.length; i++) {
			//	LOGGER.debug(plugins[i].getInfo());
			//}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
		assertFalse(plugins == null);
	}

	public void testRemovePlugin()
	{
		LOGGER.debug("PluginManagerTest.testRemovePlugin()");
		//LocalPlugin plugin = new LocalPlugin();
		//PluginInfo desc = new PluginInfo();
		//desc.setPluginID(35);
		//plugin.setInfo(desc);
		//assertTrue(pluginManager.removePlugin(plugin));
	}

	public void testSavePlugin1()
	{
		LOGGER.debug("PluginManagerTest.testSavePlugin1()");
		boolean success = false;
		// adds new plugin
		//LocalPlugin plugin = new LocalPlugin();
		PluginInfo desc = new PluginInfo("new plugin", "added");
		try {
			desc.setLocation(new URL("http://www.jakas.lokalizacja.com"));
			success = true;
		} catch (MalformedURLException e) {
			LOGGER.fatal("", e);
		}
		//plugin.setInfo(desc);
		assertTrue(success);
		//assertTrue(pluginManager.savePlugin(plugin));
	}

	public void testSavePlugin2()
	{
		LOGGER.debug("PluginManagerTest.testSavePlugin2()");
		boolean success = false;
		// updates the existing one
		//LocalPlugin plugin = new LocalPlugin();		
		PluginInfo desc = new PluginInfo("plugin updated", "updated");
		desc.setPluginID(20);
		try {
			desc.setLocation(new URL("http://www.jakas.lokalizacja.com"));
			success = true;
		} catch (MalformedURLException e) {
			LOGGER.fatal("", e);
		}
		//plugin.setInfo(desc);
		assertTrue(success);
		//assertTrue(pluginManager.savePlugin(plugin));
	}

	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
		ManagerEngine engine = new ManagerEngine();
		pluginManager = (PluginManager) engine.getPluginManager();
	}

	private static Logger LOGGER = Logger.getLogger(PluginManagerTest.class);

}
