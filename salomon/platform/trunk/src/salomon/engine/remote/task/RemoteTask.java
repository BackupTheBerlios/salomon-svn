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
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.engine.plugin.PluginLoader;
import salomon.engine.task.ITask;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/** 
 * Class representing remote instance of ITask.
 * @see salomon.engine.task.ITask
 */
public final class RemoteTask extends UnicastRemoteObject
		implements IRemoteTask
{
	private ITask _task;

	/**
	 * @pre task != null
	 * @post $none
	 */
	public RemoteTask(ITask task) throws RemoteException, PlatformException
	{
		_task = task;
	}

	/**
	 * @see IRemoteTask#getName()
	 */
	public String getName() throws RemoteException, PlatformException
	{
		return _task.getName();
	}

	/**
	 * @see IRemoteTask#getPlugin()
	 */
	public URL getPlugin() throws RemoteException, PlatformException
	{
		return PluginLoader.getPluginLocation(_task.getPlugin());
	}

	/**
	 * @see IRemoteTask#getResult()
	 */
	public IResult getResult() throws RemoteException, PlatformException
	{
		return _task.getResult();
	}

	/**
	 * @see IRemoteTask#getSettings()
	 */
	public ISettings getSettings() throws RemoteException, PlatformException
	{
		return _task.getSettings();
	}

	/**
	 * @see IRemoteTask#getStatus()
	 */
	public String getStatus() throws RemoteException, PlatformException
	{
		return _task.getStatus();
	}

	/**
	 * @see IRemoteTask#getTaskId()
	 */
	public int getTaskId() throws RemoteException, PlatformException
	{
		return _task.getTaskId();
	}

	/**
	 * @see IRemoteTask#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException, PlatformException
	{
		_task.setName(name);
	}

	/**
	 * @see IRemoteTask#setPlugin(salomon.plugin.IPlugin)
	 */
	public void setPlugin(URL plugin) throws RemoteException, PlatformException
	{
		try {
			_task.setPlugin(PluginLoader.loadPlugin(plugin));
		} catch (Exception e) {
			LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see salomon.engine.remote.task.IRemoteTask#setResult(salomon.plugin.IResult)
	 * 
	 * @see IRemoteTask#setResult(IResult)
	 */
	public void setResult(IResult result) throws RemoteException, PlatformException
	{
		_task.setResult(result);
	}

	/**
	 * @see IRemoteTask#setSettings(salomon.plugin.ISettings)
	 */
	public void setSettings(ISettings settings) throws RemoteException, PlatformException
	{
		_task.setSettings(settings);
	}

	/**
	 * @see IRemoteTask#setStatus(java.lang.String)
	 */
	public void setStatus(String status) throws RemoteException, PlatformException
	{
		_task.setStatus(status);
	}

	/**
	 * @see IRemoteTask#setTaskId(int)
	 */
	public void setTaskId(int taskId) throws RemoteException, PlatformException
	{
		_task.setTaskId(taskId);
	}

	ITask getTask()
	{
		return _task;
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteTask.class);

	/**
	 * @see salomon.engine.remote.task.IRemoteTask#setSettings(java.lang.String)
	 */
	public void setSettings(String settings) throws RemoteException, PlatformException
	{
		_task.setSettings(settings);
	}
}