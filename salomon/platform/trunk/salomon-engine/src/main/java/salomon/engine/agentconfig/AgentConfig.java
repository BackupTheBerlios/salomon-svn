/*
 * Copyright (C) 2007 Salomon Team
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

package salomon.engine.agentconfig;

import salomon.engine.agent.AbstractAgent;
import salomon.engine.agent.AgentInfo;
import salomon.engine.agent.IAgent;
import salomon.engine.database.DBManager;
import salomon.engine.project.IProject;
import salomon.platform.IInfo;
import salomon.platform.serialization.IObject;

/**
 * 
 */
public final class AgentConfig implements IAgentConfig
{
    private IAgent _agent;

    private AgentConfigInfo _agentConfigInfo;

    private IObject _config;

    private IProject _project;

    protected AgentConfig(DBManager dbManager)
    {
        _agentConfigInfo = new AgentConfigInfo(dbManager);
    }

    /**
     * Returns the agent.
     * @return The agent
     */
    public final IAgent getAgent()
    {
        return _agent;
    }

    /**
     * Returns the config.
     * @return The config
     */
    public final IObject getConfig()
    {
        return _config;
    }

    /**
     * Returns the agentConfigInfo.
     * @return The agentConfigInfo
     */
    public final IInfo getInfo()
    {
        return _agentConfigInfo;
    }

    /**
     * Returns the project.
     * @return The project
     */
    public final IProject getProject()
    {
        return _project;
    }

    /**
     * Set the value of agent field.
     * @param agent The agent to set
     */
    public final void setAgent(IAgent agent)
    {
        _agent = agent;
        ((AbstractAgent) _agent).setAgentConfig(this);
        _agentConfigInfo.setAgentId(((AbstractAgent) _agent).getInfo().getId());
    }

    /**
     * Set the value of config field.
     * @param config The config to set
     */
    public final void setConfig(IObject config)
    {
        _config = config;
    }

    /**
     * Set the value of project field.
     * @param project The project to set
     */
    public final void setProject(IProject project)
    {
        _project = project;
        _agentConfigInfo.setProjectId(_project.getInfo().getId());
    }

    @Override
    public String toString()
    {
        return ((AgentInfo) ((AbstractAgent) _agent).getInfo()).getName();
    }
    
}
