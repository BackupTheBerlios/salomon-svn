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

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.engine.plugin.PluginLoader;
import salomon.engine.task.ITask;

import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/** 
 * Class representing remote instance of ITask.  
 */
public final class RemoteTask extends UnicastRemoteObject
		implements IRemoteTask
{
	private ITask _task;

	/**
     * @pre task != null
     * @post $none
     */
	public RemoteTask(ITask task) throws RemoteException
	{
		_task = task;
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getName()
	 */
	public String getName() throws RemoteException
	{
		return _task.getName();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getPlugin()
	 */
	public URL getPlugin() throws RemoteException
	{
		return PluginLoader.getPluginLocation(_task.getPlugin());
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getResult()
	 */
	public IResult getResult() throws RemoteException
	{
		return _task.getResult();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getSettings()
	 */
	public ISettings getSettings() throws RemoteException
	{
		return _task.getSettings();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getStatus()
	 */
	public String getStatus() throws RemoteException
	{
		return _task.getStatus();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#getTaksId()
	 */
	public int getTaskId() throws RemoteException
	{
		return _task.getTaskId();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException
	{
		_task.setName(name);
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setPlugin(salomon.plugin.IPlugin)
	 */
	public void setPlugin(URL plugin) throws RemoteException
	{
		try {
			_task.setPlugin(PluginLoader.loadPlugin(plugin));
		} catch (Exception e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setResult(salomon.plugin.IResult)
	 */
	public void setResult(IResult result) throws RemoteException
	{
		_task.setResult(result);
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setSettings(salomon.plugin.ISettings)
	 */
	public void setSettings(ISettings settings) throws RemoteException
	{
		_task.setSettings(settings);
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setStatus(java.lang.String)
	 */
	public void setStatus(String status) throws RemoteException
	{
		_task.setStatus(status);
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTask#setTaksId(int)
	 */
	public void setTaskId(int taskId) throws RemoteException
	{
		_task.setTaskId(taskId);
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteTask.class);

}