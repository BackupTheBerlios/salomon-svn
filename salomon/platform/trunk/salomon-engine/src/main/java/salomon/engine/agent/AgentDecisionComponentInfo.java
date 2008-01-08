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

import salomon.agent.IAgentDecisionComponentInfo;

/**
 * @author Nikodem.Jura
 * 
 */
public class AgentDecisionComponentInfo implements IAgentDecisionComponentInfo {

	private Long _componentId;

	private String _componentName;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AgentDecisionComponentInfo) {
			AgentDecisionComponentInfo comp = (AgentDecisionComponentInfo) obj;
			return _componentId.equals(comp.getComponentId());
		}
		return false;
	}

	/**
	 * Returns the componentId.
	 * 
	 * @return The componentId
	 */
	public Long getComponentId() {
		return _componentId;
	}

	/**
	 * @see salomon.agent.IAgentDecisionComponent#getComponentName()
	 */
	public String getComponentName() {
		return _componentName;
	}

	@Override
	public int hashCode() {
		return _componentId.hashCode();
	}

	/**
	 * Set the value of _componentName field.
	 * 
	 * @param _componentName
	 *            The _componentName to set
	 */
	public void setComponentName(String componentName) {
		_componentName = componentName;
	}

	/**
	 * Set the value of componentId field.
	 * 
	 * @param componentId
	 *            The componentId to set
	 */
	@SuppressWarnings("unused")
	private void setComponentId(Long componentId) {
		_componentId = componentId;
	}

}
