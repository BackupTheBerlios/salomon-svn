
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskManagerGUI;

public class ViewTaskAction extends AbstractTaskAction
{

	protected ViewTaskAction(TaskManagerGUI taskManagerGUI)
	{
		super(taskManagerGUI);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		_taskManagerGUI.viewTasks();
	}

}
