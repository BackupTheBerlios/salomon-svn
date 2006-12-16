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
import salomon.engine.database.queries.SQLDelete;
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
import salomon.engine.task.TaskManager;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleStruct;

public class TreeTest extends TestCase
{
    private static final String TESTED_DATA_SET_NAME = "testedDataSet";

    private static final String TESTED_ATTRIBUTE_SET_NAME = "testedAttributeSet";

    private static final String SOLUTION_NAME = "Example";

    private static final String RAND_DATASET_PLUGIN = "Random dataset creator";

    private static final String ATTR_SET_PLUGIN = "Attributeset creator";

    public void testCreate() throws PlatformException
    {
        LOGGER.info("Test started");
        // clean after previous test executions
        clean();

        ManagerEngine managerEngine = TestObjectFactory.getManagerEngine();
        ISolution solution = TestObjectFactory.getSolution(SOLUTION_NAME);
        IProject project = createProject(solution);

        ITaskManager taskManager = project.getTaskManager();
        IPluginManager pluginManager = managerEngine.getPluginManager();

        createTasks(pluginManager, taskManager);

        ((TaskManager) taskManager).runTasks();

        LOGGER.info("Test finished!");
    }

    private void clean() throws PlatformException
    {
        try {
            // delete dataset to be created
            SQLDelete delete = new SQLDelete("datasets");
            delete.addCondition("dataset_name = ", TESTED_DATA_SET_NAME);
            TestObjectFactory.getDbManager().delete(delete);

            // delete attribute set to be created
            delete = new SQLDelete("attributesets");
            delete.addCondition("attributeset_name = ", TESTED_DATA_SET_NAME);
            TestObjectFactory.getDbManager().delete(delete);

            TestObjectFactory.getDbManager().commit();
        } catch (Exception e) {
            new PlatformException(e.getLocalizedMessage());
        }
    }

    private void createTasks(IPluginManager pluginManager,
            ITaskManager taskManager) throws PlatformException
    {
        //        createDataSetCreatorTask(pluginManager, taskManager);
        createRandomDataSetCreatorTask(pluginManager, taskManager);
        //        createDataSetVisualizerTask(pluginManager, taskManager);
        createAttributeSetTask(pluginManager, taskManager);

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
        SimpleStruct settings = new SimpleStruct();
        settings.setField("dataSetName", TESTED_DATA_SET_NAME);
        SimpleStruct deffinitions = new SimpleStruct();
        deffinitions.setField("rowCount", 4);
        deffinitions.setField("tableName", "CONTACT_LENSES");
        settings.setField("definitions", new IObject[]{deffinitions});

        createTask(pluginManager, taskManager, RAND_DATASET_PLUGIN, settings);

    }

    //    private void createDataSetVisualizerTask(IPluginManager pluginManager,
    //            ITaskManager taskManager) throws PlatformException
    //    {
    //        createTask(pluginManager, taskManager, "Dataset visualizer", null);
    //    }
    //
    private void createAttributeSetTask(IPluginManager pluginManager,
            ITaskManager taskManager) throws PlatformException
    {
        SimpleStruct settings = new SimpleStruct();
        settings.setField("attributeSetName", TESTED_ATTRIBUTE_SET_NAME);

        SimpleArray descriptions = new SimpleArray();

        SimpleStruct age = new SimpleStruct();
        age.setField("attributeName", "age");
        age.setField("type", AttributeType.ENUM.getDBString());
        age.setField("columnName", "AGE");
        age.setField("isOutput", "N");
        age.setField("tableName", "CONTACT_LENSES");

        SimpleStruct spectacle = new SimpleStruct();
        spectacle.setField("attributeName", "spectacle");
        spectacle.setField("type", AttributeType.ENUM.getDBString());
        spectacle.setField("columnName", "SPECTACLE_PRESCRIP");
        spectacle.setField("isOutput", "N");
        spectacle.setField("tableName", "CONTACT_LENSES");

        SimpleStruct astigmatism = new SimpleStruct();
        astigmatism.setField("attributeName", "astigmatism");
        astigmatism.setField("type", AttributeType.ENUM.getDBString());
        astigmatism.setField("columnName", "ASTIGMATISM");
        astigmatism.setField("isOutput", "N");
        astigmatism.setField("tableName", "CONTACT_LENSES");

        SimpleStruct tear = new SimpleStruct();
        tear.setField("attributeName", "tear prod rate");
        tear.setField("type", AttributeType.ENUM.getDBString());
        tear.setField("columnName", "TEAR_PROD_RATE");
        tear.setField("isOutput", "N");
        tear.setField("tableName", "CONTACT_LENSES");

        SimpleStruct contactLenses = new SimpleStruct();
        contactLenses.setField("attributeName", "contact lenses");
        contactLenses.setField("type", AttributeType.ENUM.getDBString());
        contactLenses.setField("columnName", "CONTACT_LENSES");
        contactLenses.setField("isOutput", "Y");
        contactLenses.setField("tableName", "CONTACT_LENSES");

        descriptions.setValue(new IObject[]{age, spectacle, astigmatism, tear,
                contactLenses});
        settings.setField("descriptions", descriptions);

        createTask(pluginManager, taskManager, ATTR_SET_PLUGIN, settings);
    }

    private void createTask(IPluginManager pluginManager,
            ITaskManager taskManager, String pluginName, SimpleStruct settings)
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

        ISettings pluginSettings = task.getPlugin().getSettingComponent(null).getDefaultSettings();

        pluginSettings.init(settings);
        task.setSettings(pluginSettings);

        taskManager.addTask(task);
        LOGGER.info("Task created.");
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
