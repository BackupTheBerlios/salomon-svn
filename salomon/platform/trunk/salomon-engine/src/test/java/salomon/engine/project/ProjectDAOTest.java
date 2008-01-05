/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.project;

import junit.framework.TestCase;
import salomon.agent.IAgent;
import salomon.engine.DAOContext;
import salomon.engine.DAOTestHelper;
import salomon.engine.agent.Agent;

/**
 * 
 */
public class ProjectDAOTest extends TestCase
{
    private IProjectDAO projectDAO = (IProjectDAO) DAOContext.getBean("projectDAO");

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#getProject(java.lang.String)}.
     */
    public void testGetProjectString()
    {
        Project project = DAOTestHelper.createTestProject(false);

        IProject inserted = projectDAO.getProject(project.getProjectName());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
    }

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#getProjects()}.
     */
    public void testGetProjects()
    {
        // make sure at least one domain exists
        DAOTestHelper.createTestProject(false);

        IProject[] projects = projectDAO.getProjects();
        assertNotNull(projects);
        assertTrue(projects.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#remove(salomon.engine.project.IProject)}.
     */
    public void testRemove()
    {
        Project project = DAOTestHelper.createTestProject(true);
        project.setProjectName("project-to-remove");
        projectDAO.save(project);

        IProject removed = projectDAO.getProject(project.getProjectId());
        assertNotNull(removed);

        projectDAO.remove(project);
        removed = projectDAO.getProject(project.getProjectId());
        assertNull(removed);
    }

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#save(salomon.engine.project.IProject)}.
     */
    public void testSave()
    {
        Project project = DAOTestHelper.createTestProject(true);
        projectDAO.save(project);

        IProject inserted = projectDAO.getProject(project.getProjectId());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
    }

    public void testSaveWithAgents()
    {
        Project project = DAOTestHelper.createTestProject(true);
        project.setProjectName("test-save-with-agents");
        Agent agent = new Agent();
        agent.setAgentName("test-agent-for-project");
        project.getAgentManager().addAgent(agent);
        projectDAO.save(project);

        IProject inserted = projectDAO.getProject(project.getProjectId());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
        // test getting all agents
        assertNotNull(inserted.getAgentManager().getAgents());
        assertTrue(inserted.getAgentManager().getAgents().length >= 1);
        assertEquals(agent.getAgentName(),
                inserted.getAgentManager().getAgents()[0].getAgentName());

        // test getting particular agent (by id)
        IAgent insertedAgent = inserted.getAgentManager().getAgent(agent.getAgentId());
        assertNotNull(insertedAgent);
        assertEquals(agent.getAgentName(), insertedAgent.getAgentName());
        // test getting particular agent (by name)
        insertedAgent = inserted.getAgentManager().getAgent(agent.getAgentName());
        assertNotNull(insertedAgent);
        assertEquals(agent.getAgentName(), insertedAgent.getAgentName());
    }

}
