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

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import salomon.engine.controller.gui.IControllerPanel;
import salomon.engine.controller.gui.SolutionManagerGUI;
import salomon.engine.controller.gui.StatusBar;
import salomon.engine.controller.gui.TaskManagerGUI;

public final class GraphControllerPanel extends JPanel
		implements IControllerPanel
{
	private SolutionManagerGUI _solutionManagerGUI;
	private StatusBar _statusBar;
	
	public GraphControllerPanel(SolutionManagerGUI solutionManagerGUI)
	{
		_statusBar = new StatusBar();

		_solutionManagerGUI = solutionManagerGUI;
		_solutionManagerGUI.setStatusBar(_statusBar);
		setLayout(new BorderLayout());
		this.add(_statusBar.getStatusPanel(), BorderLayout.SOUTH);
		TaskGraphEditor taskGraphEditor = new TaskGraphEditor();
		add(taskGraphEditor, BorderLayout.CENTER);
	}

	public JComponent getComponent()
	{
		return this;
	}

	public TaskManagerGUI getTaskEditionManager()
	{
		return null;
//		throw new UnsupportedOperationException(
//				"Method GraphControllerPanel.getTaskEditionManager() not implemented yet!");
	}

	public void refresh()
	{
//		throw new UnsupportedOperationException(
//				"Method GraphControllerPanel.refresh() not implemented yet!");
	}

	public void setTaskEditionManager(TaskManagerGUI taskEditionManager)
	{
		throw new UnsupportedOperationException(
				"Method GraphControllerPanel.setTaskEditionManager() not implemented yet!");
	}

}
