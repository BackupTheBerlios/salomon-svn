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

package salomon.engine.controller.gui.statusbar;

import salomon.engine.project.event.ProjectEvent;
import salomon.engine.project.event.ProjectListener;
import salomon.engine.solution.event.SolutionEvent;
import salomon.engine.solution.event.SolutionListener;
import salomon.engine.task.event.TaskEvent;
import salomon.engine.task.event.TaskListener;

/**
 * 
 */
public final class StatusBarManager
        implements TaskListener, ProjectListener, SolutionListener
{
    private static final String CURRENT_PROJECT = "TT_CURRENT_PROJECT";

    private static final String CURRENT_SOLUTION = "TT_CURRENT_SOLUTION";

    private static final String CURRENT_TASK = "TT_CURRENT_TASK";

    private StatusBar _statusBar;

    public StatusBarManager()
    {
        _statusBar = new StatusBar();
        _statusBar.addItem(CURRENT_SOLUTION);
        _statusBar.addItem(CURRENT_PROJECT);
        _statusBar.addItem(CURRENT_TASK);
    }

    public void taskFailed(TaskEvent event)
    {
        // empty body
    }

    public void taskPaused(TaskEvent event)
    {
        // empty body
    }

    public void taskProcessed(TaskEvent event)
    {
        // empty body
    }

    public void taskResumed(TaskEvent event)
    {
        // empty body
    }

    public void taskStarted(TaskEvent event)
    {
        _statusBar.setItem(CURRENT_TASK, event.getInfo().getName());
    }

    public void taskStopped(TaskEvent event)
    {
        // empty body
    }

    public void tasksInitialized(int taskCount)
    {
        _statusBar.setItem(CURRENT_TASK, "");
    }

    public void tasksProcessed()
    {
        // empty body
    }

    public void projectCreated(ProjectEvent event)
    {
        _statusBar.setItem(CURRENT_PROJECT, event.getInfo().getName());
    }

    public void projectModified(ProjectEvent event)
    {
        _statusBar.setItem(CURRENT_PROJECT, event.getInfo().getName());
    }

    public void projectOpened(ProjectEvent event)
    {
        _statusBar.setItem(CURRENT_PROJECT, event.getInfo().getName());
    }

    public StatusBar getStatusBar()
    {
        return _statusBar;
    }

    public void solutionCreated(SolutionEvent event)
    {
        _statusBar.setItem(CURRENT_SOLUTION, event.getInfo().getName());
    }

    public void solutionModified(SolutionEvent event)
    {
        _statusBar.setItem(CURRENT_SOLUTION, event.getInfo().getName());
    }

    public void solutionOpened(SolutionEvent event)
    {
        _statusBar.setItem(CURRENT_SOLUTION, event.getInfo().getName());
    }

}
