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

import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.platform.ManagerEngine;

public class ProjectManagerTest extends TestCase
{

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ProjectManager _projectManager;

    public void testCreateProject() throws Exception
    {
        LOGGER.info("ProjectManagerTest.testCreateProject()");
        boolean success = false;
        try {
            _projectManager.createProject();
            success = true;
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw e;
        }
        assertTrue(success);
    }

    public void testGetProject() throws Exception
    {
        LOGGER.info("ProjectManagerTest.testGetProject()");
        boolean success = false;
        //try {
        ///	_projectManager.getProject(10);
        //	success = true;
        //} catch (Exception e) {
        //	LOGGER.fatal("", e);
        //    throw e ;
        //}
        assertTrue(success);
    }

    public void testGetProjects()
    {
        LOGGER.info("ProjectManagerTest.testGetProjects()");
        boolean success = false;
        try {
            IProject[] projects = _projectManager.getProjects();
            success = true;
            for (int i = 0; i < projects.length; i++) {
                LOGGER.debug(projects[i]);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
        }
        assertTrue(success);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$           

        try {
            new DBManager().connect();
        } catch (SQLException e) {
            LOGGER.fatal("", e);
        } catch (ClassNotFoundException e) {
            LOGGER.error("", e);
        }

        ManagerEngine engine = new ManagerEngine();
        _projectManager = (ProjectManager) engine.getProjectManager();
    }

    private static Logger LOGGER = Logger.getLogger(ProjectManagerTest.class);
}
