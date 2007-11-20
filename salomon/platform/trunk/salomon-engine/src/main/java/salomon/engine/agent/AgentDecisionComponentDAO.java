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

/**
 * 
 */
public class AgentDecisionComponentDAO extends HibernateDaoSupport
        implements IAgentDecisionComponentDAO
{

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentDAO#getAgentDecisionComponent(java.lang.Long)
     */
    public IAgentDecisionComponent getAgentDecisionComponent(Long id)
    {
        List list = getHibernateTemplate().find(
                "from AgentDecisionComponent adc where adc.componentId = ?",
                new Long[]{id});
        return (IAgentDecisionComponent) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentDAO#getAgentDecisionComponent(java.lang.String)
     */
    public IAgentDecisionComponent getAgentDecisionComponent(
            String componentName)
    {
        List list = getHibernateTemplate().find(
                "from AgentDecisionComponent adc where adc.componentName = ?",
                new String[]{componentName});
        return (IAgentDecisionComponent) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentDAO#getAgentDecisionComponents()
     */
    @SuppressWarnings("unchecked")
    public IAgentDecisionComponent[] getAgentDecisionComponents()
    {
        List list = getHibernateTemplate().find("from AgentDecisionComponent");
        return (AgentDecisionComponent[]) list.toArray(new AgentDecisionComponent[list.size()]);
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentDAO#remove(salomon.engine.agent.IAgentDecisionComponent)
     */
    public void remove(IAgentDecisionComponent component)
    {
        getHibernateTemplate().delete(component);
    }

    /**
     * @see salomon.engine.agent.IAgentDecisionComponentDAO#save(salomon.engine.agent.IAgentDecisionComponent)
     */
    public void save(IAgentDecisionComponent component)
    {
        getHibernateTemplate().saveOrUpdate(component);
    }

}
