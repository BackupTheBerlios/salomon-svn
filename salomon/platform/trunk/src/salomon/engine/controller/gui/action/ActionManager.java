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

import salomon.engine.controller.gui.PluginMangerGUI;
import salomon.engine.controller.gui.ProjectManagerGUI;
import salomon.engine.controller.gui.SolutionManagerGUI;
import salomon.engine.controller.gui.TaskManagerGUI;

/**
 * Class manages with actions. It creates and holds actions used to create most
 * of buttons in program.
 */
public final class ActionManager
{

	private AddPluginAction _addPluginAction;

	private AddTaskAction _addTaskAction;

	private EditSolutionAction _editSolutionAction;
	
	private ExitAction _exitAction;

	private NewProjectAction _newProjectAction;

	private NewSolutionAction _newSolutionAction;

	private OpenProjectAction _openProjectAction;

	private OpenSolutionAction _openSolutionAction;
	
	private PluginMangerGUI _pluginMangerGUI;

	private ProjectManagerGUI _projectManagerGUI;

	private RemovePluginAction _removePluginAction;

	private RemoveTaskAction _removeTaskAction;

	private RunTaskAction _runTaskAction;

	private SavePluginAction _savePluginAction;

	private SaveProjectAction _saveProjectAction;
	
	private SaveSolutionAction _saveSolutionAction;
	
	private SolutionManagerGUI _solutionManagerGUI;

	private TaskManagerGUI _taskManagerGUI;
	

	/**
	 * Sets object used to create actions.
	 * 
	 * @param taskManagerGUI
	 * @param projectManagerGUI
	 */
	public ActionManager(ProjectManagerGUI projectManagerGUI,
			TaskManagerGUI taskManagerGUI, PluginMangerGUI pluginMangerGUI)
	{
		_projectManagerGUI = projectManagerGUI;
		_taskManagerGUI = taskManagerGUI;
		_pluginMangerGUI = pluginMangerGUI;
	}

	
	/**
	 * Sets object used to create actions.
	 * @param solutionManagerGUI
	 * @param projectManagerGUI
	 * @param taskManagerGUI
	 * @param pluginMangerGUI
	 */
	public ActionManager(SolutionManagerGUI solutionManagerGUI, ProjectManagerGUI projectManagerGUI,
			TaskManagerGUI taskManagerGUI, PluginMangerGUI pluginMangerGUI)
	{
		_solutionManagerGUI = solutionManagerGUI;
		_projectManagerGUI = projectManagerGUI;
		_taskManagerGUI = taskManagerGUI;
		_pluginMangerGUI = pluginMangerGUI;
	}

	/**
	 * Returns an instance of AddPluginAction.
	 * 
	 * @return an instance of AddPluginAction.
	 */
	public AddPluginAction getAddPluginAction()
	{
		if (_addPluginAction == null) {
			_addPluginAction = new AddPluginAction(_pluginMangerGUI);
		}
		return _addPluginAction;
	}

	public AddTaskAction getAddTaskAction()
	{
		if (_addTaskAction == null) {
			_addTaskAction = new AddTaskAction(_taskManagerGUI);
		}
		return _addTaskAction;
	}

	/**
	 * Returns an instance of EditSolutionAction.
	 * 
	 * @return an instance of EditSolutionAction.
	 */
	public EditSolutionAction getEditSolutionAction()
	{
		if (_editSolutionAction == null) {
			_editSolutionAction = new EditSolutionAction(_solutionManagerGUI);
		}
		return _editSolutionAction;
	}
	
	/**
	 * Returns an instance of NewProjectAction.
	 * 
	 * @return an instance of NewProjectAction.
	 */
	public NewProjectAction getNewProjectAction()
	{
		if (_newProjectAction == null) {
			_newProjectAction = new NewProjectAction(_projectManagerGUI);
		}
		return _newProjectAction;
	}
	
	/**
	 * Returns an instance of NewSolutionAction.
	 * 
	 * @return an instance of NewSolutionAction.
	 */
	public NewSolutionAction getNewSolutionAction()
	{
		if (_newSolutionAction == null) {
			_newSolutionAction = new NewSolutionAction(_solutionManagerGUI);
		}
		return _newSolutionAction;
	}

	/**
	 * Returns an instance of OpenProjectAction.
	 * 
	 * @return an instance of OpenProjectAction.
	 */
	public OpenProjectAction getOpenProjectAction()
	{
		if (_openProjectAction == null) {
			_openProjectAction = new OpenProjectAction(_projectManagerGUI);
		}
		return _openProjectAction;
	}
	
	/**
	 * Returns an instance of OpenSolutionAction.
	 * 
	 * @return an instance of OpenSolutionAction.
	 */
	public OpenSolutionAction getOpenSolutionAction()
	{
		if (_openSolutionAction == null) {
			_openSolutionAction = new OpenSolutionAction(_solutionManagerGUI);
		}
		return _openSolutionAction;
	}

	/**
	 * Returns an instance of RemovePluginAction.
	 * 
	 * @return an instance of RemovePluginAction.
	 */
	public RemovePluginAction getRemovePluginAction()
	{
		if (_removePluginAction == null) {
			_removePluginAction = new RemovePluginAction(_pluginMangerGUI);
		}
		return _removePluginAction;
	}

	public RemoveTaskAction getRemoveTaskAction()
	{
		if (_removeTaskAction == null) {
			_removeTaskAction = new RemoveTaskAction(_taskManagerGUI);
		}
		return _removeTaskAction;
	}

	/**
	 * Returns an instance of RunTaskAction.
	 * 
	 * @return an instance of RunTaskAction.
	 */
	public RunTaskAction getRunTaskAction()
	{
		if (_runTaskAction == null) {
			_runTaskAction = new RunTaskAction(_taskManagerGUI);
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
			_savePluginAction = new SavePluginAction(_pluginMangerGUI);
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
			_saveProjectAction = new SaveProjectAction(_projectManagerGUI);
		}
		return _saveProjectAction;
	}
	
	/**
	 * Returns an instance of SaveSolutionAction.
	 * 
	 * @return an instance of SaveSolutionAction.
	 */
	public SaveSolutionAction getSaveSolutionAction()
	{
		if (_saveSolutionAction == null) {
			_saveSolutionAction = new SaveSolutionAction(_solutionManagerGUI);
		}
		return _saveSolutionAction;
	}
	
	/**
	 * Returns an instance of ExitAction.
	 * 
	 * @return an instance of ExitAction.
	 */
	public ExitAction getExitAction()
	{
		if (_exitAction == null) {
			_exitAction = new ExitAction();
		}
		return _exitAction;
	}
	
}