
package salomon.controller.gui.action;

import salomon.controller.gui.ProjectEditionManager;
import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class ActionManager
{
	private NewProjectAction _newProjectAction;

	private RunTaskAction _runTaskAction;

	private OpenProjectAction _loadProjectAction;

	private SaveProjectAction _saveProjectAction;

	private TaskEditionManager _taskEditionManager;

	private ProjectEditionManager _projectEditionManager;

	/**
	 * @param taskEditionManager
	 * @param projectEditionManager
	 */
	public ActionManager(ProjectEditionManager projectEditionManager,
			TaskEditionManager taskEditionManager)
	{
		_taskEditionManager = taskEditionManager;
		_projectEditionManager = projectEditionManager;
	}

	/**
	 * @return Returns the loadProjectAction.
	 */
	public OpenProjectAction getOpenProjectAction()
	{
		if (_loadProjectAction == null) {
			_loadProjectAction = new OpenProjectAction(_projectEditionManager);
		}
		return _loadProjectAction;
	}

	/**
	 * @return Returns the newProjectAction.
	 */
	public NewProjectAction getNewProjectAction()
	{
		if (_newProjectAction == null) {
			_newProjectAction = new NewProjectAction(_projectEditionManager);
		}
		return _newProjectAction;
	}

	/**
	 * @return Returns the runTaskAction.
	 */
	public RunTaskAction getRunTaskAction()
	{
		if (_runTaskAction == null) {
			_runTaskAction = new RunTaskAction(_taskEditionManager);
		}
		return _runTaskAction;
	}

	/**
	 * @return Returns the saveProjectAction.
	 */
	public SaveProjectAction getSaveProjectAction()
	{
		if (_saveProjectAction == null) {
			_saveProjectAction = new SaveProjectAction(_projectEditionManager);
		}
		return _saveProjectAction;
	}
}