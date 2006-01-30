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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;

import salomon.engine.Messages;
import salomon.engine.controller.gui.ControllerFrame;
import salomon.engine.controller.gui.StatusBar;
import salomon.engine.controller.gui.TaskGUI;
import salomon.engine.controller.gui.action.ActionManager;
import salomon.engine.plugin.IPluginManager;
import salomon.engine.plugin.LocalPlugin;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.engine.task.Task;

import salomon.util.gui.Utils;

import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class GraphTaskManagerGUI
{

	private ActionManager _actionManager;

	private Graph _graph;

	private ControllerFrame _parent;

	private IPluginManager _pluginManager;

	private JPanel _pnlTaskProperties;

	private JComponent _positionComponent;

	private StatusBar _statusBar;

	private ITaskManager _taskManager;

	private JTextField _txtTaskCrDate;

	private JTextField _txtTaskInfo;

	private JTextField _txtTaskLastMod;

	private JTextField _txtTaskName;

	private JTextField _txtTaskStatus;

	public GraphTaskManagerGUI(ITaskManager tasksManager,
			IPluginManager pluginManager)
	{
		_taskManager = tasksManager;
		_pluginManager = pluginManager;
	}

	public ITask createTask()
	{
		ITask newTask = null;
		try {
			LocalPlugin localPlugin = (LocalPlugin) _pluginManager.getPlugins()[4];
			if (localPlugin == null) {
				LOGGER.debug("LocalPLugin == null");
				Utils.showErrorMessage(Messages.getString("ERR_PLUGIN_NOT_SELECTED"));
				return null;
			}

			// loading plugin
			localPlugin.load();

			// creating task
			newTask = _taskManager.createTask();

			// setting some params from GUI
			TaskGUI taskGUI = new TaskGUI(newTask);
			taskGUI.getTask().getInfo().setName(getTaskName());
			taskGUI.getTask().setPlugin(localPlugin);

			// adding task to managers
			_taskManager.addTask(newTask);
			//			_taskListModel.addElement(taskGUI);

		} catch (Exception e) {
			LOGGER.fatal("", e); //$NON-NLS-1$				
			Utils.showErrorMessage(Messages.getString("ERR_CANNOT_LOAD_PLUGIN"));
		}

		return newTask;
	}

	public void editTask(ITask task)
	{
		try {
			setTaskProperties(task);
		} catch (PlatformException e) {
			LOGGER.fatal("Exception was thrown!", e);
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

	public JPanel getGraphPanel()
	{

		TaskGraphEditor taskGraphEditor = new TaskGraphEditor(this);
		_graph = taskGraphEditor.getGraph();
		return taskGraphEditor;
	}

	public void refresh()
	{
		LOGGER.debug("reloading tasks");
		_graph.removeAllEdges();
		_graph.removeAllVertices();
		ITask[] tasks = null;
		try {
			// TODO: change it
			_taskManager.clearTasks();
			tasks = _taskManager.getTasks();
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
			Utils.showErrorMessage("Cannot load task list");
			return;
		}

		Vertex previousVertex = null;
		for (ITask task : tasks) {
			LOGGER.debug("adding task");
			TaskVertex vertex = new TaskVertex(task);
			_graph.addVertex(vertex);
			if (previousVertex != null) {
				DirectedSparseEdge edge = new DirectedSparseEdge(
						previousVertex, vertex);
				_graph.addEdge(edge);
			}
			previousVertex = vertex;
		}
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
		List<ITask> executionPlan = GraphPlanner.makePlan(_graph);
		if (executionPlan == null) {
			JOptionPane.showMessageDialog(_positionComponent,
					"Cannot create plan. There is a loop!",
					"Cannot create plan!", JOptionPane.PLAIN_MESSAGE); //$NON-NLS-1$			
		} else {
			try {
				for (ITask task : executionPlan) {
					_taskManager.addTask(task);
				}
				_taskManager.start();
			} catch (PlatformException e) {
				LOGGER.fatal("Exception was thrown!", e);
			}
		}
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

	public void showResultPanel(ITask task)
	{

		IPlugin plugin = null;
		IResult result = null;
		Component comp = null;
		try {
			plugin = task.getPlugin();
			IResultComponent resultComponent = plugin.getResultComponent();
			result = task.getResult();
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

	public void showSettingsPanel(ITask task)
	{
		IPlugin plugin = null;
		ISettingComponent settingComponent = null;
		ISettings inputSettings = null;
		try {
			plugin = task.getPlugin();
			settingComponent = plugin.getSettingComponent();
			inputSettings = task.getSettings();
		} catch (PlatformException e1) {
			LOGGER.fatal("", e1);
			Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_SETTINGS");
			return;
		}
		if (inputSettings == null) {
			inputSettings = plugin.getSettingComponent().getDefaultSettings();
		}
		// FIXME needed by DataSet
		IDataEngine dataEngine = null;
		try {
			dataEngine = _taskManager.getProject().getProjectManager().getSolution().getDataEngine();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_SHOW_TASK_SETTINGS");
			return;
		}
		Component taskSettings = settingComponent.getComponent(inputSettings,
				dataEngine);
		int result = JOptionPane.showConfirmDialog(_positionComponent,
				taskSettings, Messages.getString("TIT_PLUGIN_SETTINGS"), //$NON-NLS-1$
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			ISettings settings = settingComponent.getSettings();
			LOGGER.info("settings: " + settings); //$NON-NLS-1$
			try {
				task.setSettings(settings);
				task.getInfo().save();
			} catch (Exception e) {
				LOGGER.fatal("", e);
				Utils.showErrorMessage("ERR_CANNOT_SET_TASK_SETTINGS");
			}
		}
	}

	public void viewTasks()
	{
		throw new UnsupportedOperationException(
				"Method viewTasks() not implemented yet!");
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

	private void setTaskProperties(ITask iTask) throws PlatformException
	{
		Task task = (Task) iTask;

		if (_pnlTaskProperties == null) {
			_pnlTaskProperties = new JPanel();
			_pnlTaskProperties.setLayout(new GridLayout(5, 2));

			_txtTaskName = new JTextField();
			_txtTaskInfo = new JTextField();

			_txtTaskCrDate = new JTextField();
			_txtTaskCrDate.setEnabled(false);
			_txtTaskLastMod = new JTextField();
			_txtTaskLastMod.setEnabled(false);
			_txtTaskStatus = new JTextField();
			_txtTaskStatus.setEnabled(false);

			_pnlTaskProperties.add(new JLabel("Task name"));
			_pnlTaskProperties.add(_txtTaskName);
			_pnlTaskProperties.add(new JLabel("Task info"));
			_pnlTaskProperties.add(_txtTaskInfo);

			_pnlTaskProperties.add(new JLabel("Creation Date"));
			_pnlTaskProperties.add(_txtTaskCrDate);
			_pnlTaskProperties.add(new JLabel("Last Modification Date"));
			_pnlTaskProperties.add(_txtTaskLastMod);
			_pnlTaskProperties.add(new JLabel("Task Status"));
			_pnlTaskProperties.add(_txtTaskStatus);
		}

		String name = task.getInfo().getName();
		String info = task.getInfo().getInfo();
		Date dcdate = new Date(); // TODO: NYI
		// task.getInfo().getCreationDate() ;
		Date dmdate = new Date(); // TODO: NYI
		// task.getInfo().getLastModificationDate(
		String cdate = "NYI! " + DateFormat.getDateInstance().format(dcdate)
				+ " " + DateFormat.getTimeInstance().format(dcdate);
		String mdate = "NYI! " + DateFormat.getDateInstance().format(dmdate)
				+ " " + DateFormat.getTimeInstance().format(dmdate);
		String status = task.getInfo().getStatus();

		_txtTaskName.setText(name == null ? "" : name);
		_txtTaskInfo.setText(info == null ? "" : info);
		_txtTaskCrDate.setText(cdate == null ? "" : cdate);
		_txtTaskLastMod.setText(mdate == null ? "" : mdate);
		_txtTaskStatus.setText(status == null ? "" : status);

		int result = JOptionPane.showConfirmDialog(_parent, _pnlTaskProperties,
				"Enter Task properties", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.OK_OPTION) {
			task.getInfo().setName(_txtTaskName.getText());
			task.getInfo().setInfo(_txtTaskInfo.getText());

			_taskManager.saveTasks();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(GraphTaskManagerGUI.class);

}
