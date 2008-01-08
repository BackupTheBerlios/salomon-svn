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

import junit.framework.TestCase;
import salomon.agent.IAgentDecisionComponent;
import salomon.agent.IAgentDecisionComponentInfo;
import salomon.engine.DAOContext;
import salomon.engine.DAOTestHelper;

/**
 * 
 */
public class AgentDecisionComponentInfoDAOTest extends TestCase
{
    private IAgentDecisionComponentInfoDAO componentDAO = (IAgentDecisionComponentInfoDAO) DAOContext.getBean("agentDecisionComponentInfoDAO");

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentInfoDAO#getAgentDecisionComponent(java.lang.String)}.
     */
    public void testGetAgentDecisionComponentString()
    {
        AgentDecisionComponentInfo comp = DAOTestHelper.createTestAgentDecisionComponentInfo(false);

        AgentDecisionComponentInfo inserted = (AgentDecisionComponentInfo) componentDAO.getAgentDecisionComponentInfo(comp.getComponentName());
        assertNotNull(inserted);
        assertEquals(comp.getComponentName(), inserted.getComponentName());
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentInfoDAO#getAgentDecisionComponents()}.
     */
    public void testGetAgentDecisionComponents()
    {
        // make sure at least one component exists
        DAOTestHelper.createTestAgentDecisionComponentInfo(false);

        IAgentDecisionComponentInfo[] comps = componentDAO.getAgentDecisionComponentInfos();
        assertNotNull(comps);
        assertTrue(comps.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentInfoDAO#remove(salomon.agent.IAgentDecisionComponent)}.
     */
    public void testRemove()
    {
        AgentDecisionComponentInfo comp = DAOTestHelper.createTestAgentDecisionComponentInfo(true);
        comp.setComponentName("component-to-remove");
        componentDAO.save(comp);

        IAgentDecisionComponentInfo removed = componentDAO.getAgentDecisionComponentInfo(comp.getComponentId());
        assertNotNull(removed);

        componentDAO.remove(removed);
        removed = componentDAO.getAgentDecisionComponentInfo(comp.getComponentId());
        assertNull(removed);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDecisionComponentInfoDAO#save(salomon.agent.IAgentDecisionComponent)}.
     */
    public void testSave()
    {
        AgentDecisionComponentInfo comp = DAOTestHelper.createTestAgentDecisionComponentInfo(true);
        componentDAO.save(comp);

        AgentDecisionComponentInfo inserted = (AgentDecisionComponentInfo) componentDAO.getAgentDecisionComponentInfo(comp.getComponentId());
        assertNotNull(inserted);
        assertEquals(comp.getComponentName(), inserted.getComponentName());
    }

}
