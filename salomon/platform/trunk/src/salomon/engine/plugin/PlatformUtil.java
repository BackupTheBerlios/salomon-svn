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

import javax.swing.JComponent;

import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationResultViewFactory;

import salomon.plugin.IPlatformUtil;
import salomon.util.gui.validation.IValidationModel;

/**
 * 
 */
public final class PlatformUtil implements IPlatformUtil
{

    private ValidationResultModel _validationResultModel;

    /**
     * @see salomon.plugin.IPlatformUtil#setValidationModel(com.jgoodies.validation.ValidationResultModel)
     */
    public void setValidationModel(ValidationResultModel validationResultModel)
    {
        _validationResultModel = validationResultModel;
    }

    public JComponent getValidationComponent()
    {
        JComponent component = null;
        if (_validationResultModel != null) {
            component = ValidationResultViewFactory.createReportIconAndTextPane(_validationResultModel);
        }
        return component;
    }

    public IValidationModel getValidationModel(Object object)
    {
        throw new UnsupportedOperationException("Method PlatformUtil.getValidationModel() not implemented yet!");
    }
}
