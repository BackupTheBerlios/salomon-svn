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

import org.apache.log4j.Logger;

import salomon.engine.solution.SolutionInfo;
import salomon.engine.solution.SolutionManager;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

public class SolutionValidator implements IValidator
{
    private static final Logger LOGGER = Logger.getLogger(SolutionValidator.class);

    // keeps the old value
    private SolutionInfo _solutionInfo;

    private SolutionManager _solutionManager;

    private SolutionModel _solutionModel;

    /**
     * @param solutionManager
     */
    public SolutionValidator(SolutionModel model,
            SolutionManager solutionManager)
    {
        _solutionModel = model;
        _solutionManager = solutionManager;
        _solutionInfo = (SolutionInfo) _solutionManager.getCurrentSolution().getInfo();
    }

    public Model getModel()
    {
        return _solutionModel;
    }

    /**
     * Method is responsible for solution validation.
     * 
     * @see com.jgoodies.validation.Validator#validate()
     */
    public ValidationResult validate()
    {
        LOGGER.debug("SolutionValidator.validate()");
        PropertyValidationSupport support = new PropertyValidationSupport(
                _solutionInfo, "Solution");

        // solution name validation !!!
        String currentSolutioName = _solutionModel.getSolutionName();
        // not validate if the name is not changed - second validation fails as the name exists in DB
        // this may happen when editing solution
        if (!_solutionInfo.getName().equals(currentSolutioName)) {
            if (ValidationUtils.isBlank(currentSolutioName)) {
                support.addError("Solution Name", "is mandatory");
            } else {
                try {
                    boolean exists = _solutionManager.exists(currentSolutioName);
                    if (exists) {
                        support.addError("Solution Name", "already exists");
                    }
                } catch (PlatformException e) {
                    LOGGER.fatal("", e);
                    support.addError("Solution Name", "INTERNAL ERROR");
                }
            }
        }
        return support.getResult();
    }
}
