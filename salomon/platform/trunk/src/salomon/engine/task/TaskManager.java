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

package salomon.engine.task;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.plugin.PluginLoader;
import salomon.engine.solution.Solution;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IStruct;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

import salomon.engine.platform.Environment;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.serialization.XMLSerializer;

/**
 * An implemetation of ITaskManager interface. Class manages with tasks editing
 * and executing.
 * 
 */
public final class TaskManager implements ITaskManager
{

	private IDataEngine _dataEngine;

	private Environment _environment;

	private IManagerEngine _managerEngine;

	private TaskEngine _taskEngine;

	private LinkedList<ITask> _tasks;

	public TaskManager(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		_tasks = new LinkedList<ITask>();
		// FIXME
		try {
			_dataEngine = Solution.getInstance().getDataEngine();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
		}
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
	 * @see salomon.engine.task.ITaskManager#addTask(salomon.platform.task.ITask)
	 */
	public void addTask(ITask task)
	{
		_tasks.add(task);
	}

	/**
	 * @see salomon.engine.task.ITaskManager#addTask(salomon.engine.task.ITask,
	 *      java.lang.String, java.lang.String)
	 */
	public void addTask(ITask task, String pluginUrl, String settings)
			throws PlatformException
	{
		try {
			LOGGER.debug("TaskManager.addTask()");
			LOGGER.debug("pluginURL: " + pluginUrl);
			LOGGER.debug("settings: " + settings);
			IPlugin plugin = null;
			URL url = null;

			url = new URL(pluginUrl);

			plugin = PluginLoader.loadPlugin(url);
			Description desc = new Description();

			desc.setLocation(url);

			desc.setPluginID(67);
			plugin.setDescription(desc);

			ByteArrayInputStream bis = new ByteArrayInputStream(
					settings.getBytes());
			IStruct struct = XMLSerializer.deserialize(bis);

			ISettings set = plugin.getSettingComponent().getDefaultSettings();
			set.init(struct);
			task.setSettings(set);
			task.setPlugin(plugin);
			addTask(task);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	public void clearTaskList()
	{
		_tasks.clear();
	}

	public ITask createTask() throws PlatformException
	{
		Task newTask = new Task();
		newTask.setProjectID(_managerEngine.getProjectManager().getCurrentProject().getProjectID());
		return newTask;
	}

	public ITask getCurrentTask()
	{
		throw new UnsupportedOperationException(
				"Method getCurrentTask() not implemented yet!");
	} // end getCurrentTask

	/**
	 * @see salomon.engine.task.ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getRunner() not implemented yet!");
	}

	public ITask[] getTasks()
	{
		return _tasks.toArray(new ITask[_tasks.size()]);
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
			LOGGER.info("Running tasks");
			/*
			 * while (true) { Task task = getTask(); IDataPlugin plugin =
			 * task.getPlugin(); Settings settings = task.getSettings();
			 * plugin.doJob(_dataEngine, _environment, settings);
			 */

			for (ITask task : _tasks) {
				try {
					LOGGER.info("task: " + task.getName());
					ISettings settings = task.getSettings();
					task.setStatus(Task.REALIZATION);
					// changing status
					((Task) task).save();
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
					((Task) task).save();
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
					try {
						task.setStatus(Task.EXCEPTION);
						((Task) task).save();
					} catch (Exception e1) {
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
				}
				try {
					_tasks.wait();
				} catch (InterruptedException e) {
					LOGGER.fatal("", e);
				}
			} // while (currentTask != null);

			return currentTask;
		}
	}

	private static final Logger LOGGER = Logger.getLogger(TaskManager.class);
}