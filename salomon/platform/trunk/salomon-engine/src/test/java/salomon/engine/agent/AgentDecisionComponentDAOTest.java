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

import salomon.engine.DAOContext;
import junit.framework.TestCase;

/**
 * 
 */
public class AgentDecisionComponentDAOTest extends TestCase
{
    IAgentDecisionComponentDAO componentDAO = (IAgentDecisionComponentDAO) DAOContext.getBean("agentDecisionComponentDAO");

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentDAO#getAgentDecisionComponent(java.lang.String)}.
     */
    public void testGetAgentDecisionComponentString()
    {
        AgentDecisionComponent comp = new AgentDecisionComponent();
        comp.setComponentName("org.salomon.agent.DecisionComponent");
        componentDAO.save(comp);

        AgentDecisionComponent inserted = (AgentDecisionComponent) componentDAO.getAgentDecisionComponent(comp.getComponentName());
        assertNotNull(inserted);
        assertEquals(comp.getComponentName(), inserted.getComponentName());
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentDAO#getAgentDecisionComponents()}.
     */
    public void testGetAgentDecisionComponents()
    {
        IAgentDecisionComponent[] comps = componentDAO.getAgentDecisionComponents();
        assertNotNull(comps);
        for (IAgentDecisionComponent component : comps) {
            System.out.println(component);
        }
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentDAO#remove(salomon.engine.agent.IAgentDecisionComponent)}.
     */
    public void testRemove()
    {
        AgentDecisionComponent comp = new AgentDecisionComponent();
        comp.setComponentName("org.salomon.agent.DecisionComponent-to-remove");
        componentDAO.save(comp);

        IAgentDecisionComponent removed = componentDAO.getAgentDecisionComponent(comp.getComponentId());
        assertNotNull(removed);

        componentDAO.remove(removed);
        removed = componentDAO.getAgentDecisionComponent(comp.getComponentId());
        assertNull(removed);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentDAO#save(salomon.engine.agent.IAgentDecisionComponent)}.
     */
    public void testSave()
    {
        AgentDecisionComponent comp = new AgentDecisionComponent();
        comp.setComponentName("org.salomon.agent.DecisionComponent");
        componentDAO.save(comp);

        AgentDecisionComponent inserted = (AgentDecisionComponent) componentDAO.getAgentDecisionComponent(comp.getComponentId());
        assertNotNull(inserted);
        assertEquals(comp.getComponentName(), inserted.getComponentName());
    }

}
