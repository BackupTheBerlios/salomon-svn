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
import salomon.engine.DAOTestHelper;
import salomon.engine.agent.AgentProcessingComponent;

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
}
