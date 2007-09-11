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

package salomon.engine.util.validation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import salomon.util.gui.validation.IComponentFactory;
import salomon.util.gui.validation.IValidationModel;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * 
 */
public final class ValidationModel extends PresentationModel
        implements IValidationModel
{
    private IComponentFactory _componentFactory;

    private final ValidationResultModel _validationResultModel;

    private final IValidator _validator;

    public ValidationModel(IValidator validator)
    {
        super(validator.getModel());
        _validator = validator;
        _validationResultModel = new DefaultValidationResultModel();
        initEventHandling();
        updateValidationResult();
        _componentFactory = new ComponentFactory(this);
    }

    public IComponentFactory getComponentFactory()
    {
        return _componentFactory;
    }

    public ValidationResultModel getValidationResultModel()
    {
        return _validationResultModel;
    }

    private void initEventHandling()
    {
        PropertyChangeListener handler = new ValidationUpdateHandler();
        addBeanPropertyChangeListener(handler);
        getBeanChannel().addValueChangeListener(handler);
    }

    private void updateValidationResult()
    {
        ValidationResult result = _validator.validate();
        _validationResultModel.setResult(result);
    }

    private class ValidationUpdateHandler implements PropertyChangeListener
    {
        public void propertyChange(PropertyChangeEvent evt)
        {
            updateValidationResult();
        }
    }
}
