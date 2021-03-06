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

import salomon.engine.project.IProject;
import salomon.engine.remote.task.IRemoteTaskManager;
import salomon.engine.remote.task.RemoteTaskManager;
import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/**
 * Class representing remote instance of IProject.
 * 
 * @see salomon.engine.project.IProject
 */
public final class RemoteProject extends UnicastRemoteObject
        implements IRemoteProject
{

    /**
     * 
     * @uml.property name="_project"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IProject _project;

    /**
     * 
     * @uml.property name="_remoteTaskManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemoteTaskManager _remoteTaskManager;

    /**
     * @pre project != null
     * @post $none
     */
    public RemoteProject(IProject project) throws RemoteException
    {
        _project = project;
    }

    /**
     * @see IRemoteProject#getInfo()
     */
    public IInfo getInfo() throws RemoteException, PlatformException
    {
        return _project.getInfo();
    }

    /**
     * @see salomon.engine.remote.project.IRemoteProject#getTaskManager()
     */
    public IRemoteTaskManager getTaskManager() throws RemoteException,
            PlatformException
    {
        if (_remoteTaskManager == null) {
            _remoteTaskManager = new RemoteTaskManager(
                    _project.getTaskManager());
        }

        return _remoteTaskManager;
    }

    IProject getProject()
    {
        return _project;
    }

    public void setInfo(String info) throws RemoteException, PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProject::setInfo()not implemented yet!");
    }

    public void setName(String name) throws RemoteException, PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProject::setName()not implemented yet!");
    }

    public void setProjectID(int projectId) throws RemoteException,
            PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProject::setProjectID()not implemented yet!");
    }

    public String getName() throws RemoteException, PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProject::getName()not implemented yet!");
    }

    public int getProjectID() throws RemoteException, PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.project::RemoteProject::getProjectID()not implemented yet!");
    }
}