/*
 * Copyright (C) 2006 Salomon_core Team
 *
 * This file is part of Salomon_core.
 *
 * Salomon_core is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon_core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon_core; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.plugin;

import java.net.URL;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;

import salomon.engine.platform.ManagerEngine;

public class PluginInfoTest extends TestCase
{
    private DBManager _manager;

    private PluginManager _pluginManager;

    public void testAll() throws Exception
    {
        PluginInfo info = new PluginInfo(_manager);
        info.setName("test plugin");
        info.setInfo("info");
        info.setLocation(new URL("http://location.com"));

        // starting transaction
        _manager.rollback();
        // saving plugin
        final int pluginID = info.save();

        // loading created plugin
        LocalPlugin plugin = (LocalPlugin) _pluginManager.getPlugin(pluginID);
        assertNotNull(plugin);

        // deleting 
        assertTrue(plugin.getInfo().delete());
        _manager.rollback();
    }

    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   

        ManagerEngine managerEngine = new ManagerEngine();
        _manager = managerEngine.getDbManager();
        _pluginManager = (PluginManager) managerEngine.getPluginManager();
    }
}
