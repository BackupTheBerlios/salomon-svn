
package salomon.controller.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
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

import salomon.core.IManagerEngine;
import salomon.core.SQLConsole;
import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBTableName;
import salomon.core.project.IProject;
import salomon.core.project.IProjectManager;
import salomon.core.task.ITaskManager;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class ProjectEditionManager
{
	private IManagerEngine _managerEngine;

	private ControllerFrame _parent;

	private JPanel _pnlProjectProperties;

	private IProjectManager _projectManager;

	private TaskEditionManager _taskEditionManager;

	private JTextField _txtProjectInfo;

	private JTextField _txtProjectName;


	/**
	 * @param parent
	 */
	public ProjectEditionManager(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		_projectManager = _managerEngine.getProjectManager();
	}

	public void newProject()
	{
		_projectManager.newProject();
		IProject project = _projectManager.getCurrentProject();
		setProjectProperties(project);
		_parent.refreshGui();
	}

	public void openProject()
	{
		try {
			int projectId = chooseProject();
            if (projectId > 0) {
            	_projectManager.loadProject(projectId);
            	_parent.refreshGui();
            }
		} catch (Exception e) {
			_logger.fatal("", e);
			Utils.showErrorMessage("Cannot load project.");
		}
	}

	public void saveProject()
	{
		IProject project = _projectManager.getCurrentProject();
		if (project.getName() == null) {
			setProjectProperties(project);
		}
		saveTaskList(_taskEditionManager.getTasks());
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
		String name = project.getName();
		String info = project.getInfo();
		_txtProjectName.setText(name == null ? "" : name);
		_txtProjectInfo.setText(info == null ? "" : info);
		//TODO:
		JOptionPane.showMessageDialog(_parent, _pnlProjectProperties,
				"Enter project properties", JOptionPane.INFORMATION_MESSAGE);
		project.setName(_txtProjectName.getText());
		project.setInfo(_txtProjectInfo.getText());
	}

	public void setTaskEditionManager(TaskEditionManager taskEditionManager)
	{
		_taskEditionManager = taskEditionManager;
	}

	private int chooseProject()
	{
		int projectID = 0;
		DBTableName[] tableNames = {new DBTableName("projects")};
		DBColumnName[] columnNames = {
				new DBColumnName(tableNames[0], "project_id", "Id"),
				new DBColumnName(tableNames[0], "name", "Name"),
				new DBColumnName(tableNames[0], "info", "Info")};
		// executing query
		ResultSet resultSet = null;
		try {
			resultSet = DBManager.getInstance().select(columnNames, tableNames,
					null);
			JTable projectTable = null;
			projectTable = SQLConsole.createResultTable(resultSet);
			projectID = showProjectList(projectTable);
		} catch (Exception e) {
			_logger.fatal("", e);
			Utils.showErrorMessage("Cannot load project list.");
		}
		return projectID;
	}

	private void saveTaskList(List taskList)
	{
		_logger.info("taskList" + taskList);
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
				_logger.debug("getting default settings");
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
			_projectManager.saveProject();
			DBManager.getInstance().commit();
			_logger.info("Transaction commited");
			Utils.showInfoMessage("Project saved successfully");
		} catch (Exception e1) {
			_logger.fatal("", e1);
			Utils.showErrorMessage("Could not save project.");
			try {
				DBManager.getInstance().rollback();
			} catch (Exception sqlEx) {
				_logger.fatal("", sqlEx);
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

	private static Logger _logger = Logger.getLogger(ProjectEditionManager.class);
}