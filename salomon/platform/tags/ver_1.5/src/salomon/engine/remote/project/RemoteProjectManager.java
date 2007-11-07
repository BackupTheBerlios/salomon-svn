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
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.platform.exception.PlatformException;

/**
 * Class representing remote instance of IProjectManager.
 */
public final class RemoteProjectManager extends UnicastRemoteObject
        implements IRemoteProjectManager
{

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IProjectManager _projectManager;

    private Map<IProject, IRemoteProject> _proxies = new HashMap<IProject, IRemoteProject>();

    /**
     * @throws RemoteException
     */
    public RemoteProjectManager(IProjectManager projectManager)
            throws RemoteException
    {
        _projectManager = projectManager;
    }

    /**
     * @see IRemoteProjectManager#addProject(IRemoteProject)
     */
    public void addProject(IRemoteProject project) throws PlatformException,
            RemoteException
    {
        RemoteProject remoteProject = (RemoteProject) project;

        _projectManager.addProject(remoteProject.getProject());
    }

    /**
     * @throws PlatformException
     * @see IRemoteProjectManager#createProject()
     */
    public IRemoteProject createProject() throws RemoteException,
            PlatformException
    {
        IProject newProject = _projectManager.createProject();

        return getRemoteProject(newProject);
    }

    /**
     * @see salomon.engine.remote.project.IRemoteProjectManager#getCurrentProject()
     */
    public IRemoteProject getCurrentProject() throws PlatformException,
            RemoteException
    {
        IProject project = _projectManager.getCurrentProject();
        return getRemoteProject(project);
    }

    /**
     * @see IRemoteProjectManager#getProject(int)
     */
    public IRemoteProject getProject(int projectID) throws PlatformException,
            RemoteException
    {
        //IProject project = _projectManager.getProject(projectID);

        //return getRemoteProject(project);
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProjectManager::getProject()not implemented yet!");
    }

    /**
     * @see IRemoteProjectManager#getProjects()
     */
    public IRemoteProject[] getProjects() throws RemoteException,
            PlatformException
    {
        IProject[] projects = _projectManager.getProjects();
        IRemoteProject[] remoteProjects = new IRemoteProject[projects.length];

        for (int i = 0; i < projects.length; i++) {
            remoteProjects[i] = getRemoteProject(projects[i]);
        }

        return remoteProjects;
    }

    /**
     * @see IRemoteProjectManager#saveProject()
     */
    public void saveProject(boolean forceNew) throws PlatformException, RemoteException
    {
        _projectManager.saveProject(forceNew);
    }

    private IRemoteProject getRemoteProject(IProject project)
            throws RemoteException
    {
        IRemoteProject remoteProject = null;
        if (_proxies.containsKey(project)) {
            remoteProject = _proxies.get(project);
        } else {
            remoteProject = new RemoteProject(project);
            _proxies.put(project, remoteProject);
        }

        return remoteProject;
    }
}