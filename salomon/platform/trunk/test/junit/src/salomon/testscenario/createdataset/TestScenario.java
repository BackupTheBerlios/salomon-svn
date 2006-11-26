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

package salomon.testscenario.createdataset;

import java.net.URL;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.Project;
import salomon.engine.solution.ISolution;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.TaskManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

/**
 * This test scenario assumes, that database is filled only with initializing data.
 */
public final class TestScenario extends TestCase
{
    private static final Logger LOGGER = Logger.getLogger(TestScenario.class);

    private final static String PROJECT_NAME = "Dataset project";

    private static final String RDS_PLUGIN = "Random dataset creator";

    private static final String RDS_SETTINGS = "rds_settings.xml";
    
    private static final String RDS_DATASET_NAME = "Contact Lenses";

    private final static String SOLUTION_NAME = "Example";

    private IProjectManager _projectManager;

    public TestScenario() throws PlatformException, SQLException
    {
        ISolution solution = TestObjectFactory.getSolution(SOLUTION_NAME);
        _projectManager = solution.getProjectManager();
    }

    public static void main(String[] args) throws PlatformException,
            SQLException, Exception
    {
        new TestScenario().testScenerio();
    }

    /**
     * Method to be run in this Test case.
     * 
     * @throws Exception
     */
    public void testScenerio() throws Exception
    {
        try {
            // try to get project from DB,
            // if it doesn't exist, create it
            IProject project = TestObjectFactory.getProject(PROJECT_NAME);
            if (project == null) {
                project = createProject();
                createTasks(project);
                TestObjectFactory.getDbManager().commit();
            }           
            // cleaning before tasks execution
            clear();

            // running tasks
            TaskManager taskManager = (TaskManager) project.getTaskManager();
            taskManager.addTaskListener(new ScenerioTaskListener(this));
            taskManager.runTasks();

        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw(e);
        } finally {
            TestObjectFactory.getDbManager().disconnect();
        }        
    }

    private void clear() throws Exception
    {
        // delete dataset to be created
        SQLDelete delete = new SQLDelete("datasets");
        delete.addCondition("dataset_name = ", RDS_DATASET_NAME);
        TestObjectFactory.getDbManager().delete(delete);
        
        TestObjectFactory.getDbManager().commit();
    }
    
    /**
     * Creates project.
     * 
     * @return
     * @throws PlatformException
     */
    private Project createProject() throws PlatformException
    {
        Project project = (Project) _projectManager.createProject();
        project.getInfo().setName(PROJECT_NAME);
        _projectManager.addProject(project);
        return project;
    }

    /**
     * Creates tasks for given project
     * 
     * @param taskManager
     * @param pluginName
     * @param settingsFile
     * @return
     * @throws Exception
     */
    private ITask createTask(ITaskManager taskManager, String pluginName,
            String settingsFile) throws Exception
    {
        // loading plugin from DB
        LocalPlugin plugin = (LocalPlugin) TestObjectFactory.getPlugin(pluginName);
        // loading task settings
        URL url = getClass().getResource(settingsFile);
        SimpleStruct struct = XMLSerializer.deserialize(url.openStream());
        // creating task
        ITask task = taskManager.createTask();

        // setting task properties
        task.setPlugin(plugin);
        plugin.load();
        ISettings settings = plugin.getSettingComponent(null).getDefaultSettings();
        settings.init(struct);
        task.setSettings(settings);

        return task;
    }

    /**
     * Creates task basing on the plugin name and sets settings from the file.
     * 
     * @param project
     * @throws Exception
     */
    private void createTasks(IProject project) throws Exception
    {
        ITaskManager taskManager = project.getTaskManager();
        
        ITask createRDSTask = createTask(taskManager, RDS_PLUGIN, RDS_SETTINGS);
        taskManager.addTask(createRDSTask);
    }
}
