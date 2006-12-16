/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.tree;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.ProjectInfo;
import salomon.engine.solution.ISolution;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.TaskInfo;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

public class TreeTest extends TestCase
{
    private static final String TESTED_DATA_SET_NAME = "testedDataSet";

    private final static String SOLUTION_NAME = "Example";

    public void testCreate() throws PlatformException
    {
        LOGGER.info("Test started");
        ManagerEngine managerEngine = TestObjectFactory.getManagerEngine();
        ISolution solution = TestObjectFactory.getSolution(SOLUTION_NAME);
        IProject project = createProject(solution);

        ITaskManager taskManager = project.getTaskManager();
        IPluginManager pluginManager = managerEngine.getPluginManager();

        createTasks(pluginManager, taskManager);

        taskManager.getRunner().start();

        LOGGER.info("Test finished!");
    }

    private void createTasks(IPluginManager pluginManager,
            ITaskManager taskManager) throws PlatformException
    {
        //        createDataSetCreatorTask(pluginManager, taskManager);
        createRandomDataSetCreatorTask(pluginManager, taskManager);
        //        createDataSetVisualizerTask(pluginManager, taskManager);
        //        createAttributeSetTask(pluginManager, taskManager);

        taskManager.saveTasks();
    }

    //    private void createDataSetCreatorTask(IPluginManager pluginManager,
    //            ITaskManager taskManager) throws PlatformException
    //    {
    //        createTask(pluginManager, taskManager, "DataSet Creator", null);
    //    }

    private void createRandomDataSetCreatorTask(IPluginManager pluginManager,
            ITaskManager taskManager) throws PlatformException
    {
        ITask task = createTask(pluginManager, taskManager,
                "Random dataset creator");
        SimpleStruct settings = (SimpleStruct) task.getPlugin().getSettingComponent(
                null).getDefaultSettings();
        settings.setField("dataSetName", TESTED_DATA_SET_NAME);
        SimpleStruct deffinitions = new SimpleStruct();
        deffinitions.setField("rowCount", 4);
        deffinitions.setField("tableName", "CONTACT_LENSES");
        settings.setField("definitions", new IObject[]{deffinitions});
        task.setSettings((ISettings) settings);

        taskManager.addTask(task);
        LOGGER.info("Task created.");
    }

    //    private void createDataSetVisualizerTask(IPluginManager pluginManager,
    //            ITaskManager taskManager) throws PlatformException
    //    {
    //        createTask(pluginManager, taskManager, "Dataset visualizer", null);
    //    }
    //
    //    private void createAttributeSetTask(IPluginManager pluginManager,
    //            ITaskManager taskManager) throws PlatformException
    //    {
    //        createTask(pluginManager, taskManager, "Attributeset creator", null);
    //    }

    private ITask createTask(IPluginManager pluginManager,
            ITaskManager taskManager, String pluginName)
            throws PlatformException
    {
        LOGGER.info("Creating task: " + pluginName);
        LocalPlugin plugin = (LocalPlugin) pluginManager.getPlugin(pluginName);
        
        // loading plugin
        try {
            plugin.load();
        } catch (Exception e) {
            throw new PlatformException(e.getLocalizedMessage());
        }

        ITask task = taskManager.createTask();
        TaskInfo taskInfo = (TaskInfo) task.getInfo();
        taskInfo.setName(pluginName);
        task.setPlugin(plugin);

        return task;
    }

    private IProject createProject(ISolution solution) throws PlatformException
    {
        LOGGER.info("Creating project!");
        IProjectManager projectManager = solution.getProjectManager();
        IProject project = projectManager.createProject();
        ProjectInfo projectInfo = (ProjectInfo) project.getInfo();
        projectInfo.setName("Tree testing project");
        projectInfo.setInfo("The tree testing project creating automaticly");
        projectManager.saveProject(false);

        LOGGER.info("Project created!");

        return project;
    }

    private static final Logger LOGGER = Logger.getLogger(TreeTest.class);
}
