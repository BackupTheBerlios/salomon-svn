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
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.task.ITaskManager;

import salomon.engine.platform.IManagerEngine;
import salomon.plugin.ISettings;
import salomon.util.gui.Utils;

import salomon.platform.exception.PlatformException;

/**
 * Class used to manage with projects editing.  
 *  
 */
public final class ProjectEditionManager
{
	private IManagerEngine _managerEngine;

	private ControllerFrame _parent;

	private JPanel _pnlProjectProperties;

	private TaskEditionManager _taskEditionManager;

	private JTextField _txtProjectInfo;

	private JTextField _txtProjectName;

	/**
	 * @param parent
	 */
	public ProjectEditionManager(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
	}

	public void newProject()
	{
		//FIXME
		throw new UnsupportedOperationException(
				"Method newProject() not implemented yet!");
		//		IProjectManager projectManager = _managerEngine.getProjectManager();
		//		projectManager.ceateProject();
		//IProject project = projectManager.getCurrentProject();
		//		setProjectProperties(project);
		//		_parent.refreshGui();
	}

	public void openProject()
	{
		try {
			int projectId = chooseProject();
			if (projectId > 0) {
				_managerEngine.getProjectManager().getProject(projectId);
				_parent.refreshGui();
			}
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load project.");
		}
	}

	public void saveProject()
	{
		//FIXME
		//		IProject project = _managerEngine.getProjectManager().getCurrentProject();
		//		if (project.getName() == null) {
		//			setProjectProperties(project);
		//		}
		//		saveTaskList(_taskEditionManager.getTasks());
	}

	/**
	 * @param parent The parent to set.
	 */
	public void setParent(ControllerFrame parent)
	{
		_parent = parent;
	}

	/**
	 * Shows dialog which enables to initialize project settings.
	 * 
	 * @param project
	 */
	public void setProjectProperties(IProject project)
	{
		if (_pnlProjectProperties == null) {
			_pnlProjectProperties = new JPanel();
			_pnlProjectProperties.setLayout(new GridLayout(0, 2));
			_txtProjectName = new JTextField();
			_txtProjectInfo = new JTextField();
			_pnlProjectProperties.add(new JLabel("Project name"));
			_pnlProjectProperties.add(_txtProjectName);
			_pnlProjectProperties.add(new JLabel("Project info"));
			_pnlProjectProperties.add(_txtProjectInfo);
		}
		try {
			String name = project.getName();
			String info = project.getInfo();
			_txtProjectName.setText(name == null ? "" : name);
			_txtProjectInfo.setText(info == null ? "" : info);
			//TODO:
			JOptionPane.showMessageDialog(_parent, _pnlProjectProperties,
					"Enter project properties", JOptionPane.INFORMATION_MESSAGE);
			project.setName(_txtProjectName.getText());
			project.setInfo(_txtProjectInfo.getText());
		} catch (PlatformException e) {
			LOGGER.error("", e);
			//FIXME   
		}
	}

	public void setTaskEditionManager(TaskEditionManager taskEditionManager)
	{
		_taskEditionManager = taskEditionManager;
	}

	private int chooseProject()
	{
		//FIXME
		throw new UnsupportedOperationException(
				"Method chooseProject() not implemented yet!");
		//		int projectID = 0;
		//		
		//		try {
		//			Collection projects = _managerEngine.getProjectManager().getProjects();
		//			JTable projectTable = null;
		//			projectTable = Utils.createResultTable(projects);
		//			projectID = showProjectList(projectTable);
		//		} catch (Exception e) {
		//			LOGGER.fatal("", e);
		//			Utils.showErrorMessage("Cannot load project list.");
		//		}
		//
		//		return projectID;
	}

	private void saveTaskList(List taskList)
	{
		LOGGER.info("taskList = " + taskList);
		//
		// task list cannot be empty
		//
		if (taskList.isEmpty()) {
			Utils.showErrorMessage("There is no tasks to save");
			return;
		}
		//
		// checking if all tasks have settings set
		// if not - question to user, if he agrees
		// default settings will be set and project will be saved
		//
		List incorrectTasks = new LinkedList();
		for (Iterator iter = taskList.iterator(); iter.hasNext();) {
			TaskGUI task = (TaskGUI) iter.next();
			if (task.getSettings() == null) {
				incorrectTasks.add(task);
			}
		}
		if (!incorrectTasks.isEmpty()) {
			String message = "There are tasks with settings not set:\n";
			for (Iterator iter = incorrectTasks.iterator(); iter.hasNext();) {
				TaskGUI task = (TaskGUI) iter.next();
				message += task.getName() + "\n";
			}
			message += "Do you want to use default settings?";
			if (Utils.showWarningMessage(message)) {
				// getting default settings
				LOGGER.debug("getting default settings");
				for (Iterator iter = incorrectTasks.iterator(); iter.hasNext();) {
					TaskGUI task = (TaskGUI) iter.next();
					ISettings defaultSettings = task.getPlugin().getSettingComponent().getDefaultSettings();
					task.setSettings(defaultSettings);
				}
			} else {
				return;
			}
		}
		//
		// saving project
		//
		try {
			//
			// removing old tasks
			//
			ITaskManager taskManager = _managerEngine.getTasksManager();

			taskManager.clearTaskList();
			for (Iterator iter = taskList.iterator(); iter.hasNext();) {
				TaskGUI taskGUI = (TaskGUI) iter.next();
				taskGUI.save(taskManager.createTask());
			}

			// saving project
			_managerEngine.getProjectManager().saveProject();
			DBManager.getInstance().commit();
			LOGGER.info("Transaction commited");
			Utils.showInfoMessage("Project saved successfully");
		} catch (Exception e1) {
			LOGGER.fatal("", e1);
			Utils.showErrorMessage("Could not save project.");
			try {
				DBManager.getInstance().rollback();
			} catch (Exception sqlEx) {
				LOGGER.fatal("", sqlEx);
				Utils.showErrorMessage("Could not rollback transaction.");
			}
		}
	}

	private int showProjectList(JTable table)
	{
		int projectID = 0;
		JScrollPane panel = new JScrollPane();
		panel.setViewportView(table);
		Dimension dim = new Dimension(250, 200);
		panel.setMaximumSize(dim);
		panel.setPreferredSize(dim);

		int result = JOptionPane.showConfirmDialog(_parent, panel,
				"Choose project", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow >= 0) {
				projectID = ((Integer) table.getValueAt(selectedRow, 0)).intValue();
			}
		}
		return projectID;
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectEditionManager.class);
}