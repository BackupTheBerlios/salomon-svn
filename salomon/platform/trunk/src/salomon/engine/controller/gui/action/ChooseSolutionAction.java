package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.SolutionManagerGUI;

public class ChooseSolutionAction extends AbstractSolutionAction
{

	/**
	 * Sets an object which method is called in implementation of
	 * actionPerformed() method
	 * 
	 * @param solutionEditionManager an instance of SolutionManagerGUI
	 */
	ChooseSolutionAction(SolutionManagerGUI solutionEditionManager)
	{
		super(solutionEditionManager);
	}

	public void actionPerformed(ActionEvent event)
	{
		_solutionEditionManager.chooseSolutionOnStart();
	}
	
}
