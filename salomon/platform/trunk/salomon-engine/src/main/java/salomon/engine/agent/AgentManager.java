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

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import salomon.agent.IAgent;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AgentManager implements IAgentManager
{
    static final Logger LOGGER = Logger.getLogger(AgentManager.class);

    private DBManager _dbManager;

    public AgentManager(DBManager dbManager)
    {
        _dbManager = dbManager;
    }

    /**
     * Creates agent basing on given agent info.
     * 
     * @param agentInfo
     * @return
     * @throws Exception
     */
    public IAgent createAgent(IInfo agentInfo)
    {
        IAgent agent = null;
        try {
            Class agentClass = Class.forName(((AgentInfo) agentInfo).getAgentClass());
            Constructor constructor = agentClass.getConstructor(new Class[]{AgentInfo.class});
            agent = (IAgent) constructor.newInstance(agentInfo);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return agent;
    }

    /**
     * @see salomon.agent.IAgentManager#getAgent(int)
     */
    public IAgent getAgent(int agentId)
    {
        IAgent[] agents = getAgents(agentId);
        IAgent agent = agents.length > 0 ? agents[0] : null;
        return agent;
    }

    /**
     * @see salomon.agent.IAgentManager#getAgents()
     */
    public IAgent[] getAgents()
    {
        return getAgents(-1);
    }

    private IAgent[] getAgents(int agentId)
    {
        SQLSelect select = new SQLSelect();
        select.addTable(AgentInfo.TABLE_NAME);
        if (agentId > 0) {
            select.addCondition(AgentInfo.PRIMARY_KEY + " =", agentId);
        }

        ResultSet resultSet = null;
        ArrayList<IAgent> agentArrayList = new ArrayList<IAgent>();

        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                AgentInfo agentInfo = new AgentInfo();
                agentInfo.load(resultSet);
                IAgent agent = createAgent(agentInfo);
                agentArrayList.add(agent);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        IAgent[] agentArray = new IAgent[agentArrayList.size()];
        agentArray = agentArrayList.toArray(agentArray);

        return agentArray;
    }
}
