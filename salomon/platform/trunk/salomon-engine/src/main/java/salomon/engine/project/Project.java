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

import salomon.engine.agent.IAgent;
import salomon.engine.agent.IAgentManager;
import salomon.engine.agentconfig.AgentConfig;
import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.agentconfig.IAgentConfigManager;
import salomon.engine.database.DBManager;
import salomon.engine.domain.Domain;
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

    private Long _projectId;

    private Domain _domain;

    /**
     * 
     * @uml.property name="_projectInfo"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ProjectInfo _projectInfo;

    private IProjectManager _projectManager;

    private String _projectName;

    /**
     * 
     * @uml.property name="_taskManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskManager _taskManager;

    public Project()
    {
        // to make DAO test running
    }

    protected Project(IManagerEngine managerEngine, DBManager manager)
            throws PlatformException
    {
        _taskManager = managerEngine.getTasksManager();
        _projectManager = managerEngine.getProjectManager();
        _agentConfigManager = managerEngine.getAgentConfigManager();
        _agentManager = managerEngine.getAgentManager();
        _projectInfo = new ProjectInfo(manager);
    }

    public void addAgent(IAgent agent)
    {
        throw new UnsupportedOperationException(
                "Method Project.addAgent() not implemented yet!");
    }

    public void addAgentConfig(IAgentConfig agentConfig)
    {
        ((AgentConfig) agentConfig).setProject(this);
        _agentConfigManager.addAgentConfig(agentConfig);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Project) {
            Project project = (Project) obj;
            return _projectId.equals(project.getProjectId());
        }
        return false;
    }

    public IAgent getAgent(long agentId)
    {
        throw new UnsupportedOperationException(
                "Method Project.getAgent() not implemented yet!");
    }

    public IAgent getAgent(String agentName)
    {
        throw new UnsupportedOperationException(
                "Method Project.getAgent() not implemented yet!");
    }

    /**
     * @see salomon.engine.project.IProject#getAgents()
     */
    public IAgentConfig[] getAgentConfigs()
    {
        return _agentConfigManager.getAgentConfigs(this);
    }

    public IAgent[] getAgents()
    {
        throw new UnsupportedOperationException(
                "Method Project.getAgents() not implemented yet!");
    }

    public ProjectInfo getInfo()
    {
        return _projectInfo;
    }

    /**
     * Returns the projectId.
     * @return The projectId
     */
    public Long getProjectId()
    {
        return _projectId;
    }

    public IProjectManager getProjectManager() throws PlatformException
    {
        //FIXME: change it after implementing cascade model
        return _projectManager;
    }

    /**
     * Returns the projectName.
     * @return The projectName
     */
    public String getProjectName()
    {
        return _projectName;
    }

    /**
     * @see salomon.engine.project.IProject#getTaskManager()
     */
    public ITaskManager getTaskManager() throws PlatformException
    {
        return _taskManager;
    }

    @Override
    public int hashCode()
    {
        return _projectId == null ? 0 : _projectId.hashCode();
    }

    public void removeAgent(IAgent agent)
    {
        throw new UnsupportedOperationException(
                "Method Project.removeAgent() not implemented yet!");
    }

    public void removeAgentConfig(IAgentConfig agentConfig)
    {
        _agentConfigManager.removeAgentConfig(agentConfig);
    }

    /**
     * Set the value of projectName field.
     * @param projectName The projectName to set
     */
    public void setProjectName(String projectName)
    {
        _projectName = projectName;
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

    /**
     * Set the value of projectId field.
     * @param projectId The projectId to set
     */
    @SuppressWarnings("unused")
    private void setProjectId(Long projectId)
    {
        _projectId = projectId;
    }

    /**
     * Returns the domain.
     * @return The domain
     */
    public Domain getDomain()
    {
        return _domain;
    }

    /**
     * Set the value of domain field.
     * @param domain The domain to set
     */
    public void setDomain(Domain domain)
    {
        _domain = domain;
    }

}