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

package salomon.engine.controller.gui.action;

import salomon.engine.controller.gui.ProjectEditionManager;
import salomon.engine.controller.gui.TaskEditionManager;

/**
 * Class manages with actions. It creates and holds actions used to create most
 * of buttons in program.
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
	 * Sets object used to create actions.
	 * 
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
	 * Returns an instance of AddPluginAction.
	 * 
	 * @return an instance of AddPluginAction.
	 */
	public AddPluginAction getAddPluginAction()
	{
		if (_addPluginAction == null) {
			_addPluginAction = new AddPluginAction(_taskEditionManager);
		}
		return _addPluginAction;
	}

	/**
	 * Returns an instance of NewProjectAction.
	 * 
	 * @return an instance of NewProjectAction.
	 */
	public NewProjectAction getNewProjectAction()
	{
		if (_newProjectAction == null) {
			_newProjectAction = new NewProjectAction(_projectEditionManager);
		}
		return _newProjectAction;
	}

	/**
	 * Returns an instance of OpenProjectAction.
	 * 
	 * @return an instance of OpenProjectAction.
	 */
	public OpenProjectAction getOpenProjectAction()
	{
		if (_openProjectAction == null) {
			_openProjectAction = new OpenProjectAction(_projectEditionManager);
		}
		return _openProjectAction;
	}

	/**
	 * Returns an instance of RemovePluginAction.
	 * 
	 * @return an instance of RemovePluginAction.
	 */
	public RemovePluginAction getRemovePluginAction()
	{
		if (_removePluginAction == null) {
			_removePluginAction = new RemovePluginAction(_taskEditionManager);
		}
		return _removePluginAction;
	}

	/**
	 * Returns an instance of RunTaskAction.
	 * 
	 * @return an instance of RunTaskAction.
	 */
	public RunTaskAction getRunTaskAction()
	{
		if (_runTaskAction == null) {
			_runTaskAction = new RunTaskAction(_taskEditionManager);
		}
		return _runTaskAction;
	}

	/**
	 * Returns an instance of SavePluginAction.
	 * 
	 * @return an instance of SavePluginAction.
	 */
	public SavePluginAction getSavePluginAction()
	{
		if (_savePluginAction == null) {
			_savePluginAction = new SavePluginAction(_taskEditionManager);
		}
		return _savePluginAction;
	}

	/**
	 * Returns an instance of SaveProjectAction.
	 * 
	 * @return an instance of SaveProjectAction.
	 */
	public SaveProjectAction getSaveProjectAction()
	{
		if (_saveProjectAction == null) {
			_saveProjectAction = new SaveProjectAction(_projectEditionManager);
		}
		return _saveProjectAction;
	}
}