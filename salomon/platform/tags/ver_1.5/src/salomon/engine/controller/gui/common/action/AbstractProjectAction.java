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

package salomon.engine.controller.gui.common.action;

import javax.swing.AbstractAction;

import salomon.engine.controller.gui.project.ProjectManagerGUI;

/**
 * Represents abstract action caused while editing projects.
 */
abstract class AbstractProjectAction extends AbstractAction
{

    /**
     * an object which method are called in implementation of actionPerformed()
     * method
     * 
     * @uml.property name="_projectEditionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    protected ProjectManagerGUI _projectEditionManager;

    protected AbstractProjectAction(ProjectManagerGUI projectEditionManager)
    {
        _projectEditionManager = projectEditionManager;
    }
}