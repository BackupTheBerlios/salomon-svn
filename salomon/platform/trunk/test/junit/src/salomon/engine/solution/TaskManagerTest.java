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
package salomon.engine.solution;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.platform.ManagerEngine;
import salomon.engine.project.Project;
import salomon.engine.project.ProjectManager;
import salomon.engine.task.ITask;
import salomon.engine.task.TaskManager;
import salomon.platform.exception.PlatformException;

public class TaskManagerTest extends TestCase {

    private TaskManager _TaskManager;

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TaskManagerTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        PropertyConfigurator.configure("log.conf");
        _TaskManager = new TaskManager() ;
    }

    public void testCreateTask() {
        ITask task ;
        try {
            task = _TaskManager.createTask();
            assertNotNull(task) ;
            
        } catch (PlatformException e) {
            LOGGER.error(e,e);
            assertFalse(true);
            e.printStackTrace();
       }  
    }
    
    public void testAddTask() {
        ITask task ;
        try {
            task = _TaskManager.createTask();
            assertNotNull(task) ;
            
            _TaskManager.addTask(task);
            
            //TODO i think addAllTask should take collection<ITask> not Collection<Task>, this cast here could be possibly wrong
            Collection<ITask> tasklist = new ArrayList<ITask>() ;
            tasklist.add(_TaskManager.createTask());
            tasklist.add(_TaskManager.createTask());
            tasklist.add(_TaskManager.createTask());
          
            _TaskManager.addAllTasks(tasklist);
            
            ITask tasks[] = _TaskManager.getTasks() ;
            assertNotNull(tasks) ;
            
        } catch (PlatformException e) {
            e.printStackTrace();
            LOGGER.error(e,e);
            assertFalse(true);
        }

    }
    
    public void testClearTasks() {
        _TaskManager.clearTaskList() ;
    }
    
    public void testGetCurrentTask() {
       ITask task = _TaskManager.getCurrentTask() ;
    }
    
    public void testGetRunner() {
        
        try {
            _TaskManager.getRunner() ;
        } catch (PlatformException e) {
            e.printStackTrace();
            LOGGER.error("PlatformError", e) ;
            assertFalse(true) ;
        }
    }
    
    public void testSetProjectManager() {
        ProjectManager projectManager = new ProjectManager(new ManagerEngine()) ;
        Project project = new Project() ;
        try {
            projectManager.addProject(projectManager.ceateProject()) ;
        } catch (PlatformException e) {
            e.printStackTrace();
            LOGGER.error("PlatformError", e) ;
            assertFalse(true) ;
        }      
        _TaskManager.setProjectManger(projectManager) ;
    }
    
    public void testEverything() {
        //TODO to be implemented
        _TaskManager.start();
    }
    
    private static Logger LOGGER = Logger.getLogger(TaskManagerTest.class);
}
