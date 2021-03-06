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

package salomon.engine.platform;

import salomon.engine.agent.IAgentManager;
import salomon.engine.domain.IDomainManager;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.project.IProjectManager;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

/**
 * 
 */
public interface IManagerEngine
{
    /**
     * Returns the PluginManager.
     * @return The PluginManager.
     * 
     * @pre $none
     * @post $result != null
     */
    IPluginManager getPluginManager() throws PlatformException;

    /**
     * @deprecated Use {@link salomon.engine.domain.IDomain#getProjectManager()}
     * 
     * Returns the ProjectManager.
     * @return The ProjectManager.
     * 
     * @pre $none
     * @post $result != null
     */
    @Deprecated
    IProjectManager getProjectManager() throws PlatformException;

    /**
     * Returns the DomainManager.
     * @return The DomainManager
     * 
     * @pre $none
     * @post $result != null
     */
    IDomainManager getSolutionManager() throws PlatformException;

    /**
     * @deprecated Use {@link salomon.engine.project.IProject#getTaskManager()}
     * 
     * Returns the TaskManager.
     * @return The TaskManager
     * 
     * @pre $none
     * @post $result != null
     */
    @Deprecated
    ITaskManager getTasksManager() throws PlatformException;

    //FIXME: move to spring context
    IPlatformUtil getPlatformUtil();

    /**
     * Returns the AgentManager.
     * 
     * @return
     */
    IAgentManager getAgentManager();
    
}