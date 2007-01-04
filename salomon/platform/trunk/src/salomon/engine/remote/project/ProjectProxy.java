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

import org.apache.log4j.Logger;

import salomon.engine.agent.IAgent;
import salomon.engine.agent.IAgentManager;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.remote.task.TaskManagerProxy;
import salomon.engine.task.ITaskManager;

import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/**
 * Class is a sever side wrapper of IRemoteProject object. It implements
 * IProject interface and delegates methods execution to remote object catching
 * all RemoteExceptions.
 * 
 * @see salomon.engine.remote.project.IRemoteProject
 */
public final class ProjectProxy implements IProject
{

    /**
     * 
     * @uml.property name="_remoteProject"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemoteProject _remoteProject;

    /**
     * 
     * @uml.property name="_taskManagerProxy"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskManager _taskManagerProxy;

    /**
     * @pre $none
     * @post $none
     */
    public ProjectProxy(IRemoteProject remoteProject)
    {
        _remoteProject = remoteProject;
    }

    /**
     * @see IProject#getInfo()
     */
    public IInfo getInfo() throws PlatformException
    {
        IInfo info = null;
        try {
            info = _remoteProject.getInfo();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return info;
    }

    public String getName() throws PlatformException
    {
        String name = null;
        try {
            name = _remoteProject.getName();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return name;
    }

    public int getProjectID() throws PlatformException
    {
        int id = -1;
        try {
            id = _remoteProject.getProjectID();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return id;
    }

    /**
     * @see salomon.engine.project.IProject#getTaskManager()
     */
    public ITaskManager getTaskManager() throws PlatformException
    {
        if (_taskManagerProxy == null) {
            try {
                _taskManagerProxy = new TaskManagerProxy(
                        _remoteProject.getTaskManager());
            } catch (RemoteException e) {
                LOGGER.error("Remote error!");
                throw new PlatformException(e.getLocalizedMessage());
            }
        }

        return _taskManagerProxy;
    }

    public void setInfo(String info) throws PlatformException
    {
        try {
            _remoteProject.setInfo(info);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    public void setName(String name) throws PlatformException
    {
        try {
            _remoteProject.setName(name);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    public void setProjectID(int projectId) throws PlatformException
    {
        try {
            _remoteProject.setProjectID(projectId);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

    }

    IRemoteProject getRemoteProject()
    {
        return _remoteProject;
    }

    private static final Logger LOGGER = Logger.getLogger(ProjectProxy.class);

    public IProjectManager getProjectManager() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getProjectManager() not implemented yet!");
    }

    public IAgentManager getAgentEventManager() throws PlatformException
    {
        return null;
    }

    public void addAgent(IAgent agent)
    {
    }

    public void removeAgent(IAgent agent)
    {
    }

    public IAgent[] getAgents()
    {
        return new IAgent[0];
    }

}