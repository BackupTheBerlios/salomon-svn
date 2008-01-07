/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.engine.controller.gui.task;

import org.apache.log4j.Logger;

import salomon.engine.task.ITask;
import salomon.engine.task.Task;
import salomon.platform.exception.PlatformException;
import edu.uci.ics.jung.graph.impl.SparseVertex;

public final class TaskVertex extends SparseVertex
{
    private ITask _task;

    /**
     * @param task
     */
    public TaskVertex(ITask task)
    {
        _task = task;
    }

    public ITask getTask()
    {
        return _task;
    }

    @Override
    public String toString()
    {
        String name = "";
        try {
            name = ((Task) _task).getInfo().getName();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        // plugin cannot be NULL!
        String plugin = "ERROR";
        //FIXME:
//        try {
//            plugin = _task.getPlugin().toString();
//        } catch (PlatformException e) {
//            LOGGER.fatal("", e);
//        }
        return (name != null ? name : "") + " [" + plugin + "]";
    }

    private static final Logger LOGGER = Logger.getLogger(TaskVertex.class);
}
