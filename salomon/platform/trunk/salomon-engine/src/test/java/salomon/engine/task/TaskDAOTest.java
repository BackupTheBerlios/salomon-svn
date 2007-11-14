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

package salomon.engine.task;

import salomon.engine.DAOContext;
import junit.framework.TestCase;

public class TaskDAOTest extends TestCase
{
    ITaskDAO taskDAO = (ITaskDAO) DAOContext.getBean("taskDAO");

    public void testSave()
    {
        Task task = new Task(null);
        taskDAO.save(task);
        System.out.println("Task inserted: " + task.getTaskId());
    }

    public void testRemove()
    {
        Task task = new Task(null);
        task.setTaskNr(1);
        taskDAO.save(task);
        Task removedTask = taskDAO.getTask(task.getTaskId());
        assertNotNull(removedTask);
        
        taskDAO.remove(removedTask);
        removedTask = taskDAO.getTask(task.getTaskId());
        assertNull(removedTask);
    }

    public void testGetTasks()
    {
        Task[] tasks = taskDAO.getTasks();
        assertNotNull(tasks);
        assertTrue(tasks.length >= 1);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void testGetTaskLong()
    {
        Task task = new Task(null);
        task.setTaskNr(2);
        taskDAO.save(task);
        Task newTask = taskDAO.getTask(task.getTaskId());
        assertNotNull(newTask);
        assertEquals(2, newTask.getTaskNr());        
    }
    
    public void testGetTaskString()
    {
        Task task = new Task(null);
        task.setTaskName("test-task");
        taskDAO.save(task);
        Task newTask = taskDAO.getTask(task.getTaskName());
        assertNotNull(newTask);
        assertEquals("test-task", newTask.getTaskName());        
    }
}
