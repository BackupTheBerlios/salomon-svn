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
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.Solution;
import salomon.engine.task.ITask;

import salomon.util.gui.Utils;

import salomon.platform.IUniqueId;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager
{

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	//FIXME: change it afeter implementing cascade model 
	IManagerEngine _managerEngine = null;

	/**
	 * 
	 * @uml.property name="_currentProject"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	// current project has to be kept
	// because it is possible to switch between projectManagers
	// so currentProject cannot be kept in GUI
	private IProject _currentProject = null;

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	/**
	 * 
	 * @uml.property name="_solution"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ISolution _solution;

	public ProjectManager(IManagerEngine managerEngine, DBManager manager)
	{
		_managerEngine = managerEngine;
		_dbManager = manager;
	}

	/**
	 * @throws PlatformException 
	 * @see IProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project) throws PlatformException
	{
		try {
			project.getInfo().save();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
		_currentProject = project;
		LOGGER.info("nowy project " + _currentProject.getInfo().getInfo());
	}

	/**
	 * @see salomon.engine.project.IProjectManager#createProject()
	 */
	public IProject createProject() throws PlatformException
	{
		// clearing old tasks
		if (_currentProject != null) {
			_currentProject.getTaskManager().clearTasks();
		}
		// FIXME workaround - getCurrentProject method should be removed.
		_currentProject = new Project(_managerEngine, _dbManager);
		// FIXME - after cascade model adding, method _solution.getInfo().getSolutionID()
		// should be used instead
		Solution solution = (Solution) _managerEngine.getSolutionManager().getCurrentSolution();
		((Project) _currentProject).getInfo().setSolutionID(
				solution.getInfo().getId());
		return _currentProject;
	}

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject()
	{
		return _currentProject;
	}

	public DBManager getDbManager()
	{
		return _dbManager;
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
	public IProject getProject(IUniqueId projectID) throws PlatformException
	{
		IProject project = this.createProject();
		// loading plugin information
		// building query
		SQLSelect select = new SQLSelect();
		select.addTable(ProjectInfo.TABLE_NAME);
		select.addCondition("project_id =", projectID.getId());
		Collection<ITask> tasks = null;
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(select);
			resultSet.next();
			// loading project
			((Project) project).getInfo().load(resultSet);

			// one row should be found, if found more, the first is got
			if (resultSet.next()) {
				LOGGER.warn("TOO MANY ROWS");
			}
			resultSet.close();

			// loading tasks
			_managerEngine.getTasksManager().getTasks();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		}
		LOGGER.info("Project successfully loaded.");

		// setting current project
		_currentProject = project;
		return _currentProject;
	}

	// FIXME this method has to be removed but after implementing
	// component to show table

	public Collection getProjectList() throws PlatformException
	{
		Collection projects = null;
		SQLSelect select = new SQLSelect();
		select.addTable(ProjectInfo.TABLE_NAME);
		//select.addCondition("SOLUTION_ID");
		LOGGER.info("Zly _currentProject = " + _currentProject);
		LOGGER.info("Zly _currentProject name = "
				+ _currentProject.getInfo().getInfo());
		select.addCondition("solution_id =",
				((Project) _currentProject).getInfo().getSolutionID());
		// executing query
		ResultSet resultSet = null;

		try {
			resultSet = _dbManager.select(select);
			projects = Utils.getDataFromResultSet(resultSet);
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new DBException(e.getLocalizedMessage());
		}
		return projects;
	}

	/**
	 * @see IProjectManager#getProjects()
	 */
	public IProject[] getProjects() throws PlatformException
	{
		// FIXME
		throw new UnsupportedOperationException("");
	}

	public ISolution getSolution() throws PlatformException
	{
		return _managerEngine.getSolutionManager().getCurrentSolution();
	}

	public boolean removeAll() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeAll() not implemented yet!");
	}

	public boolean removeProject(IProject project) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeProject() not implemented yet!");
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
			((Project) _currentProject).getInfo().save();
			saveTasks(_currentProject.getInfo().getId());
			_dbManager.commit();
		} catch (Exception e) {
			_dbManager.rollback();
			LOGGER.fatal("", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	public void setSolution(ISolution solution)
	{
		_solution = solution;
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
		_managerEngine.getTasksManager().saveTasks();
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

} 
