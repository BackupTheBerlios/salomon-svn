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

package salomon.engine.project;

import salomon.engine.database.DBManager;
import salomon.engine.task.ITaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * Represents a project, it is an implementation of IProject interface.
 */
public final class Project implements IProject
{
	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	/**
	 * 
	 * @uml.property name="_projectInfo"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ProjectInfo _projectInfo;

	/**
	 * 
	 * @uml.property name="_taskManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ITaskManager _taskManager;

	private IProjectManager _projectManager;

	protected Project(IManagerEngine managerEngine, DBManager manager)
			throws PlatformException
	{
		_taskManager = managerEngine.getTasksManager();
		_projectManager = managerEngine.getProjectManager();
		_dbManager = manager;
		_projectInfo = new ProjectInfo(manager);
	}

	public ProjectInfo getInfo()
	{
		return _projectInfo;
	}

	public IProjectManager getProjectManager() throws PlatformException
	{
		//FIXME: change it after implementing cascade model
		return _projectManager;
	}

	/**
	 * @see salomon.engine.project.IProject#getTaskManager()
	 */
	public ITaskManager getTaskManager() throws PlatformException
	{
		return _taskManager;
	}

	@Override
	public String toString()
	{
		return _projectInfo.toString();
	}
}