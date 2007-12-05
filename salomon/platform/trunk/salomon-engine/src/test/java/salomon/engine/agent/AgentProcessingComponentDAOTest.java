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

package salomon.engine.agent;

import java.util.Arrays;

import junit.framework.TestCase;
import salomon.agent.IAgentProcessingComponent;
import salomon.engine.DAOContext;
import salomon.engine.DAOTestHelper;
import salomon.engine.task.ITask;
import salomon.engine.task.Task;

/**
 * 
 */
public class AgentProcessingComponentDAOTest extends TestCase
{
    private IAgentProcessingComponentDAO componentDAO = (IAgentProcessingComponentDAO) DAOContext.getBean("agentProcessingComponentDAO");

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#getAgentProcessingComponents()}.
     */
    public void testGetAgentProcessingComponents()
    {
        // make sure at least one comp exists
        DAOTestHelper.createTestAgentProcessingComponent(false);

        IAgentProcessingComponent[] components = componentDAO.getAgentProcessingComponents();
        assertNotNull(components);
        assertTrue(components.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#getAgentProcessingComponent(java.lang.String)}.
     */
    public void testGetAgentProcessingComponentString()
    {
        AgentProcessingComponent comp = DAOTestHelper.createTestAgentProcessingComponent(false);

        AgentProcessingComponent inserted = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentName());
        assertEquals(comp.getComponentName(), inserted.getComponentName());
        Arrays.equals(comp.getTasks(), inserted.getTasks());
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#remove(salomon.engine.agent.IAgentProcessingComponentDAO)}.
     */
    public void testRemove()
    {
        AgentProcessingComponent comp = DAOTestHelper.createTestAgentProcessingComponent(true);
        comp.setComponentName("component-to-remove");

        componentDAO.save(comp);
        AgentProcessingComponent removed = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentId());
        assertNotNull(removed);

        componentDAO.remove(comp);
        removed = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentId());
        assertNull(removed);
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#save(salomon.engine.agent.IAgentProcessingComponentDAO)}.
     */
    public void testSave()
    {
        AgentProcessingComponent comp = DAOTestHelper.createTestAgentProcessingComponent(true);
        componentDAO.save(comp);

        AgentProcessingComponent inserted = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentId());
        assertEquals(comp.getComponentName(), inserted.getComponentName());
    }

    public void testSaveWithAgents()
    {
        AgentProcessingComponent comp = DAOTestHelper.createTestAgentProcessingComponent(true);
        comp.setComponentName("test-comp-with-tasks");
        componentDAO.save(comp);

        // add tasks to Agent
        Task task1 = new Task();
        task1.setTaskName("task1");
        task1.setTaskNr(2);

        Task task2 = new Task();
        task2.setTaskName("task2");
        task2.setTaskNr(1);

        Task task3 = new Task();
        task3.setTaskName("task3");
        task3.setTaskNr(0);

        comp.addTask(task1);
        comp.addTask(task2);
        comp.addTask(task3);

        componentDAO.save(comp);

        AgentProcessingComponent inserted = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentId());
        assertEquals(comp.getComponentName(), inserted.getComponentName());
        Task[] tasks = inserted.getTasks();
        assertNotNull(tasks);
        assertEquals(3, tasks.length);
        // check the order of tasks
        assertEquals("task3", tasks[0].getTaskName());
        assertEquals("task2", tasks[1].getTaskName());
        assertEquals("task1", tasks[2].getTaskName());

        // test getting tasks by id
        ITask insertedTask = inserted.getTask(task1.getTaskId());
        assertNotNull(insertedTask);
        assertEquals(task1.getTaskName(), insertedTask.getTaskName());

        // test getting tasks by id
        insertedTask = inserted.getTask(task1.getTaskName());
        assertNotNull(insertedTask);
        assertEquals(task1.getTaskName(), insertedTask.getTaskName());
    }

}
