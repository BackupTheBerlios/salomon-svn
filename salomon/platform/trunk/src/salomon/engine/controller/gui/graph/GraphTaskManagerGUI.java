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

package salomon.engine.controller.gui.graph;

import javax.swing.JPanel;

import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.StatusBar;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.task.ITaskManager;

public final class GraphTaskManagerGUI
{

	private ActionManager _actionManager;

	private ControllerFrame _parent;

	private StatusBar _statusBar;

	private ITaskManager _taskManager;

	public GraphTaskManagerGUI(ITaskManager tasksManager)
	{
		_taskManager = tasksManager;
	}

	public void addTask(LocalPlugin plugin)
	{
		throw new UnsupportedOperationException(
				"Method addTask() not implemented yet!");
	}

	public void editTask()
	{
		throw new UnsupportedOperationException(
				"Method editTask() not implemented yet!");
	}

	/**
	 * Returns the actionManager.
	 * 
	 * @return The actionManager
	 */
	public ActionManager getActionManager()
	{
		return _actionManager;
	}

	public JPanel getGraphPanel()
	{
		// FIXME:
		return new TaskGraphEditor();
	}

	public void refresh()
	{
		//FIXME:
	}

	public void removeAllTasks()
	{
		throw new UnsupportedOperationException(
				"Method removeAllTasks() not implemented yet!");
	}

	public void removeTask()
	{
		throw new UnsupportedOperationException(
				"Method removeTask() not implemented yet!");
	}

	public void runTasks()
	{
		throw new UnsupportedOperationException(
				"Method runTasks() not implemented yet!");
	}

	public boolean saveTasks()
	{
		throw new UnsupportedOperationException(
				"Method saveTasks() not implemented yet!");
	}

	public void setActionManager(ActionManager actionManager)
	{
		_actionManager = actionManager;
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ControllerFrame parent)
	{
		_parent = parent;
	}

	/**
	 * Set the value of statusBar field.
	 * 
	 * @param statusBar The statusBar to set
	 */
	public void setStatusBar(StatusBar statusBar)
	{
		_statusBar = statusBar;
	}

	public void viewTasks()
	{
		throw new UnsupportedOperationException(
				"Method viewTasks() not implemented yet!");
	}

}
