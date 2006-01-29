
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.graph.GraphTaskManagerGUI;

public class ViewTaskAction extends AbstractTaskAction
{

	protected ViewTaskAction(GraphTaskManagerGUI taskManagerGUI)
	{
		super(taskManagerGUI);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		_taskManagerGUI.viewTasks();
	}

}
