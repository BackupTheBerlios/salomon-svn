/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.platform.task;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.DBManager;

import salomon.engine.platform.DataEngine;
import salomon.engine.platform.Environment;
import salomon.engine.platform.project.ProjectManager;

import salomon.platform.exception.PlatformException;
import salomon.platform.task.ITask;
import salomon.platform.task.ITaskManager;
import salomon.platform.task.ITaskRunner;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * An implemetation of ITaskManager interface. Class manages with tasks editing
 * and executing.
 * 
 */
public final class TaskManager implements ITaskManager
{

	private DataEngine _dataEngine;

	private Environment _environment;

	private ProjectManager _projectManger;

	private TaskEngine _taskEngine;

	private LinkedList<ITask> _tasks;

	public TaskManager()
	{
		_tasks = new LinkedList<ITask>();
		_dataEngine = new DataEngine();
		_taskEngine = new TaskEngine();
		// TODO: where it should be created?
		_environment = new Environment();
		// TODO: temporary
		_environment.put("CURRENT_DATA_SET", "all_data");
	}

	public void addAllTasks(Collection<ITask> tasks)
	{
		_tasks.addAll(tasks);
	}

	/**
	 * @see salomon.platform.task.ITaskManager#addTask(salomon.platform.task.ITask)
	 */
	public void addTask(ITask task)
	{
		throw new UnsupportedOperationException(
				"Method addTask() not implemented yet!");
	}

	public void clearTaskList()
	{
		_tasks.clear();
	}

	public ITask createTask()
	{
		ITask newTask = new Task();
		_tasks.add(newTask);

		return newTask;
	}

	public ITask getCurrentTask()
	{
		Task result = null;
		return result;
		// if (_tasks.)
	} // end getCurrentTask

	/**
	 * @see salomon.platform.task.ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getRunner() not implemented yet!");
	}

	public ITask[] getTasks()
	{
		return _tasks.toArray(new ITask[_tasks.size()]);
	}

	/**
	 * @param projectManger The projectManger to set.
	 */
	public void setProjectManger(ProjectManager projectManger)
	{
		_projectManger = projectManger;
	}

	public void start()
	{
		// _taskEngine.start();
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
					_projectManger.updateTask(task, projectId);
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						LOGGER.fatal("", e1);
					} catch (SQLException e1) {
						LOGGER.fatal("", e1);
					}
					//
					// processing task
					//
					IPlugin plugin = task.getPlugin();
					LOGGER.debug("plugin: " + plugin + " id: "
							+ plugin.getDescription().getPluginID());
					IResult result = plugin.doJob(_dataEngine, _environment,
							settings);
					//
					// saving result of its execution
					//
					task.setResult(result);
					_projectManger.updateTask(task, projectId);
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						LOGGER.fatal("", e1);
					} catch (SQLException e1) {
						LOGGER.fatal("", e1);
					}
					//
					// if task was not processed correctly
					// exception is caught and shoul be handled here
					//
				} catch (Exception e) {
					LOGGER.fatal("TASK PROCESSING ERROR", e);
					task.setStatus(Task.EXCEPTION);
					try {
						_projectManger.updateTask(task, projectId);
					} catch (SQLException e1) {
						LOGGER.fatal("", e1);
					}
					try {
						DBManager.getInstance().commit();
					} catch (ClassNotFoundException e1) {
						LOGGER.fatal("", e1);
					} catch (SQLException e1) {
						LOGGER.fatal("", e1);
					}
				}
			}
			// _tasks.clear();
		}

		private ITask getTask()
		{
			ITask currentTask = null;
			while (true) {
				synchronized (_tasks) {
					if (_tasks.size() > 0) {
						currentTask = _tasks.getFirst();
					}
				}
				_tasks.notifyAll();
				if (currentTask != null) {
					break;
				} else {
					try {
						_tasks.wait();
					} catch (InterruptedException e) {
						LOGGER.fatal("", e);
					}
				}
			} // while (currentTask != null);
			return currentTask;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TaskManager.class);
}