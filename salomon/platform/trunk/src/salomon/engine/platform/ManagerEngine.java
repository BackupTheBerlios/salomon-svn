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

package salomon.engine.platform;

import org.apache.log4j.Logger;

import salomon.engine.agent.IAgentManager;
import salomon.engine.database.DBManager;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.PlatformUtil;
import salomon.engine.plugin.PluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.ProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.SolutionManager;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.TaskManager;

import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlatformUtil;

/**
 * Class creates and holds all managers used by plugins. They are created only
 * in this class to avoid multiple instances.
 */
public final class ManagerEngine implements IManagerEngine
{
    private static final Logger LOGGER = Logger.getLogger(ManagerEngine.class);

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    private IPlatformUtil _platformUtil;

    /**
     * 
     * @uml.property name="_pluginManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IPluginManager _pluginManager;

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IProjectManager _projectManager;

    /**
     * 
     * @uml.property name="_solutionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ISolutionManager _solutionManager;

    /**
     * 
     * @uml.property name="_taskManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskManager _taskManager;

    public ManagerEngine() throws PlatformException
    {
        _dbManager = new DBManager();
        try {
            _dbManager.connect();
        } catch (Exception e) {
            LOGGER.fatal("Cannot create connection to data base", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        _platformUtil = new PlatformUtil();
        _solutionManager = new SolutionManager(this, _dbManager);
        _projectManager = new ProjectManager(this, _dbManager);
        _taskManager = new TaskManager(this, _dbManager);
        _pluginManager = new PluginManager(_dbManager);
    }

    /**
     * Method used only to support SQLConsole 
     * 
     * @return
     */
    public DBManager getDbManager()
    {
        return _dbManager;
    }

    /**
     * Returns the platformUtil.
     * @return The platformUtil
     */
    public final IPlatformUtil getPlatformUtil()
    {
        return _platformUtil;
    }

    public IAgentManager getAgentManager()
    {
        return null;
    }

    /**
     * @return Returns the pluginManager.
     */
    public IPluginManager getPluginManager()
    {
        return _pluginManager;
    }

    /**
     * @return Returns the projectManager.
     */
    public IProjectManager getProjectManager()
    {
        return _projectManager;
    }

    /**
     * @see salomon.engine.platform.IManagerEngine#getSolutionManager()
     */
    public ISolutionManager getSolutionManager()
    {
        return _solutionManager;
    }

    public ITaskManager getTasksManager()
    {
        return _taskManager;
    }
} // end ManagerEngine
