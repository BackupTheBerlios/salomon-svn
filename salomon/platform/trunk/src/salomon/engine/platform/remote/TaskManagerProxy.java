
package salomon.engine.platform.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.platform.task.ITask;
import salomon.engine.platform.task.ITaskManager;

/**
 * Class is a sever side wrapper of IRemoteTaskManager object. It implements
 * ITaskManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 *  
 */
public final class TaskManagerProxy implements ITaskManager
{
	private Map _proxies = new HashMap();

	private IRemoteTaskManager _remoteTaskManager;

	/**
	 * @pre $remoteTaskManager != null
	 * @post $none
	 */
	public TaskManagerProxy(IRemoteTaskManager remoteTaskManager)
	{
		_remoteTaskManager = remoteTaskManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList()
	{
		try {
			_remoteTaskManager.clearTaskList();
		} catch (RemoteException e) {
			_logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#createTask()
	 */
	public ITask createTask()
	{
		ITask task = null;
		try {
			task = getTaskProxy(_remoteTaskManager.createTask());
		} catch (RemoteException e) {
			_logger.error(e);
		}
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask()
	{
		ITask task = null;
		try {
			task = getTaskProxy(_remoteTaskManager.getCurrentTask());
		} catch (RemoteException e) {
			_logger.error(e);
		}
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#getTasks()
	 */
	public ITask[] getTasks()
	{
		ITask[] tasks = null;
		try {
			IRemoteTask[] remoteTasks = _remoteTaskManager.getTasks();
			tasks = new ITask[remoteTasks.length];
			for (int i = 0; i < remoteTasks.length; i++) {
				tasks[i] = getTaskProxy(remoteTasks[i]);
			}
		} catch (RemoteException e) {
			_logger.error(e);
		}

		return tasks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#start()
	 */
	public void start()
	{
		try {
			_remoteTaskManager.start();
		} catch (RemoteException e) {
			_logger.error(e);
		}
	}

	private ITask getTaskProxy(IRemoteTask remoteTask)
	{
		ITask task = null;
		if (_proxies.containsKey(remoteTask)) {
			task = (ITask) _proxies.get(remoteTask);
		} else {
			task = new TaskProxy(remoteTask);
			_proxies.put(remoteTask, task);
		}
		return task;
	}

	private static final Logger _logger = Logger.getLogger(TaskManagerProxy.class);
}