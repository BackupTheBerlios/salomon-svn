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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.ITaskRunner;
import salomon.engine.task.Task;

/**
 * 
 */
public class AgentProcessingComponent implements IAgentProcessingComponent
{
    private Long _componentId;

    private String _componentName;

    private List<Task> _tasks;

    protected AgentProcessingComponent()
    {
        _tasks = new ArrayList<Task>();
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#addTask(salomon.engine.task.ITask)
     */
    public void addTask(ITask task)
    {
        _tasks.add((Task) task);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AgentProcessingComponent) {
            AgentProcessingComponent comp = (AgentProcessingComponent) obj;
            return _componentId.equals(comp.getComponentId());
        }
        return false;
    }

    /**
     * Returns the _componentId.
     * @return The _componentId
     */
    public Long getComponentId()
    {
        return _componentId;
    }

    /**
     * Returns the _componentName.
     * @return The _componentName
     */
    public String getComponentName()
    {
        return _componentName;
    }

    public ITaskRunner getRunner()
    {
        throw new UnsupportedOperationException("Method AgentProcessingComponent.getRunner() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTask(long)
     */
    public Task getTask(long taskId)
    {
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.getTask() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTask(java.lang.String)
     */
    public Task getTask(String taskName)
    {
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.getTask() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTaskManager()
     */
    public ITaskManager getTaskManager()
    {
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.getTaskManager() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTasks()
     */
    public Task[] getTasks()
    {
        return _tasks.toArray(new Task[_tasks.size()]);
    }

    @Override
    public int hashCode()
    {
        return _componentId.hashCode();
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#removeTask(salomon.engine.task.ITask)
     */
    public void removeTask(ITask task)
    {
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.removeTask() not implemented yet!");
    }

    /**
     * Set the value of _componentId field.
     * @param _componentId The _componentId to set
     */
    public void setComponentId(Long componentId)
    {
        _componentId = componentId;
    }

    /**
     * Set the value of _componentName field.
     * @param _componentName The _componentName to set
     */
    public void setComponentName(String componentName)
    {
        _componentName = componentName;
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#setTaskManager(salomon.engine.task.ITaskManager)
     */
    public void setTaskManager(ITaskManager taskManager)
    {
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.setTaskManager() not implemented yet!");
    }

    /**
     * Set the value of tasks field.
     * @param tasks The tasks to set
     */
    public void setTasks(Task[] tasks)
    {
        _tasks = Arrays.asList(tasks);
    }
}
