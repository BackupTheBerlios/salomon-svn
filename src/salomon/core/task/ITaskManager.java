
package salomon.core.task;

import java.util.Collection;

/**
 * Interface implemented by TaskManager, which manages with tasks.
 *  
 */
public interface ITaskManager
{
	/**
	 * Clear list of created tasks.
	 *  
	 */
	public void clearTaskList();

	/**
	 * Creates new task.
	 * 
	 * @return new task
	 */
	public ITask createTask();

	/**
	 * Returns current task.
	 * 
	 * @return current task
	 */
	public ITask getCurrentTask();
	/**
     * Returns all tasks.
	 * 
	 * @return collection of tasks
	 */
	public Collection getTasks();

	/**
     * Starts tasks execution. 
	 *
	 */
	public void start();
}