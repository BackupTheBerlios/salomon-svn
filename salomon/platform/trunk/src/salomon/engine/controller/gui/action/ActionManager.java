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

import salomon.engine.controller.gui.PluginManagerGUI;
import salomon.engine.controller.gui.ProjectManagerGUI;
import salomon.engine.controller.gui.SolutionManagerGUI;
import salomon.engine.controller.gui.graph.GraphTaskManagerGUI;
import salomon.engine.task.ITask;

/**
 * Class manages with actions. It creates and holds actions used to create most
 * of buttons in program.
 */
public final class ActionManager
{

	/**
	 * 
	 * @uml.property name="_addPluginAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private AddPluginAction _addPluginAction;

	/**
	 * 
	 * @uml.property name="_addTaskAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private AddTaskAction _addTaskAction;

	private ChoosePluginFileAction _choosePluginFileAction;
	
	private SwitchPluginLocationTypeAction _switchPluginLocationTypeAction;
	
	/**
	 * @uml.property name="_editProjectAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private EditProjectAction _editProjectAction;

	/**
	 * 
	 * @uml.property name="_editSolutionAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private EditSolutionAction _editSolutionAction;

	/**
	 * 
	 * @uml.property name="_editTaskAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private EditTaskAction _editTaskAction;

	/**
	 * 
	 * @uml.property name="_exitAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ExitAction _exitAction;

	/**
	 * 
	 * @uml.property name="_newProjectAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private NewProjectAction _newProjectAction;

	/**
	 * 
	 * @uml.property name="_newSolutionAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private NewSolutionAction _newSolutionAction;

	/**
	 * 
	 * @uml.property name="_openProjectAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private OpenProjectAction _openProjectAction;

	/**
	 * 
	 * @uml.property name="_openSolutionAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private OpenSolutionAction _openSolutionAction;

	/**
	 * 
	 * @uml.property name="_pluginManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private PluginManagerGUI _pluginManagerGUI;

	/**
	 * 
	 * @uml.property name="_projectManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ProjectManagerGUI _projectManagerGUI;

	private RemoveAllTasksAction _removeAllTasksAction;

	/**
	 * 
	 * @uml.property name="_removePluginAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RemovePluginAction _removePluginAction;

	/**
	 * 
	 * @uml.property name="_removeTaskAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RemoveTaskAction _removeTaskAction;

	/**
	 * 
	 * @uml.property name="_runTaskAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RunTaskAction _runTaskAction;

	/**
	 * 
	 * @uml.property name="_savePluginAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SavePluginAction _savePluginAction;

	/**
	 * 
	 * @uml.property name="_saveProjectAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SaveProjectAction _saveProjectAction;

	/**
	 * 
	 * @uml.property name="_saveSolutionAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SaveSolutionAction _saveSolutionAction;

	/**
	 * 
	 * @uml.property name="_solutionManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private SolutionManagerGUI _solutionManagerGUI;

	/**
	 * 
	 * @uml.property name="_taskManagerGUI"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private GraphTaskManagerGUI _taskManagerGUI;

	/**
	 * 
	 * @uml.property name="_viewPluginsAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ViewPluginsAction _viewPluginsAction;

	/**
	 * 
	 * @uml.property name="_viewProjectsAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ViewProjectsAction _viewProjectsAction;

	/**
	 * 
	 * @uml.property name="_viewSolutionAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ViewSolutionAction _viewSolutionAction;

	/**
	 * 
	 * @uml.property name="_viewTaskAction"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ViewTaskAction _viewTaskAction;

	/**
	 * Sets object used to create actions.
	 * 
	 * @param taskManagerGUI
	 * @param projectManagerGUI
	 */
	public ActionManager(ProjectManagerGUI projectManagerGUI,
			GraphTaskManagerGUI taskManagerGUI, PluginManagerGUI pluginMangerGUI)
	{
		_projectManagerGUI = projectManagerGUI;
		_taskManagerGUI = taskManagerGUI;
		_pluginManagerGUI = pluginMangerGUI;
	}

	/**
	 * Sets object used to create actions.
	 * @param solutionManagerGUI
	 * @param projectManagerGUI
	 * @param taskManagerGUI
	 * @param pluginMangerGUI
	 */
	public ActionManager(SolutionManagerGUI solutionManagerGUI,
			ProjectManagerGUI projectManagerGUI, GraphTaskManagerGUI taskManagerGUI,
			PluginManagerGUI pluginMangerGUI)
	{
		_solutionManagerGUI = solutionManagerGUI;
		_projectManagerGUI = projectManagerGUI;
		_taskManagerGUI = taskManagerGUI;
		_pluginManagerGUI = pluginMangerGUI;
	}

	/**
	 * Returns an instance of AddPluginAction.
	 * 
	 * @return an instance of AddPluginAction.
	 */
	public AddPluginAction getAddPluginAction()
	{
		if (_addPluginAction == null) {
			_addPluginAction = new AddPluginAction(_pluginManagerGUI);
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

	public ChoosePluginFileAction getChoosePluginFileAction()
	{
		if (_choosePluginFileAction == null) {
			_choosePluginFileAction  = new ChoosePluginFileAction(_pluginManagerGUI);
		}
		return _choosePluginFileAction ;
	}
	
	

	/**
	 * Returns the editProjectAction.
	 * @return The editProjectAction
	 */
	public EditProjectAction getEditProjectAction()
	{
		if (_editProjectAction == null) {
			_editProjectAction = new EditProjectAction(_projectManagerGUI);
		}
		return _editProjectAction;
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

	public EditTaskAction getEditTaskAction(ITask task)
	{
		if (_editTaskAction == null) {
			_editTaskAction = new EditTaskAction(_taskManagerGUI, task);
		}
		return _editTaskAction;
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

	public RemoveAllTasksAction getRemoveAllTasksAction()
	{
		if (_removeAllTasksAction == null) {
			_removeAllTasksAction = new RemoveAllTasksAction(_taskManagerGUI);
		}
		return _removeAllTasksAction;
	}

	/**
	 * Returns an instance of RemovePluginAction.
	 * 
	 * @return an instance of RemovePluginAction.
	 */
	public RemovePluginAction getRemovePluginAction()
	{
		if (_removePluginAction == null) {
			_removePluginAction = new RemovePluginAction(_pluginManagerGUI);
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
			_savePluginAction = new SavePluginAction(_pluginManagerGUI);
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
	 * Returns an instance of SwitchPluginLocationTypeAction.
	 * 
	 * @return an instance of SwitchPluginLocationTypeAction.
	 */
	public SwitchPluginLocationTypeAction getSwitchPluginLocationTypeAction()
	{
		if (_switchPluginLocationTypeAction == null) {
			_switchPluginLocationTypeAction  = new SwitchPluginLocationTypeAction(_pluginManagerGUI);
		}
		return _switchPluginLocationTypeAction ;
	}

	public ViewPluginsAction getViewPluginsAction()
	{
		if (_viewPluginsAction == null) {
			_viewPluginsAction = new ViewPluginsAction(_pluginManagerGUI);
		}
		return _viewPluginsAction;
	}

	public ViewProjectsAction getViewProjectsAction()
	{
		if (_viewProjectsAction == null) {
			_viewProjectsAction = new ViewProjectsAction(_projectManagerGUI);
		}
		return _viewProjectsAction;
	}

	public ViewSolutionAction getViewSolutionAction()
	{
		if (_viewSolutionAction == null) {
			_viewSolutionAction = new ViewSolutionAction(_solutionManagerGUI);
		}
		return _viewSolutionAction;
	}

	public ViewTaskAction getViewTasksAction()
	{
		if (_viewTaskAction == null) {
			_viewTaskAction = new ViewTaskAction(_taskManagerGUI);
		}
		return _viewTaskAction;
	}

}