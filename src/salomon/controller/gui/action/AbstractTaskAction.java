
package salomon.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
abstract class AbstractTaskAction extends AbstractAction
{
	protected TaskEditionManager _taskEditionManager = null;

	/**
	 * @param editionManager
	 */
	protected AbstractTaskAction(TaskEditionManager editionManager)
	{
		_taskEditionManager = editionManager;
	}
}