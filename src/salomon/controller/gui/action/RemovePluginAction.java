
package salomon.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * @author nico
 * 
 */
public final class RemovePluginAction extends AbstractPluginAction
{

	/**
	 * @param editionManager
	 */
	public RemovePluginAction(TaskEditionManager editionManager)
	{
		super(editionManager);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		_taskEditionManager.removePlugin();
	}

}
