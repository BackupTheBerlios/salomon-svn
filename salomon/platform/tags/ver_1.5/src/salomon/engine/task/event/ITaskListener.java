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

package salomon.engine.task.event;

/**
 * 
 */
public interface ITaskListener
{
    void taskFailed(TaskEvent event);

    void taskPaused(TaskEvent event);

    void taskProcessed(TaskEvent event);

    void taskResumed(TaskEvent event);

    void tasksInitialized(int taskCount);

    void tasksProcessed();

    void taskStarted(TaskEvent event);

    void taskStopped(TaskEvent event);
}
