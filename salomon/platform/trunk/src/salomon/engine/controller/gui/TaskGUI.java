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

package salomon.engine.controller.gui;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;
import salomon.engine.task.Task;
import salomon.platform.exception.PlatformException;

/**
 * Class is graphic representation of a task. It is used to display them on the
 * list of tasks. This class is designed for future use of graphical task representation.
 */
public final class TaskGUI
{
    /**
     * 
     * @uml.property name="_task"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private Task _task;

    public TaskGUI(ITask task)
    {
        _task = (Task) task;
    }

    /**
     * Returns the task.
     * @return The task
     */
    public Task getTask()
    {
        return _task;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String name = "";
        try {
            name = _task.getInfo().getName();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        // plugin cannot be NULL!
        String plugin = "ERROR";
        try {
            plugin = _task.getPlugin().toString();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        return (name != null ? name : "") + " [" + plugin + "]";
    }

    private static final Logger LOGGER = Logger.getLogger(TaskGUI.class);
}