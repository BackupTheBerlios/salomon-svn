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
            IAgent[] agents = project.getAgents();
            if (agents != null) {
                for (IAgent agent : agents) {
                    LOGGER.debug("agent: " + agent);
                    LOGGER.debug("config: " + agent.getAgentConfig());
                }
            } else {
                LOGGER.debug("No agents for project: " + project.getInfo());
            }
        }
    }

    private static Logger LOGGER = Logger.getLogger(DescriptionTest.class);

}