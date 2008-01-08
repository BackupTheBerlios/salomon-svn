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

package salomon.engine;

import salomon.engine.agent.Agent;
import salomon.engine.agent.AgentDecisionComponentInfo;
import salomon.engine.agent.AgentProcessingComponent;
import salomon.engine.agent.IAgentDAO;
import salomon.engine.agent.IAgentDecisionComponentInfoDAO;
import salomon.engine.agent.IAgentProcessingComponentDAO;
import salomon.engine.domain.Domain;
import salomon.engine.domain.DomainDataSource;
import salomon.engine.domain.DomainManager;
import salomon.engine.domain.IDomainDAO;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.project.IProjectDAO;
import salomon.engine.project.Project;
import salomon.engine.task.ITaskDAO;
import salomon.engine.task.Task;

/**
 * 
 */
public class DAOTestHelper {
	private static IAgentDAO agentDAO = (IAgentDAO) DAOContext
			.getBean("agentDAO");

	private static IAgentDecisionComponentInfoDAO decisionComponentDAO = (IAgentDecisionComponentInfoDAO) DAOContext
			.getBean("agentDecisionComponentInfoDAO");

	private static IDomainDAO domainDAO = (IDomainDAO) DAOContext
			.getBean("domainDAO");

	private static IAgentProcessingComponentDAO processingComponentDAO = (IAgentProcessingComponentDAO) DAOContext
			.getBean("agentProcessingComponentDAO");

	private static IProjectDAO projectDAO = (IProjectDAO) DAOContext
			.getBean("projectDAO");

	private static ITaskDAO taskDAO = (ITaskDAO) DAOContext.getBean("taskDAO");

	private static final String TEST_AGENT_NAME = "test-agent";

	private static final String TEST_DOMAIN_NAME = "test-domain";

	private static final String TEST_PROJECT_NAME = "test-project";

	private static final String TEST_TASK_NAME = "test-task";

	private static final String TEST_PROCESSING_COMPONENT_NAME = "test-processing-component";

	private static final String TEST_DECISION_COMPONENT_INFO_NAME = "test-decision-component";

	public static Agent createTestAgent(boolean forceNew) {
		Agent agent = null;
		if (!forceNew) {
			// FIXME: specify the project for the agent
			agent = (Agent) agentDAO.getAgent(TEST_AGENT_NAME);
		}
		if (forceNew || agent == null) {
			agent = new Agent();
			agent.setAgentName(TEST_AGENT_NAME);
			agent
					.setAgentDecisionComponentInfo(createTestAgentDecisionComponentInfo(forceNew));
			agent
					.setAgentProcessingComponent(createTestAgentProcessingComponent(forceNew));
			agent.setProject(createTestProject(forceNew));
			agentDAO.save(agent);
		}
		return agent;
	}

	public static AgentDecisionComponentInfo createTestAgentDecisionComponentInfo(
			boolean forceNew) {
		AgentDecisionComponentInfo decComp = null;
		if (!forceNew) {
			decComp = (AgentDecisionComponentInfo) decisionComponentDAO
					.getAgentDecisionComponentInfo(TEST_DECISION_COMPONENT_INFO_NAME);
		}
		if (forceNew || decComp == null) {
			decComp = new AgentDecisionComponentInfo();
			decComp.setComponentName(TEST_DECISION_COMPONENT_INFO_NAME);
			// Task task = new Task(null);
			// task.setTaskNr(1);
			// task.setTaskName("task");
			// comp.addTask(task);
			decisionComponentDAO.save(decComp);
			return decComp;
		}
		return decComp;
	}

	public static AgentProcessingComponent createTestAgentProcessingComponent(
			boolean forceNew) {
		AgentProcessingComponent procComp = null;
		if (!forceNew) {
			procComp = (AgentProcessingComponent) processingComponentDAO
					.getAgentProcessingComponent(TEST_PROCESSING_COMPONENT_NAME);
		}
		if (forceNew || procComp == null) {
			procComp = new AgentProcessingComponent();
			procComp.setComponentName(TEST_PROCESSING_COMPONENT_NAME);
			// Task task = new Task(null);
			// task.setTaskNr(1);
			// task.setTaskName("task");
			// comp.addTask(task);
			processingComponentDAO.save(procComp);
			return procComp;
		}
		return procComp;
	}

	public static Domain createTestDomain(boolean forceNew) {
		Domain domain = null;
		if (!forceNew) {
			domain = (Domain) domainDAO.getDomain(TEST_DOMAIN_NAME);
		}
		if (forceNew || domain == null) {
			// FIXME:
			domain = (Domain) new DomainManager(null).createDomain();
			domain.setDomainName(TEST_DOMAIN_NAME);
			DomainDataSource dds = new DomainDataSource();
			dds.setUrl("file://test/url");
			dds.setUserName("test-user");
			dds.setPassword("test-password");
			domain.setDomainDataSource(dds);
			domainDAO.save(domain);
		}

		return domain;
	}

	public static Project createTestProject(boolean forceNew) {
		Project project = null;
		if (!forceNew) {
			project = (Project) projectDAO.getProject(TEST_PROJECT_NAME);
		}

		if (forceNew || project == null) {
			project = new Project();
			project.setProjectName(TEST_PROJECT_NAME);
			project.setDomain(createTestDomain(forceNew));
			projectDAO.save(project);
		}
		return project;
	}

	public static Task createTestTask(boolean forceNew) {
		Task task = null;
		if (!forceNew) {
			// FIXME: specify the agent processing component for the task
			task = (Task) taskDAO.getTask(TEST_TASK_NAME);
		}
		if (forceNew || task == null) {
			task = new Task();
			task.setTaskName(TEST_PROJECT_NAME);
			task
					.setAgentProcessingComponent(createTestAgentProcessingComponent(forceNew));
			task.setPluginInfo(new PluginInfo("dummy.Plugin"));
			taskDAO.save(task);
		}

		return task;
	}
}
