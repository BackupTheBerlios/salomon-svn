
package salomon.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class RunTaskAction extends AbstractTaskAction
{

	/**
	 * @param taskEditionManager
	 */
	public RunTaskAction(TaskEditionManager taskEditionManager)
	{
		super(taskEditionManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		_taskEditionManager.runTasks();
	}

}