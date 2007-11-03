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

package salomon.engine.holder;

import salomon.engine.project.IProject;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.ITaskRunner;
import salomon.platform.exception.PlatformException;

/**
 * Holds taskManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 */
final class TaskManagerHolder implements ITaskManager
{

    /**
     * 
     * @uml.property name="_currentTaskManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskManager _currentTaskManager;

    /**
     *  
     */
    TaskManagerHolder(ITaskManager taskManager)
    {
        _currentTaskManager = taskManager;
    }

    /**
     * @see ITaskManager#addTask(ITask)
     */
    public void addTask(ITask task) throws PlatformException
    {
        _currentTaskManager.addTask(task);
    }

    /**
     * @see ITaskManager#clearTasks()
     */
    public void clearTasks() throws PlatformException
    {
        _currentTaskManager.clearTasks();
    }

    /**
     * @see ITaskManager#createTask()
     */
    public ITask createTask() throws PlatformException
    {
        return _currentTaskManager.createTask();
    }

    /**
     * @see ITaskManager#getCurrentTask()
     */
    public ITask getCurrentTask() throws PlatformException
    {
        return _currentTaskManager.getCurrentTask();
    }

    public IProject getProject() throws PlatformException
    {
        return _currentTaskManager.getProject();
    }

    /**
     * @see salomon.engine.task.ITaskManager#getRunner()
     */
    public ITaskRunner getRunner() throws PlatformException
    {
        return _currentTaskManager.getRunner();
    }

    /**
     * @see ITaskManager#getTasks()
     */
    public ITask[] getTasks() throws PlatformException
    {
        return _currentTaskManager.getTasks();
    }

    public boolean removeAll() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method removeAll() not implemented yet!");
    }

    public boolean removeTask(ITask task) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method removeTask() not implemented yet!");
    }

    public void saveTasks() throws PlatformException
    {
        _currentTaskManager.saveTasks();
    }

    void setCurrent(ITaskManager taskManager)
    {
        _currentTaskManager = taskManager;
    }
}