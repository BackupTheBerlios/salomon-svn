
package salomon.core.remote;

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

import salomon.core.task.ITask;
import salomon.core.task.ITaskManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
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
	 * @see salomon.core.IRemoteTaskManager#clearTaskList()
	 */
	public void clearTaskList() throws RemoteException
	{
		_taskManager.clearTaskList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteTaskManager#createTask()
	 */
	public IRemoteTask createTask() throws RemoteException
	{
		return getRemoteTask(_taskManager.createTask());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#getCurrentTask()
	 */
	public IRemoteTask getCurrentTask() throws RemoteException
	{
		return getRemoteTask(_taskManager.getCurrentTask());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#getTasks()
	 */
	public Collection getTasks() throws RemoteException
	{
		List remoteTasks = new ArrayList();

		Collection tasks = _taskManager.getTasks();
		for (Iterator iter = tasks.iterator(); iter.hasNext();) {
			ITask task = (ITask) iter.next();
			remoteTasks.add(getRemoteTask(task));
		}
		return Collections.unmodifiableCollection(remoteTasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#start()
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