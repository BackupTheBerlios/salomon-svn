
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskEditionManager;

/**
 * Class represents action fired while adding new plugin.
 *  
 */
public final class AddPluginAction extends AbstractPluginAction
{
	/**
	 * Sets an object which method is called in implementation of
	 * actionPerformed() method
	 * 
	 * @param editionManager an instance of TaskEditionManager
	 */
	public AddPluginAction(TaskEditionManager editionManager)
	{
		super(editionManager);
	}

	public void actionPerformed(ActionEvent e)
	{
		_taskEditionManager.addPlugin();
	}

}