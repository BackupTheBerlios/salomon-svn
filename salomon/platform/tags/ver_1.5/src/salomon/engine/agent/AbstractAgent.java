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

package salomon.engine.agent;

import salomon.engine.agentconfig.IAgentConfig;
import salomon.platform.IInfo;

/**
 * 
 */
public abstract class AbstractAgent implements IAgent
{
    protected IAgentConfig _agentConfig;

    protected AgentInfo _agentInfo;

    /**
     * @param agentInfo
     */
    public AbstractAgent(AgentInfo agentInfo)
    {
        _agentInfo = agentInfo;
    }

    /**
     * Returns the agentConfig.
     * @return The agentConfig
     */
    public final IAgentConfig getAgentConfig()
    {
        return _agentConfig;
    }

    /**
     * @see salomon.engine.agent.IAgent#getInfo()
     */
    public final IInfo getInfo()
    {
        return _agentInfo;
    }

    /**
     * Set the value of agentConfig field.
     * @param agentConfig The agentConfig to set
     */
    public final void setAgentConfig(IAgentConfig agentConfig)
    {
        _agentConfig = agentConfig;
    }

    @Override
    public String toString()
    {
        return (_agentInfo == null ? "" : _agentInfo.toString());
    }
}