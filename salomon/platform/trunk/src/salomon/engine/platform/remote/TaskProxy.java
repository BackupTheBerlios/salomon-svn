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

import org.apache.log4j.Logger;

import salomon.engine.platform.plugin.PluginLoader;
import salomon.platform.task.ITask;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Class is a sever side wrapper of IRemoteTask object. It implements ITask
 * interface and delegates methods execution to remote object catching all
 * RemoteExceptions.
 */
public final class TaskProxy implements ITask
{
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
	 * @see salomon.engine.platform.task.ITask#getName()
	 */
	public String getName()
	{
		String name = null;
		try {
			name = _remoteTask.getName();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return name;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#getPlugin()
	 */
	public IPlugin getPlugin()
	{
		IPlugin plugin = null;
		try {
			URL pluginLocation = _remoteTask.getPlugin();
			try {
				plugin = PluginLoader.loadPlugin(pluginLocation);
			} catch (Exception e1) {
				LOGGER.fatal("", e1);
			}
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return plugin;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#getResult()
	 */
	public IResult getResult()
	{
		IResult result = null;
		try {
			result = _remoteTask.getResult();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return result;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#getSettings()
	 */
	public ISettings getSettings()
	{
		ISettings settings = null;
		try {
			settings = _remoteTask.getSettings();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return settings;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#getStatus()
	 */
	public String getStatus()
	{
		String status = null;
		try {
			status = _remoteTask.getStatus();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return status;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#getTaskId()
	 */
	public int getTaskId()
	{
		int id = -1;
		try {
			id = _remoteTask.getTaskId();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return id;
	}

	/**
	 * @see salomon.engine.platform.task.ITask#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		try {
			_remoteTask.setName(name);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.task.ITask#setPlugin(salomon.plugin.IPlugin)
	 */
	public void setPlugin(IPlugin plugin)
	{
		try {
			_remoteTask.setPlugin(PluginLoader.getPluginLocation(plugin));
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.task.ITask#setResult(salomon.plugin.IResult)
	 */
	public void setResult(IResult result)
	{
		try {
			_remoteTask.setResult(result);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.task.ITask#setSettings(salomon.plugin.ISettings)
	 */
	public void setSettings(ISettings settings)
	{
		try {
			_remoteTask.setSettings(settings);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.task.ITask#setStatus(java.lang.String)
	 */
	public void setStatus(String status)
	{
		try {
			_remoteTask.setStatus(status);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}

	}

	/**
	 * @see salomon.engine.platform.task.ITask#setTaskId(int)
	 */
	public void setTaskId(int taskId)
	{
		try {
			_remoteTask.setTaskId(taskId);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TaskProxy.class);

}