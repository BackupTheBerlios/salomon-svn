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

package salomon.engine.platform.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.DBManager;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.data.common.SQLSelect;
import salomon.engine.platform.data.common.SQLUpdate;
import salomon.engine.platform.plugin.PluginLoader;
import salomon.engine.platform.task.Task;

import salomon.util.gui.Utils;

import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;
import salomon.platform.project.IProject;
import salomon.platform.project.IProjectManager;
import salomon.platform.task.ITask;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.ISettings;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager
{
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
	 * @see salomon.platform.project.IProjectManager#addProject(salomon.platform.project.IProject)
	 */
	public void addProject(IProject project)
	{
		throw new UnsupportedOperationException("Method addProject() not implemented yet!");
	}

	public void ceateProject() throws PlatformException
	{
		_currentProject = new Project();
		// clearing old tasks
		_managerEngine.getTasksManager().clearTaskList();
	}

	/**
	 * @see salomon.platform.project.IProjectManager#createProject()
	 */
	public IProject createProject()
	{
		throw new UnsupportedOperationException("Method createProject() not implemented yet!");
	}

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject()
	{
		return _currentProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.project.IProjectManager#getAvailableProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		IProject[] projects = null;
        SQLSelect select = new SQLSelect();
        select.addTable(Project.TABLE_NAME);
		// executing query
		ResultSet resultSet = null;

        try {
        	resultSet = DBManager.getInstance().select(select);
        	Collection projectList = Utils.getDataFromResultSet(resultSet);
        } catch (SQLException e) {        
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
        	LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        
        //FIXME
        throw new UnsupportedOperationException("");
	}

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public IProject loadProject(int projectID) throws PlatformException
	{
        //FIXME:
        throw new UnsupportedOperationException(
				"Method loadProject() not implemented yet!");
//		Project project = new Project();
//		// loading plugin information
//        // building query
//		SQLSelect select = new SQLSelect();
//        select.addTable(Project.TABLE_NAME);
//        select.addCondition("project_id =", projectID);
//        
//		ResultSet resultSet = null;
//		resultSet = _dbManager.select(select);
//		resultSet.next();
//		// loading project
//        project.load(resultSet);
//		
//		// one row should be found, if found more, the first is got		
//		if (resultSet.next()) {
//			LOGGER.warn("TOO MANY ROWS");
//		}
//		resultSet.close();        
//		
//		// adding tasks				
//		Collection<ITask> tasks = getTasksForProject(projectID);
//		
//		// clearing old tasks
//		TaskManager taskManeger = (TaskManager) _managerEngine.getTasksManager();
//		taskManeger.clearTaskList();
//		taskManeger.addAllTasks(tasks);
//		LOGGER.debug("project: " + project);
//		LOGGER.debug("tasks: " + taskManeger.getTasks());
//        LOGGER.info("Project successfully loaded.");
//
//		// setting current project
//		_currentProject = project;
	}

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @param project
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject() throws PlatformException
	{
        //FIXME
        throw new UnsupportedOperationException(
				"Method saveProject() not implemented yet!");
//		// saving project header
//		int projectID = saveProjectHeader();
//		_currentProject.setProjectID(projectID);
//		// saving tasks
//		saveTasks();
//		LOGGER.info("Project successfully saved.");
//		//TODO: bez tego czasem zawisa :-(
//		_dbManager.commit();
	}

	/**
	 * Updates task connected to project. Method can change only status and
	 * result of task. Other columns will be unaffected. If one want change
	 * other settings, saveProject() method should be used. The Project object
	 * is not passed to this method, so method allows to save only one task and
	 * may be called after every task processing. 
     * TODO: update date_finished
	 * 
	 * @param
	 * @throws SQLException
	 */
	public void updateTask(Task task, int projectId) throws SQLException
	{
		SQLUpdate update = new SQLUpdate(Task.TABLE_NAME);
        update.addValue("plugin_result", task.getResult().resultToString());
        update.addValue("status", task.getStatus());
        update.addValue("stop_time", new Time(System.currentTimeMillis()));
        update.addCondition("project_id =", projectId);

        _dbManager.update(update);
	}

	/**
	 * Method selects tasks for given project id
	 * 
	 * @param projectID
	 * @return @throws ClassNotFoundException
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
				//TODO: move task loading to task manager ?                
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
	 * Saves project header. If project does not exist in data base it is
	 * inserted.
	 * 
	 * @return project id
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private int saveProjectHeader() throws SQLException, ClassNotFoundException
	{
		return ((Project)_currentProject).save();
	}

	/**
	 * Method saves tasks for given project. 
	 * 
	 * @param project
	 * @throws SQLException
	 */
	private void saveTasks() throws Exception
	{		
		// saving tasks		
		ITask[] tasks = _managerEngine.getTasksManager().getTasks();
        for (ITask task : tasks) {
            // TODO: is it neccessary?
            ((Task)task).setProjectID(_currentProject.getProjectID());
        	((Task)task).save();
        }
	}
	private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

} // end KnowledgeSystemManager
