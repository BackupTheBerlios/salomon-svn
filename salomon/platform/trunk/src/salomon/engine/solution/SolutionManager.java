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

package salomon.engine.solution;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.project.IProject;
import salomon.engine.project.Project;
import salomon.engine.project.ProjectInfo;
import salomon.engine.project.ProjectManager;
import salomon.engine.task.ITask;
import salomon.engine.task.Task;
import salomon.engine.task.TaskInfo;
import salomon.engine.task.TaskManager;

import salomon.util.gui.Utils;

import salomon.platform.IUniqueId;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

import salomon.plugin.ISettings;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

public final class SolutionManager implements ISolutionManager
{

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _managerEngine;

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	/**
	 * 
	 * @uml.property name="_currentSolution"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ISolution _currentSolution;

	public SolutionManager(IManagerEngine managerEngine, DBManager manager)
	{
		_managerEngine = managerEngine;
		_dbManager = manager;
	}

	/**
	 * @throws PlatformException 
	 * @see salomon.engine.solution.ISolutionManager#addSolution(salomon.platform.solution.ISolution)
	 */
	public void addSolution(ISolution solution) throws PlatformException
	{
		try {
			solution.getInfo().save();
			_dbManager.commit();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		_currentSolution = solution;
	}

	/**
	 * @see salomon.engine.solution.ISolutionManager#createSolution()
	 */
	public ISolution createSolution()
	{
		ISolution result = new Solution((ManagerEngine) _managerEngine , _dbManager);
		_currentSolution = result;
		return result;
	}
	
	/**
	 * @throws PlatformException 
	 * @see salomon.engine.solution.ISolutionManager#getSolution(java.lang.String)
	 */
	public ISolution getSolution(int id) throws PlatformException
	{
		ISolution solution = this.createSolution();
		// loading plugin information
		// building query
		SQLSelect select = new SQLSelect();
		select.addTable(SolutionInfo.TABLE_NAME);
		select.addCondition("solution_id =", id);
		List<IProject> projects = null;
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(select);
			resultSet.next();
			// loading solution
			((Solution) solution).getInfo().load(resultSet);

			// one row should be found, if found more, the first is got
			if (resultSet.next()) {
				LOGGER.warn("TOO MANY ROWS");
			}
			resultSet.close();

			// adding tasks
		//	projects = getProjectsForSolution(id);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		}

		// clearing old tasks
		//ProjectManager projectManager = (ProjectManager) _managerEngine.getProjectManager();
	//	taskManager.clearTaskList();
	//	taskManager.addAllTasks(tasks);
		LOGGER.debug("solution: " + solution);
	//	for (IProject project : projectManager.getProjects()) {
	//		LOGGER.debug("projects: " + project);
	//	}
		LOGGER.info("Solution successfully loaded.");

		// setting current project
		_currentSolution = solution;
		return _currentSolution;
	}

	/**
	 * Method selects projects for given solution id
	 * 
	 * @param solutionID
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	private List<IProject> getProjectsForSolution(int solutionID) throws Exception
	{
		List<IProject> projects = new LinkedList<IProject>();
		SQLSelect select = new SQLSelect();
		select.addTable(ProjectInfo.TABLE_NAME);
		select.addColumn("project_id");
		select.addColumn("solution_id");
		select.addColumn("project_name");
		select.addColumn("project_info");
		select.addColumn("lm_date");
		select.addCondition("solution_id =", solutionID);
		
		// executing query
		ResultSet resultSet = null;
		resultSet = _dbManager.select(select);
		ProjectManager projectManager = (ProjectManager)_currentSolution.getProjectManager();
		try {
			while (resultSet.next()) {	
				Project project = (Project) projectManager.createProject();
				// loading task
				project.getInfo().load(resultSet);
				projectManager.addProject(project);
				projects.add(project);
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			resultSet.close();
			throw e;
		}
		resultSet.close();

		return projects;
	}

	
	
	/**
	 * @see salomon.engine.solution.ISolutionManager#getSolutions()
	 */
	public ISolution[] getSolutions()
	{
		throw new UnsupportedOperationException(
				"Method getSolutions() not implemented yet!");
	}

	// FIXME this method has to be removed but after implementing
	// component to show table

	public Collection getSolutionList() throws PlatformException
	{
		Collection solutions = null;
		SQLSelect select = new SQLSelect();
		select.addTable(SolutionInfo.TABLE_NAME);
		// executing query
		ResultSet resultSet = null;

		try {
			resultSet = _dbManager.select(select);
			solutions = Utils.getDataFromResultSet(resultSet);
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		} 
		return solutions;
	}

	
	public ISolution getCurrentSolution() throws PlatformException
	{
		return _currentSolution;
	}
	
    public DBManager getDBManager()
    {
        return _dbManager ; 
    }
    
	private static final Logger LOGGER = Logger.getLogger(SolutionManager.class);
	
}
