
package salomon.engine.platform.holder;


import salomon.platform.task.ITask;
import salomon.platform.task.ITaskManager;

/**
 * Holds taskManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 *  
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList()
	{
		_currentTaskManager.clearTaskList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask()
	{
		return _currentTaskManager.getCurrentTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#getTasks()
	 */
	public ITask[] getTasks()
	{
		return _currentTaskManager.getTasks();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#start()
	 */
	public void start()
	{
		_currentTaskManager.start();
	}

	void setCurrent(ITaskManager taskManager)
	{
		_currentTaskManager = taskManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.task.ITaskManager#createTask()
	 */
	public ITask createTask()
	{
		return _currentTaskManager.createTask();
	}
}