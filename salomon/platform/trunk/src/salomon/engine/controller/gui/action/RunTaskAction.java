
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskEditionManager;

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
     * Sets an object which method is called in implementation of
     * actionPerformed() method
     * 
     * @param editionManager an instance of TaskEditionManager
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