
package salomon.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * Represents abstract action caused while editing tasks.
 *  
 */
abstract class AbstractTaskAction extends AbstractAction
{
	/**
	 * an object which method are called in implementation of actionPerformed()
	 * method
	 */
	protected TaskEditionManager _taskEditionManager = null;

	protected AbstractTaskAction(TaskEditionManager editionManager)
	{
		_taskEditionManager = editionManager;
	}
}