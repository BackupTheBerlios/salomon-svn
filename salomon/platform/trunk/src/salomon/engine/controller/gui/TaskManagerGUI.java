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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.controller.gui.viewer.TaskViewer;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.TaskManager;

import salomon.util.gui.Utils;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * Class used to manage with tasks editing. It enables creating and configuring
 * tasks and a queue of tasks.
 */
public final class TaskManagerGUI
{

	/**
	 * 
	 * @uml.property name="_actionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ActionManager _actionManager;

	/**
	 * 
	 * @uml.property name="_parent"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ControllerFrame _parent;

	private MouseListener _popupListener;

	private JComponent _positionComponent;

	private int _selectedItem;

	private JList _taskList;

	private DefaultListModel _taskListModel;

	/**
	 * 
	 * @uml.property name="_taskManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ITaskManager _taskManager;

	private JPopupMenu _taskPopup;

	private JFrame _tasksViewerFrame;

	public TaskManagerGUI(ITaskManager taskManager)
	{
		_taskManager = taskManager;
		_taskListModel = new DefaultListModel();
		_taskList = new JList(_taskListModel);
		// adding listener
		_popupListener = new PopupListener();
		_taskList.addMouseListener(_popupListener);
	}

	/**
	 * Adds selected plugin to list of tasks
	 */
	public void addTask(LocalPlugin localPlugin)
	{
		if (localPlugin == null) {
			LOGGER.debug("LocalPLugin == null");
			Utils.showErrorMessage(Messages.getString("ERR_PLUGIN_NOT_SELECTED"));
			return;
		}

		try {
			// loading plugin			
			localPlugin.load();

			// creating task
			ITask task = _taskManager.createTask();

			// setting some params from GUI
			TaskGUI taskGUI = new TaskGUI(task);
			taskGUI.getTask().getInfo().setName(getTaskName());
			taskGUI.getTask().setPlugin(localPlugin);

			// adding task to managers
			_taskManager.addTask(task);
			_taskListModel.addElement(taskGUI);

		} catch (Exception e) {
			LOGGER.fatal("", e); //$NON-NLS-1$				
			Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
		}
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
				LOGGER.warn("Nothing or wrong index selected"); //$NON-NLS-1$
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
				LOGGER.warn("Nothing or wrong index selected"); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Loads tasks to task list.
	 */
	public void refresh()
	{
		LOGGER.debug("reloading tasks");
		_taskListModel.removeAllElements();
		ITask[] tasks = null;
		try {
			// TODO: change it
			tasks = _taskManager.getTasks();
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
			Utils.showErrorMessage("Cannot load task list");
			return;
		}
		for (ITask task : tasks) {
			LOGGER.debug("adding task");
			_taskListModel.addElement(new TaskGUI(task));
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
			TaskGUI task = (TaskGUI) _taskListModel.remove(index);
			LOGGER.debug("plugin = " + task); //$NON-NLS-1$
		} else {
			LOGGER.warn("Invalid index. Wrong list selected?"); //$NON-NLS-1$
		}
	}

	/**
	 * Starts executing task.
	 */
	public void runTasks()
	{
		try {
			_taskManager.start();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_RUN_TASKS");
		}
	}

	public boolean saveTasks()
	{

		// task list cannot be empty		
		if (_taskListModel.isEmpty()) {
			Utils.showErrorMessage("WRN_NO_TASK_TO_SAVE");
			return false;
		}

		// checking if all tasks have settings set
		// if not - question to user, if he agrees
		// default settings will be set and project will be saved

		TaskGUI[] tasks = new TaskGUI[_taskListModel.size()];
		_taskListModel.copyInto(tasks);

		List<TaskGUI> incorrectTasks = new LinkedList<TaskGUI>();

		try {
			for (TaskGUI taskGUI : tasks) {
				if (taskGUI.getTask().getSettings() == null) {
					incorrectTasks.add(taskGUI);
				}
			}
			if (!incorrectTasks.isEmpty()) {
				String message = "There are tasks with settings not set:\n";
				for (TaskGUI taskGUI : incorrectTasks) {
					message += taskGUI.getTask().getInfo().getName() + "\n";
				}
				message += "Do you want to use default settings?";
				if (Utils.showWarningMessage(message)) {
					// getting default settings
					LOGGER.debug("getting default settings");
					for (TaskGUI task : incorrectTasks) {
						ISettings defaultSettings = task.getTask().getPlugin().getSettingComponent().getDefaultSettings();
						task.getTask().setSettings(defaultSettings);
					}
				} else {
					return false;
				}
			}
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			return false;
		}

		return true;
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

	public void viewTasks()
	{
		if (_tasksViewerFrame == null) {
			_tasksViewerFrame = new JFrame(Messages.getString("TIT_TASKS"));
			_tasksViewerFrame.getContentPane().add(
					new TaskViewer(((TaskManager) _taskManager).getDBManager()));
			_tasksViewerFrame.pack();
		}

		_tasksViewerFrame.setVisible(true);
	}

	private String getTaskName()
	{
		JTextField txtTaskName = new JTextField();
		JOptionPane.showMessageDialog(
				_positionComponent,
				txtTaskName,
				Messages.getString("TXT_ENTER_TASK_NAME"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$

		return txtTaskName.getText();
	}

	private JPopupMenu getTaskPopup()
	{
		if (_taskPopup == null) {
			_taskPopup = new JPopupMenu();
			JMenuItem itmSettings = new JMenuItem(
					Messages.getString("MNU_SETTINGS")); //$NON-NLS-1$
			itmSettings.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showSettingsPanel();
				}
			});
			JMenuItem itmResult = new JMenuItem(
					Messages.getString("MNU_RESULT")); //$NON-NLS-1$
			itmResult.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					showResultPanel();
				}
			});
			_taskPopup.add(itmSettings);
			_taskPopup.add(itmResult);
		}

		return _taskPopup;
	}

	private void showResultPanel()
	{
		TaskGUI currentTask = (TaskGUI) _taskListModel.get(_selectedItem);

		IPlugin plugin = null;
		IResult result = null;
		Component comp = null;
		try {
			plugin = currentTask.getTask().getPlugin();
			IResultComponent resultComponent = plugin.getResultComponent();
			result = currentTask.getTask().getResult();
			comp = resultComponent.getComponent(result);
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_RESULT");
			return;
		}

		Dimension maxDim = new Dimension(400, 300);
		Dimension prefDim = comp.getPreferredSize();
		// setting maximum size
		if (prefDim.height > maxDim.height) {
			prefDim.height = maxDim.height;
		}
		if (prefDim.width > maxDim.width) {
			prefDim.width = maxDim.width;
		}
		comp.setSize(prefDim);
		JOptionPane.showMessageDialog(
				_positionComponent,
				comp,
				Messages.getString("TIT_PLUGIN_RESULT"), JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$
	}

	private void showSettingsPanel()
	{
		TaskGUI currentTask = (TaskGUI) _taskListModel.get(_selectedItem);
		IPlugin plugin = null;
		ISettingComponent settingComponent = null;
		ISettings inputSettings = null;
		try {
			plugin = currentTask.getTask().getPlugin();
			settingComponent = plugin.getSettingComponent();
			inputSettings = currentTask.getTask().getSettings();
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
			Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_SETTINGS");
			return;
		}
		if (inputSettings == null) {
			inputSettings = plugin.getSettingComponent().getDefaultSettings();
		}
		//FIXME needed by DataSet
		IDataEngine dataEngine = null;
		//		try 
		//			dataEngine = Solution.getInstance().getDataEngine();
		//		} catch (PlatformException e) {
		//			LOGGER.fatal("", e);
		//		} 
		Component taskSettings = settingComponent.getComponent(inputSettings,
				dataEngine);
		int result = JOptionPane.showConfirmDialog(_positionComponent,
				taskSettings, Messages.getString("TIT_PLUGIN_SETTINGS"), //$NON-NLS-1$
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			ISettings settings = settingComponent.getSettings();
			LOGGER.info("settings: " + settings); //$NON-NLS-1$
			try {
				currentTask.getTask().setSettings(settings);
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage("ERR_CANNOT_SET_TASK_SETTINGS");
			}
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
					getTaskPopup().show(e.getComponent(), e.getX(), e.getY());
				}
			}
		}
	}

	static final Logger LOGGER = Logger.getLogger(TaskManagerGUI.class);
}