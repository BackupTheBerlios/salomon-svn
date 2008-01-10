/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.agent;

import java.util.List;

import salomon.agent.IAgentProcessingComponent;
import salomon.communication.ICommunicationBus;
import salomon.engine.SalomonEngineContext;
import salomon.engine.platform.Environment;
import salomon.engine.task.Task;
import salomon.engine.task.TaskManager;
import salomon.platform.IEnvironment;

/**
 * 
 */
public class AgentProcessingComponent implements IAgentProcessingComponent {
	private static ICommunicationBus _communicationBus = (ICommunicationBus) SalomonEngineContext
			.getBean("communicationBus");

	private Long _componentId;

	private String _componentName;

	private IEnvironment _environment;

	private transient boolean _started;

	private TaskManager _taskManager;

	public AgentProcessingComponent() {
		_taskManager = new TaskManager(this);
		_environment = new Environment();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgentProcessingComponent) {
			AgentProcessingComponent comp = (AgentProcessingComponent) obj;
			return _componentId.equals(comp.getComponentId());
		}
		return false;
	}

	/**
	 * Returns the communicationBus.
	 * 
	 * @return The communicationBus
	 */
	public final ICommunicationBus getCommunicationBus() {
		return _communicationBus;
	}

	/**
	 * Returns the _componentId.
	 * 
	 * @return The _componentId
	 */
	public Long getComponentId() {
		return _componentId;
	}

	/**
	 * Returns the _componentName.
	 * 
	 * @return The _componentName
	 */
	public String getComponentName() {
		return _componentName;
	}

	public IEnvironment getEnvironment() {
		return _environment;
	}

	// this method is not a part of an interface, because salomon-agent-api
	// cannot depend on salomon-engine, where ITaskManager is defined
	// it leads to cyclic dependency between projects
	public TaskManager getTaskManager() {
		return _taskManager;
	}

	@Override
	public int hashCode() {
		return _componentId.hashCode();
	}

	public synchronized boolean isStarted() {
		return _started;
	}

	/**
	 * Set the value of communicationBus field.
	 * 
	 * @param communicationBus
	 *            The communicationBus to set
	 */
	public final void setCommunicationBus(ICommunicationBus communicationBus) {
		_communicationBus = communicationBus;
	}

	/**
	 * Set the value of _componentName field.
	 * 
	 * @param _componentName
	 *            The _componentName to set
	 */
	public void setComponentName(String componentName) {
		_componentName = componentName;
	}

	public synchronized void start() {
		_started = true;
		_taskManager.getRunner().start();
	}

	public synchronized void stop() {
		_started = false;
	}

	/**
	 * Returns the taskList.
	 * 
	 * @return The taskList
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private List<Task> getTaskList() {
		return _taskManager.getTaskList();
	}

	/**
	 * Set the value of _componentId field.
	 * 
	 * @param _componentId
	 *            The _componentId to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setComponentId(Long componentId) {
		_componentId = componentId;
	}

	/**
	 * Set the value of tasks field.
	 * 
	 * @param tasks
	 *            The tasks to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setTaskList(List<Task> tasks) {
		_taskManager.setTaskList(tasks);
	}
}
