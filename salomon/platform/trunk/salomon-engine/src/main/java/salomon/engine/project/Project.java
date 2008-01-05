/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.engine.project;

import java.util.Set;

import org.apache.log4j.Logger;

import salomon.agent.IAgent;
import salomon.engine.agent.Agent;
import salomon.engine.agent.AgentManager;
import salomon.engine.database.DBManager;
import salomon.engine.domain.Domain;
import salomon.engine.platform.IManagerEngine;
import salomon.platform.exception.PlatformException;

/**
 * Represents a project, it is an implementation of IProject interface.
 */
public final class Project implements IProject {
	private static final Logger LOGGER = Logger.getLogger(Project.class);

	private AgentManager _agentManager;

	private Domain _domain;

	private Long _projectId;

	private String _projectName;

	public Project() {
		_agentManager = new AgentManager(this);
	}

	@Deprecated
	protected Project(IManagerEngine managerEngine, DBManager manager)
			throws PlatformException {
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Project) {
			Project project = (Project) obj;
			return _projectId.equals(project.getProjectId());
		}
		return false;
	}

	public AgentManager getAgentManager() {
		return _agentManager;
	}

	/**
	 * Returns the domain.
	 * 
	 * @return The domain
	 */
	public Domain getDomain() {
		return _domain;
	}

	@Deprecated
	// FIXME:
	public ProjectInfo getInfo() {
		throw new UnsupportedOperationException(
				"Method Project.getInfo() is deprecated!");
	}

	/**
	 * Returns the projectId.
	 * 
	 * @return The projectId
	 */
	public Long getProjectId() {
		return _projectId;
	}

	@Deprecated
	public IProjectManager getProjectManager() throws PlatformException {
		// FIXME:
		throw new UnsupportedOperationException(
				"Method Project.getProjectManager() is deprecated!");
	}

	/**
	 * Returns the projectName.
	 * 
	 * @return The projectName
	 */
	public String getProjectName() {
		return _projectName;
	}

	@Override
	public int hashCode() {
		return _projectId == null ? 0 : _projectId.hashCode();
	}

	public void removeAgent(IAgent agent) {
		_agentManager.removeAgent(agent);
	}

	/**
	 * Set the value of domain field.
	 * 
	 * @param domain
	 *            The domain to set
	 */
	public void setDomain(Domain domain) {
		_domain = domain;
	}

	/**
	 * Set the value of projectName field.
	 * 
	 * @param projectName
	 *            The projectName to set
	 */
	public void setProjectName(String projectName) {
		_projectName = projectName;
	}

	public void start() {
		throw new UnsupportedOperationException(
				"Method Project.start() is not implemented yet!");
	}

	/**
	 * Returns the agentSet.
	 * 
	 * @return The agentSet
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private Set<Agent> getAgentSet() {
		return _agentManager.getAgentSet();
	}

	/**
	 * Set the value of agentSet field.
	 * 
	 * @param agentSet
	 *            The agentSet to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setAgentSet(Set<Agent> agentSet) {
		_agentManager.setAgentSet(agentSet);
	}

	/**
	 * Set the value of projectId field.
	 * 
	 * @param projectId
	 *            The projectId to set
	 */
	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setProjectId(Long projectId) {
		_projectId = projectId;
	}

}