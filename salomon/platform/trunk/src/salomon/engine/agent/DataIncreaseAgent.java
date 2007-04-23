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

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;

/**
 * 
 */
public final class DataIncreaseAgent extends AbstractAgent
{
    private static final Logger LOGGER = Logger.getLogger(DataIncreaseAgent.class);

    public DataIncreaseAgent(AgentInfo agentInfo)
    {
        super(agentInfo);
    }

    /**
     * @see salomon.engine.agent.IAgent#start(IProject)
     */
    public void start(IProject project)
    {
        LOGGER.info("DataIncreaseAgent.start()");
        try {
            for (int i = 0; i < 10; ++i) {
                Thread.sleep(5000);
                project.start();
            }
        } catch (InterruptedException e) {
            LOGGER.fatal("", e);
        }
    }

    /**
     * @see salomon.engine.agent.IAgent#stop()
     */
    public void stop()
    {
        LOGGER.info("DataIncreaseAgent.stop()");
    }

    @Override
    public String toString()
    {
        return _agentInfo.toString();
    }

}
