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

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.engine.project.IProject;

import salomon.platform.exception.PlatformException;

/**
 * Remote version of IProjectManager interface. It has all methods from
 * IProjectManager interface, but adds throwing RemoteException declaration to
 * each of methods.
 * 
 * @see salomon.engine.project.IProjectManager
 */
public interface IRemoteProjectManager extends Remote
{

	/**
	 * @see salomon.engine.project.IProjectManager#addProject(IProject)
	 */
	void addProject(IRemoteProject project) throws PlatformException,
			RemoteException;

	/**
	 * @see salomon.engine.project.IProjectManager#ceateProject()
	 * 
	 * @throws PlatformException
	 * @throws RemoteException
	 */
	IRemoteProject createProject() throws PlatformException, RemoteException;

	/**
	 * Method loads project from data base.
	 * @pre $none
	 * @post $none
	 */
	IRemoteProject getProject(int projectID) throws PlatformException,
			RemoteException;

	/**
	 * @see salomon.engine.project.IProjectManager#getProjects()
	 */
	IRemoteProject[] getProjects() throws RemoteException, PlatformException;

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @throws PlatformException
	 * @pre $none
	 * @post $none
	 */
	void saveProject() throws PlatformException, RemoteException;
}