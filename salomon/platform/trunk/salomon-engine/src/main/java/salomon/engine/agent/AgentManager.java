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

package salomon.engine.agent;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.agent.IAgent;
import salomon.engine.project.Project;

/**
 * 
 */
public final class AgentManager implements IAgentManager {
	static final Logger LOGGER = Logger.getLogger(AgentManager.class);

	private Set<Agent> _agentSet;

	private Project _project;

	public AgentManager(Project project) {
		_project = project;
		_agentSet = new HashSet<Agent>();
	}

	/**
	 * Creates an agent.
	 * 
	 * @param agentInfo
	 * @return
	 * @throws Exception
	 */
	public IAgent createAgent() {
		Agent agent = new Agent();
		agent.setProject(_project);
		return agent;
	}

	/**
	 * @see salomon.engine.project.IProject#getAgent(long)
	 */
	public IAgent getAgent(long agentId) {
		IAgent agent = null;
		for (Agent a : _agentSet) {
			if (agentId == a.getAgentId()) {
				agent = a;
				break;
			}
		}
		return agent;
	}

	/**
	 * @see salomon.engine.project.IProject#addAgent(salomon.agent.IAgent)
	 */
	public void addAgent(IAgent agent) {
		// TODO:
		// this statement is redundant, project should be set by calling
		// AgentManager.createAgent()
		((Agent) agent).setProject(_project);
		_agentSet.add((Agent) agent);
	}

	/**
	 * @see salomon.engine.project.IProject#getAgent(java.lang.String)
	 */
	public IAgent getAgent(String agentName) {
		IAgent agent = null;
		for (Agent a : _agentSet) {
			if (agentName.equals(a.getAgentName())) {
				agent = a;
				break;
			}
		}
		return agent;
	}

	/**
	 * @see salomon.engine.project.IProject#getAgents()
	 */
	public IAgent[] getAgents() {
		return _agentSet.toArray(new Agent[_agentSet.size()]);
	}

	/**
	 * Set the value of agentSet field.
	 * 
	 * @param agentSet
	 *            The agentSet to set
	 */
	public void setAgentSet(Set<Agent> agentSet) {
		_agentSet = agentSet;
	}

	/**
	 * Returns the agentSet.
	 * 
	 * @return The agentSet
	 */
	public Set<Agent> getAgentSet() {
		return _agentSet;
	}

	public void removeAgent(IAgent agent) {
		_agentSet.remove(agent);
	}

	public IAgentRunner getAgentRunner() {
		// TODO: optimize:
		AgentRunner runner = new AgentRunner(_agentSet);
		return runner;
	}

}
