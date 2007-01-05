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

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AgentManager implements IAgentManager
{
    private static final Logger LOGGER = Logger.getLogger(AgentManager.class);

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
    public IAgent createAgent(AgentInfo agentInfo)
    {
        IAgent agent = null;
        try {
            Class agentClass = Class.forName(agentInfo.getAgentClass());
            Constructor constructor = agentClass.getConstructor(new Class[]{AgentInfo.class});
            agent = (IAgent) constructor.newInstance(agentInfo);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return agent;
    }

    /**
     * @see salomon.engine.agent.IAgentManager#getAgents()
     */
    public IAgent[] getAgents()
    {
        SQLSelect select = new SQLSelect();
        select.addTable(AgentInfo.TABLE_NAME);

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
            throw new DBException(e.getLocalizedMessage());
        }

        IAgent[] agentArray = new IAgent[agentArrayList.size()];
        agentArray = agentArrayList.toArray(agentArray);

        return agentArray;
    }

}
