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

package salomon.engine.platform.remote.task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;

import salomon.platform.exception.PlatformException;

/**
 * Class representing remote instance of ITaskManager.
 * 
 * @see salomon.engine.task.ITaskManager
 */
public final class RemoteTaskManager extends UnicastRemoteObject
		implements IRemoteTaskManager
{
	private Map<ITask, IRemoteTask> _proxies = new HashMap<ITask, IRemoteTask>();

	private IRemoteTaskRunner _remoteTaskRunner;

	private ITaskManager _taskManager;

	/**
	 * @throws RemoteException
	 */
	public RemoteTaskManager(ITaskManager taskManager) throws RemoteException
	{
		_taskManager = taskManager;
	}

	/**
	 * @see IRemoteTaskManager#addTask(IRemoteTask)
	 */
	public void addTask(IRemoteTask task) throws PlatformException
	{
		RemoteTask remoteTask = (RemoteTask) task;
		_taskManager.addTask(remoteTask.getTask());
	}

	/**
	 * @see IRemoteTaskManager#clearTaskList()
	 */
	public void clearTaskList() throws RemoteException, PlatformException
	{
		_taskManager.clearTaskList();
	}

	/**
	 * @see IRemoteTaskManager#createTask()
	 */
	public IRemoteTask createTask() throws RemoteException, PlatformException
	{
		return getRemoteTask(_taskManager.createTask());
	}

	/**
	 * @see IRemoteTaskManager#getCurrentTask()
	 */
	public IRemoteTask getCurrentTask() throws RemoteException,
			PlatformException
	{
		return getRemoteTask(_taskManager.getCurrentTask());
	}

	/**
	 * @see IRemoteTaskManager#getRunner()
	 */
	public IRemoteTaskRunner getRunner() throws PlatformException
	{
		if (_remoteTaskRunner == null) {
			try {
				_remoteTaskRunner = new RemoteTaskRunner(
						_taskManager.getRunner());
			} catch (RemoteException e) {
				LOGGER.error("Remote error!", e);
				throw new PlatformException(e.getLocalizedMessage());
			}
		}

		return _remoteTaskRunner;
	}

	/**
	 * @see IRemoteTaskManager#getTasks()
	 */
	public IRemoteTask[] getTasks() throws RemoteException, PlatformException
	{
		ITask[] tasks = _taskManager.getTasks();
		IRemoteTask[] remoteTasks = new IRemoteTask[tasks.length];
		for (int i = 0; i < tasks.length; i++) {
			remoteTasks[i] = getRemoteTask(tasks[i]);
		}

		return remoteTasks;
	}

	/**
	 * @see IRemoteTaskManager#start()
	 */
	public void start() throws RemoteException, PlatformException
	{
		_taskManager.start();
	}

	private IRemoteTask getRemoteTask(ITask task) throws RemoteException,
			PlatformException
	{
		IRemoteTask remoteTask = null;
		if (_proxies.containsKey(task)) {
			remoteTask = _proxies.get(task);
		} else {
			remoteTask = new RemoteTask(task);
			_proxies.put(task, remoteTask);
		}

		return remoteTask;
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteTaskManager.class);
}