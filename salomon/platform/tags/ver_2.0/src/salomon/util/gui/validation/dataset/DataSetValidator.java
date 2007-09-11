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

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;
import salomon.util.gui.validation.IValidator;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.PropertyValidationSupport;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * 
 */
public class DataSetValidator implements IValidator
{

    private static final Logger LOGGER = Logger.getLogger(DataSetValidator.class);

    private final IDataEngine _dataEngine;

    /**
     * Holds the dataSet to be validated.
     */
    private final DataSet _dataSet;

    /**
     * @param dataSet
     * @param engine 
     */
    public DataSetValidator(DataSet dataSet, IDataEngine engine)
    {
        _dataSet = dataSet;
        _dataEngine = engine;
    }

    /**
     * Method is responsible for dataset validation.
     * 
     * @see com.jgoodies.validation.Validator#validate()
     */
    public ValidationResult validate()
    {
        PropertyValidationSupport support = new PropertyValidationSupport(
                _dataSet, "DataSet");

        // here whole validation !!!
        if (ValidationUtils.isBlank(_dataSet.getDataSetName())) {
            support.addError("DataSet Name", "is mandatory");
        } else {
            try {
                boolean exists = exists(_dataSet);
                if (exists) {
                    support.addError("DataSet Name", "already exists");
                }
            } catch (PlatformException e) {
                LOGGER.fatal("", e);
                support.addError("DataSet Name", "INTERNAL ERROR");
            }
        }
        return support.getResult();
    }

    private boolean exists(DataSet dataSet) throws PlatformException
    {
        // if dataSetManager returns null it means that given dataset doesn't exist
        IDataSet iDataSet = _dataEngine.getDataSetManager().getDataSet(
                dataSet.getDataSetName());
        return (iDataSet != null);
    }

    public Model getModel()
    {
        return _dataSet;
    }
}
