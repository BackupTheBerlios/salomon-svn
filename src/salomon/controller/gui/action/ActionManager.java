
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

	private AddPluginAction _addPluginAction;

	private NewProjectAction _newProjectAction;

	private OpenProjectAction _openProjectAction;

	private ProjectEditionManager _projectEditionManager;

	private RemovePluginAction _removePluginAction;

	private RunTaskAction _runTaskAction;

	private SavePluginAction _savePluginAction;

	private SaveProjectAction _saveProjectAction;

	private TaskEditionManager _taskEditionManager;

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

	public AddPluginAction getAddPluginAction()
	{
		if (_addPluginAction == null) {
			_addPluginAction = new AddPluginAction(_taskEditionManager);
		}
		return _addPluginAction;
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
	 * @return Returns the openProjectAction.
	 */
	public OpenProjectAction getOpenProjectAction()
	{
		if (_openProjectAction == null) {
			_openProjectAction = new OpenProjectAction(_projectEditionManager);
		}
		return _openProjectAction;
	}

	public RemovePluginAction getRemovePluginAction()
	{
		if (_removePluginAction == null) {
			_removePluginAction = new RemovePluginAction(_taskEditionManager);
		}
		return _removePluginAction;
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

	public SavePluginAction getSavePluginAction()
	{
		if (_savePluginAction == null) {
			_savePluginAction = new SavePluginAction(_taskEditionManager);
		}
		return _savePluginAction;
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