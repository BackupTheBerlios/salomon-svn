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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public class LibraryControllerTest extends TestCase
{
    public void test() throws PlatformException
    {
        LibraryController controller = new LibraryController();
        IProjectManager projectManager = null;
        try {
            projectManager = controller.getManagerEngine().getProjectManager();
        } catch (PlatformException e) {
            LOGGER.fatal("Exception was thrown!", e);
        }

        IProject project = projectManager.createProject();
        //project.setName("testLib");
        //project.setInfo("Created from library controller");
        ITaskManager taskManager = controller.getManagerEngine().getTasksManager();
        ITask task = taskManager.createTask();
        //task.setName("testName");

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<!DOCTYPE struct SYSTEM \"config/struct.dtd\">");
        builder.append("<struct><string name=\"resultDataSet\" value=\"ResultDataSet\"/>");
        builder.append("<string name=\"firstDataSet\" value=\"second\"/>");
        builder.append("<string name=\"secondDataSet\" value=\"third\"/></struct>");
        // this method is not supported now
        //		taskManager.addTask(task, pluginUrl, builder.toString());
        projectManager.saveProject();
        taskManager.start();
        LOGGER.debug("task result:" + task.getResult());
    }

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$  
    }

    private static final Logger LOGGER = Logger.getLogger(LibraryControllerTest.class);
}
