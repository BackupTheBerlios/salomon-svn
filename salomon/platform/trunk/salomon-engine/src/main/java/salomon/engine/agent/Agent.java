/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.agent;

import salomon.agent.IAgent;
import salomon.agent.IAgentDecisionComponent;
import salomon.agent.IAgentProcessingComponent;
import salomon.engine.project.Project;

/**
 * 
 */
public class Agent implements IAgent
{
    private IAgentDecisionComponent _agentDecisionComponent;

    private Long _agentId;

    private String _agentName;

    private IAgentProcessingComponent _agentProcessingComponent;

    private Project _project;

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Agent) {
            Agent agent = (Agent) obj;
            return _agentId.equals(agent.getAgentId());
        }
        return false;
    }

    /**
     * @see salomon.agent.IAgent#getAgentDecisionComponent()
     */
    public IAgentDecisionComponent getAgentDecisionComponent()
    {
        return _agentDecisionComponent;
    }

    /**
     * Returns the agentId.
     * @return The agentId
     */
    public Long getAgentId()
    {
        return _agentId;
    }

    /**
     * @see salomon.agent.IAgent#getAgentName()
     */
    public String getAgentName()
    {
        return _agentName;
    }

    /**
     * @see salomon.agent.IAgent#getAgentProcessingComponent()
     */
    public IAgentProcessingComponent getAgentProcessingComponent()
    {
        return _agentProcessingComponent;
    }

    /**
     * Returns the project.
     * @return The project
     */
    public Project getProject()
    {
        return _project;
    }

    @Override
    public int hashCode()
    {
        return _agentId == null ? 0 : _agentId.hashCode();
    }

    /**
     * @see salomon.agent.IAgent#setAgentDecisionComponent(salomon.agent.IAgentDecisionComponent)
     */
    public void setAgentDecisionComponent(
            IAgentDecisionComponent agentDecisionComponent)
    {
        _agentDecisionComponent = agentDecisionComponent;
    }

    /**
     * Set the value of agentName field.
     * @param agentName The agentName to set
     */
    public void setAgentName(String agentName)
    {
        _agentName = agentName;
    }

    /**
     * @see salomon.agent.IAgent#setAgentProcessingComponent(salomon.agent.IAgentProcessingComponent)
     */
    public void setAgentProcessingComponent(
            IAgentProcessingComponent agentProcessingComponent)
    {
        _agentProcessingComponent = agentProcessingComponent;
    }

    /**
     * Set the value of project field.
     * @param project The project to set
     */
    public void setProject(Project project)
    {
        _project = project;
    }

    /**
     * @see salomon.agent.IRunnable#start()
     */
    public void start()
    {
        if (!_agentDecisionComponent.isStarted()) {
            _agentDecisionComponent.start();
        }        
    }

    /**
     * @see salomon.agent.IRunnable#stop()
     */
    public void stop()
    {
        throw new UnsupportedOperationException(
                "Method Agent.stop() not implemented yet!");
    }

    /**
     * Set the value of agentId field.
     * @param agentId The agentId to set
     */
    @SuppressWarnings("unused")
    private void setAgentId(Long agentId)
    {
        _agentId = agentId;
    }

    public boolean isStarted()
    {
        throw new UnsupportedOperationException(
                "Method Agent.isStarted() not implemented yet!");
    }

}
