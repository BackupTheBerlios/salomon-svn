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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator.result;

import salomon.platform.serialization.IObject;
import salomon.plugin.IResult;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 *
 */
public final class CreatorResult extends SimpleStruct implements IResult
{
    public static final String DATA_SET_NAME = "dataSetName";

    private String _dataSetName;

    private boolean _isSuccessful = false;

    public String getDataSetName()
    {
        return _dataSetName;
    }

    public boolean isSuccessful()
    {
        return _isSuccessful;
    }

    public void setDataSetName(String dataSetName)
    {
        _dataSetName = dataSetName;
        setField(DATA_SET_NAME, new SimpleString(dataSetName));
    }

    /**
     * @param isSuccessful the isSuccessful to set
     */
    public void setSuccessful(boolean isSuccessful)
    {
        _isSuccessful = isSuccessful;
    }

    public void init(IObject object)
    {
        SimpleStruct struct = (SimpleStruct) object;
        // setting struct fields
        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET_NAME);
        setField(DATA_SET_NAME, dataSetName);
        _dataSetName = dataSetName.getValue();
    }

}
