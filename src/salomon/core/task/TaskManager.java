/** Java class "TaskManager.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core.task;

import java.util.Iterator;
import java.util.LinkedList;
import org.apache.log4j.Logger;
import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 *  
 */
public final class TaskManager
{
	private LinkedList _tasks;

	private TaskEngine _taskEngine;

	private DataEngine _dataEngine;

	private Environment _environment;

	private static Logger _logger = Logger.getLogger(TaskManager.class);

	public TaskManager()
	{
		_tasks = new LinkedList();
		_dataEngine = new DataEngine();
		_taskEngine = new TaskEngine();
		//TODO: where it should be created?
		_environment = new Environment();
		//TODO: temporary		
		_environment.put("CURRENT_DATA_SET", "all_data");		
	}

	public void start()
	{
		//_taskEngine.start();
		new TaskEngine().start();		
	}
	
	public void clearTaskList() {
		_tasks.clear();
	}
	
	public void addTask(Task task)
	{
		//		synchronized (_tasks) {
		_tasks.addLast(task);
		//		}
		//		_tasks.notifyAll();
	} // end addTask

	public Task getCurrentTask()
	{
		Task result = null;
		return result;
		//if (_tasks.)
	} // end getCurrentTask

	private final class TaskEngine extends Thread
	{
		private Task getTask()
		{
			Task currentTask = null;
			while (true) {
				synchronized (_tasks) {
					if (_tasks.size() > 0) {
						currentTask = (Task) _tasks.getFirst();
					}
				}
				_tasks.notifyAll();
				if (currentTask != null) {
					break;
				} else {
					try {
						_tasks.wait();
					} catch (InterruptedException e) {
						_logger.fatal("", e);
					}
				}
			} //while (currentTask != null);
			return currentTask;
		}

		public void run()
		{
			/*
			 * while (true) { Task task = getTask(); IDataPlugin plugin =
			 * task.getPlugin(); Settings settings = task.getSettings();
			 * plugin.doJob(_dataEngine, _environment, settings);
			 */
			for (Iterator iter = _tasks.iterator(); iter.hasNext();) {
				Task task = (Task) iter.next();
				ISettings settings = task.getSettings();
				//TODO:
				if (settings == null) {
					_logger.error("settings for task: " + task + " are NULL ");
					_logger.error("creating empty settings");					
				}				
				IResult result = task.getPlugin().doJob(_dataEngine, _environment, settings);
				task.setResult(result);
				// removing task after execution?				
			}
			//_tasks.clear();
		}
	}
} // end TaskManager
