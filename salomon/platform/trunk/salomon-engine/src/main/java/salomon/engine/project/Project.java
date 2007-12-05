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

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.agent.IAgent;
import salomon.engine.agent.Agent;
import salomon.engine.agent.IAgentManager;
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

    private IAgentManager _agentManager;

    private Set<Agent> _agentSet;

    private Domain _domain;

    private Long _projectId;

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
        _agentSet = new HashSet<Agent>();
    }

    protected Project(IManagerEngine managerEngine, DBManager manager)
            throws PlatformException
    {
        _taskManager = managerEngine.getTasksManager();
        _projectManager = managerEngine.getProjectManager();
        _agentManager = managerEngine.getAgentManager();
        _projectInfo = new ProjectInfo(manager);
    }

    /**
     * @see salomon.engine.project.IProject#addAgent(salomon.agent.IAgent)
     */
    public void addAgent(IAgent agent)
    {
        ((Agent) agent).setProject(this);
        _agentSet.add((Agent) agent);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Project) {
            Project project = (Project) obj;
            return _projectId.equals(project.getProjectId());
        }
        return false;
    }

    /**
     * @see salomon.engine.project.IProject#getAgent(long)
     */
    public IAgent getAgent(long agentId)
    {
        IAgent agent = null;
        for (Agent a : _agentSet) {
            if (agentId == a.getAgentId()) {
                agent = a;
                break;
            }
        }
        return agent;
    }

    /**
     * @see salomon.engine.project.IProject#getAgent(java.lang.String)
     */
    public IAgent getAgent(String agentName)
    {
        IAgent agent = null;
        for (Agent a : _agentSet) {
            if (agentName.equals(a.getAgentName())) {
                agent = a;
                break;
            }
        }
        return agent;
    }

    /**
     * @see salomon.engine.project.IProject#getAgents()
     */
    public IAgent[] getAgents()
    {
        return _agentSet.toArray(new Agent[_agentSet.size()]);
    }

    /**
     * Returns the domain.
     * @return The domain
     */
    public Domain getDomain()
    {
        return _domain;
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
        _agentSet.remove(agent);
    }

    /**
     * Set the value of domain field.
     * @param domain The domain to set
     */
    public void setDomain(Domain domain)
    {
        _domain = domain;
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
     * Returns the agentSet.
     * @return The agentSet
     */
    // used by Hibernate only
    @SuppressWarnings("unused")
    private Set<Agent> getAgentSet()
    {
        return _agentSet;
    }

    /**
     * Set the value of agentSet field.
     * @param agentSet The agentSet to set
     */
    //  used by Hibernate only
    @SuppressWarnings("unused")
    private void setAgentSet(Set<Agent> agentSet)
    {
        _agentSet = agentSet;
    }

    /**
     * Set the value of projectId field.
     * @param projectId The projectId to set
     */
    //  used by Hibernate only
    @SuppressWarnings("unused")
    private void setProjectId(Long projectId)
    {
        _projectId = projectId;
    }

}