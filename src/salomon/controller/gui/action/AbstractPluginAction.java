
package salomon.controller.gui.action;

import javax.swing.AbstractAction;

import salomon.controller.gui.TaskEditionManager;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public abstract class AbstractPluginAction extends AbstractAction
{
    /**
     * an object which method are called in implementation of actionPerformed()
     * method
     */   

    protected TaskEditionManager _taskEditionManager = null;

    protected AbstractPluginAction(TaskEditionManager editionManager)
    {
        _taskEditionManager = editionManager;
    }

}
