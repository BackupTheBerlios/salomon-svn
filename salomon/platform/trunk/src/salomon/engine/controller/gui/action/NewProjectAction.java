
package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.ProjectEditionManager;

/**
 * Class represents action fired while creating a new project.
 *  
 */
public final class NewProjectAction extends AbstractProjectAction
{
	/**
	 * Sets an object which method is called in implementation of
	 * actionPerformed() method
	 * 
	 * @param projectEditionManager an instance of ProjectEditionManager
	 */
	NewProjectAction(ProjectEditionManager projectEditionManager)
	{
		super(projectEditionManager);
	}

	public void actionPerformed(ActionEvent event)
	{
		_projectEditionManager.newProject();
	}
}