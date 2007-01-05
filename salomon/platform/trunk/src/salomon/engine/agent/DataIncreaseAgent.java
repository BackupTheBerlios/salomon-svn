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

import java.awt.Component;

import salomon.engine.agentconfig.IAgentConfig;
import salomon.engine.agentconfig.IAgentConfigManager;

/**
 * 
 */
public final class DataIncreaseAgent extends AbstractAgent
{
    public DataIncreaseAgent(IAgentConfigManager agentConfigManager,
            AgentInfo agentInfo)
    {
        super(agentConfigManager, agentInfo);
    }

    public IAgentConfig getAgentConfig()
    {
        return _agentConfig;
    }

    /**
     * @see salomon.engine.agent.IAgent#getConfigurationComponent()
     */
    public Component getConfigurationComponent()
    {
        throw new UnsupportedOperationException(
                "Method DataIncreaseAgent.getConfigurationComponent() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgent#start()
     */
    public void start()
    {
        throw new UnsupportedOperationException(
                "Method DataIncreaseAgent.start() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgent#stop()
     */
    public void stop()
    {
        throw new UnsupportedOperationException(
                "Method DataIncreaseAgent.stop() not implemented yet!");
    }

    @Override
    public String toString()
    {
        return _agentInfo.toString();
    }

}
