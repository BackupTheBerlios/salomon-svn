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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.Messages;
import salomon.engine.Resources;

/**
 * Class agregates all buttons used in GUIController.
 */
public final class GUIButtons
{
	private JButton _btnAdd = null;

	private JButton _btnDown = null;

	private JButton _btnFirst = null;

	private JButton _btnLast = null;

	private JButton _btnRemove = null;

	private JButton _btnRemoveAll = null;

	private JButton _btnRun;

	private JButton _btnUp = null;

	/**
	 * 
	 * @uml.property name="_manipulationListener"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ManipulationListener _manipulationListener = null;

	private String _resourcesDir = null;

	/**
	 * 
	 * @uml.property name="_taskManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TaskManagerGUI _taskManagerGUI = null;

	public GUIButtons(TaskManagerGUI taskManagerGUI)
	{
		_taskManagerGUI = taskManagerGUI;
		_manipulationListener = new ManipulationListener();
		_resourcesDir = Config.getString("RESOURCES_DIR");
	}

	public void setEditionManager(TaskManagerGUI manager)
	{
		_taskManagerGUI = manager;
	}

	/**
	 * This method initializes _btnAdd
	 * 
	 * @return JButton
	 */
	JButton getBtnAdd()
	{
		if (_btnAdd == null) {
			_btnAdd = createManipulationButton(
					Resources.getString("ICO_TASK_ADD"), _taskManagerGUI.getActionManager().getAddTaskAction()); //$NON-NLS-1$
		}
		return _btnAdd;
	}

	/**
	 * This method initializes _btnDown
	 * 
	 * @return JButton
	 */
	JButton getBtnDown()
	{
		if (_btnDown == null) {
			_btnDown = createManipulationButton(
					Resources.getString("ICO_TASK_DOWN"), null); //$NON-NLS-1$
		}
		return _btnDown;
	}

	/**
	 * This method initializes _btnFirst
	 * 
	 * @return JButton
	 */
	JButton getBtnFirst()
	{
		if (_btnFirst == null) {
			_btnFirst = createManipulationButton(
					Resources.getString("ICO_TASK_FIRST"), null); //$NON-NLS-1$
		}
		return _btnFirst;
	}

	/**
	 * This method initializes _btnLast
	 * 
	 * @return JButton
	 */
	JButton getBtnLast()
	{
		if (_btnLast == null) {
			_btnLast = createManipulationButton(
					Resources.getString("ICO_TASK_LAST"), null); //$NON-NLS-1$
		}
		return _btnLast;
	}

	/**
	 * This method initializes _btnRemove
	 * 
	 * @return JButton
	 */
	JButton getBtnRemove()
	{
		if (_btnRemove == null) {
			_btnRemove = createManipulationButton(
					Resources.getString("ICO_TASK_REMOVE"), _taskManagerGUI.getActionManager().getRemoveTaskAction()); //$NON-NLS-1$
		}
		return _btnRemove;
	}

	/**
	 * This method initializes _btnRemoveAll
	 * 
	 * @return JButton
	 */
	JButton getBtnRemoveAll()
	{
		if (_btnRemoveAll == null) {
			_btnRemoveAll = createManipulationButton(
					Resources.getString("ICO_TASK_REMOVEALL"), _taskManagerGUI.getActionManager().getRemoveAllTasksAction()); //$NON-NLS-1$
		}
		return _btnRemoveAll;
	}

	/**
	 * This method initializes _btnRun
	 * 
	 * @return JButton
	 */
	JButton getBtnRun()
	{
		if (_btnRun == null) {
			_btnRun = new JButton(
					_taskManagerGUI.getActionManager().getRunTaskAction());
			_btnRun.setText(Messages.getString("BTN_RUN")); //$NON-NLS-1$
		}
		return _btnRun;
	}

	/**
	 * This method initializes _btnUp
	 * 
	 * @return JButton
	 */
	JButton getBtnUp()
	{
		if (_btnUp == null) {
			_btnUp = createManipulationButton(
					Resources.getString("ICO_TASK_UP"), null); //$NON-NLS-1$
		}
		return _btnUp;
	}

	/**
	 * Creates button with given text and standard settings
	 * 
	 * @param text
	 * @return
	 */
	private JButton createManipulationButton(String text, Action action)
	{
		JButton button;
		if (action == null) {
			button = new JButton();
		} else {
			button = new JButton(action);
		}

		button.setIcon(new ImageIcon(_resourcesDir + Config.FILE_SEPARATOR
				+ text));
		button.addActionListener(_manipulationListener);
		Dimension dim = new Dimension(50, 25);
		button.setPreferredSize(dim);
		button.setMinimumSize(dim);
		button.setMaximumSize(dim);
		return button;
	}

	/**
	 * Class handles events from buttons, which are used to manage tasks.
	 */
	private class ManipulationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object object = e.getSource();
			if (object == _btnUp) {
				_taskManagerGUI.moveUp();
			} else if (object == _btnDown) {
				_taskManagerGUI.moveDown();
			} else if (object == _btnFirst) {
				_taskManagerGUI.moveFirst();
			} else if (object == _btnLast) {
				_taskManagerGUI.moveLast();
			} else {
				LOGGER.error("Not supported button: " + object); //$NON-NLS-1$
			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(GUIButtons.class);
}