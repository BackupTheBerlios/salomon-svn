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
import salomon.communication.ICommunicationBus;
import salomon.communication.IMessageEvent;
import salomon.engine.SalomonEngineContext;
import salomon.engine.project.Project;
import salomon.platform.IVariable;
import salomon.platform.message.IMessageMetadata;
import salomon.platform.serialization.ILong;

/**
 * 
 */
public class AgentTest extends TestCase {
	
	private static ICommunicationBus _communicationBus = (ICommunicationBus) SalomonEngineContext.getBean("communicationBus");
	
	public void testAgentRun() throws Exception {
		// FIXME: use Spring
		// the project should be obtained from ProjectManager, but in this case
		// it's irrelevant
		AgentManager agentManager = new AgentManager(new Project());
		Agent agent = (Agent) agentManager.createAgent();
		AgentDecisionComponentInfo dci = new AgentDecisionComponentInfo();
		dci.setComponentName("salomon.agent.DummyDecisionComponent");
		agent.setAgentDecisionComponentInfo(dci);
		agent.setAgentName("test-agent");
		agentManager.addAgent(agent);

		// start agents
		agentManager.getAgentRunner().start();

		// fire the message event to make the DummyDecisionComponent start
		// processing
		IMessageEvent messageEvent = new IMessageEvent() {
			public IMessageMetadata getMessageMetadata() {
				return new IMessageMetadata() {
					public long getMessageId() {
						return 911L;
					}
				};
			}
		};

		// fire the event
		_communicationBus.fireCommunicationEvent(messageEvent);

		// wait a while until the event is processed by a component
		Thread.sleep(1000);

		// check if the message-id was added to the environment (simple
		// check if decisionComponent added the variable)
		IVariable variable = agent.getAgentProcessingComponent()
				.getEnvironment().getVariable("dummy-message-id");
		assertNotNull(variable);
		assertTrue(variable.getValue() instanceof ILong);
		assertEquals(911L, variable.getValue().getValue());
	}
}
