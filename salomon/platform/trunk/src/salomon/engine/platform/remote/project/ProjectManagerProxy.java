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

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;

import salomon.platform.exception.PlatformException;


/**
 * Class is a sever side wrapper of IRemoteProjectManager object. It implements
 * IProjectManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 * 
 * @see salomon.engine.platform.remote.project.IRemoteProjectManager
 * 
 */
public final class ProjectManagerProxy implements IProjectManager
{
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
	 * @see salomon.engine.project.IProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project) throws PlatformException
	{
        try {
        	_remoteProjectManager.addProject(project);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());        	
        }
		throw new UnsupportedOperationException(
				"Method addProject() not implemented yet!");
	}

	/**
	 * @see salomon.engine.project.IProjectManager#ceateProject()
	 */
	public IProject ceateProject() throws PlatformException
	{
        IProject project = null;
		try {
			project = _remoteProjectManager.createProject();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
        
        return project;
	}

	/**
	 * @see IProjectManager#getProject(int)
	 */
	public IProject getProject(int projectID) throws PlatformException
	{
        IProject project = null;
		try {
			project = _remoteProjectManager.getProject(projectID);
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
        
        return project;
	}

	/**
	 * @see IProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		IProject[] projects = null;
		try {
			projects = _remoteProjectManager.getProjects();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return projects;
	}

	/**
	 * @see IProjectManager#saveProject()
	 */
	public void saveProject() throws PlatformException
	{
		try {
			_remoteProjectManager.saveProject();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectManagerProxy.class);

}