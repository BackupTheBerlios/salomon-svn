
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * @author nico
 * 
 */
public final class SavePluginAction extends AbstractPluginAction
{

	/**
	 * @param editionManager
	 */
	public SavePluginAction(TaskEditionManager editionManager)
	{
		super(editionManager);	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{		
		_taskEditionManager.savePlugin();
	}

}
