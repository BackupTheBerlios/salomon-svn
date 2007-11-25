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

package salomon.engine.controller.gui.project;

import org.apache.log4j.Logger;

import salomon.engine.project.ProjectInfo;
import salomon.engine.project.ProjectManager;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * 
 */
public class ProjectValidator implements IValidator
{
    private static final Logger LOGGER = Logger.getLogger(ProjectValidator.class);

    private ProjectInfo _projectInfo;

    private ProjectManager _projectManager;

    private ProjectModel _projectModel;

    public ProjectValidator(ProjectModel model, ProjectManager projectManager)
    {
        _projectModel = model;
        _projectManager = projectManager;
//FIXME:        _projectInfo = (ProjectInfo) _projectManager.getCurrentProject().getInfo();
    }

    /**
     * @see salomon.util.gui.validation.IValidator#getModel()
     */
    public Model getModel()
    {
        return _projectModel;
    }

    /**
     * Method is responsible for project validation.
     * 
     * @see com.jgoodies.validation.Validator#validate()
     */
    public ValidationResult validate()
    {
        LOGGER.debug("ProjectValidator.validate()");
        PropertyValidationSupport support = new PropertyValidationSupport(
                _projectInfo, "Project");

        String currentProjectName = _projectModel.getProjectName();
        // name validatation if it has changed
        String projectName = _projectInfo.getName();
        if (projectName == null || !projectName.equals(currentProjectName)) {
            if (ValidationUtils.isBlank(currentProjectName)) {
                support.addError("Project Name", "is mandatory");
            } else {
                try {
                    boolean exists = _projectManager.exists(currentProjectName);
                    if (exists) {
                        support.addError("Project Name", "already exists");
                    }
                } catch (PlatformException e) {
                    LOGGER.fatal("", e);
                    support.addError("Project Name", "INTERNAL ERROR");
                }
            }
        }
        return support.getResult();
    }
}
