/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.engine.controller.gui.solution;

import com.jgoodies.binding.beans.Model;

/**
 * 
 */
class SolutionModel extends Model
{
    public static final String PROPERTYNAME_SOLUTION_NAME = "solutionName";

    private String _solutionName;

    /**
     * Returns the solutionName.
     * @return The solutionName
     */
    public String getSolutionName()
    {
        return _solutionName;
    }

    /**
     * Set the value of solutionName field.
     * @param solutionName The solutionName to set
     */
    public void setSolutionName(String solutionName)
    {
        String oldValue = getSolutionName();
        _solutionName = solutionName;
        firePropertyChange(PROPERTYNAME_SOLUTION_NAME, oldValue, solutionName);
    }

}
