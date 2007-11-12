
package salomon.engine.controller.gui.common.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.task.GraphTaskManagerGUI;
import salomon.engine.task.ITask;

public class EditTaskAction extends AbstractTaskAction
{
    private static final long serialVersionUID = 1L;

    private ITask _task;

    /**
     * Sets an object which method is called in implementation of
     * actionPerformed() method
     * 
     * @param taskManagerGUI an instance of TaskManagerGUI
     */
    public EditTaskAction(GraphTaskManagerGUI taskManagerGUI, ITask task)
    {
        super(taskManagerGUI);

        _task = task;
    }

    public void actionPerformed(ActionEvent event)
    {
        _taskManagerGUI.editTask(_task);
    }

}
