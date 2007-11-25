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

import salomon.engine.DAOContext;
import junit.framework.TestCase;

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

    private Project createProject()
    {
        Project project = new Project();
        project.setProjectName("test-project");
        return project;
    }

}
