/*
 * $RCSfile:$
 * $Revision:$
 *
 * Comments:
 *
 * (C) Copyright ParaSoft Corporation 2004.  All rights reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF ParaSoft
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 *
 * $Author:$          $Locker:$
 * $Date:$
 * $Log:$
 */

package salomon.core.task;

import java.util.List;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface ITaskManager
{
	public void addAllTasks(List tasks);

	public void addTask(Task task);

	public void clearTaskList();

	public Task getCurrentTask();

	public List getTasks();

	public void start();
}