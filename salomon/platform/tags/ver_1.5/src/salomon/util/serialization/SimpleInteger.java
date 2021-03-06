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

import salomon.platform.serialization.IInteger;
import salomon.platform.serialization.IObject;

/**
 * 
 */
public class SimpleInteger implements IInteger
{
    private int _value;

    /**
     * 
     */
    public SimpleInteger()
    {
        // empty body
    }

    /**
     * @param value
     */
    public SimpleInteger(int value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IInteger#getValue()
     */
    public int getValue()
    {
        return _value;
    }

    /**
     * @see salomon.platform.serialization.IInteger#setValue(int)
     */
    public void setValue(int value)
    {
        _value = value;
    }

    /**
     * @see salomon.platform.serialization.IObject#getType()
     */
    public IObject.Types getType()
    {
        return IObject.Types.INT;
    }

    /**
     * @see salomon.platform.serialization.IObject#equals(salomon.platform.serialization.IObject)
     */
    public boolean equals(IObject object)
    {
        boolean result = false;
        if (object.getType() == getType()) {
            result = (_value == ((SimpleInteger) object).getValue());
        }
        return result;
    }
}
