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

package salomon.engine.solution;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.task.ITask;
import salomon.engine.task.TaskManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.ManagerEngine;

public class TaskManagerTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="_taskManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskManager _taskManager;

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(TaskManagerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		PropertyConfigurator.configure("log.conf");
		ManagerEngine engine = new ManagerEngine();
		_taskManager = (TaskManager) engine.getTasksManager();
	}

	public void testCreateTask()
	{
		ITask task;
		try {

			task = _taskManager.createTask();
			assertNotNull(task);

		} catch (PlatformException e) {
			LOGGER.error(e, e);
			assertFalse(true);
			e.printStackTrace();
		}
	}

	public void testAddTask()
	{
		ITask task;
		try {
			task = _taskManager.createTask();
			assertNotNull(task);

			_taskManager.addTask(task);

			//TODO i think addAllTask should take collection<ITask> not Collection<Task>, this cast here could be possibly wrong
			Collection<ITask> tasklist = new ArrayList<ITask>();
			tasklist.add(_taskManager.createTask());
			tasklist.add(_taskManager.createTask());
			tasklist.add(_taskManager.createTask());

			_taskManager.addAllTasks(tasklist);

			ITask tasks[] = _taskManager.getTasks();
			assertNotNull(tasks);

		} catch (PlatformException e) {
			e.printStackTrace();
			LOGGER.error(e, e);
			assertFalse(true);
		}

	}

	public void testClearTasks()
	{
		_taskManager.clearTaskList();
	}

	public void testGetCurrentTask()
	{
		ITask task = _taskManager.getCurrentTask();
	}

	public void testGetRunner()
	{

		try {
			_taskManager.getRunner();
		} catch (PlatformException e) {
			e.printStackTrace();
			LOGGER.error("PlatformError", e);
			assertFalse(true);
		}
	}

	public void testEverything()
	{
		//TODO to be implemented
		try {
			_taskManager.start();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
	}

	private static Logger LOGGER = Logger.getLogger(TaskManagerTest.class);
}
