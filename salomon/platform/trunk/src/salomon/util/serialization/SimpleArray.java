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

import salomon.platform.serialization.IArray;
import salomon.platform.serialization.IObject;

public class SimpleArray implements IArray
{

    /**
     * 
     * @uml.property name="_value"
     * @uml.associationEnd multiplicity="(0 -1)"
     */
    private IObject[] _value;

    /**
     * 
     */
    public SimpleArray()
    {
        _value = new IObject[0];
    }

    /**
     * @param value
     */
    public SimpleArray(IObject[] value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IArray#getValue()
     */
    public IObject[] getValue()
    {
        return _value;
    }

    /**
     * @see salomon.platform.serialization.IArray#setValue(salomon.platform.serialization.IObject[])
     */
    public void setValue(IObject[] value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IObject#getType()
     */
    public IObject.Types getType()
    {
        return IObject.Types.ARRAY;
    }

    /**
     * @see salomon.platform.serialization.IObject#equals(salomon.platform.serialization.IObject)
     */
    public boolean equals(IObject object)
    {
        if (!(object instanceof SimpleArray)) {
            return false;
        }

        SimpleArray otherArray = (SimpleArray) object;
        if (_value.length != otherArray._value.length) {
            return false;
        }

        for (int i = 0; i < _value.length; ++i) {
            IObject item = _value[i];
            IObject otherItem = otherArray._value[i];
            if (item == null) {
                if (otherItem != null) {
                    return false;
                }
            } else {
                if (!item.equals(otherItem)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int size()
    {
        return (_value == null ? 0 : _value.length);
    }

}
