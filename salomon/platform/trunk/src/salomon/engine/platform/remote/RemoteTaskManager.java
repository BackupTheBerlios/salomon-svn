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

package salomon.engine.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.platform.exception.PlatformException;
import salomon.platform.task.ITask;
import salomon.platform.task.ITaskManager;

/**
 * Class representing remote instance of ITaskManager.
 */
public final class RemoteTaskManager extends UnicastRemoteObject
		implements IRemoteTaskManager
{
	private Map _proxies = new HashMap();

	private ITaskManager _taskManager;

	/**
	 * @throws RemoteException
	 */
	protected RemoteTaskManager(ITaskManager taskManager)
			throws RemoteException
	{
		_taskManager = taskManager;
	}

	/**
	 * @see salomon.engine.platform.IRemoteTaskManager#clearTaskList()
	 */
	public void clearTaskList() throws RemoteException, PlatformException
	{
		_taskManager.clearTaskList();
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteTaskManager#createTask()
	 */
	public IRemoteTask createTask() throws RemoteException, PlatformException
	{
		return getRemoteTask(_taskManager.createTask());
	}

	/**
	 * @see salomon.engine.platform.IRemoteTaskManager#getCurrentTask()
	 */
	public IRemoteTask getCurrentTask() throws RemoteException, PlatformException
	{
		return getRemoteTask(_taskManager.getCurrentTask());
	}

	/**
	 * @see salomon.engine.platform.IRemoteTaskManager#getTasks()
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
	 * @see salomon.engine.platform.IRemoteTaskManager#start()
	 */
	public void start() throws RemoteException, PlatformException
	{
		_taskManager.start();
	}

	private IRemoteTask getRemoteTask(ITask task) throws RemoteException
	{
		IRemoteTask remoteTask = null;
		if (_proxies.containsKey(task)) {
			remoteTask = (IRemoteTask) _proxies.get(task);
		} else {
			remoteTask = new RemoteTask(task);
			_proxies.put(task, remoteTask);
		}
		return remoteTask;
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteTaskManager.class);

}