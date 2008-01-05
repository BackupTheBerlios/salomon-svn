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
package salomon.engine.project;

import junit.framework.TestCase;
import salomon.engine.DAOTestHelper;
import salomon.engine.domain.Domain;

/**
 * @author Nikodem.Jura
 *
 */
public class ProjectManagerTest extends TestCase {

	/**
	 * Test method for {@link salomon.engine.project.ProjectManager#addProject(salomon.engine.project.IProject)}.
	 */
	public void testAddProject() {
		Domain domain = DAOTestHelper.createTestDomain(false);
		IProjectManager projectManager = domain.getProjectManager();
		
		IProject project = (Project) projectManager.createProject();
		project.setProjectName("test-project-name");
		projectManager.addProject(project);
		
		IProject inserted = projectManager.getProject(project.getProjectName());
		assertNotNull(inserted);
		assertEquals(project.getProjectName(), inserted.getProjectName());
	}

}
