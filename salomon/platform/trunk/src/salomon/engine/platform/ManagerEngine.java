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
import salomon.engine.plugin.PluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.ProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.SolutionManger;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.TaskManager;

/**
 * Class creates and holds all managers used by plugins. They are created only
 * in this class to avoid multiple instances.
 */
public final class ManagerEngine implements IManagerEngine
{

	private IPluginManager _pluginManager;

	private IProjectManager _projectManager;

	private ITaskManager _taskManager;
    
    private ISolutionManager _solutionManager;

	public ManagerEngine()
	{
		_taskManager = new TaskManager();
		_projectManager = new ProjectManager(this);
		_pluginManager = new PluginManager();
        _solutionManager = new SolutionManger(); 

	}

	public ManagerEngine(ITaskManager taskManager,
			IProjectManager projectManager, IPluginManager pluginManager)
	{
		_taskManager = taskManager;
		_projectManager = projectManager;
		_pluginManager = pluginManager;
	}

	/**
	 * @return Returns the pluginManager.
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManager;
	}

	/**
	 * @return Returns the projectManager.
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManager;
	}

	/**
	 * @see salomon.engine.platform.IManagerEngine#getSolutionManager()
	 */
	public ISolutionManager getSolutionManager()
	{
        return _solutionManager;
	}

	public ITaskManager getTasksManager()
	{
		return _taskManager;
	}
} // end ManagerEngine
