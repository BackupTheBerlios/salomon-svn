
package salomon.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.controller.gui.ProjectEditionManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
abstract class AbstractProjectAction extends AbstractAction
{
	protected ProjectEditionManager _projectEditionManager;

	/**
	 * @param editionManager
	 */
	protected AbstractProjectAction(ProjectEditionManager projectEditionManager)
	{
		_projectEditionManager = projectEditionManager;
	}
}