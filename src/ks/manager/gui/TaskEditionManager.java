/*
 * Created on 2004-05-03
 *
 */

package ks.manager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import ks.core.plugin.AbstractPlugin;
import ks.core.plugin.Settings;
import ks.core.task.Task;
import ks.plugins.AveragePrice;
import ks.plugins.SimpleSQLConsole;

import org.apache.log4j.Logger;

/**
 * @author nico
 *  
 */
class TaskEditionManager
{
	private JList _pluginList = null;

	private DefaultListModel _pluginListModel = null;

	private JList _taskList = null;

	private DefaultListModel _taskListModel = null;

	private JPopupMenu _taskPopup = null;

	private JPopupMenu _pluginPopup = null;

	private int _selectedItem = -1;

	private MouseListener _popupListener = null;

	private static Logger _logger = Logger.getLogger(TaskEditionManager.class);
	
	public TaskEditionManager()
	{
		_taskListModel = new DefaultListModel();
		_pluginListModel = new DefaultListModel();
		_taskList = new JList(_taskListModel);		
		_pluginList = new JList(_pluginListModel);
		// adding listener
		_popupListener = new PopupListener();
		_taskList.addMouseListener(_popupListener);
		_pluginList.addMouseListener(_popupListener);
		// sample - TODO: add here PluginLoadera
		_pluginListModel.addElement(new AveragePrice());
		_pluginListModel.addElement(new SimpleSQLConsole());
	}

	/**
	 * Adds selected plugin to list of tasks and removes it from available
	 * plugin list (?)
	 *  
	 */
	public void addTask()
	{
		int index = _pluginList.getSelectedIndex();
		if (index >= 0) {
			AbstractPlugin plugin = (AbstractPlugin) _pluginListModel.get(index);		
			AbstractPlugin pluginCopy = (AbstractPlugin) plugin.clone();
			_logger.debug("plugin = " + pluginCopy);
			Task task = new Task();
			task.setPlugin(pluginCopy);
			_taskListModel.addElement(task);
		} else {
			_logger.warn("Invalid index. Wrong list selected?");
		}
	}

	public JList getPluginList()
	{
		return _pluginList;
	}

	public JList getTaskList()
	{
		return _taskList;
	}

	public List getTasks()
	{
		return Arrays.asList(_taskListModel.toArray());
	}

	/**
	 * Moves task up in the task list. It will be executed earlier.
	 *  
	 */
	public void moveDown()
	{		
		if (_taskListModel.size() > 1) {
			int index = _taskList.getSelectedIndex();
			if (index >= 0 && index < _taskListModel.getSize() - 1) {
				Object task = _taskListModel.remove(index);
				_taskListModel.add(index + 1, task);
				_taskList.setSelectedIndex(index + 1);
			} else {
				_logger.warn("Nothing or wrong index selected");
			}
		}
	}

	/**
	 * Moves task down in the task list. It will be executed later.
	 *  
	 */
	public void moveUp()
	{
		if (_taskListModel.size() > 1) {
			int index = _taskList.getSelectedIndex();
			if (index >= 1) {
				Object task = _taskListModel.remove(index);
				_taskListModel.add(index - 1, task);
				_taskList.setSelectedIndex(index - 1);
			} else {
				_logger.warn("Nothing or wrong index selected");
			}
		}
	}

	/**
	 * Removes plugin from list of tasks and adds it to available plugins list
	 * (?)
	 *  
	 */
	public void removeTask()
	{
		int index = _taskList.getSelectedIndex();
		if (index >= 0) {
			Task task = (Task) _taskListModel.remove(index);
			System.out.println("plugin = " + task);
		} else {
			_logger.warn("Invalid index. Wrong list selected?");
		}
	}

	private JPopupMenu getPluginPopup()
	{
		if (_pluginPopup == null) {
			_pluginPopup = new JPopupMenu();
			JMenuItem itmInfo = new JMenuItem("Info");
			itmInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					// TODO:
					String info = _pluginListModel.get(_selectedItem)
							.toString();
					JOptionPane.showMessageDialog(null, info);
				}
			});
			_pluginPopup.add(itmInfo);
		}
		return _pluginPopup;
	}

	private JPopupMenu getTaskPopup()
	{
		if (_taskPopup == null) {
			_taskPopup = new JPopupMenu();
			JMenuItem itmSettings = new JMenuItem("Settings");
			itmSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					// TODO:
					Task currentTask = (Task) _taskListModel.get(_selectedItem);
					AbstractPlugin plugin = currentTask.getPlugin();
					int result = JOptionPane.showConfirmDialog(null, plugin
							.getSettingsPanel(), "Plugin settings",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						Settings settings = plugin.getSettings();
						_logger.info("settings: " + settings);
						currentTask.setSettings(settings);
					}
				}
			});
			JMenuItem itmResult = new JMenuItem("Result");
			itmResult.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					// TODO:
					Task currentTask = (Task) _taskListModel.get(_selectedItem);
					AbstractPlugin plugin = currentTask.getPlugin();
					JOptionPane.showMessageDialog(null, plugin
							.getResultPanel(), "Plugin result",
							JOptionPane.INFORMATION_MESSAGE);
					
				}
			});
			_taskPopup.add(itmSettings);
			_taskPopup.add(itmResult);
		}
		return _taskPopup;
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
				if (list == _pluginList) {
					getPluginPopup().show(e.getComponent(), e.getX(), e.getY());
				} else {
					getTaskPopup().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}
}
