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

package salomon.engine.remote.task;

import java.net.URL;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.PluginLoader;
import salomon.engine.task.ITask;
import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Class is a sever side wrapper of IRemoteTask object. It implements ITask
 * interface and delegates methods execution to remote object catching all
 * RemoteExceptions.
 * 
 * @see salomon.engine.remote.task.IRemoteTask
 */
public final class TaskProxy implements ITask
{

    /**
     * 
     * @uml.property name="_remoteTask"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemoteTask _remoteTask;

    /**
     * @pre remoteTask != null
     * @post $none
     */
    public TaskProxy(IRemoteTask remoteTask)
    {
        _remoteTask = remoteTask;
    }

    /**
     * @see ITask#getName()
     */
    public String getName() throws PlatformException
    {
        String name = null;
        try {
            name = _remoteTask.getName();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return name;
    }

    /**
     * @see ITask#getPlugin()
     */
    public ILocalPlugin getPlugin() throws PlatformException
    {
        ILocalPlugin plugin = null;
        try {
            URL pluginLocation = _remoteTask.getPlugin();
            try {
                plugin = (ILocalPlugin) PluginLoader.loadPlugin(pluginLocation);
            } catch (Exception e1) {
                LOGGER.error("Remote error!", e1);
            }
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return plugin;
    }

    /**
     * @see ITask#getResult()
     */
    public IResult getResult() throws PlatformException
    {
        IResult result = null;
        try {
            result = _remoteTask.getResult();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return result;
    }

    /**
     * @see ITask#getSettings()
     */
    public ISettings getSettings() throws PlatformException
    {
        ISettings settings = null;
        try {
            settings = _remoteTask.getSettings();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return settings;
    }

    /**
     * @see ITask#getStatus()
     */
    public String getStatus() throws PlatformException
    {
        String status = null;
        try {
            status = _remoteTask.getStatus();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return status;
    }

    /**
     * @see ITask#getTaskId()
     */
    public int getTaskId() throws PlatformException
    {
        int id = -1;
        try {
            id = _remoteTask.getTaskId();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return id;
    }

    /**
     * @see ITask#setName(java.lang.String)
     */
    public void setName(String name) throws PlatformException
    {
        try {
            _remoteTask.setName(name);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * @see ITask#setPlugin(salomon.plugin.IPlugin)
     */
    public void setPlugin(IPlugin plugin) throws PlatformException
    {
        try {
            _remoteTask.setPlugin(PluginLoader.getPluginLocation((ILocalPlugin) plugin));
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * @see ITask#setResult(salomon.plugin.IResult)
     */
    public void setResult(IResult result) throws PlatformException
    {
        try {
            _remoteTask.setResult(result);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * @see ITask#setSettings(salomon.plugin.ISettings)
     */
    public void setSettings(ISettings settings) throws PlatformException
    {
        try {
            _remoteTask.setSettings(settings);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * @see ITask#setStatus(java.lang.String)
     */
    public void setStatus(String status) throws PlatformException
    {
        try {
            _remoteTask.setStatus(status);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * @see ITask#setTaskId(int)
     */
    public void setTaskId(int taskId) throws PlatformException
    {
        try {
            _remoteTask.setTaskId(taskId);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    /**
     * Gets the RemoteTask.
     * @return The RemoteTask
     * 
     * @pre $none
     * @post $result != null
     */
    IRemoteTask getRemoteTask()
    {
        return _remoteTask;
    }

    private static final Logger LOGGER = Logger.getLogger(TaskProxy.class);

    public IInfo getInfo() throws PlatformException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPlugin(ILocalPlugin plugin) throws PlatformException
    {
        // TODO Auto-generated method stub

    }
}