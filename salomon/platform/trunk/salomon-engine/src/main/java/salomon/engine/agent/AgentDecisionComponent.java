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

/**
 * 
 */
public class AgentDecisionComponent implements IAgentDecisionComponent
{
    private Long _componentId;

    private String _componentName;

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AgentDecisionComponent) {
            AgentDecisionComponent comp = (AgentDecisionComponent) obj;
            return _componentId.equals(comp.getComponentId());
        }
        return false;
    }

    /**
     * Returns the componentId.
     * @return The componentId
     */
    public Long getComponentId()
    {
        return _componentId;
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponent#getComponentName()
     */
    public String getComponentName()
    {
        return _componentName;
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponent#getConfigurationComponent()
     */
    public IConfigComponent getConfigurationComponent()
    {
        throw new UnsupportedOperationException(
                "Method AgentDecisionComponent.getConfigurationComponent() not implemented yet!");
    }

    @Override
    public int hashCode()
    {
        return _componentId.hashCode();
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponent#setAgentProcessingComponent(salomon.engine.agent.IAgentProcessingComponent)
     */
    public void setAgentProcessingComponent(
            IAgentProcessingComponent agentProcessingComponent)
    {
        throw new UnsupportedOperationException(
                "Method AgentDecisionComponent.setAgentProcessingComponent() not implemented yet!");
    }

    /**
     * Set the value of componentId field.
     * @param componentId The componentId to set
     */
    public void setComponentId(Long componentId)
    {
        _componentId = componentId;
    }

    /**
     * Set the value of _componentName field.
     * @param _componentName The _componentName to set
     */
    public void setComponentName(String componentName)
    {
        _componentName = componentName;
    }

    /**
     * @see salomon.engine.agent.IRunnable#start()
     */
    public void start()
    {
        throw new UnsupportedOperationException(
                "Method AgentDecisionComponent.start() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IRunnable#stop()
     */
    public void stop()
    {
        throw new UnsupportedOperationException(
                "Method AgentDecisionComponent.stop() not implemented yet!");
    }

}
