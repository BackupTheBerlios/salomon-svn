
package salomon.core.holder;

import java.util.Collection;

import salomon.core.task.ITask;
import salomon.core.task.ITaskManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
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
	 * @see salomon.core.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList()
	{
		_currentTaskManager.clearTaskList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask()
	{
		return _currentTaskManager.getCurrentTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getTasks()
	 */
	public Collection getTasks()
	{
		return _currentTaskManager.getTasks();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#start()
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
	 * @see salomon.core.task.ITaskManager#createTask()
	 */
	public ITask createTask()
	{
		return _currentTaskManager.createTask();
	}
}