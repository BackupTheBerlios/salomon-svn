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

import salomon.engine.agent.IAgent;
import salomon.engine.agent.IAgentManager;
import salomon.engine.task.ITaskManager;

import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/**
 * An interface for projects.
 */
public interface IProject
{
    /**
     * @return Returns the info.
     */
    IInfo getInfo() throws PlatformException;

    /**
     * Returns the TaskManager.
     *
     * @return The TaskManager
     * @throws PlatformException
     * @pre $none
     * @post $result != null
     */
    ITaskManager getTaskManager() throws PlatformException;

    /**
     * Returns ProjectManager managing this project.
     *
     * @return
     * @throws PlatformException
     */
    IProjectManager getProjectManager() throws PlatformException;

    /**
     * Adds a new agent to listen to database events.
     * 
     * @param agent
     */
    void addAgent(IAgent agent);

    /**
     * Removes given agent.
     * 
     * @param agent
     */
    void removeAgent(IAgent agent);

    /**
     * Returns all agents registered for a project.
     * 
     * @return
     */
    IAgent[] getAgents();
}