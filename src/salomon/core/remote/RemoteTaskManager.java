
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import salomon.core.task.ITaskManager;
import salomon.core.task.Task;

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

	private ITaskManager _taskManager;

	/**
	 * @throws RemoteException
	 */
	protected RemoteTaskManager(ITaskManager taskManager)
			throws RemoteException
	{
		super();
		_taskManager = taskManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#addAllTasks(java.util.List)
	 */
	public void addAllTasks(List tasks) throws RemoteException
	{
		_taskManager.addAllTasks(tasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#addTask(salomon.core.task.Task)
	 */
	public void addTask(Task task) throws RemoteException
	{
		_taskManager.addTask(task);
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
	 * @see salomon.core.IRemoteTaskManager#getCurrentTask()
	 */
	public Task getCurrentTask() throws RemoteException
	{
		return _taskManager.getCurrentTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteTaskManager#getTasks()
	 */
	public List getTasks() throws RemoteException
	{
		return _taskManager.getTasks();
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

}