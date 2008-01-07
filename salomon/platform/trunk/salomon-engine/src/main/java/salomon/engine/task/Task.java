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

import org.apache.log4j.Logger;

import salomon.engine.agent.AgentProcessingComponent;
import salomon.engine.database.DBManager;
import salomon.engine.plugin.IPluginInfo;
import salomon.engine.plugin.PluginInfo;
import salomon.platform.exception.PlatformException;

/**
 * Represents task that may be executed. It is an implementation of ITask
 * interface.
 */
public class Task implements ITask {
	private static final Logger LOGGER = Logger.getLogger(Task.class);

	private AgentProcessingComponent _agentProcessingComponent;

	private PluginInfo _pluginInfo;

	private String _result;

	private String _settings;

	private Long _taskId;

	// FIXME: remove it;
	private TaskInfo _taskInfo;

	private String _taskName;

	private int _taskNr;

	public Task() {

	}

	// FIXME: make it protected,
	// it should be accessed via TaskManager
	@Deprecated
	public Task(DBManager manager) {
		_taskInfo = new TaskInfo(manager);
		_taskInfo.setStatus(TaskInfo.ACTIVE);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return _taskId.equals(task.getTaskId());
		} else {
			return false;
		}
	}

	/**
	 * Returns the agentProcessingComponent.
	 * 
	 * @return The agentProcessingComponent
	 */
	public AgentProcessingComponent getAgentProcessingComponent() {
		return _agentProcessingComponent;
	}

	public TaskInfo getInfo() throws PlatformException {
		return _taskInfo;
	}


	/**
	 * @return Returns the _result.
	 */
	public String getResult() throws PlatformException {
		// FIXME:
		// if (_result == null) {
		// _result = _plugin.getResultComponent().getDefaultResult();
		// }
		return _result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public String getSettings() throws PlatformException {
		return _settings;
	}

	/**
	 * Returns the taskId.
	 * 
	 * @return The taskId
	 */
	public Long getTaskId() {
		return _taskId;
	}

	/**
	 * Returns the name.
	 * 
	 * @return The name
	 */
	public String getTaskName() {
		return _taskName;
	}

	/**
	 * Returns the taskNr.
	 * 
	 * @return The taskNr
	 */
	public int getTaskNr() {
		return _taskNr;
	}

	@Override
	public int hashCode() {
		return _taskId.hashCode();
	}

	/**
	 * Set the value of agentProcessingComponent field.
	 * 
	 * @param agentProcessingComponent
	 *            The agentProcessingComponent to set
	 */
	public void setAgentProcessingComponent(
			AgentProcessingComponent agentProcessingComponent) {
		_agentProcessingComponent = agentProcessingComponent;
	}

	/**
	 * @param result
	 *            The result to set.
	 * @pre result != null
	 */
	// FIXME: set status of task execution
	public void setResult(String result) throws PlatformException {
		_result = result;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	/**
	 * Set the value of name field.
	 * 
	 * @param name
	 *            The name to set
	 */
	public void setTaskName(String name) {
		_taskName = name;
	}

	/**
	 * Set the value of taskNr field.
	 * 
	 * @param taskNr
	 *            The taskNr to set
	 */
	public void setTaskNr(int taskNr) {
		_taskNr = taskNr;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return _taskName == null ? "" : _taskName + " (" + _pluginInfo + ","
				+ _settings + "," + _result + ")";
	}

	/**
	 * Set the value of taskId field.
	 * 
	 * @param taskId
	 *            The taskId to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setTaskId(Long taskId) {
		_taskId = taskId;
	}

	public IPluginInfo getPluginInfo() {
		return _pluginInfo;
	}

	public void setPluginInfo(IPluginInfo pluginInfo) {
		_pluginInfo = (PluginInfo) pluginInfo;
	}
} // end Task
