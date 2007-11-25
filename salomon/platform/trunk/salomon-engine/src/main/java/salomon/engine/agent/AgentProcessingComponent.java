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
import java.util.Collections;
import java.util.Comparator;
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

    private TaskComparator _taskComparator;

    private List<Task> _taskList;

    protected AgentProcessingComponent()
    {
        _taskList = new ArrayList<Task>();
        _taskComparator = new TaskComparator();
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#addTask(salomon.engine.task.ITask)
     */
    public void addTask(ITask task)
    {
        ((Task) task).setAgentProcessingComponent(this);
        _taskList.add((Task) task);
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
        throw new UnsupportedOperationException(
                "Method AgentProcessingComponent.getRunner() not implemented yet!");
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTask(long)
     */
    public ITask getTask(long taskId)
    {
        ITask task = null;
        for (Task t : _taskList) {
            if (taskId == t.getTaskId()) {
                task = t;
                break;
            }
        }
        return task;
    }

    /**
     * @see salomon.engine.agent.IAgentProcessingComponent#getTask(java.lang.String)
     */
    public ITask getTask(String taskName)
    {
        ITask task = null;
        for (Task t : _taskList) {
            if (taskName.equals(t.getTaskName())) {
                task = t;
                break;
            }
        }
        return task;
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
        // make sure the list of tasks is sorted
        Collections.sort(_taskList, _taskComparator);
        return _taskList.toArray(new Task[_taskList.size()]);
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
        _taskList.remove(task);
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
     * Returns the taskList.
     * @return The taskList
     */
    // used by Hibernate only
    @SuppressWarnings("unused")
    private List<Task> getTaskList()
    {
        // make sure the list of tasks is sorted
        Collections.sort(_taskList, _taskComparator);
        return _taskList;
    }

    /**
     * Set the value of _componentId field.
     * @param _componentId The _componentId to set
     */
    //  used by Hibernate only
    @SuppressWarnings("unused")
    private void setComponentId(Long componentId)
    {
        _componentId = componentId;
    }

    /**
     * Set the value of tasks field.
     * @param tasks The tasks to set
     */
    // used by Hibernate only
    @SuppressWarnings("unused")
    private void setTaskList(List<Task> tasks)
    {
        _taskList = tasks;
    }

    /**
     * Comparator used to mantain the order of tasks in the collection.
     */
    private class TaskComparator implements Comparator<Task>
    {
        public int compare(Task task1, Task task2)
        {
            int task1Nr = task1.getTaskNr();
            int task2Nr = task2.getTaskNr();
            return (task1Nr < task2Nr ? -1 : (task1Nr == task2Nr ? 0 : 1));
        }
    }
}
