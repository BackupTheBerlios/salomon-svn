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
import java.util.Collection;
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
import salomon.engine.platform.IManagerEngine;
import salomon.engine.project.IProject;
import salomon.engine.project.IProjectManager;
import salomon.engine.project.ProjectManager;
import salomon.engine.task.ITask;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettings;
import salomon.util.gui.Utils;

/**
 * Class used to manage with projects editing.
 * 
 */
public final class ProjectManagerGUI
{
	private IManagerEngine _managerEngine;

	private ControllerFrame _parent;

	private JPanel _pnlProjectProperties;

	private TaskManagerGUI _taskManagerGUI;

	private JTextField _txtProjectInfo;

	private JTextField _txtProjectName;

	/**
	 */
	public ProjectManagerGUI(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
	}	
	
	public void newProject()
	{
		IProjectManager projectManager;
		try {
			projectManager = _managerEngine.getProjectManager();
			IProject project = projectManager.ceateProject();
			setProjectProperties(project);
			projectManager.addProject(project);
			_parent.refreshGui();
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot create project");
		}
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
		try {
			IProject project = _managerEngine.getProjectManager().getCurrentProject();

			// setting project name if neccessary
			// TODO: remove this checking, make user to enter project name while
			// creating it
			if (project.getName() == null) {
				setProjectProperties(project);
			}

			List<TaskGUI> taskList = _taskManagerGUI.getTasks();
			LOGGER.info("taskList = " + taskList);
			//
			// task list cannot be empty
			//
			if (taskList.isEmpty()) {
				Utils.showErrorMessage("WRN_NO_TASK_TO_SAVE");
				return;
			}
			//
			// checking if all tasks have settings set
			// if not - question to user, if he agrees
			// default settings will be set and project will be saved
			//
			List<TaskGUI> incorrectTasks = new LinkedList<TaskGUI>();
			for (TaskGUI task : taskList) {
				if (task.getSettings() == null) {
					incorrectTasks.add(task);
				}
			}
			if (!incorrectTasks.isEmpty()) {
				String message = "There are tasks with settings not set:\n";
				for (TaskGUI task : incorrectTasks) {
					message += task.getName() + "\n";
				}
				message += "Do you want to use default settings?";
				if (Utils.showWarningMessage(message)) {
					// getting default settings
					LOGGER.debug("getting default settings");
					for (TaskGUI task : incorrectTasks) {
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

			//
			// removing old tasks
			//
			ITaskManager taskManager;
			try {
				taskManager = _managerEngine.getTasksManager();
				taskManager.clearTaskList();
				for (TaskGUI taskGUI : taskList) {
					ITask task = taskManager.createTask();
					taskGUI.initialize(task);
					taskManager.addTask(task);
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
		} catch (PlatformException e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CANNOT_SAVE_PROJECT");
		}
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
			// TODO:
			JOptionPane.showMessageDialog(_parent, _pnlProjectProperties,
					"Enter project properties", JOptionPane.INFORMATION_MESSAGE);
			project.setName(_txtProjectName.getText());
			project.setInfo(_txtProjectInfo.getText());
		} catch (PlatformException e) {
			LOGGER.error("", e);
			// FIXME
		}
	}

	public void setTaskManagerGUI(TaskManagerGUI taskEditionManager)
	{
		_taskManagerGUI = taskEditionManager;
	}

	private int chooseProject()
	{
		int projectID = 0;

		try {
            //FIXME ugly but quick
			Collection projects = ((ProjectManager)_managerEngine.getProjectManager()).getProjectList();
			JTable projectTable = null;
			projectTable = Utils.createResultTable(projects);
			projectID = showProjectList(projectTable);
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("Cannot load project list.");
		}

		return projectID;
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

	private static final Logger LOGGER = Logger.getLogger(ProjectManagerGUI.class);
}