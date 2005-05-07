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

package salomon.engine.task;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.plugin.ILocalPlugin;

import salomon.util.serialization.SimpleStruct;

import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

import salomon.engine.platform.serialization.XMLSerializer;

/**
 * Represents task which may be executed. It is an implementation of ITask
 * interface.
 */
public final class Task implements ITask
{
	private DBManager _dbManager;

	private ILocalPlugin _plugin;

	private IResult _result;

	private ISettings _settings;

	private TaskInfo _taskInfo;

	protected Task(DBManager manager)
	{
		_dbManager = manager;
		_taskInfo = new TaskInfo(manager);
		_taskInfo.setStatus(TaskInfo.ACTIVE);
	}

	public TaskInfo getInfo() throws PlatformException
	{
		return _taskInfo;
	}

	/**
	 * @return Returns the _plugin.
	 */
	public ILocalPlugin getPlugin() throws PlatformException
	{
		return _plugin;
	}

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult() throws PlatformException
	{
		IResult result = _result;
		if (_result == null) {
			result = _plugin.getResultComponent().getDefaultResult();
		}
		return result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings() throws PlatformException
	{
		return _settings;
	}

	/**
	 * @param plugin The plugin to set.
	 */
	public void setPlugin(ILocalPlugin plugin) throws PlatformException
	{
		_plugin = plugin;
		_taskInfo.setPluginID(plugin.getInfo().getId());
	}

	/**
	 * @param result The result to set.
	 * @pre result != null
	 */
	public void setResult(IResult result) throws PlatformException
	{
		_result = result;
		_taskInfo.setResult(_result.resultToString());
		if (_result.isSuccessful()) {
			_taskInfo.setStatus(TaskInfo.FINISHED);
		} else {
			_taskInfo.setStatus(TaskInfo.ERROR);
		}
	}

	/**
	 * @param settings The settings to set.
	 * @pre settings != null
	 */
	public void setSettings(ISettings settings) throws PlatformException
	{
		_settings = settings;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLSerializer.serialize((SimpleStruct) _settings, bos);
		_taskInfo.setSettings(bos.toString());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _taskInfo.getName() + " [" + _plugin + "," + _settings + ","
				+ _result + "]";
	}

	private static final Logger LOGGER = Logger.getLogger(Task.class);
} // end Task
