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

package salomon.engine.task;

import salomon.agent.IAgentProcessingComponent;
import salomon.platform.exception.PlatformException;

/**
 * Interface implemented by <code>TaskManager</code>, which manages with tasks.
 */
public interface ITaskManager
{
    void addTask(ITask task) throws PlatformException;

    /**
     * Creates new task.
     * 
     * @return new task
     */
    ITask createTask();

    /**
     * Returns current task.
     * 
     * @return current task
     */
    ITask getCurrentTask();

    /**
     * Returns the TaskRunner instance.
     */
    ITaskRunner getRunner();

    /**
     * Returns all tasks.
     * 
     * @return collection of tasks
     */
    ITask[] getTasks() throws PlatformException;

    /**
     * Removes task from the task list.
     * 
     * @param task to be removed
     * @return
     * @throws PlatformException
     */
    void removeTask(ITask task) throws PlatformException;

    ITask getTask(long taskId) throws PlatformException;
    
    ITask getTask(String taskName) throws PlatformException;
    
    IAgentProcessingComponent getAgentProcessingComponent();
}