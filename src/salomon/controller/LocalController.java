
package salomon.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import salomon.controller.gui.ControllerGUI;
import salomon.core.Project;
import salomon.core.ProjectManager;
import salomon.core.data.DBManager;
import salomon.core.event.ProjectEvent;
import salomon.core.event.ProjectListener;
import salomon.core.event.TaskEvent;
import salomon.core.event.TaskListener;
import salomon.core.task.Task;
import salomon.core.task.TaskManager;
import salomon.plugin.ISettings;

/**
 *  
 */
public final class LocalController implements IController
{
	private static Logger _logger = Logger.getLogger(LocalController.class);

	private ControllerGUI _controllerGUI = null;

	private ProjectManager _projectManager = null;

	public void start(ProjectManager projectManager)
	{
		_projectManager = projectManager;
		projectManager.newProject();
		_controllerGUI = new ControllerGUI();
		_controllerGUI.addTaskListener(new LocalTaskListener());
		_controllerGUI.addProjectListener(new LocalProjectListener());
		_controllerGUI.setVisible(true);
	}

	private void loadProject(int projectID)
	{
		try {
			Project project = _projectManager.loadProject(projectID);
			_controllerGUI.refreshGui(project);
		} catch (Exception e) {
			_logger.fatal("",e);
			_controllerGUI.showErrorMessage("Cannot load project.");
		}
	}

	private void newProject()
	{
		Project project = _projectManager.newProject();
		_controllerGUI.setProjectProperties(project);
		_controllerGUI.refreshGui(project);
	}

	private void saveProject(List taskList)
	{
		Project project = _projectManager.getCurrentProject();
		if (project.getName() == null) {
			_controllerGUI.setProjectProperties(project);
		}		
		saveTaskList(taskList);
	}

	private void saveTaskList(List taskList)
	{
		_logger.info("taskList" + taskList);
		//
		// task list cannot be empty
		//
		if (taskList.isEmpty()) {
			_controllerGUI.showErrorMessage("There is no tasks to save");
			return;
		}
		//
		// checking if all tasks have settings set
		// if not - question to user, if he agrees
		// default settings will be set and project will be saved
		//
		List incorrectTasks = new LinkedList();
		for (Iterator iter = taskList.iterator(); iter.hasNext();) {
			Task task = (Task) iter.next();
			if (task.getSettings() == null) {
				incorrectTasks.add(task);
			}
		}
		if (!incorrectTasks.isEmpty()) {
			String message = "There are tasks with settings not set:\n";
			for (Iterator iter = incorrectTasks.iterator(); iter.hasNext();) {
				Task task = (Task) iter.next();
				message += task.getName() + "\n";
			}
			message += "Do you want to use default settings?";
			if (_controllerGUI.showWarningMessage(message)) {
				// getting default settings
				_logger.debug("getting default settings");
				for (Iterator iter = incorrectTasks.iterator(); iter.hasNext();) {
					Task task = (Task) iter.next();
					ISettings defaultSettings = task.getPlugin()
							.getSettingComponent().getDefaultSettings();
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
			TaskManager taskManager = _projectManager.getCurrentProject()
					.getManagerEngine().getTasksManager();
			taskManager.clearTaskList();
			for (Iterator iter = taskList.iterator(); iter.hasNext();) {
				taskManager.addTask((Task) iter.next());
			}
			// saving project
			_projectManager.saveProject(_projectManager.getCurrentProject());
			DBManager.getInstance().commit();
			_logger.info("Transaction commited");
			_controllerGUI.showInfoMessage("Project saved successfully");
		} catch (Exception e1) {
			_logger.fatal("", e1);
			_controllerGUI.showErrorMessage("Could not save project.");
			try {
				DBManager.getInstance().rollback();
			} catch (Exception sqlEx) {
				_logger.fatal("", sqlEx);
				_controllerGUI
						.showErrorMessage("Could not rollback transaction.");
			}
		}
	}

	private class LocalProjectListener implements ProjectListener
	{
		public void loadProject(ProjectEvent e)
		{
			LocalController.this.loadProject(e.getProjectID());
		}

		public void newProject(ProjectEvent e)
		{
			LocalController.this.newProject();
		}

		public void saveProject(ProjectEvent e)
		{
			LocalController.this.saveProject(e.getTaskList());
		}
	}

	private class LocalTaskListener implements TaskListener
	{
		public void applyTasks(TaskEvent e)
		{
			saveTaskList(e.getTaskList());
		}

		public void runTasks(TaskEvent e)
		{
			_projectManager.getCurrentProject().getManagerEngine()
					.getTasksManager().start();
		}
	}
}