
package salomon.engine.controller.gui.common.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.domain.SolutionManagerGUI;

public class ViewSolutionAction extends AbstractSolutionAction
{
    private static final long serialVersionUID = 1L;

    protected ViewSolutionAction(SolutionManagerGUI solutionManagerGUI)
    {
        super(solutionManagerGUI);
    }

    public void actionPerformed(ActionEvent arg0)
    {
        System.out.println("action preformator");
        _solutionEditionManager.viewSolutions();
    }

}
