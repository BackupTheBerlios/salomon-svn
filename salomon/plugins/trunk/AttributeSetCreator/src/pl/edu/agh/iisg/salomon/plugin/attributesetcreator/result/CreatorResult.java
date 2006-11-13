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

package pl.edu.agh.iisg.salomon.plugin.attributesetcreator.result;

import salomon.platform.serialization.IObject;
import salomon.plugin.IResult;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 *
 */
public final class CreatorResult extends SimpleStruct implements IResult
{
    public static final String ATTRIBUTE_SET_NAME = "attributeSetName";

    private String _attributeSetName;

    private boolean _isSuccessful = false;

    public String getAttributeSetName()
    {
        return _attributeSetName;
    }

    public boolean isSuccessful()
    {
        return _isSuccessful;
    }

    public void setAttributeSetName(String attributeSetName)
    {
        _attributeSetName = attributeSetName;
        setField(ATTRIBUTE_SET_NAME, new SimpleString(attributeSetName));
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
        SimpleString dataSetName = (SimpleString) struct.getField(ATTRIBUTE_SET_NAME);
        setField(ATTRIBUTE_SET_NAME, dataSetName);
        _attributeSetName = dataSetName.getValue();
    }

}
