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

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import salomon.agent.IAgent;

/**
 * 
 */
public class AgentDAO extends HibernateDaoSupport implements IAgentDAO
{

    /**
     * @see salomon.engine.agent.IAgentDAO#getAgent(java.lang.Long)
     */
    public IAgent getAgent(Long id)
    {
        List list = getHibernateTemplate().find(
                "from Agent a where a.agentId = ?", new Long[]{id});
        return (IAgent) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDAO#getAgent(java.lang.String)
     */
    public IAgent getAgent(String agentName)
    {
        List list = getHibernateTemplate().find(
                "from Agent a where a.agentName = ?", new String[]{agentName});
        return (IAgent) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDAO#getAgents()
     */
    @SuppressWarnings("unchecked")
    public IAgent[] getAgents()
    {
        List list = getHibernateTemplate().find("from Agent");
        return (Agent[]) list.toArray(new Agent[list.size()]);
    }

    /**
     * @see salomon.engine.agent.IAgentDAO#remove(salomon.agent.IAgent)
     */
    public void remove(IAgent agent)
    {
        getHibernateTemplate().delete(agent);
    }

    /**
     * @see salomon.engine.agent.IAgentDAO#save(salomon.agent.IAgent)
     */
    public void save(IAgent agent)
    {
        getHibernateTemplate().saveOrUpdate(agent);
    }

}
