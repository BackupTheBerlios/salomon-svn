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

package salomon.engine.platform.remote.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;

import salomon.platform.exception.PlatformException;


/**
 * Class representing remote instance of IProjectManager.
 */
public final class RemoteProjectManager extends UnicastRemoteObject
		implements IRemoteProjectManager
{
	private IProjectManager _projectManager;

	/**
	 * @throws RemoteException
	 */
	public RemoteProjectManager(IProjectManager projectManager)
			throws RemoteException
	{
		_projectManager = projectManager;
	}

	/**
	 * @see IRemoteProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project) throws PlatformException, RemoteException
	{
		throw new UnsupportedOperationException("Method addProject() not implemented yet!");
	}

	/**
	 * @throws PlatformException
	 * @see IRemoteProjectManager#createProject()
	 */
	public IProject createProject() throws RemoteException, PlatformException
	{
		return _projectManager.ceateProject();
	}

	/**
	 * @see IRemoteProjectManager#getProject(int)
	 */
	public IProject getProject(int projectID) throws PlatformException
	{
		return _projectManager.getProject(projectID);
	}

	/**
	 * @see IRemoteProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws RemoteException,
			PlatformException
	{
		return _projectManager.getProjects();
	}

	/**
	 * @see IRemoteProjectManager#saveProject()
	 */
	public void saveProject() throws PlatformException, RemoteException
	{
		_projectManager.saveProject();
	}
}