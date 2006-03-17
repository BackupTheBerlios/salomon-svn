/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.controller.gui.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.SolutionManagerGUI;

/**
 * Class represents action fired while saving solution.
 *  
 */
public final class SaveSolutionAction extends AbstractSolutionAction
{

    /**
     * Sets an object which method is called in implementation of actionPerformed()
     * of method
     * 
     * @param solutionEditionManager an instance of SolutionManagerGUI
     */
    protected SaveSolutionAction(SolutionManagerGUI solutionEditionManager)
    {
        super(solutionEditionManager);
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e)
    {
        _solutionEditionManager.saveSolution();
    }

}