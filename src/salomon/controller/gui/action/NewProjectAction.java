
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
public final class NewProjectAction extends AbstractProjectAction
{

	/**
	 * @param projectEditionManager
	 * @param taskEditionManager
	 */
	NewProjectAction(ProjectEditionManager projectEditionManager)
	{
		super(projectEditionManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event)
	{
		_projectEditionManager.newProject();
	}
}