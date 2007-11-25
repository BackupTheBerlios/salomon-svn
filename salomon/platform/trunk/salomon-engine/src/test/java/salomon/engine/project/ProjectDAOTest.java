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
import salomon.engine.DAOContext;
import salomon.engine.agent.Agent;
import salomon.engine.agent.IAgent;
import salomon.engine.domain.Domain;
import salomon.engine.domain.IDomainDAO;

/**
 * 
 */
public class ProjectDAOTest extends TestCase
{
    private IProjectDAO projectDAO = (IProjectDAO) DAOContext.getBean("projectDAO");
    
    private IDomainDAO domainDAO = (IDomainDAO) DAOContext.getBean("domainDAO");

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#getProject(java.lang.String)}.
     */
    public void testGetProjectString()
    {
        Project project = createProject();
        projectDAO.save(project);

        IProject inserted = projectDAO.getProject(project.getProjectName());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
    }

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#getProjects()}.
     */
    public void testGetProjects()
    {
        Project project = createProject();
        projectDAO.save(project);

        IProject[] projects = projectDAO.getProjects();
        assertNotNull(projects);
        assertTrue(projects.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.project.ProjectDAO#remove(salomon.engine.project.IProject)}.
     */
    public void testRemove()
    {
        Project project = createProject();
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
        Project project = createProject();
        projectDAO.save(project);

        IProject inserted = projectDAO.getProject(project.getProjectId());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
    }
    
    public void testSaveWithAgents()
    {
        Project project = createProject();
        project.setProjectName("test-save-with-agents");
        Agent agent = new Agent();
        agent.setAgentName("test-agent-for-project");
        project.addAgent(agent);
        projectDAO.save(project);

        IProject inserted = projectDAO.getProject(project.getProjectId());
        assertNotNull(inserted);
        assertEquals(project.getProjectName(), inserted.getProjectName());
        // test getting all agents
        assertNotNull(inserted.getAgents());
        assertTrue(inserted.getAgents().length >= 1);
        assertEquals(agent.getAgentName(),
                inserted.getAgents()[0].getAgentName());

        // test getting particular agent (by id)
        IAgent insertedAgent = inserted.getAgent(agent.getAgentId());
        assertNotNull(insertedAgent);
        assertEquals(agent.getAgentName(), insertedAgent.getAgentName());
        // test getting particular agent (by name)
        insertedAgent = inserted.getAgent(agent.getAgentName());
        assertNotNull(insertedAgent);
        assertEquals(agent.getAgentName(), insertedAgent.getAgentName());
    }

    private Project createProject()
    {
        Domain domain = new Domain();
        domainDAO.save(domain);
        Project project = new Project();
        project.setDomain(domain);
        project.setProjectName("test-project");
        return project;
    }

}
