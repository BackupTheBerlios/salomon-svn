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

import java.util.Collection;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface ITaskManager
{
	public void clearTaskList();

	public ITask createTask();

	public ITask getCurrentTask();

	public Collection getTasks();

	public void start();
}