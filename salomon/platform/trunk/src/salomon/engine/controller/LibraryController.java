/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller;

import org.apache.log4j.Logger;

import salomon.engine.project.IProjectManager;
import salomon.engine.project.ProjectManager;
import salomon.engine.task.TaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

/**
 * 
 */
public final class LibraryController implements IController
{
	private IManagerEngine _managerEngine;

	public LibraryController()
	{
		initManagers();
	}
	
	/**
	 * @see salomon.engine.controller.IController#exit()
	 */
	public void exit()
	{
		// empty body
	}

	public IManagerEngine getManagerEngine()
	{
		return _managerEngine;
	}

	/**
	 * @see salomon.engine.controller.IController#start(salomon.engine.platform.IManagerEngine)
	 */
	public void start(IManagerEngine managerEngine)
	{
		// empty body
	}

	private void initManagers()
	{
		// FIXME: Create
		try {
			_managerEngine = new ManagerEngine();
			TaskManager taskManager = (TaskManager) _managerEngine.getTasksManager();
			IProjectManager projectManager = _managerEngine.getProjectManager();
			taskManager.setProjectManger((ProjectManager) projectManager);
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(LibraryController.class);
}
