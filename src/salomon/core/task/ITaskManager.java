
package salomon.core.task;

import java.util.Collection;

/**
 * Interface implemented by TaskManager, which manages with tasks.
 *  
 */
public interface ITaskManager
{
	public void clearTaskList();

	public ITask createTask();

	public ITask getCurrentTask();

	public Collection getTasks();

	public void start();
}