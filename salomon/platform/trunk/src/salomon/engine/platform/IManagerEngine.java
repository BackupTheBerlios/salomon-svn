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

package salomon.engine.platform;

import salomon.engine.plugin.IPluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IManagerEngine
{
	/**
	 * Returns the PluginManager.
	 * @return The PluginManager.
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	IPluginManager getPluginManager() throws PlatformException;

	/**
     * @deprecated Use {@link salomon.engine.solution.ISolution#getProjectManager()}
     * 
	 * Returns the ProjectManager.
	 * @return The ProjectManager.
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	IProjectManager getProjectManager() throws PlatformException;

	/**
	 * Returns the SolutionManager.
	 * @return The SolutionManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	ISolutionManager getSolutionManager() throws PlatformException;

	/**
     * @deprecated Use {@link salomon.engine.project.IProject#getTaskManager()}
     * 
	 * Returns the TaskManager.
	 * @return The TaskManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	ITaskManager getTasksManager() throws PlatformException;
}