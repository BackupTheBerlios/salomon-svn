
package salomon.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.controller.gui.ProjectEditionManager;

/**
 * Represents abstract action caused while editing projects.
 *  
 */
abstract class AbstractProjectAction extends AbstractAction
{
	/**
	 * an object which method are called in implementation of actionPerformed()
	 * method
	 */
	protected ProjectEditionManager _projectEditionManager;

	protected AbstractProjectAction(ProjectEditionManager projectEditionManager)
	{
		_projectEditionManager = projectEditionManager;
	}
}