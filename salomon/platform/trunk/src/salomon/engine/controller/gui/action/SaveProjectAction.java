
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.ProjectEditionManager;

/**
 * Class represents action fired while saving project.
 *  
 */
public final class SaveProjectAction extends AbstractProjectAction
{

	/**
	 * Sets an object which method is called in implementation of actionPerformed()
	 * of method
	 * 
	 * @param projectEditionManager an instance of ProjectEditionManager
	 */
	protected SaveProjectAction(ProjectEditionManager projectEditionManager)
	{
		super(projectEditionManager);
	}

	public void actionPerformed(ActionEvent e)
	{
		_projectEditionManager.saveProject();
	}

}