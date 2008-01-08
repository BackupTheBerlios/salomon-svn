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

import salomon.agent.IAgentDecisionComponentInfo;

/**
 * 
 */
public class AgentDecisionComponentInfoDAO extends HibernateDaoSupport
        implements IAgentDecisionComponentInfoDAO
{

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentInfoDAO#getAgentDecisionComponentInfo(java.lang.Long)
     */
    public IAgentDecisionComponentInfo getAgentDecisionComponentInfo(Long id)
    {
        List list = getHibernateTemplate().find(
                "from AgentDecisionComponentInfo adc where adc.componentId = ?",
                new Long[]{id});
        return (IAgentDecisionComponentInfo) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentInfoDAO#getAgentDecisionComponentInfo(java.lang.String)
     */
    public IAgentDecisionComponentInfo getAgentDecisionComponentInfo(
            String componentName)
    {
        List list = getHibernateTemplate().find(
                "from AgentDecisionComponentInfo adc where adc.componentName = ?",
                new String[]{componentName});
        return (IAgentDecisionComponentInfo) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentInfoDAO#getAgentDecisionComponentInfos()
     */
    @SuppressWarnings("unchecked")
    public IAgentDecisionComponentInfo[] getAgentDecisionComponentInfos()
    {
        List list = getHibernateTemplate().find("from AgentDecisionComponentInfo");
        return (AgentDecisionComponentInfo[]) list.toArray(new AgentDecisionComponentInfo[list.size()]);
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentInfoDAO#remove(salomon.agent.IAgentDecisionComponent)
     */
    public void remove(IAgentDecisionComponentInfo component)
    {
        getHibernateTemplate().delete(component);
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentInfoDAO#save(salomon.agent.IAgentDecisionComponent)
     */
    public void save(IAgentDecisionComponentInfo component)
    {
        getHibernateTemplate().saveOrUpdate(component);
    }

}
