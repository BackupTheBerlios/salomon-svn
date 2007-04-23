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

import org.apache.log4j.Logger;

import salomon.engine.agent.AgentInfo;
import salomon.engine.agent.IAgentManager;
import salomon.engine.agentconfig.AgentConfig;
import salomon.engine.agentconfig.AgentConfigInfo;
import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.agentconfig.IAgentConfigManager;
import salomon.engine.database.DBManager;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;

/**
 * Represents a project, it is an implementation of IProject interface.
 */
public final class Project implements IProject
{
    private static final Logger LOGGER = Logger.getLogger(Project.class);

    private IAgentConfigManager _agentConfigManager;

    private IAgentManager _agentManager;

    /**
     * 
     * @uml.property name="_projectInfo"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ProjectInfo _projectInfo;

    private IProjectManager _projectManager;

    /**
     * 
     * @uml.property name="_taskManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskManager _taskManager;

    protected Project(IManagerEngine managerEngine, DBManager manager)
            throws PlatformException
    {
        _taskManager = managerEngine.getTasksManager();
        _projectManager = managerEngine.getProjectManager();
        _agentConfigManager = managerEngine.getAgentConfigManager();
        _agentManager = managerEngine.getAgentManager();
        _projectInfo = new ProjectInfo(manager);
    }

    public void addAgentConfig(IAgentConfig agentConfig)
    {
        ((AgentConfig) agentConfig).setProject(this);
        _agentConfigManager.addAgentConfig(agentConfig);
    }

    /**
     * @see salomon.engine.project.IProject#getAgents()
     */
    public IAgentConfig[] getAgentConfigs()
    {
        IAgentConfig[] agentConfigs = _agentConfigManager.getAgentConfigs(this);
        if (agentConfigs != null && agentConfigs.length > 0) {
            for (int i = 0; i < agentConfigs.length; ++i) {
                AgentConfigInfo agentConfigInfo = (AgentConfigInfo) agentConfigs[i].getInfo();
                AgentInfo agentInfo = (AgentInfo) _agentManager.getAgent(
                        agentConfigInfo.getAgentId()).getInfo();
                ((AgentConfig) agentConfigs[i]).setAgent(_agentManager.createAgent(agentInfo));
            }
        }
        return agentConfigs;
    }

    public ProjectInfo getInfo()
    {
        return _projectInfo;
    }

    public IProjectManager getProjectManager() throws PlatformException
    {
        //FIXME: change it after implementing cascade model
        return _projectManager;
    }

    /**
     * @see salomon.engine.project.IProject#getTaskManager()
     */
    public ITaskManager getTaskManager() throws PlatformException
    {
        return _taskManager;
    }

    public void removeAgentConfig(IAgentConfig agentConfig)
    {
        _agentConfigManager.removeAgentConfig(agentConfig);
    }

    public void start()
    {
        LOGGER.info("Project.start()");
        _taskManager.getRunner().start();
    }

    @Override
    public String toString()
    {
        return _projectInfo.toString();
    }
}