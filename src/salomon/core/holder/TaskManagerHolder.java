
package salomon.core.holder;

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
	 * @see salomon.core.task.ITaskManager#addAllTasks(java.util.List)
	 */
	public void addAllTasks(List tasks)
	{
		_currentTaskManager.addAllTasks(tasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#addTask(salomon.core.task.Task)
	 */
	public void addTask(Task task)
	{
		_currentTaskManager.addTask(task);
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
	public Task getCurrentTask()
	{
		return _currentTaskManager.getCurrentTask();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getTasks()
	 */
	public List getTasks()
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
}