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

package salomon.engine.remote.project;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolution;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

/**
 * Class is a sever side wrapper of IRemoteProjectManager object. It implements
 * IProjectManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 * 
 * @see salomon.engine.remote.project.IRemoteProjectManager
 */
public final class ProjectManagerProxy implements IProjectManager
{

	private Map<IRemoteProject, IProject> _proxies = new HashMap<IRemoteProject, IProject>();

	/**
	 * 
	 * @uml.property name="_remoteProjectManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
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
			ProjectProxy projectProxy = (ProjectProxy) project;
			_remoteProjectManager.addProject(projectProxy.getRemoteProject());
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		throw new UnsupportedOperationException(
				"Method addProject() not implemented yet!");
	}

	/**
	 * @see salomon.engine.project.IProjectManager#createProject()
	 */
	public IProject createProject() throws PlatformException
	{
		IProject project = null;
		try {
			IRemoteProject remoteProject = _remoteProjectManager.createProject();
			project = getProject(remoteProject);
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return project;
	}

	/**
	 * @see salomon.engine.project.IProjectManager#getCurrentProject()
	 */
	public IProject getCurrentProject() throws PlatformException
	{
		IProject project = null;
		try {
			IRemoteProject remoteProject = _remoteProjectManager.getCurrentProject();
			project = getProject(remoteProject);
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
			IRemoteProject remoteProject = _remoteProjectManager.getProject(projectID);
			project = getProject(remoteProject);
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
			IRemoteProject[] remoteProjects = _remoteProjectManager.getProjects();
			projects = new IProject[remoteProjects.length];
			for (int i = 0; i < remoteProjects.length; i++) {
				projects[i] = getProject(remoteProjects[i]);
			}
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

	IProject getProject(IRemoteProject remoteProject)
	{
		IProject project = null;
		if (_proxies.containsKey(remoteProject)) {
			project = _proxies.get(remoteProject);
		} else {
			project = new ProjectProxy(remoteProject);
			_proxies.put(remoteProject, project);
		}

		return project;
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectManagerProxy.class);

	public ISolution getSolution() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getSolution() not implemented yet!");
	}

	public boolean removeProject(IProject project) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeProject(IProject project) not implemented yet!");
	}

	public boolean removeAll() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method  removeAll()  not implemented yet!");
	}

	public IProject getProject(IUniqueId projectID) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method salomon.engine.remote.project::ProjectManagerProxy::getProject()not implemented yet!");
	}

}