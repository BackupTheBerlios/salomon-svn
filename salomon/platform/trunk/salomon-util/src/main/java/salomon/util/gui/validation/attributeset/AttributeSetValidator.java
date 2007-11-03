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

package salomon.util.gui.validation.attributeset;

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * 
 */
public final class AttributeSetValidator implements IValidator
{
    private static final Logger LOGGER = Logger.getLogger(AttributeSetValidator.class);

    /**
     * Holds the attributeSet to be validated.
     */
    private final AttributeSet _attributeSet;

    private final IDataEngine _dataEngine;

    /**
     * @param attributeSet
     * @param engine 
     */
    public AttributeSetValidator(AttributeSet attributeSet, IDataEngine engine)
    {
        _attributeSet = attributeSet;
        _dataEngine = engine;
    }

    public Model getModel()
    {
        return _attributeSet;
    }

    /**
     * Method is responsible for dataset validation.
     * 
     * @see com.jgoodies.validation.Validator#validate()
     */
    public ValidationResult validate()
    {
        PropertyValidationSupport support = new PropertyValidationSupport(
                _attributeSet, "AttributeSet");

        // here whole validation !!!
        if (ValidationUtils.isBlank(_attributeSet.getAttributeSetName())) {
            support.addError("AttributeSet Name", "is mandatory");
        } else {
            try {
                boolean exists = exists(_attributeSet);
                if (exists) {
                    support.addError("AttributeSet Name", "already exists");
                }
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
                support.addError("AttributeSet Name", "INTERNAL ERROR");
            }
        }
        return support.getResult();
    }

    private boolean exists(AttributeSet attributeSet) throws PlatformException
    {
        // if attributeSetManager returns null it means that given dataset doesn't exist
        IAttributeSet iAttributeSet = _dataEngine.getAttributeManager().getAttributeSet(
                attributeSet.getAttributeSetName());
        return (iAttributeSet != null);
    }
}
