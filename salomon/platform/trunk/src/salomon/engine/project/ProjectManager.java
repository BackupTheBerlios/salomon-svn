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

package salomon.engine.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.database.queries.SQLUpdate;
import salomon.engine.plugin.PluginLoader;
import salomon.engine.task.ITask;
import salomon.engine.task.Task;
import salomon.engine.task.TaskManager;

import salomon.util.gui.Utils;

import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.ISettings;

import salomon.engine.platform.IManagerEngine;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager
{
    // current project has to be kept
    // because it is possible to switch between projectManagers
    // so currentProject cannot be kept in GUI
    
	private IProject _currentProject = null;

	private DBManager _dbManager;

	private IManagerEngine _managerEngine = null;

	public ProjectManager(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		try {
			_dbManager = DBManager.getInstance();
		} catch (SQLException e) {
			LOGGER.fatal("", e);
		} catch (ClassNotFoundException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see IProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project)
	{
        _currentProject = project;
	}

	/**
	 * @see salomon.engine.project.IProjectManager#ceateProject()
	 */
	public IProject ceateProject() throws PlatformException
	{
		// clearing old tasks
        //TODO: add support for Solution
		 _managerEngine.getTasksManager().clearTaskList();        
		return new Project();
	}

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject()
	{
		return _currentProject;
	}

	/**
	 * @see IProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
        // FIXME
        throw new UnsupportedOperationException("");
	}
    // FIXME this method has to be removed but after implementing 
    // component to show table
    
    public Collection getProjectList() throws PlatformException
	{
        Collection projects = null;
        SQLSelect select = new SQLSelect();
        select.addTable(Project.TABLE_NAME);
        // executing query
        ResultSet resultSet = null;

        try {
            resultSet = DBManager.getInstance().select(select);
            projects = Utils.getDataFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return projects;
    }  
    
	/**
	 * Method loads project from data base.
	 * 
	 * @see IProjectManager#getProject(int)
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws PlatformException
	 */
	public IProject getProject(int projectID) throws PlatformException
	{
		IProject project = this.ceateProject();
		// loading plugin information
		// building query
		SQLSelect select = new SQLSelect();
		select.addTable(Project.TABLE_NAME);
		select.addCondition("project_id =", projectID);
		Collection<ITask> tasks = null;
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(select);
			resultSet.next();
			// loading project
			((Project) project).load(resultSet);

			// one row should be found, if found more, the first is got
			if (resultSet.next()) {
				LOGGER.warn("TOO MANY ROWS");
			}
			resultSet.close();

			// adding tasks
			tasks = getTasksForProject(projectID);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		}

		// clearing old tasks
		TaskManager taskManager = (TaskManager) _managerEngine.getTasksManager();
		taskManager.clearTaskList();
		taskManager.addAllTasks(tasks);
		LOGGER.debug("project: " + project);
        for(ITask task : taskManager.getTasks()) {
        	LOGGER.debug("tasks: " + task);
        }
		LOGGER.info("Project successfully loaded.");

		// setting current project
        _currentProject = project;
		return _currentProject;
	}

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @see IProjectManager#saveProject()
	 *
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject() throws PlatformException
	{
		// saving project header
        try {		 
		 // saving tasks
		 saveTasks(_currentProject.getProjectID());
         ((Project)_currentProject).save();
		 LOGGER.info("Project successfully saved.");
		 //TODO: bez tego czasem zawisa :-(
		 _dbManager.commit();
        } catch (Exception e) {
        	LOGGER.fatal("",e);
            throw new PlatformException(e.getLocalizedMessage());
        }
	}

	/**
	 * Method selects tasks for given project id
	 * 
	 * @param projectID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private List<ITask> getTasksForProject(int projectID) throws Exception
	{
		List<ITask> tasks = new LinkedList<ITask>();
		SQLSelect select = new SQLSelect();
		select.addTable(Task.TABLE_NAME + " t");
		select.addTable(Description.TABLE_NAME + " p");
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
		select.addCondition("t.project_id =", projectID);
		select.addCondition("t.plugin_id = p.plugin_id");

		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(select);
		try {
			while (resultSet.next()) {
				// TODO: move task loading to task manager ?
				Description description = new Description();
				// loading plugin description
				description.load(resultSet);
				IPlugin plugin = PluginLoader.loadPlugin(description.getLocation());
				ISettings pluginSettings = plugin.getSettingComponent().getSettings();

				Task task = (Task) _managerEngine.getTasksManager().createTask();
				task.setSettings(pluginSettings);
				task.setPlugin(plugin);
				// loading task
				task.load(resultSet);
				tasks.add(task);
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			resultSet.close();
			throw e;
		}
		resultSet.close();

		return tasks;
	}

	/**
	 * Method saves tasks for given project.
	 * 
	 * @param project
	 * @throws SQLException
	 */
	private void saveTasks(int projectID) throws Exception
	{
		// saving tasks
		ITask[] tasks = _managerEngine.getTasksManager().getTasks();
		for (ITask task : tasks) {
			// TODO: is it neccessary?
			((Task) task).setProjectID(projectID);
			((Task) task).save();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

} // end KnowledgeSystemManager
