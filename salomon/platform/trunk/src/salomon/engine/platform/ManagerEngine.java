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

import salomon.engine.platform.plugin.PluginManager;
import salomon.engine.platform.project.ProjectManager;
import salomon.engine.platform.task.TaskManager;
import salomon.platform.plugin.IPluginManager;
import salomon.platform.project.IProjectManager;
import salomon.platform.task.ITaskManager;

/**
 * Class creates and holds all managers used by plugins. They are created only
 * in this class to avoid multiple instances.
 */
public final class ManagerEngine implements IManagerEngine
{
	private ITaskManager _taskManager = null;

	private IProjectManager _projectManager = null;

	private IPluginManager _pluginManager = null;

	public ManagerEngine()
	{
		_taskManager = new TaskManager();
		_projectManager = new ProjectManager(this);
		_pluginManager = new PluginManager();

	}

	public ManagerEngine(ITaskManager taskManager,
			IProjectManager projectManager, IPluginManager pluginManager)
	{
		_taskManager = taskManager;
		_projectManager = projectManager;
		_pluginManager = pluginManager;
	}

	public ITaskManager getTasksManager()
	{
		return _taskManager;
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
} // end ManagerEngine
