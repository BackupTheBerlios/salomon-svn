
package salomon.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.controller.gui.ProjectEditionManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class SaveProjectAction extends AbstractProjectAction
{

	/**
	 * @param projectEditionManager
	 * @param taskEditionManager
	 */
	protected SaveProjectAction(ProjectEditionManager projectEditionManager)
	{
		super(projectEditionManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		_projectEditionManager.saveProject();
	}

}