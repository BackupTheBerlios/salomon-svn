/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.Config;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.task.ITaskManager;

import salomon.platform.exception.PlatformException;

import junit.framework.TestCase;

/**
 * 
 */
public class LibraryControllerTest extends TestCase
{
    public void test() throws PlatformException
    {
        LibraryController controller = new LibraryController();
        controller.start();
        ISolutionManager solutionManager = null;
        try {
            solutionManager = controller.getManagerEngine().getSolutionManager();
        } catch (PlatformException e) {
            LOGGER.fatal("Exception was thrown!", e);
        }


        IProjectManager projectManager = solutionManager.getSolution(1).getProjectManager();
        IProject project = projectManager.createProject();
        //project.setName("testLib");
        //project.setInfo("Created from library controller");
        ITaskManager taskManager =  project.getTaskManager();
        assertNotNull(taskManager.createTask());
        //task.setName("testName");
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
        Config.readConfiguration();
    }

    private static final Logger LOGGER = Logger.getLogger(LibraryControllerTest.class);
}
