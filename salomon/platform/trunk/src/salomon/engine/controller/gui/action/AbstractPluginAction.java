
package salomon.engine.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.engine.controller.gui.TaskEditionManager;

/**
 * Represents abstract action caused while editing plugins.
 *  
 */
public abstract class AbstractPluginAction extends AbstractAction
{
	/**
	 * an object which method are called in implementation of actionPerformed()
	 * method
	 */
	protected TaskEditionManager _taskEditionManager;

	public AbstractPluginAction(TaskEditionManager editionManager)
	{
		_taskEditionManager = editionManager;
	}
}