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

import junit.framework.TestCase;
import salomon.agent.IAgentProcessingComponent;
import salomon.engine.DAOTestHelper;
import salomon.engine.agent.AgentProcessingComponent;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.task.TaskManager.TaskEngine;
import salomon.platform.IVariable;
import salomon.platform.exception.PlatformException;
import salomon.util.serialization.SimpleString;

/**
 * @author Nikodem.Jura
 *
 */
public class TaskManagerTest extends TestCase {

	/**
	 * Test method for {@link salomon.engine.task.TaskManager#addTask(salomon.engine.task.ITask)}.
	 */
	public void testAddTask() {		
		AgentProcessingComponent procComp = DAOTestHelper.createTestAgentProcessingComponent(false);
		ITaskManager taskManager = procComp.getTaskManager();
		assertNotNull(taskManager);
		
		ITask task = taskManager.createTask();
		task.setTaskName("test-task-name");
		taskManager.addTask(task);
		
		ITask inserted = taskManager.getTask(task.getTaskName());
		assertNotNull(inserted);
		assertEquals(task.getTaskName(), inserted.getTaskName());		
	}
	
	public void testRunTasks() throws Exception
	{
		AgentProcessingComponent procComp = DAOTestHelper.createTestAgentProcessingComponent(true);
		// test plugin needs this variable to run
		IVariable var = procComp.getEnvironment().createEmpty("message");
		var.setValue(new SimpleString("Some message"));
		procComp.getEnvironment().add(var);
		
		ITaskManager taskManager = procComp.getTaskManager();
		assertNotNull(taskManager);
		TestTaskManager testTaskManager = new TestTaskManager((TaskManager) taskManager);
		// null the unused reference
		taskManager = null;
		
		ITask task = testTaskManager.createTask();
		task.setTaskName("test-task-name");
		task.setPluginInfo(new PluginInfo("salomon.plugin.DummyPlugin"));
		task.setSettings("<struct><string name=\"word\" value=\"sample\"/></struct>");
		
		testTaskManager.addTask(task);
		testTaskManager.startTasks();
	}
	
	private class TestTaskManager implements ITaskManager {

		private TaskManager _taskManager;
		
		public TestTaskManager(TaskManager taskManager) {
			_taskManager = taskManager;
		}
		
		public void addTask(ITask task) throws PlatformException {
			_taskManager.addTask(task);
		}

		public ITask createTask() {
			return _taskManager.createTask();
		}

		public IAgentProcessingComponent getAgentProcessingComponent() {			
			return _taskManager.getAgentProcessingComponent();
		}

		public ITask getCurrentTask() {
			return _taskManager.getCurrentTask();
		}

		public ITaskRunner getRunner() {
			return _taskManager.getRunner();
		}

		public ITask getTask(long taskId) throws PlatformException {
			return _taskManager.getTask(taskId);
		}

		public ITask getTask(String taskName) throws PlatformException {
			return _taskManager.getTask(taskName);
		}

		public ITask[] getTasks() throws PlatformException {
			return _taskManager.getTasks();
		}

		public void removeTask(ITask task) throws PlatformException {
			_taskManager.removeTask(task);
		}
		// for test purpose only
		public void startTasks() throws Exception {
			TaskEngine engine = _taskManager.new TaskEngine();
			engine.initializeTasks();
			engine.run();
		}
		
	}
}
