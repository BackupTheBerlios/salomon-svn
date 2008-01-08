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
import salomon.agent.IAgent;
import salomon.engine.DAOContext;
import salomon.engine.DAOTestHelper;

/**
 * 
 */
public class AgentDAOTest extends TestCase
{
    private IAgentDAO agentDAO = (IAgentDAO) DAOContext.getBean("agentDAO");

    /**
     * Test method for {@link salomon.engine.agent.AgentDAO#getAgent(java.lang.String)}.
     */
    public void testGetAgentString()
    {
        Agent agent = DAOTestHelper.createTestAgent(false);

        IAgent inserted = agentDAO.getAgent(agent.getAgentName());
        assertNotNull(inserted);
        assertEquals(agent.getAgentName(), inserted.getAgentName());
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDAO#getAgents()}.
     */
    public void testGetAgents()
    {
        // make sure at least one agent exists
        DAOTestHelper.createTestAgent(false);

        IAgent[] agents = agentDAO.getAgents();
        assertNotNull(agents);
        assertTrue(agents.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDAO#remove(salomon.agent.IAgent)}.
     */
    public void testRemove()
    {
        Agent agent = DAOTestHelper.createTestAgent(true);
        agent.setAgentName("agent-to-remove");
        agentDAO.save(agent);

        IAgent removed = agentDAO.getAgent(agent.getAgentId());
        assertNotNull(removed);

        agentDAO.remove(agent);
        removed = agentDAO.getAgent(agent.getAgentId());
        assertNull(removed);

    }

    /**
     * Test method for {@link salomon.engine.agent.AgentDAO#save(salomon.agent.IAgent)}.
     */
    public void testSave()
    {
        Agent agent = DAOTestHelper.createTestAgent(true);
        AgentProcessingComponent procComp = (AgentProcessingComponent) agent.getAgentProcessingComponent();
        AgentDecisionComponentInfo decComp = (AgentDecisionComponentInfo) agent.getAgentDecisionComponentInfo();

        agentDAO.save(agent);

        Agent inserted = (Agent) agentDAO.getAgent(agent.getAgentId());
        assertNotNull(inserted);
        assertEquals(agent.getAgentName(), inserted.getAgentName());
        assertEquals(
                procComp.getComponentName(),
                ((AgentProcessingComponent) inserted.getAgentProcessingComponent()).getComponentName());
        assertEquals(
                decComp.getComponentName(),
                ((AgentDecisionComponentInfo) inserted.getAgentDecisionComponentInfo()).getComponentName());

        // modify the name of the components (but they should be still the same components, with the same id)
        procComp.setComponentName("sampleProcessingComponent2");
        decComp.setComponentName("sampleDecisionComponent2");
        agent.setAgentName("sampleAgent2");
        agentDAO.save(agent);
        Agent inserted2 = (Agent) agentDAO.getAgent(agent.getAgentId());
        assertNotNull(inserted2);
        assertEquals("sampleAgent2", inserted2.getAgentName());
        assertEquals(inserted.getAgentId(), inserted2.getAgentId());
        assertEquals(inserted.getAgentDecisionComponentInfo(),
                inserted2.getAgentDecisionComponentInfo());
        assertEquals(inserted.getAgentProcessingComponent(),
                inserted2.getAgentProcessingComponent());
        assertEquals(
                "sampleProcessingComponent2",
                ((AgentProcessingComponent) inserted2.getAgentProcessingComponent()).getComponentName());
        assertEquals(
                "sampleDecisionComponent2",
                ((AgentDecisionComponentInfo) inserted2.getAgentDecisionComponentInfo()).getComponentName());
    }
}
