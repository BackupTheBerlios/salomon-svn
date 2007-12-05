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

import salomon.agent.IAgentProcessingComponent;

/**
 * 
 */
public class AgentProcessingComponentDAO extends HibernateDaoSupport
        implements IAgentProcessingComponentDAO
{

    /**
     * @see salomon.engine.agent.IAgentProcessingComponentDAO#getAgentProcessingComponent(java.lang.Long)
     */
    public IAgentProcessingComponent getAgentProcessingComponent(Long id)
    {
        List list = getHibernateTemplate().find(
                "from AgentProcessingComponent apc where apc.componentId = ?",
                new Long[]{id});
        return (IAgentProcessingComponent) (list.size() == 0
                ? null
                : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponentDAO#getAgentProcessingComponent(java.lang.String)
     */
    public IAgentProcessingComponent getAgentProcessingComponent(
            String componentName)
    {
        List list = getHibernateTemplate().find(
                "from AgentProcessingComponent apc where apc.componentName = ?",
                new String[]{componentName});
        return (IAgentProcessingComponent) (list.size() == 0
                ? null
                : list.get(0));
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponentDAO#getAgentProcessingComponents()
     */
    @SuppressWarnings("unchecked")
    public IAgentProcessingComponent[] getAgentProcessingComponents()
    {
        List list = getHibernateTemplate().find("from AgentProcessingComponent");
        return (AgentProcessingComponent[]) list.toArray(new AgentProcessingComponent[list.size()]);
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponentDAO#remove(salomon.agent.IAgentProcessingComponent)
     */
    public void remove(IAgentProcessingComponent component)
    {
        getHibernateTemplate().delete(component);
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponentDAO#save(salomon.agent.IAgentProcessingComponent)
     */
    public void save(IAgentProcessingComponent component)
    {
        getHibernateTemplate().saveOrUpdate(component);
    }

}
