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

import junit.framework.TestCase;
import salomon.engine.DAOContext;
import salomon.engine.DAOTestHelper;

public class TaskDAOTest extends TestCase
{
    private ITaskDAO taskDAO = (ITaskDAO) DAOContext.getBean("taskDAO");

    public void testSave()
    {
        Task task = DAOTestHelper.createTestTask(true);
        taskDAO.save(task);

        ITask inserted = taskDAO.getTask(task.getTaskId());
        assertNotNull(inserted);
    }

    public void testRemove()
    {
        Task task = DAOTestHelper.createTestTask(true);
        task.setTaskName("task-to-remove");
        taskDAO.save(task);

        Task removedTask = (Task) taskDAO.getTask(task.getTaskId());
        assertNotNull(removedTask);

        taskDAO.remove(removedTask);
        removedTask = (Task) taskDAO.getTask(task.getTaskId());
        assertNull(removedTask);
    }

    public void testGetTasks()
    {
        // make sure at least one task exists
        DAOTestHelper.createTestTask(false);

        Task[] tasks = (Task[]) taskDAO.getTasks();
        assertNotNull(tasks);
        assertTrue(tasks.length >= 1);
    }

    public void testGetTaskString()
    {
        Task task = DAOTestHelper.createTestTask(false);

        Task inserted = (Task) taskDAO.getTask(task.getTaskName());
        assertNotNull(inserted);
        assertEquals(task.getTaskName(), inserted.getTaskName());
    }

}
