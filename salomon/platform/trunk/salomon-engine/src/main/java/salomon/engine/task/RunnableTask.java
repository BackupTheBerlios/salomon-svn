/*
 * Copyright (C) 2008 Salomon Team
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
/**
 * 
 */
package salomon.engine.task;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * @author Nikodem.Jura
 * 
 * FIXME: Consider redisign: 1. Make RunnableTask extend Task 2. Make IRunnable
 * not implementing ITask
 */
public class RunnableTask implements IRunnableTask {

	private ISettings _settingObject;

	private IResult _resultObject;

	private IPlugin _plugin;

	private ITask _task;

	public RunnableTask(ITask task) {
		_task = task;
	}

	public ISettings getSettingObject() {
		return _settingObject;
	}

	public void setSettingObject(ISettings settingObject) {
		_settingObject = settingObject;
	}

	public IResult getResultObject() {
		return _resultObject;
	}

	public void setResultObject(IResult resultObject) {
		_resultObject = resultObject;
	}

	public IPlugin getPlugin() {
		return _plugin;
	}

	public void setPlugin(IPlugin plugin) {
		_plugin = plugin;
	}

	public ITask getTask() {
		return _task;
	}
}
