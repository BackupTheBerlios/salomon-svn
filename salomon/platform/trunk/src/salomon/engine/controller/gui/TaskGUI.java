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

package salomon.engine.controller.gui;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;

import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Class is graphic representation of a task. It is used to display them on the
 * list of tasks.
 * 
 */
public final class TaskGUI
{
	private String _name;

	private IPlugin _plugin;

	private IResult _result;

	private ISettings _settings;

	private String _status;

	private ITask _task;

	private int _taskId;

	public TaskGUI()
	{
		_taskId = 0;
	}

	public TaskGUI(ITask task)
	{
		_task = task;
		try {
			_name = _task.getName();
			_plugin = _task.getPlugin();
			_result = _task.getResult();
			_settings = _task.getSettings();
			_status = _task.getStatus();
			_taskId = _task.getTaskId();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the _plugin.
	 */
	public IPlugin getPlugin()
	{
		IPlugin plugin = null;
		if (isInitialized()) {
			try {
				plugin = _task.getPlugin();
			} catch (PlatformException e) {
				LOGGER.error("", e);
				// FIXME
			}
		} else {
			plugin = _plugin;
		}

		return plugin;
	}

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult()
	{
		IResult result = null;
		if (isInitialized()) {
			try {
				result = _task.getResult();
			} catch (PlatformException e) {
				LOGGER.error("", e);
				// FIXME
			}
		} else {
			result = _result;
		}

		return result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		ISettings settings = null;
		if (isInitialized()) {
			try {
				settings = _task.getSettings();
			} catch (PlatformException e) {
				LOGGER.error("", e);
				// FIXME
			}
		} else {
			settings = _settings;
		}

		return settings;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus()
	{
		String status = null;
		if (isInitialized()) {
			try {
				status = _task.getStatus();
			} catch (PlatformException e) {
				LOGGER.error("", e);
				// FIXME
			}
		} else {
			status = _status;
		}

		return status;
	}

	/**
	 * @return Returns the taksId.
	 */
	public int getTaskId()
	{
		int taskId = -1;
		if (isInitialized()) {
			try {
				taskId = _task.getTaskId();
			} catch (PlatformException e) {
				LOGGER.error("", e);
				// FIXME
			}
		} else {
			taskId = _taskId;
		}

		return taskId;
	}

	public boolean isInitialized()
	{
		return _task != null;
	}

	// public void save()
	// {
	// save(_task);
	// }
	//
	/**
	 * Initializes itself using given task. Class cannot create Task directly,
	 * to get instance of ITask ITaskManager.createTask() has to be used.
	 * Task given to the method has already been created.
     * 
	 */
	public void initialize(ITask task)
	{
		_task = task;
		// important: set first plugin
		try {
			_task.setPlugin(_plugin);
			_task.setSettings(_settings);
			_task.setName(_name);
		} catch (PlatformException e) {
			LOGGER.error("", e);
			// FIXME
		}
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this._name = name;
	}

	/**
	 * @param plugin The plugin to set.
	 */
	public void setPlugin(IPlugin plugin)
	{
		_plugin = plugin;
	}

	// /**
	// * @param _result
	// * The _result to set.
	// */
	// public void setResult(IResult result)
	// {
	// _result = result;
	// if (_result.isSuccessful()) {
	// _status = Task.FINISHED;
	// } else {
	// _status = Task.ERROR;
	// }
	// }

	/**
	 * @param settings The settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}

	// /**
	// * @param status
	// * The status to set.
	// */
	// public void setStatus(String status)
	// {
	// this._status = status;
	// }

	// /**
	// * @param taksId
	// * The taksId to set.
	// */
	// public void setTaskId(int taskId)
	// {
	// _taskId = taskId;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _name + " [" + _plugin + "]";
	}

	private static final Logger LOGGER = Logger.getLogger(TaskGUI.class);
}