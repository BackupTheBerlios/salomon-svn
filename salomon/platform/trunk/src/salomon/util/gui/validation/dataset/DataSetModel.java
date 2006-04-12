/*
 * Copyright (C) 2006 DataSetCreator Team
 *
 * This file is part of DataSetCreator.
 *
 * DataSetCreator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * DataSetCreator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with DataSetCreator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.util.gui.validation.dataset;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


import salomon.platform.IDataEngine;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * 
 */
public final class DataSetModel extends PresentationModel
{
    private final ValidationResultModel _validationResultModel;
    
    private final IDataEngine _dataEngine;

    public DataSetModel(DataSet dataSet, IDataEngine dataEngine)
    {
        super(dataSet);
        _dataEngine = dataEngine;
        _validationResultModel = new DefaultValidationResultModel();
        initEventHandling();
        updateValidationResult();
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
        DataSet dataSet = (DataSet) getBean();
        ValidationResult result = new DataSetValidator(dataSet, _dataEngine).validate();
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
