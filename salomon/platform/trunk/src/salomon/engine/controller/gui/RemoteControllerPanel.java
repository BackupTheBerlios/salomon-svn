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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import salomon.engine.Messages;

import salomon.engine.platform.holder.ManagerEngineHolder;

/**
 * Panel displaying list of connected clients. 
 */
public final class RemoteControllerPanel
{

	private JList _controllerList;

	private DefaultListModel _controllerListModel;

	private JPopupMenu _controllerPopup;

	private ManagerEngineHolder _engineHolder;

	private ControllerFrame _parent;

	private int _selectedItem;

	public RemoteControllerPanel(ManagerEngineHolder engineHolder)
	{
		_engineHolder = engineHolder;
		_controllerListModel = new DefaultListModel();
		_controllerList = new JList(_controllerListModel);
		_controllerList.addMouseListener(new PopupListener());
		_controllerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_controllerList.addListSelectionListener(new RemoteControllerSelectionListener());
	}

	public void addController(RemoteControllerGUI controller)
	{
		_controllerListModel.addElement(controller);
		LOGGER.debug("controller added.");
	}

	/**
	 * @return Returns the hostList.
	 */
	public JComponent getControllerPanel()
	{
		return new JScrollPane(_controllerList);
	}

	public void removeAllControllers()
	{
		LOGGER.debug("Removing controllers: " + _controllerListModel.getSize());
		// slow but simple ;-)
		Object[] controllers = _controllerListModel.toArray();
		for (int i = 0; i < controllers.length; i++) {
			removeController((RemoteControllerGUI) controllers[i]);
		}
	}

	public void removeController(RemoteControllerGUI controller)
	{
		LOGGER.debug("RemoteControllerPanel.removeController()");
		controller.exit();
		_controllerListModel.removeElement(controller);
		LOGGER.debug("controller removed.");
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ControllerFrame parent)
	{
		_parent = parent;
	}

	private JPopupMenu getControllerPopup()
	{
		if (_controllerPopup == null) {
			_controllerPopup = new JPopupMenu();
			JMenuItem itmRemoveController = new JMenuItem(
					Messages.getString("MNU_REMOVE_CONTROLLER")); //$NON-NLS-1$
			itmRemoveController.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					removeController();
				}
			});
			_controllerPopup.add(itmRemoveController);
		}
		return _controllerPopup;
	}

	private void removeController()
	{
		RemoteControllerGUI controllerGUI = (RemoteControllerGUI) _controllerListModel.get(_selectedItem);
		removeController(controllerGUI);
	}

	private class PopupListener extends MouseAdapter
	{
		public void mousePressed(MouseEvent e)
		{
			maybeShowPopup(e);
		}

		public void mouseReleased(MouseEvent e)
		{
			maybeShowPopup(e);
		}

		private void maybeShowPopup(MouseEvent e)
		{
			if (e.isPopupTrigger()) {
				// zapamietanie ktory komponent z listy wywoluje menu
				JList list = (JList) e.getSource();
				_selectedItem = list.locationToIndex(e.getPoint());
				if (_selectedItem >= 0) {
					getControllerPopup().show(e.getComponent(), e.getX(),
							e.getY());
				}
			}
		}
	}

	private final class RemoteControllerSelectionListener
			implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			RemoteControllerGUI controllerGUI = (RemoteControllerGUI) ((JList) e.getSource()).getSelectedValue();
            if (controllerGUI != null) {
            	_engineHolder.setCurrentManager(controllerGUI.getManagerEngine());
            	_parent.refreshGui();
            }
		}

	}

	private static Logger LOGGER = Logger.getLogger(RemoteControllerPanel.class);
}