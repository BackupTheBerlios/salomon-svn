
package salomon.core.remote;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.core.task.ITaskManager;
import salomon.core.task.Task;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class TaskManagerProxy implements ITaskManager
{
	private static Logger _logger = Logger.getLogger(TaskManagerProxy.class);

	private IRemoteTaskManager _remoteTaskManager;

	/**
	 *  
	 */
	public TaskManagerProxy(IRemoteTaskManager remoteTaskManager)
	{
		_remoteTaskManager = remoteTaskManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#addAllTasks(java.util.List)
	 */
	public void addAllTasks(List tasks)
	{
		try {
			_remoteTaskManager.addAllTasks(tasks);
		} catch (RemoteException e) {
			_logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#addTask(salomon.core.task.Task)
	 */
	public void addTask(Task task)
	{
		try {
			_remoteTaskManager.addTask(task);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList()
	{
		try {
			_remoteTaskManager.clearTaskList();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getCurrentTask()
	 */
	public Task getCurrentTask()
	{
		Task task = null;
		try {
			task = _remoteTaskManager.getCurrentTask();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getTasks()
	 */
	public List getTasks()
	{
		List tasks = Collections.emptyList();
		try {
			_remoteTaskManager.getTasks();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		return tasks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#start()
	 */
	public void start()
	{
		try {
			_remoteTaskManager.start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
	}
}