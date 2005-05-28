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

import java.util.Collection;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.plugin.ILocalPlugin;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IResult;
import salomon.plugin.ISettings;

import salomon.engine.platform.Environment;
import salomon.engine.platform.IManagerEngine;

/**
 * An implemetation of ITaskManager interface. Class manages with tasks editing
 * and executing.
 */
public final class TaskManager implements ITaskManager
{

	/**
	 * 
	 * @uml.property name="_dataEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IDataEngine _dataEngine;

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	/**
	 * 
	 * @uml.property name="_environment"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private Environment _environment;

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	//	private IProject _project;
	private IManagerEngine _managerEngine;

	/**
	 * 
	 * @uml.property name="_taskEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskEngine _taskEngine;

	private LinkedList<ITask> _tasks;

	public TaskManager(IManagerEngine project, DBManager manager)
	{
		_managerEngine = project;
		_dbManager = manager;
		_tasks = new LinkedList<ITask>();
		// FIXME needed by DataSet
		//		try {
		//			_dataEngine = project.getSolution().getDataEngine();
		//		} catch (PlatformException e) {
		//			LOGGER.fatal("", e);
		//		}
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
	//FIXME: is it called anywhere??
	public void addTask(ITask task, String pluginUrl, String settings)
			throws PlatformException
	{
		//		try {
		//			LOGGER.debug("TaskManager.addTask()");
		//			LOGGER.debug("pluginURL: " + pluginUrl);
		//			LOGGER.debug("settings: " + settings);
		//			IPlugin plugin = null;
		//			URL url = null;
		//
		//			url = new URL(pluginUrl);
		//
		//			plugin = PluginLoader.loadPlugin(url);
		//			PluginInfo desc = new PluginInfo();
		//
		//			desc.setLocation(url);
		//
		//			desc.setPluginID(67);
		//			plugin.setDescription(desc);
		//
		//			ByteArrayInputStream bis = new ByteArrayInputStream(
		//					settings.getBytes());
		//			IStruct struct = XMLSerializer.deserialize(bis);
		//
		//			ISettings set = plugin.getSettingComponent().getDefaultSettings();
		//			set.init(struct);
		//			task.setSettings(set);
		//			task.setPlugin(plugin);
		//			addTask(task);
		//		} catch (Exception e) {
		//			LOGGER.fatal("", e);
		//			throw new PlatformException(e.getLocalizedMessage());
		//		}
		throw new UnsupportedOperationException(
				"Method addTask() not implemented yet!");
	}

	public void clearTaskList()
	{
		_tasks.clear();
	}

	public ITask createTask() throws PlatformException
	{
		Task newTask = new Task(_dbManager);
		newTask.getInfo().setProjectID(
				_managerEngine.getProjectManager().getCurrentProject().getInfo().getId());
		//		try {
		//			newTask.getInfo().save();
		//		} catch (Exception e) {
		//			LOGGER.fatal("", e);
		//			throw new PlatformException(e.getLocalizedMessage());
		//		}
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
		if (_tasks.size() == 0) {
			// load tasks from data base
		}
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
			TaskInfo taskInfo = null;
			for (ITask task : _tasks) {
				try {
					taskInfo = ((Task) task).getInfo();
					LOGGER.info("task: " + taskInfo.getName());
					ISettings settings = task.getSettings();
					taskInfo.setStatus(TaskInfo.REALIZATION);
					// changing status
					taskInfo.save();
					_dbManager.commit();
					//
					// processing task
					//
					ILocalPlugin plugin = task.getPlugin();
					LOGGER.debug("plugin: " + plugin + " id: "
							+ plugin.getInfo().getId());
					IResult result = plugin.doJob(_dataEngine, _environment,
							settings);
					//
					// saving result of its execution
					//
					task.setResult(result);
					taskInfo.save();
					_dbManager.commit();
					//
					// if task was not processed correctly
					// exception is caught and shoul be handled here
					//
				} catch (Exception e) {
					LOGGER.fatal("TASK PROCESSING ERROR", e);
					try {
						taskInfo.setStatus(TaskInfo.EXCEPTION);
						taskInfo.save();
					} catch (Exception ex) {
						LOGGER.fatal("", ex);
					}
					_dbManager.commit();
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

	public DBManager getDBManager()
	{
		return _dbManager;
	}
}