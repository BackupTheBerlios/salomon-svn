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


import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;

/**
 * Interface implemented by PluginManager, which manages with editing plugins.
 */
public interface IPluginManager
{
	/**
	 * Returns collection of plugin descriptions
	 * 
	 * @return plugin descriptions
	 */
	IPlugin[] getPlugins() throws PlatformException;

	/**
	 * Removes plugin corresponding to given plugin description.
	 * 
	 * @param description description of plugin to be removed
	 * @return true if successfully removed, false otherwise
	 */
	boolean removePlugin(IPlugin plugin) throws PlatformException;

	/**
	 * Saves plugin description.
	 * 
	 * @return true if successfully saved, false otherwise
	 */
	boolean savePlugin(IPlugin plugin) throws PlatformException;
    
    IPlugin createPlugin();
    
    void addPlugin(IPlugin plugin);
}