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
import salomon.engine.DAOContext;
import salomon.engine.task.Task;

/**
 * 
 */
public class AgentProcessingComponentDAOTest extends TestCase
{
    IAgentProcessingComponentDAO componentDAO = (IAgentProcessingComponentDAO) DAOContext.getBean("agentProcessingComponentDAO");

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#getAgentProcessingComponents()}.
     */
    public void testGetAgentProcessingComponents()
    {
        IAgentProcessingComponent[] components = componentDAO.getAgentProcessingComponents();
        assertNotNull(components);
        for (IAgentProcessingComponent comp : components) {
            System.out.println(comp);
        }        
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#getAgentProcessingComponent(java.lang.String)}.
     */
    public void testGetAgentProcessingComponentString()
    {
        AgentProcessingComponent comp = new AgentProcessingComponent();
        comp.setComponentName("Test-to-get");
        Task task = new Task(null);
        task.setTaskNr(1);
        task.setTaskName("component-to-get-task");
        comp.addTask(task);

        componentDAO.save(comp);

        AgentProcessingComponent inserted = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentName());
        assertEquals(comp.getComponentName(), inserted.getComponentName());
        Arrays.equals(comp.getTasks(), inserted.getTasks());
    }

    /**
     * Test method for {@link salomon.engine.agent.AgentProcessingComponentDAO#remove(salomon.engine.agent.IAgentProcessingComponentDAO)}.
     */
    public void testRemove()
    {
        AgentProcessingComponent comp = new AgentProcessingComponent();
        comp.setComponentName("Test-to-remove");
        Task task = new Task(null);
        task.setTaskNr(1);
        task.setTaskName("component-to-remove-task");
        comp.addTask(task);

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
        AgentProcessingComponent comp = new AgentProcessingComponent();
        comp.setComponentName("Test-name");
        Task task = new Task(null);
        task.setTaskNr(1);
        task.setTaskName("component-task");
        comp.addTask(task);

        componentDAO.save(comp);

        AgentProcessingComponent inserted = (AgentProcessingComponent) componentDAO.getAgentProcessingComponent(comp.getComponentId());
        assertEquals(comp.getComponentName(), inserted.getComponentName());
        Arrays.equals(comp.getTasks(), inserted.getTasks());
    }

}
