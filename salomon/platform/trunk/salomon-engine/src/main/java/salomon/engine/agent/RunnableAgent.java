/*
 * Copyright (C) 2008 Salomon Team
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
/**
 * 
 */
package salomon.engine.agent;

import salomon.agent.IAgent;
import salomon.agent.IAgentDecisionComponent;

/**
 * @author Nikodem.Jura
 * 
 */
public class RunnableAgent implements IRunnableAgent {

	private IAgent _agent;

	private IAgentDecisionComponent _agentDecisionComponent;

	public RunnableAgent(IAgent agent) {
		_agent = agent;
	}

	public IAgentDecisionComponent getAgentDecisionComponent() {
		return _agentDecisionComponent;
	}

	public void setAgentDecisionComponent(IAgentDecisionComponent component) {
		_agentDecisionComponent = component;
	}

	public boolean isStarted() {
		throw new UnsupportedOperationException(
				"Method RunnableAgent.isStarted() is not implemented yet!");
	}

	public void start() {
		throw new UnsupportedOperationException(
				"Method RunnableAgent.start() is not implemented yet!");

	}

	public void stop() {
		throw new UnsupportedOperationException(
				"Method RunnableAgent.stop() is not implemented yet!");
	}

	public IAgent getAgent() {
		return _agent;
	}

}
