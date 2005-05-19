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

package salomon.engine.remote.task;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.ITaskRunner;
import salomon.platform.exception.PlatformException;

/** * Class is a sever side wrapper of IRemoteTaskManager object. It implements * ITaskManager interface and delegates methods execution to remote object * catching all RemoteExceptions. *  * @see salomon.engine.remote.task.IRemoteTaskManager */
public final class TaskManagerProxy implements ITaskManager
{
	private Map<IRemoteTask, ITask> _proxies = new HashMap<IRemoteTask, ITask>();

	/**
	 * 
	 * @uml.property name="_remoteTaskManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IRemoteTaskManager _remoteTaskManager;

	/**
	 * 
	 * @uml.property name="_taskRunnerProxy"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskRunnerProxy _taskRunnerProxy;

	/**
	 * @pre $remoteTaskManager != null
	 * @post $none
	 */
	public TaskManagerProxy(IRemoteTaskManager remoteTaskManager)
	{
		_remoteTaskManager = remoteTaskManager;
	}
	/**
	 * @see ITaskManager#addTask(ITask)
     * 
     * @pre task instanceof TaskProxy 
	 */
	public void addTask(ITask task) throws PlatformException
	{        
		TaskProxy taskProxy = (TaskProxy) task;
        IRemoteTask remoteTask = taskProxy.getRemoteTask();
        try {
        	_remoteTaskManager.addTask(remoteTask);
        } catch (RemoteException e) {
            LOGGER.error("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
	}
    
	/**
	 * @see ITaskManager#clearTaskList()
	 */
	public void clearTaskList() throws PlatformException
	{
		try {
			_remoteTaskManager.clearTaskList();
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see ITaskManager#createTask()
	 */
	public ITask createTask() throws PlatformException
	{
		ITask task = null;
		try {
			task = getTaskProxy(_remoteTaskManager.createTask());
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return task;
	}

	/**
	 * @see ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask() throws PlatformException
	{
		ITask task = null;
		try {
			task = getTaskProxy(_remoteTaskManager.getCurrentTask());
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return task;
	}

	/**
	 * @see ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException
	{
        if (_taskRunnerProxy == null) {
            try {
            _taskRunnerProxy = new TaskRunnerProxy(_remoteTaskManager.getRunner());
            } catch (RemoteException e) {
            	LOGGER.error("Remote error!", e);
                throw new PlatformException(e.getLocalizedMessage());
            }
        }
        
        return _taskRunnerProxy;
	}
	/**
	 * @see ITaskManager#getTasks()
	 */
	public ITask[] getTasks() throws PlatformException
	{
		ITask[] tasks = null;
		try {
			IRemoteTask[] remoteTasks = _remoteTaskManager.getTasks();
			tasks = new ITask[remoteTasks.length];
			for (int i = 0; i < remoteTasks.length; i++) {
				tasks[i] = getTaskProxy(remoteTasks[i]);
			}
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return tasks;
	}

	/**
	 * @see ITaskManager#start()
	 */
	public void start() throws PlatformException
	{
		try {
			_remoteTaskManager.start();
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	private ITask getTaskProxy(IRemoteTask remoteTask)
	{
		ITask task = null;
		if (_proxies.containsKey(remoteTask)) {
			task = _proxies.get(remoteTask);
		} else {
			task = new TaskProxy(remoteTask);
			_proxies.put(remoteTask, task);
		}

		return task;
	}

	private static final Logger LOGGER = Logger.getLogger(TaskManagerProxy.class);

	/**
	 * @see salomon.engine.task.ITaskManager#addTask(salomon.engine.task.ITask, java.lang.String, java.lang.String)
	 */
	public void addTask(ITask task, String pluginUrl, String settings) throws PlatformException
	{
		try {
			TaskProxy taskProxy = (TaskProxy) task;
	        IRemoteTask remoteTask = taskProxy.getRemoteTask();
			_remoteTaskManager.addTask(remoteTask, pluginUrl, settings);
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}
}