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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.agent.IAgentDecisionComponent;
import salomon.engine.ComponentLoader;
import salomon.engine.SalomonEngineContext;
import salomon.platform.exception.PlatformException;

/**
 * @author Nikodem.Jura
 * 
 */
public class AgentRunner implements IAgentRunner {

	private Map<String, RunnableAgent> _agentMap;

	static final Logger LOGGER = Logger.getLogger(AgentRunner.class);

	private static ComponentLoader _agentLoader = (ComponentLoader) SalomonEngineContext
			.getBean("agentLoader");

	public AgentRunner(Set<Agent> agentSet) {
		_agentMap = new HashMap<String, RunnableAgent>();
		try {
			initialize(agentSet);
		} catch (Exception e) {
			LOGGER.error("Agents could not be initialized", e);
			throw new PlatformException("Agents could not be initialized", e);
		}
	}

	// TODO: make it public and move to the interface?
	// TODO: deal with Exceptions
	private void initialize(Set<Agent> agentSet) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		for (Agent agent : agentSet) {
			IAgentDecisionComponent dc = (IAgentDecisionComponent) _agentLoader
					.loadComponent(agent.getAgentDecisionComponentInfo()
							.getComponentName());
			RunnableAgent ra = new RunnableAgent(agent);
			ra.setAgentDecisionComponent(dc);
			_agentMap.put(agent.getAgentName(), ra);
		}
	}

	/**
	 * @see salomon.engine.agent.IAgentRunner#getAgent(java.lang.String)
	 */
	public RunnableAgent getAgent(String name) {
		return _agentMap.get(name);
	}

	/**
	 * @see salomon.engine.agent.IAgentRunner#getAgents()
	 */
	public RunnableAgent[] getAgents() {
		return _agentMap.values().toArray(
				new RunnableAgent[_agentMap.values().size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.agent.IAgentRunner#start()
	 */
	public void start() {
		// FIXME:
		throw new UnsupportedOperationException(
				"Method AgentRunner.start() is not implemented yet!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.agent.IAgentRunner#stop()
	 */
	public void stop() {
		// FIXME:
		throw new UnsupportedOperationException(
				"Method AgentRunner.stop() is not implemented yet!");
	}

}
