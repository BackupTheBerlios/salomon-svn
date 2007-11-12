/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.controller.gui.common.action;

import java.awt.event.ActionEvent;

import salomon.engine.controller.gui.project.ProjectManagerGUI;

/**
 * Class represents action fired while editing a project.
 *  
 */
public class EditProjectAction extends AbstractProjectAction
{
    /**
     * Sets an object which method is called in implementation of
     * actionPerformed() method
     * 
     * @param projectEditionManager an instance of ProjectManagerGUI
     */
    public EditProjectAction(ProjectManagerGUI projectManagerGUI)
    {
        super(projectManagerGUI);
    }

    public void actionPerformed(ActionEvent event)
    {
        _projectEditionManager.editProject();
    }
}
