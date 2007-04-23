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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.TestObjectFactory;
import salomon.engine.agent.IAgent;
import salomon.engine.agentconfig.AgentConfig;
import salomon.engine.agentconfig.IAgentConfig;
import salomon.plugin.DescriptionTest;

public class ProjectTest extends TestCase
{
    private ProjectManager _projectManager;

    private static final String SOLUTION_NAME = "Example";

    static {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
    }

    public ProjectTest()
    {
        _projectManager = (ProjectManager) TestObjectFactory.getSolution(
                SOLUTION_NAME).getProjectManager();
    }

    public void testGetAgents() throws Exception
    {
        LOGGER.info("ProjectTest.testGetAgents()");
        IProject[] projects = _projectManager.getProjects();
        for (IProject project : projects) {
            IAgentConfig[] agents = project.getAgentConfigs();
            if (agents != null) {
                for (IAgentConfig agent : agents) {
                    LOGGER.debug("config: " + agent);
                    LOGGER.debug("agent: " + agent.getAgent());
                }
            } else {
                LOGGER.debug("No agents for project: " + project.getInfo());
            }
        }
    }

    public void testAddAgent() throws Exception
    {
        LOGGER.info("ProjectTest.testAddAgent()");
        IProject project = _projectManager.createProject();
        ((Project) project).getInfo().setName(
                "Test agent project " + System.currentTimeMillis());

        IAgentConfig agentConfig = TestObjectFactory.getManagerEngine().getAgentConfigManager().createAgentConfig();
        IAgent agent = TestObjectFactory.getManagerEngine().getAgentManager().getAgents()[0];
        ((AgentConfig)agentConfig).setAgent(agent);
                
        _projectManager.addProject(project);
        // project must be saved before adding agents
        project.addAgentConfig(agentConfig);
    }

    private static Logger LOGGER = Logger.getLogger(DescriptionTest.class);

}