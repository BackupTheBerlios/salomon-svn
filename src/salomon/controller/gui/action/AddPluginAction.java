
package salomon.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * @author nico
 * 
 */
public final class AddPluginAction extends AbstractPluginAction
{

	/**
	 * @param editionManager
	 */
	public AddPluginAction(TaskEditionManager editionManager)
	{
		super(editionManager);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		_taskEditionManager.addPlugin();		
	}

}
