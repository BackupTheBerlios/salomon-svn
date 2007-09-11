/*
 * Copyright (C) 2004 Salomon Team
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

package salomon.util.serialization;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IString;

/**
 * 
 */
public class SimpleString implements IString
{
    private String _value;

    /**
     * 
     */
    public SimpleString()
    {
        // empty body
    }

    /**
     * @param value
     */
    public SimpleString(String value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IString#getValue()
     */
    public String getValue()
    {
        return _value;
    }

    /**
     * @see salomon.platform.serialization.IString#setValue(java.lang.String)
     */
    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IObject#getType()
     */
    public IObject.Types getType()
    {
        // TODO Auto-generated method stub
        return IObject.Types.STRING;
    }

    /**
     * @see salomon.platform.serialization.IObject#equals(salomon.platform.serialization.IObject)
     */
    public boolean equals(IObject object)
    {
        boolean result = false;
        if (object.getType() == getType()) {
            String toCompare = ((SimpleString) object).getValue();
            if (_value == null) {
                result = (toCompare == null);
            } else {
                result = (_value.equals(toCompare));
            }
        }
        return result;
    }

}
