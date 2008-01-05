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
package salomon.engine;

import junit.framework.TestCase;
import salomon.agent.IAgent;
import salomon.engine.agent.AgentDecisionComponent;
import salomon.engine.agent.AgentProcessingComponent;
import salomon.engine.agent.IAgentManager;
import salomon.engine.domain.DomainManager;
import salomon.engine.domain.IDomain;
import salomon.engine.domain.IDomainManager;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;

/**
 * @author Nikodem.Jura
 * 
 */
public class EngineTest extends TestCase {

	private static final long TEST_SUFFIX = System.currentTimeMillis();

	private static final String TEST_DOMAIN_NAME = "test-domain-name-"
			+ TEST_SUFFIX;

	private static final String TEST_PROJECT_NAME = "test-project-name"
			+ TEST_SUFFIX;

	private static final String DUMMY_AGENT_DECISIONING_COMP = "salomon.agent.DummyDecisionComponent";

	private static final String TEST_AGENT_NAME = "test-agent-name"
			+ TEST_SUFFIX;

	private IDomainManager _domainManager = new DomainManager();

	public void testEngine() {
		// create Domain
		addDomain();

		// load Domain and add project to it
		addProject();

		// load project and add agent to it
		addAgent();
		
		// load agent
		loadAgent();
		
		// TODO:
		// add tasks to agent processing part (load appropriate plugins)
		// start agent (processing part)
		// simulate generating a message
		
	}

	private void loadAgent() {
		IProject project = getProject(getDomain());
		IAgent agent = project.getAgentManager().getAgent(TEST_AGENT_NAME);
		assertNotNull(agent);
		assertNotNull(agent.getAgentDecisionComponent());
		// load agent decisioning component
		agent.load();
		assertEquals(DUMMY_AGENT_DECISIONING_COMP, agent.getAgentDecisionComponent().getClass().getName());
	}

	private void addAgent() {
		IDomain domain = getDomain();

		IProject project = getProject(domain);

		IAgentManager agentManager = project.getAgentManager();
		IAgent agent = agentManager.createAgent();
		agent.setAgentName(TEST_AGENT_NAME);

		AgentDecisionComponent decisionComponent = new AgentDecisionComponent();
		decisionComponent.setComponentName(DUMMY_AGENT_DECISIONING_COMP);
		agent.setAgentDecisionComponent(decisionComponent);
		agent.setAgentProcessingComponent(new AgentProcessingComponent());

		agentManager.addAgent(agent);
		
		// FIXME:
		_domainManager.addDomain(domain);
	}

	private IProject getProject(IDomain domain) {
		IProject project = domain.getProjectManager().getProject(
				TEST_PROJECT_NAME);
		assertNotNull(project);
		assertEquals(TEST_PROJECT_NAME, project.getProjectName());
		return project;
	}

	private void addDomain() {
		IDomain domain = _domainManager.createDomain();
		domain.setDomainName(TEST_DOMAIN_NAME);
		_domainManager.addDomain(domain);
	}

	private void addProject() {

		IDomain domain = getDomain();
		// create project
		IProjectManager projectManager = domain.getProjectManager();
		IProject project = projectManager.createProject();
		project.setProjectName(TEST_PROJECT_NAME);
		projectManager.addProject(project);

		// FIXME: whole domain is saved, only the project should be saved
		// it must be reimplemented -- the whole object tree cannot be saved
		// after any change
		_domainManager.addDomain(domain);
	}

	private IDomain getDomain() {
		// load domain
		IDomain domain = _domainManager.getDomain(TEST_DOMAIN_NAME);
		assertNotNull(domain);
		assertEquals(TEST_DOMAIN_NAME, domain.getDomainName());

		return domain;
	}
}
