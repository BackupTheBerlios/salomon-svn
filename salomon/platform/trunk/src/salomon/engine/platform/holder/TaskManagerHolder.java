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

package salomon.engine.platform.holder;


import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.ITaskRunner;

import salomon.platform.exception.PlatformException;

/**
 * Holds taskManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 */
final class TaskManagerHolder implements ITaskManager
{
	private ITaskManager _currentTaskManager;

	/**
	 *  
	 */
	TaskManagerHolder(ITaskManager taskManager)
	{
		_currentTaskManager = taskManager;
	}

	/**
	 * @see salomon.engine.platform.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList() throws PlatformException
	{
		_currentTaskManager.clearTaskList();
	}

	/**
	 * @see salomon.engine.platform.task.ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask() throws PlatformException
	{
		return _currentTaskManager.getCurrentTask();
	}

	/**
	 * @see salomon.engine.platform.task.ITaskManager#getTasks()
	 */
	public ITask[] getTasks() throws PlatformException
	{
		return _currentTaskManager.getTasks();
	}

	/**
	 * @see salomon.engine.platform.task.ITaskManager#start()
	 */
	public void start() throws PlatformException
	{
		_currentTaskManager.start();
	}

	void setCurrent(ITaskManager taskManager)
	{
		_currentTaskManager = taskManager;
	}

	/**
	 * @see salomon.engine.platform.task.ITaskManager#createTask()
	 */
	public ITask createTask() throws PlatformException
	{
		return _currentTaskManager.createTask();
	}

	/**
	 * @see salomon.engine.task.ITaskManager#addTask()
	 */
	public void addTask()
	{
		throw new UnsupportedOperationException("Method addTask() not implemented yet!");
	}

	/**
	 * @see salomon.engine.task.ITaskManager#addTask(salomon.platform.task.ITask)
	 */
	public void addTask(ITask task) throws PlatformException
	{
		throw new UnsupportedOperationException("Method addTask() not implemented yet!");
	}

	/**
	 * @see salomon.engine.task.ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getRunner() not implemented yet!");
	}
}