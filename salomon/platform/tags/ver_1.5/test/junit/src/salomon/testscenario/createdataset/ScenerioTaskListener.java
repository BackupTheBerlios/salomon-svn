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

package salomon.testscenario.createdataset;

import salomon.engine.task.event.ITaskListener;
import salomon.engine.task.event.TaskEvent;

import junit.framework.TestCase;

/**
 * Listener to handle task events in tests.
 *
 */
class ScenerioTaskListener implements ITaskListener
{
    private TestCase _testCase;

    public ScenerioTaskListener(TestCase testCase)
    {
        _testCase = testCase;
    }

    public void taskFailed(TaskEvent event)
    {
        _testCase.assertTrue("task: " + event.getInfo().getName() + " FAILED",
                false);
    }

    public void taskPaused(TaskEvent event)
    {
        // empty body
    }

    public void taskProcessed(TaskEvent event)
    {
        _testCase.assertTrue("task: " + event.getInfo().getName()
                + " PROCESSED", true);
    }

    public void taskResumed(TaskEvent event)
    {
        // empty body
    }

    public void tasksInitialized(int taskCount)
    {
        // empty body
    }

    public void tasksProcessed()
    {
        _testCase.assertTrue("Task executed", true);
    }

    public void taskStarted(TaskEvent event)
    {
        // empty body
    }

    public void taskStopped(TaskEvent event)
    {
        // empty body
    }
}