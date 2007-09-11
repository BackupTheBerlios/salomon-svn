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
import java.util.ArrayList;

import org.apache.log4j.Logger;

import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.Solution;
import salomon.engine.task.ITask;
import salomon.engine.task.TaskInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

/**
 * An implemetation of IProjectManager interface. Class manages with projects
 * editing.
 */
public final class ProjectManager implements IProjectManager
{

    private static final Logger LOGGER = Logger.getLogger(ProjectManager.class);

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
            _dbManager.commit();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            _dbManager.rollback();
            throw new PlatformException(e.getLocalizedMessage());
        }
        _currentProject = project;
        LOGGER.info("New project: "
                + ((ProjectInfo) _currentProject.getInfo()).getName());
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
     * Checks if project with give name already exists.
     * It's much faster than loading project basing on name.
     * 
     * @param projectName
     * @return true if project exists, false otherwise
     * @throws SQLException 
     */
    public boolean exists(String projectName) throws PlatformException
    {
        boolean exists = false;
        SQLSelect select = new SQLSelect();
        select.addTable(ProjectInfo.TABLE_NAME);
        select.addCondition("project_name =", projectName);
        try {
            exists = _dbManager.existsSelect(select);
        } catch (SQLException e) {
            throw new PlatformException(e.getLocalizedMessage());
        }
        return exists;
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

    public IPlatformUtil getPlaftormUtil()
    {
        return _managerEngine.getPlatformUtil();
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
        IProject project = this.createProject();
        // loading plugin information
        // building query
        SQLSelect select = new SQLSelect();
        select.addTable(ProjectInfo.TABLE_NAME);
        select.addCondition("project_id = ", projectID);
        select.addCondition("solution_id =",
                ((Project) _currentProject).getInfo().getSolutionID());
        ResultSet resultSet = null;
        try {
            resultSet = _dbManager.select(select);
            resultSet.next();
            // loading project
            ProjectInfo projectInfo = (ProjectInfo) project.getInfo();
            projectInfo.load(resultSet);

            // one row should be found, if found more, the first is got
            if (resultSet.next()) {
                LOGGER.warn("TOO MANY ROWS");
            }
            _dbManager.closeResultSet(resultSet);

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

    /**
     * @see IProjectManager#getProjects()
     */
    public IProject[] getProjects() throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(ProjectInfo.TABLE_NAME);
        select.addCondition(
                "solution_id =",
                _managerEngine.getSolutionManager().getCurrentSolution().getInfo().getId());

        ResultSet resultSet = null;

        ArrayList<IProject> projectArrayList = new ArrayList<IProject>();

        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                IProject project = this.createProject();
                project.getInfo().load(resultSet);
                projectArrayList.add(project);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        Project[] projectArray = new Project[projectArrayList.size()];
        projectArray = projectArrayList.toArray(projectArray);

        return projectArray;
    }

    public ISolution getSolution() throws PlatformException
    {
        return _managerEngine.getSolutionManager().getCurrentSolution();
    }

    public boolean removeAll() throws PlatformException
    {
        SQLDelete delete = new SQLDelete(ProjectInfo.TABLE_NAME);
        boolean success = false;
        try {
            _dbManager.delete(delete);
            _dbManager.commit();
            success = true;
        } catch (SQLException e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return success;
    }

    public boolean removeProject(IProject project) throws PlatformException
    {
        SQLDelete delete = new SQLDelete(ProjectInfo.TABLE_NAME);
        delete.addCondition("project_id =", project.getInfo().getId());
        boolean success = false;
        try {
            _dbManager.delete(delete);
            _dbManager.commit();
            success = true;
        } catch (SQLException e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return success;
    }

    /**
     * Method saves project in data base - project header, plugins and tasks are
     * saved.
     * 
     * @param forceNew true, if new project must be created
     * 
     * @throws PlatformException
     */
    public void saveProject(boolean forceNew) throws PlatformException
    {
        // saving project header
        try {
            // saving tasks
            ProjectInfo projectInfo = (ProjectInfo) _currentProject.getInfo();

            // forcing setting new id
            if (forceNew) {
                projectInfo.setProjectID(-1);
            }
            projectInfo.save();

            if (forceNew) {
                int projectId = projectInfo.getId();
                // changing the project id of tasks
                ITask[] tasks = _managerEngine.getTasksManager().getTasks();
                for (ITask task : tasks) {
                    TaskInfo taskInfo = (TaskInfo) task.getInfo();
                    // forcing setting new task id
                    taskInfo.setTaskId(-1);
                    taskInfo.setProjectID(projectId);
                }
            }
            _managerEngine.getTasksManager().saveTasks();
            _dbManager.commit();

        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

}
