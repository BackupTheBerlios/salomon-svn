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

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.engine.DAOContext;
import salomon.engine.database.DBManager;
import salomon.engine.domain.Domain;
import salomon.engine.domain.IDomain;
import salomon.engine.platform.IManagerEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager {

	private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	// FIXME: change it afeter implementing cascade model
	IManagerEngine _managerEngine = null;

	private Domain _domain;

	private Set<Project> _projectSet;

	public ProjectManager(Domain domain) {
		_domain = domain;
		_projectSet = new HashSet<Project>();
	}

	@Deprecated
	public ProjectManager(IManagerEngine managerEngine, DBManager manager) {
		throw new UnsupportedOperationException(
				"Method ProjectManager.ProjectManager() is deprecated!");
	}

	/**
	 * @throws PlatformException
	 * @see IProjectManager#addProject(IProject)
	 */
	public void addProject(IProject project) throws PlatformException {
		// TODO: first statement unnecessery if method createProject is used
		// (and it should be)
		((Project) project).setDomain(_domain);
		_projectSet.add((Project) project);
	}

	/**
	 * @see salomon.engine.project.IProjectManager#createProject()
	 */
	public IProject createProject() throws PlatformException {
		// clearing old tasks
		// if (_currentProject != null) {
		// // FIXME: _currentProject.getTaskManager().clearTasks();
		// }
		// FIXME workaround - getCurrentProject method should be removed.
		// _currentProject = new Project(_managerEngine, _dbManager);
		// FIXME - after cascade model adding, method
		// _solution.getInfo().getSolutionID()
		// should be used instead
		// FIXME:
		// Solution solution = (Solution)
		// _managerEngine.getSolutionManager().getCurrentSolution();
		// ((Project) _currentProject).getInfo().setSolutionID(
		// solution.getInfo().getId());
		// return _currentProject;
		Project project = new Project();
		project.setDomain(_domain);
		return project;
	}

	/**
	 * Checks if project with give name already exists. It's much faster than
	 * loading project basing on name.
	 * 
	 * @param projectName
	 * @return true if project exists, false otherwise
	 * @throws SQLException
	 */
	public boolean exists(String projectName) throws PlatformException {
		boolean exists = false;
		//FIXME: reimplement using Hibernate
//		SQLSelect select = new SQLSelect();
//		select.addTable(ProjectInfo.TABLE_NAME);
//		select.addCondition("project_name =", projectName);
//		try {
//			exists = _dbManager.existsSelect(select);
//		} catch (SQLException e) {
//			throw new PlatformException(e.getLocalizedMessage());
//		}
		return exists;
	}

	/**
	 * @return Returns the currentProject.
	 */
	@Deprecated
	public IProject getCurrentProject() {
		throw new UnsupportedOperationException(
				"ProjectManager.getCurrentProject() method should not be used");
	}

	public IPlatformUtil getPlaftormUtil() {
		return _managerEngine.getPlatformUtil();
	}

	public IProject getProject(long projectId) {
		// TODO: optimize the search
		IProject project = null;
		for (Project p : _projectSet) {
			if (projectId == p.getProjectId()) {
				project = p;
				break;
			}
		}
		return project;
	}

	public IProject getProject(String projectName) {
		// TODO: optimize the search
		IProject project = null;
		for (Project p : _projectSet) {
			if (projectName.equals(p.getProjectName())) {
				project = p;
				break;
			}
		}
		return project;
	}

	/**
	 * @see IProjectManager#getProjects()
	 */
	public IProject[] getProjects() {
		return _projectSet.toArray(new Project[_projectSet.size()]);
	}

	public IDomain getDomain() {
		return _domain;
	}

	public void removeAll() throws PlatformException {
		throw new UnsupportedOperationException(
				"Method ProjectManager.removeAll() is not implemented yet!");
	}

	public void removeProject(IProject project) throws PlatformException {
		throw new UnsupportedOperationException(
				"Method ProjectManager.removeProject() is not implemented yet!");
	}

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @param forceNew
	 *            true, if new project must be created
	 * 
	 * @throws PlatformException
	 */
	public void saveProject(boolean forceNew) throws PlatformException {
		// saving project header
		// FIXME:
		// try {
		// // saving tasks
		// ProjectInfo projectInfo = (ProjectInfo) _currentProject.getInfo();
		//
		// // forcing setting new id
		// if (forceNew) {
		// projectInfo.setProjectID(-1);
		// }
		// projectInfo.save();
		//
		// if (forceNew) {
		// int projectId = projectInfo.getId();
		// // changing the project id of tasks
		// ITask[] tasks = _managerEngine.getTasksManager().getTasks();
		// for (ITask task : tasks) {
		// TaskInfo taskInfo = (TaskInfo) task.getInfo();
		// // forcing setting new task id
		// taskInfo.setTaskId(-1);
		// taskInfo.setProjectID(projectId);
		// }
		// }
		// _managerEngine.getTasksManager().saveTasks();
		// _dbManager.commit();
		//
		// } catch (Exception e) {
		// _dbManager.rollback();
		// LOGGER.fatal("", e);
		// throw new PlatformException(e.getLocalizedMessage());
		// }
	}

	/**
	 * Set the value of projects field.
	 * 
	 * @param projects
	 *            The projects to set
	 */
	public void setProjectSet(Set<Project> projects) {
		_projectSet = projects;
	}

	/**
	 * @return
	 */
	public Set<Project> getProjectSet() {
		return _projectSet;
	}
}
