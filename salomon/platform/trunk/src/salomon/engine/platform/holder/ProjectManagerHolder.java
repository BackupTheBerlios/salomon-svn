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

package salomon.engine.platform.holder;

import salomon.platform.exception.PlatformException;
import salomon.platform.project.IProject;
import salomon.platform.project.IProjectManager;

/**
 * Holds projectManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 *  
 */
final class ProjectManagerHolder implements IProjectManager
{
	private IProjectManager _currentProjectManager;

	/**
	 *  
	 */
	ProjectManagerHolder(IProjectManager projectManager)
	{
		_currentProjectManager = projectManager;
	}

	/**
	 * @see salomon.platform.project.IProjectManager#addProject(salomon.platform.project.IProject)
	 */
	public void addProject(IProject project) throws PlatformException
	{
		_currentProjectManager.addProject(project);
	}

	/**
	 * @see salomon.platform.project.IProjectManager#ceateProject()
	 */
	public void ceateProject()
	{
		throw new UnsupportedOperationException("Method ceateProject() not implemented yet!");
	}

	/**
	 * @see salomon.platform.project.IProjectManager#createProject()
	 */
	public IProject createProject()
	{
		throw new UnsupportedOperationException("Method createProject() not implemented yet!");
	}

	/**
	 * @see salomon.platform.project.IProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getProjects() not implemented yet!");
	}

	/**
	 * @see salomon.platform.project.IProjectManager#loadProject(int)
	 */
	public IProject loadProject(int projectID) throws PlatformException
	{
		throw new UnsupportedOperationException("Method loadProject() not implemented yet!");
	}

	/**
	 * @see salomon.platform.project.IProjectManager#saveProject()
	 */
	public void saveProject() throws PlatformException
	{
		throw new UnsupportedOperationException("Method saveProject() not implemented yet!");
	}


}