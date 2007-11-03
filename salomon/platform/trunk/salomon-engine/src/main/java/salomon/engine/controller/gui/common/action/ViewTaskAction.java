
package salomon.engine.controller.gui.common.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.task.GraphTaskManagerGUI;

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
