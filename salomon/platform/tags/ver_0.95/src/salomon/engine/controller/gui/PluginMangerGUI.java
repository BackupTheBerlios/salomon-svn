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
import javax.swing.JFrame;
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
import salomon.engine.controller.gui.viewer.PluginViewer;
import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.plugin.PluginManager;

import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

public final class PluginMangerGUI
{

	/**
	 * 
	 * @uml.property name="_actionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ActionManager _actionManager;

	private JComponent _editPluginPanel;

	/**
	 * 
	 * @uml.property name="_parent"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ControllerFrame _parent;

	private JList _pluginList;

	private DefaultListModel _pluginListModel;

	/**
	 * 
	 * @uml.property name="_pluginManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IPluginManager _pluginManager;

	private JPopupMenu _pluginPopup;

	private JFrame _pluginsViewerFrame;

	private MouseListener _popupListener;

	private int _selectedItem;

	private StatusBar _statusBar;

	private JTextField _txtPluginDescription;

	private JTextField _txtPluginLocation;

	private JTextField _txtPluginName;

	/**
	 * 
	 */
	public PluginMangerGUI(IPluginManager pluginManager)
	{
		_pluginManager = pluginManager;

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
		_txtPluginDescription.setText("");
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
			LocalPlugin plugin = null;
			try {
				plugin = (LocalPlugin) _pluginManager.createPlugin();
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
				return;
			}
			boolean wasOk = false;
			try {
				PluginInfo desc = plugin.getInfo();
				desc.setName(_txtPluginName.getText());
				desc.setLocation(url);
				desc.setInfo(_txtPluginDescription.getText());

				wasOk = _pluginManager.savePlugin(plugin);
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

		ILocalPlugin[] plugins = null;
		try {
			_pluginListModel.removeAllElements();
			_pluginManager.clearPluginList();
			plugins = _pluginManager.getPlugins();

			for (ILocalPlugin plugin : plugins) {
				LOGGER.debug("adding plugin:" + plugin);
				LOGGER.debug("description:" + plugin.getInfo().getInfo());
				_pluginListModel.addElement(plugin);
			}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load plugin list");
			return;
		}
	}

	public void removePlugin()
	{
		if (Utils.showQuestionMessage(Messages.getString("TIT_WARN"),
				Messages.getString("TXT_REMOVE_PLUGIN_QUESTION"))) {
			ILocalPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);

			boolean wasOk = false;
			try {
				wasOk = _pluginManager.removePlugin(plugin);
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
		LocalPlugin plugin = (LocalPlugin) _pluginListModel.get(_selectedItem);
		PluginInfo pluginInfo = null;
		try {
			pluginInfo = plugin.getInfo();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage(Messages.getString("ERR_CANNOT_SAVE_PLUGIN"));
			return;
		}
		// to initialize components
		getEditPluginPanel();
		_txtPluginName.setText(pluginInfo.getName());
		_txtPluginLocation.setText(pluginInfo.getLocation().toString());
		_txtPluginDescription.setText(pluginInfo.getInfo());
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
			boolean wasOk = false;
			try {
				pluginInfo.setName(_txtPluginName.getText());
				pluginInfo.setLocation(url);
				pluginInfo.setInfo(_txtPluginDescription.getText());

				wasOk = _pluginManager.savePlugin(plugin);
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

	/**
	 * Set the value of statusBar field.
	 * @param statusBar The statusBar to set
	 */
	public void setStatusBar(StatusBar statusBar)
	{
		_statusBar = statusBar;
	}

	public void viewPlugins()
	{
		if (_pluginsViewerFrame == null) {
			_pluginsViewerFrame = new JFrame(Messages.getString("TIT_PLUGINS"));
			_pluginsViewerFrame.getContentPane().add(
					new PluginViewer(
							((PluginManager) _pluginManager).getDBManager()));
			_pluginsViewerFrame.pack();
		}

		_pluginsViewerFrame.setVisible(true);
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

	private final class PluginEditPanel extends JPanel
	{

		private Dimension _labelDim = new Dimension(100, 20);

		private Dimension _textDim = new Dimension(250, 20);

		public PluginEditPanel()
		{
			_txtPluginName = new JTextField();
			_txtPluginLocation = new JTextField();
			_txtPluginDescription = new JTextField();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_NAME"),
					_txtPluginName));
			this.add(createLabeledText(
					Messages.getString("TXT_PLUGIN_LOCATION"),
					_txtPluginLocation));
			this.add(createLabeledText(Messages.getString("TXT_PLUGIN_INFO"),
					_txtPluginDescription));
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

	private final class PluginSelectionListener
			implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			_actionManager.getAddTaskAction().setPlugin(
					(LocalPlugin) ((JList) e.getSource()).getSelectedValue());
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
