
package salomon.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteTaskManager#clearTaskList()
	 */
	public void clearTaskList() throws RemoteException
	{
		_taskManager.clearTaskList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.remote.IRemoteTaskManager#createTask()
	 */
	public IRemoteTask createTask() throws RemoteException
	{
		return getRemoteTask(_taskManager.createTask());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteTaskManager#getCurrentTask()
	 */
	public IRemoteTask getCurrentTask() throws RemoteException
	{
		return getRemoteTask(_taskManager.getCurrentTask());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteTaskManager#getTasks()
	 */
	public IRemoteTask[] getTasks() throws RemoteException
	{
		ITask[] tasks = _taskManager.getTasks();
        IRemoteTask[] remoteTasks = new IRemoteTask[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            remoteTasks[i] = getRemoteTask(tasks[i]);
		}
		return remoteTasks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteTaskManager#start()
	 */
	public void start() throws RemoteException
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

	private static Logger _logger = Logger.getLogger(RemoteTaskManager.class);

}