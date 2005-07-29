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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.project.IProject;

import salomon.util.serialization.SimpleString;

import salomon.platform.IDataEngine;
import salomon.platform.IVariable;
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

	public TaskManager(IManagerEngine managerEngine, DBManager manager)
	{
		_managerEngine = managerEngine;
		_dbManager = manager;
		_tasks = new LinkedList<ITask>();
		_taskEngine = new TaskEngine();
		// TODO: where it should be created?
		_environment = new Environment();
		// TODO: temporary
		// TODO: temporary
		IVariable variable;
		try {
			variable = _environment.createEmpty("CURRENT_DATA_SET");
			variable.setValue(new SimpleString("all_data"));
			_environment.add(variable);
		} catch (PlatformException e) {
			LOGGER.fatal("Exception was thrown!", e);
		}
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

	public void clearTasks()
	{
		_tasks.clear();
	}

	public ITask createTask() throws PlatformException
	{
		Task newTask = new Task(_dbManager);
		newTask.getInfo().setProjectID(
				_managerEngine.getProjectManager().getCurrentProject().getInfo().getId());
		return newTask;
	}

	public ITask getCurrentTask()
	{
		throw new UnsupportedOperationException(
				"Method getCurrentTask() not implemented yet!");
	}

	public DBManager getDBManager()
	{
		return _dbManager;
	}

	/**
	 * @see salomon.engine.task.ITaskManager#getRunner()
	 */
	public ITaskRunner getRunner() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getRunner() not implemented yet!");
	}

	public ITask[] getTasks() throws PlatformException
	{
		List<ITask> tasks = new LinkedList<ITask>();
		// TODO: use _project in stead of it
		IProject currProject = _managerEngine.getProjectManager().getCurrentProject();		
		if (_tasks.isEmpty() && currProject != null) {
			SQLSelect select = new SQLSelect();
			select.addTable(TaskInfo.TABLE_NAME + " t");
			select.addTable(PluginInfo.TABLE_NAME + " p");
			select.addColumn("t.task_id");
			select.addColumn("t.project_id");
			select.addColumn("t.task_name");
			select.addColumn("t.task_info");
			select.addColumn("t.status");
			select.addColumn("t.plugin_settings");
			select.addColumn("t.plugin_result");
			select.addColumn("p.plugin_id");
			select.addColumn("p.plugin_name");
			select.addColumn("p.plugin_info");
			select.addColumn("p.location");
			int projectID = currProject.getInfo().getId();
			select.addCondition("t.project_id =", projectID);
			select.addCondition("t.plugin_id = p.plugin_id");

			// executing query
			ResultSet resultSet = null;
			try {
				resultSet = _dbManager.select(select);
				while (resultSet.next()) {
					// TODO: Plugin info shoul not be instantied from outside of pluginManger :-/
					PluginInfo pluginInfo = new PluginInfo(_dbManager);
					pluginInfo.load(resultSet);
					LocalPlugin localPlugin = (LocalPlugin) _managerEngine.getPluginManager().getPlugin(
							pluginInfo);
					//FIXME: real settings should be loaded
					ISettings pluginSettings = localPlugin.getSettingComponent().getDefaultSettings();

					Task task = (Task) this.createTask();
					task.setSettings(pluginSettings);
					task.setPlugin(localPlugin);
					// loading task
					task.getInfo().load(resultSet);
					this.addTask(task);
					tasks.add(task);
				}
				resultSet.close();
			} catch (Exception e) {
				LOGGER.fatal("", e);
				try {
					resultSet.close();
				} catch (SQLException ex) {
					LOGGER.fatal("", ex);
				}
				throw new PlatformException(e.getLocalizedMessage());
			}
		}
		ITask[] tasksArr = new ITask[tasks.size()];
		return tasks.toArray(tasksArr);
	}

	public boolean removeAll() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeAll() not implemented yet!");
	}

	public boolean removeTask(ITask task) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeTask() not implemented yet!");
	}

	public void saveTasks() throws PlatformException
	{
		try {
			for (ITask task : _tasks) {
				((Task) task).getInfo().save();
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	public void start() throws PlatformException
	{
		// initializing data engine if it is not initialized yet
		if (_dataEngine == null) {
			_dataEngine = _managerEngine.getSolutionManager().getCurrentSolution().getDataEngine();
		}
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
}