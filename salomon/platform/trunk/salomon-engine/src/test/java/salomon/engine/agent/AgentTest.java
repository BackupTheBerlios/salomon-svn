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
import salomon.communication.IMessageEvent;
import salomon.engine.ComponentLoader;
import salomon.engine.SalomonEngineContext;
import salomon.engine.communication.CommunicationBus;
import salomon.platform.IVariable;
import salomon.platform.message.IMessageMetadata;
import salomon.platform.serialization.ILong;

/**
 * 
 */
public class AgentTest extends TestCase
{
    public void testAgentRun() throws Exception
    {
    	ComponentLoader loader = (ComponentLoader) SalomonEngineContext.getBean("agentLoader");
        IAgentDecisionComponent decComp = (IAgentDecisionComponent) loader.loadComponent(
                "salomon.agent.DummyDecisionComponent");
        assertNotNull(decComp);

        // TODO: load the agent via AgentManager
        AgentProcessingComponent procComp = new AgentProcessingComponent();
        // TODO: use single instance loaded via Spring
        procComp.setCommunicationBus(new CommunicationBus());
        Agent agent = new Agent();
        // FIXME: ugly assignments
        decComp.setAgentProcessingComponent(procComp);
        agent.setAgentProcessingComponent(procComp);
        

        // start the agent
//        agent.start();

        //TODO: fire the message event
        IMessageEvent messageEvent = new IMessageEvent() {
            public IMessageMetadata getMessageMetadata()
            {
                return new IMessageMetadata() {
                    public long getMessageId()
                    {
                        return 911L;
                    }
                };
            }
        };

        // get the Bus from Spring Context
        agent.getAgentProcessingComponent().getCommunicationBus().fireCommunicationEvent(messageEvent);
        
        // wait a while until the event is processed
        Thread.sleep(1000);
        
        //TODO: check if the message-id was added to the environment (simple check if decisionComponent added the variable)
        IVariable variable = agent.getAgentProcessingComponent().getEnvironment().getVariable("dummy-message-id");
        assertNotNull(variable);
        assertTrue(variable.getValue() instanceof ILong);
        assertEquals(911L, ((ILong)variable.getValue()).getValue());
    }
}
