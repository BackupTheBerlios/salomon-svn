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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.plugin.LocalPlugin;
import salomon.platform.exception.PlatformException;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.util.gui.Utils;

/**
 * 
 */
public final class PluginMangerGUI
{
	private ActionManager _actionManager;

	private JComponent _editPluginPanel;

	private IManagerEngine _managerEngine;

	private ControllerFrame _parent;

	private JList _pluginList;

	private DefaultListModel _pluginListModel;

	private JPopupMenu _pluginPopup;

	private MouseListener _popupListener;

	private int _selectedItem;

	private JTextField _txtPluginInfo;

	private JTextField _txtPluginLocation;

	private JTextField _txtPluginName;

	/**
	 * 
	 */
	public PluginMangerGUI(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;

		_pluginListModel = new DefaultListModel();
		_pluginList = new JList(_pluginListModel);
		// adding listener
		_popupListener = new PopupListener();
		_pluginList.addMouseListener(_popupListener);
		// TODO:
		_pluginList.addListSelectionListener(new PluginSelectionListener());
	}

	public void addPlugin()
	{
		// TODO: change it
		getEditPluginPanel();
		_txtPluginName.setText("");
		_txtPluginLocation.setText("");
		_txtPluginInfo.setText("");
		int retVal = JOptionPane.showConfirmDialog(_parent,
				getEditPluginPanel(), Messages.getString("MNU_ADD_PLUGIN"),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (retVal == JOptionPane.OK_OPTION) {
			URL url = null;
			try {
				url = new URL(_txtPluginLocation.getText());
			} catch (MalformedURLException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN_INVALID_URL"));
				return;
			}
			IPlugin plugin = null;
			try {
				plugin = _managerEngine.getPluginManager().createPlugin();
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
				return;
			}
			Description desc = plugin.getDescription();
			desc.setName(_txtPluginName.getText());
			desc.setLocation(url);
			desc.setInfo(_txtPluginInfo.getText());
			boolean wasOk = false;
			try {
				wasOk = _managerEngine.getPluginManager().savePlugin(plugin);
			} catch (PlatformException e) {
				LOGGER.error("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
			if (wasOk) {
				refresh();
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
		}
	}

	public JList getPluginList()
	{
		return _pluginList;
	}

	public void refresh()
	{
		LOGGER.debug("reloading plugins");
		_pluginListModel.removeAllElements();

		IPlugin[] plugins = null;
		try {
			plugins = _managerEngine.getPluginManager().getPlugins();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load plugin list");
			return;
		}
		for (IPlugin plugin : plugins) {
			LOGGER.debug("adding plugin:" + plugin);
			LOGGER.debug("description:" + plugin.getDescription());
			_pluginListModel.addElement(plugin);
		}
	}

	public void removePlugin()
	{
		if (Utils.showQuestionMessage(Messages.getString("TIT_WARN"),
				Messages.getString("TXT_REMOVE_PLUGIN_QUESTION"))) {
			IPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);

			boolean wasOk = false;
			try {
				wasOk = _managerEngine.getPluginManager().removePlugin(plugin);
			} catch (PlatformException e) {
				LOGGER.error("", e);
			}

			if (wasOk) {
				_pluginListModel.remove(_selectedItem);
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
		}
	}

	public void savePlugin()
	{
		IPlugin plugin = (IPlugin) _pluginListModel.get(_selectedItem);
		Description desc = plugin.getDescription();
		// to initialize components
		getEditPluginPanel();
		_txtPluginName.setText(desc.getName());
		_txtPluginLocation.setText(desc.getLocation().toString());
		_txtPluginInfo.setText(desc.getInfo());
		int retVal = JOptionPane.showConfirmDialog(_parent,
				getEditPluginPanel(), Messages.getString("MNU_EDIT_PLUGIN"),
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (retVal == JOptionPane.OK_OPTION) {
			URL url = null;
			try {
				url = new URL(_txtPluginLocation.getText());
			} catch (MalformedURLException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
				return;
			}
			desc.setName(_txtPluginName.getText());
			desc.setLocation(url);
			desc.setInfo(_txtPluginInfo.getText());

			boolean wasOk = false;
			try {
				wasOk = _managerEngine.getPluginManager().savePlugin(plugin);
			} catch (PlatformException e) {
				LOGGER.error("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}

			if (wasOk) {
				// refresh();
			} else {
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			}
		}
	}

	public void setActionManager(ActionManager actionManager)
	{
		_actionManager = actionManager;
	}

	private JComponent getEditPluginPanel()
	{
		if (_editPluginPanel == null) {
			_editPluginPanel = new PluginEditPanel();
		}

		return _editPluginPanel;
	}

	private JPopupMenu getPluginPopup()
	{
		if (_pluginPopup == null) {
			_pluginPopup = new JPopupMenu();

			JMenuItem itmAdd = new JMenuItem(
					_actionManager.getAddPluginAction());
			itmAdd.setText(Messages.getString("MNU_ADD_PLUGIN")); //$NON-NLS-1$

			JMenuItem itmEdit = new JMenuItem(
					_actionManager.getSavePluginAction()); //$NON-NLS-1$
			itmEdit.setText(Messages.getString("MNU_EDIT_PLUGIN"));
			JMenuItem itmRemove = new JMenuItem(
					_actionManager.getRemovePluginAction()); //$NON-NLS-1$
			itmRemove.setText(Messages.getString("MNU_REMOVE_PLUGIN"));
			_pluginPopup.add(itmAdd);
			_pluginPopup.add(itmEdit);
			_pluginPopup.add(itmRemove);
		}
		return _pluginPopup;
	}

	private final class PluginSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			_actionManager.getAddTaskAction().setPlugin((LocalPlugin)((JList)e.getSource()).getSelectedValue());				
		}
	}

	private final class PluginEditPanel extends JPanel
	{

		private Dimension _labelDim = new Dimension(100, 20);

		private Dimension _textDim = new Dimension(250, 20);

		public PluginEditPanel()
		{
			_txtPluginName = new JTextField();
			_txtPluginLocation = new JTextField();
			_txtPluginInfo = new JTextField();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_NAME"),
					_txtPluginName));
			this.add(createLabeledText(
					Messages.getString("TXT_PLUGIN_LOCATION"),
					_txtPluginLocation));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_INFO"),
					_txtPluginInfo));
		}

		private Box createLabeledText(String labelText, JTextField textField)
		{
			Box box = Box.createHorizontalBox();
			JLabel label = new JLabel(labelText);
			label.setPreferredSize(_labelDim);
			label.setMinimumSize(_labelDim);
			label.setMaximumSize(_labelDim);
			textField.setPreferredSize(_textDim);
			textField.setMinimumSize(_textDim);
			textField.setMaximumSize(_textDim);
			box.add(label);
			box.add(textField);

			return box;
		}
	}

	private final class PopupListener extends MouseAdapter
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
				JList list = (JList) e.getSource();
				_selectedItem = list.locationToIndex(e.getPoint());
				if (_selectedItem >= 0) {
					getPluginPopup().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	private static final Logger LOGGER = Logger.getLogger(PluginMangerGUI.class);
}
