/** Java class "TaskManager.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core.task;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import salomon.core.ProjectManager;
import salomon.core.data.DBManager;
import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 *  
 */
public final class TaskManager
{
	private static Logger _logger = Logger.getLogger(TaskManager.class);

	private DataEngine _dataEngine;

	private Environment _environment;

	private TaskEngine _taskEngine;

	private LinkedList _tasks;

	private ProjectManager _projectManger;

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

	public void addAllTasks(List tasks)
	{
		_tasks.addAll(tasks);
	}

	public void addTask(Task task)
	{
		//		synchronized (_tasks) {
		_tasks.addLast(task);
		//		}
		//		_tasks.notifyAll();
	} // end addTask

	public void clearTaskList()
	{
		_tasks.clear();
	}

	public Task getCurrentTask()
	{
		Task result = null;
		return result;
		//if (_tasks.)
	} // end getCurrentTask

	public List getTasks()
	{		
		return  _tasks;
	}

	public void start()
	{
		//_taskEngine.start();
		new TaskEngine().start();
	}

	private final class TaskEngine extends Thread
	{
		public void run()
		{
			/*
			 * while (true) { Task task = getTask(); IDataPlugin plugin =
			 * task.getPlugin(); Settings settings = task.getSettings();
			 * plugin.doJob(_dataEngine, _environment, settings);
			 */
			int projectId = _projectManger.getCurrentProject().getProjectID();
			for (Iterator iter = _tasks.iterator(); iter.hasNext();) {
				Task task = (Task) iter.next();
				ISettings settings = task.getSettings();
				task.setStatus(Task.REALIZATION);
				try {
					// changing status
					_projectManger.updateTasks(new Task[]{task}, projectId);
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						_logger.fatal("", e1);
					} catch (SQLException e1) {
						_logger.fatal("", e1);
					}
					//
					// processing task
					//
					IPlugin plugin = task.getPlugin();
					_logger.debug("plugin: " + plugin + " id: " + plugin.getDescription().getPluginID());
					IResult result = plugin.doJob(_dataEngine,
							_environment, settings);
					//
					// saving result of its execution
					//
					task.setResult(result);
					_projectManger.updateTasks(new Task[]{task}, projectId);
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						_logger.fatal("", e1);
					} catch (SQLException e1) {
						_logger.fatal("", e1);
					}
					//
					// if task was not processed correctly
					// exception is caught and shoul be handled here
					//
				} catch (Exception e) {
					_logger.fatal("TASK PROCESSING ERROR", e);
					task.setStatus(Task.EXCEPTION);
					try {
						_projectManger.updateTasks(new Task[]{task}, projectId);
					} catch (SQLException e1) {
						_logger.fatal("", e1);
					}
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						_logger.fatal("", e1);
					} catch (SQLException e1) {
						_logger.fatal("", e1);
					}
				}
			}
			//_tasks.clear();
		}

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
	}

	/**
	 * @param projectManger
	 *            The projectManger to set.
	 */
	public void setProjectManger(ProjectManager projectManger)
	{
		_projectManger = projectManger;
	}
} // end TaskManager
