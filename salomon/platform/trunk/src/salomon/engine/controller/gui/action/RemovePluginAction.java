
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskEditionManager;

/**
 * Class represents action fired while removing plugin.
 *  
 */
public final class RemovePluginAction extends AbstractPluginAction
{
	/**
	 * Sets an object which method is called in implementation of
	 * actionPerformed() method
	 * 
	 * @param editionManager an instance of TaskEditionManager
	 */
	public RemovePluginAction(TaskEditionManager editionManager)
	{
		super(editionManager);
	}

	public void actionPerformed(ActionEvent arg0)
	{
		_taskEditionManager.removePlugin();
	}

}