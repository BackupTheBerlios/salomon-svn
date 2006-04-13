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

package salomon.engine.plugin;

import salomon.engine.util.validation.ValidationModel;
import salomon.plugin.IPlatformUtil;
import salomon.util.gui.validation.IValidationModel;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.validation.ValidationResultModel;

/**
 * 
 */
public final class PlatformUtil implements IPlatformUtil
{

    private ValidationResultModel _validationResultModel;

    //    /**
    //     * @see salomon.plugin.IPlatformUtil#setValidationModel(com.jgoodies.validation.ValidationResultModel)
    //     */
    //    public void setValidationModel(ValidationResultModel validationResultModel)
    //    {
    //        _validationResultModel = validationResultModel;
    //    }

//    public JComponent getValidationComponent()
//    {
//        JComponent component = null;
//        if (_validationResultModel != null) {
//            component = ValidationResultViewFactory.createReportIconAndTextPane(_validationResultModel);
//        }
//        return component;
//    }

    public IValidationModel getValidationModel(IValidator validator)
    {
        ValidationModel validationModel = new ValidationModel(validator);
        _validationResultModel = validationModel.getValidationResultModel();
//        _validationResultModel.addPropertyChangeListener(new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent e)
//            {
//                if (_validationResultModel.hasErrors()) {
//                    System.out.println("Dupa");
//                } else {
//                    System.out.println("OKS");
//                }
//                _settingsPanel.refresh();
//            }
//        });
        return validationModel;
    }

    /**
     * Returns the validationResultModel.
     * @return The validationResultModel
     */
    public ValidationResultModel getValidationResultModel()
    {
        return _validationResultModel;
    }

    /**
     * Set the value of validationResultModel field.
     * @param validationResultModel The validationResultModel to set
     */
    public void setValidationResultModel(ValidationResultModel validationResultModel)
    {
        _validationResultModel = validationResultModel;
    }
}
