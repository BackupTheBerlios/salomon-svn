package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskManagerGUI;



public class EditTaskAction extends AbstractTaskAction {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets an object which method is called in implementation of
	 * actionPerformed() method
	 * 
	 * @param taskManagerGUI an instance of TaskManagerGUI
	 */
	public EditTaskAction(TaskManagerGUI taskManagerGUI)
	{
		super(taskManagerGUI );
	}

	public void actionPerformed(ActionEvent event)
	{
		_taskManagerGUI.editTask() ; 
	}

}
