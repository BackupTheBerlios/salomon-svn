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

import com.jgoodies.binding.beans.Model;

/**
 * 
 */
public final class DataSet extends Model
{
    public static final String PROPERTYNAME_DATASET_NAME = "dataSetName";

    private String _dataSetName;

    /**
     * Returns the dataSetName.
     * @return The dataSetName
     */
    public String getDataSetName()
    {
        return _dataSetName;
    }

    /**
     * Set the value of dataSetName field.
     * @param dataSetName The dataSetName to set
     */
    public void setDataSetName(String dataSetName)
    {
        String oldValue = getDataSetName();
        _dataSetName = dataSetName;
        firePropertyChange(PROPERTYNAME_DATASET_NAME, oldValue, dataSetName);
    }

}
