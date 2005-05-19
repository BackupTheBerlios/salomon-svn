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

package salomon.engine.holder;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolution;

import salomon.platform.exception.PlatformException;

/** * Holds projectManager of current client. It is used by ManagerEngineHolder to * switch between connected clients. */
final class ProjectManagerHolder implements IProjectManager
{

	/**
	 * 
	 * @uml.property name="_currentProjectManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IProjectManager _currentProjectManager;

	/**
	 * 
	 */
	ProjectManagerHolder(IProjectManager projectManager)
	{
		_currentProjectManager = projectManager;
	}

	/**
	 * @see IProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project) throws PlatformException
	{
		_currentProjectManager.addProject(project);
	}

	/**
	 * @see IProjectManager#createProject()
	 */
	public IProject createProject() throws PlatformException
	{
		return _currentProjectManager.createProject();
	}

	/**
	 * @see salomon.engine.project.IProjectManager#getCurrentProject()
	 */
	public IProject getCurrentProject() throws PlatformException
	{
		return _currentProjectManager.getCurrentProject();
	}

	/**
	 * @see IProjectManager#getProject(int)
	 */
	public IProject getProject(int projectID) throws PlatformException
	{
		return _currentProjectManager.getProject(projectID);
	}

	/**
	 * @see IProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		return _currentProjectManager.getProjects();
	}

	/**
	 * @see IProjectManager#saveProject()
	 */
	public void saveProject() throws PlatformException
	{
		_currentProjectManager.saveProject();
	}

	/**
	 * @pre currentProjectManager != null
	 * @post $none
	 */
	void setCurrent(IProjectManager currentProjectManager)
	{
		_currentProjectManager = currentProjectManager;
	}

	public ISolution getSolution() throws PlatformException
	{
		return _currentProjectManager.getSolution();
	}

	/**
	 * @pre currentProjectManager != null
	 * @post $none
	 */
	public boolean removeProject(IProject project) throws PlatformException
	{
		return _currentProjectManager.removeProject(project);
	}

	public boolean removeAll() throws PlatformException
	{
		return _currentProjectManager.removeAll();
	}

}