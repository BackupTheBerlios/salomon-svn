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

package salomon.engine.platform.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;

import salomon.platform.exception.PlatformException;

/**
 * Class is a sever side wrapper of IRemoteProjectManager object. It implements
 * IProjectManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 * 
 */
public final class ProjectManagerProxy implements IProjectManager
{
	private IProject _currentProject;

	private IRemoteProject _currentRemoteProject;

	private IRemoteProjectManager _remoteProjectManager;

	/**
	 * @pre $none
	 * @post $none
	 */
	public ProjectManagerProxy(IRemoteProjectManager remoteProjectManager)
	{
		_remoteProjectManager = remoteProjectManager;
	}

	/**
	 * @see salomon.engine.project.IProjectManager#addProject(salomon.platform.project.IProject)
	 */
	public void addProject(IProject project)
	{
		throw new UnsupportedOperationException(
				"Method addProject() not implemented yet!");
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IProjectManager#newProject()
	 */
	public void ceateProject() throws PlatformException
	{
		try {
			_remoteProjectManager.createProject();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see salomon.engine.project.IProjectManager#createProject()
	 */
	public IProject createProject()
	{
		throw new UnsupportedOperationException(
				"Method createProject() not implemented yet!");
	}

	/**
	 * @see salomon.engine.platform.project.IProjectManager#getAvailableProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		IProject[] projects = null;
		try {
			projects = _remoteProjectManager.getProjects();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return projects;
	}

	/**
	 * @see salomon.engine.platform.IProjectManager#loadProject(int)
	 */
	public IProject loadProject(int projectID) throws PlatformException
	{
        IProject project = null;
		try {
			project = _remoteProjectManager.loadProject(projectID);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
        
        return project;
	}

	/**
	 * @see salomon.engine.platform.IProjectManager#saveProject(salomon.engine.platform.Project)
	 */
	public void saveProject() throws PlatformException
	{
		try {
			_remoteProjectManager.saveProject();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectManagerProxy.class);

}