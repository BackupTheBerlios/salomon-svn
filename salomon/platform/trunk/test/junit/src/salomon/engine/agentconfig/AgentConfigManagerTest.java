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

package salomon.engine.agentconfig;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.TestObjectFactory;
import salomon.engine.agent.IAgent;
import salomon.engine.project.IProject;
import salomon.engine.solution.Solution;

public class AgentConfigManagerTest extends TestCase
{
    private static final Logger LOGGER = Logger.getLogger(AgentConfigManagerTest.class);

    static {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
    }

    private IAgent _agent;

    private IAgentConfigManager _agentConfigManager;

    private IProject _project;
    
    private static final String SOLUTION_NAME = "Example";

    public AgentConfigManagerTest()
    {
        _agentConfigManager = TestObjectFactory.getManagerEngine().getAgentConfigManager();
        _agent = TestObjectFactory.getManagerEngine().getAgentManager().getAgents()[0];
        Solution solution = (Solution) TestObjectFactory.getSolution(SOLUTION_NAME); 
        _project = solution.getProjectManager().getProjects()[0];
    }

    public void testAddAgentConfig() throws Exception
    {
        LOGGER.info("AgentConfigManagerTest.testAddAgentConfig()");
        AgentConfig agentConfig = createAgentConfig();
        _agentConfigManager.addAgentConfig(agentConfig);

        TestObjectFactory.getDbManager().commit();
    }

    public void testGetAgentConfigs() throws Exception
    {
        LOGGER.info("AgentConfigManagerTest.enclosing_method()");
        IAgentConfig[] configs = _agentConfigManager.getAgentConfigs(_project.getInfo().getId());
        for (IAgentConfig config : configs) {
            LOGGER.debug("config: " + config);
        }
    }

    public void testRemoveAgentConfig() throws Exception
    {
        LOGGER.info("AgentConfigManagerTest.testRemoveAgentConfig()");
        AgentConfig agentConfig = createAgentConfig();
        _agentConfigManager.addAgentConfig(agentConfig);
        _agentConfigManager.removeAgentConfig(agentConfig);

        TestObjectFactory.getDbManager().commit();
    }

    private AgentConfig createAgentConfig()
    {
        AgentConfig agentConfig = (AgentConfig) _agentConfigManager.createAgentConfig();
        AgentConfigInfo info = (AgentConfigInfo) agentConfig.getInfo();
        info.setAgentId(_agent.getInfo().getId());
        info.setProjectId(_project.getInfo().getId());
        info.setConfiguration("Sample configutation");
        return agentConfig;
    }
}
