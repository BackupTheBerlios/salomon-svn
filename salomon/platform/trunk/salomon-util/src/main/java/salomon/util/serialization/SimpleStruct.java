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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;

/**
 * 
 */
public class SimpleStruct implements IStruct
{

    private final Map<String, IObject> _fields = new HashMap<String, IObject>();

    /**
     * @see salomon.platform.serialization.IObject#equals(salomon.platform.serialization.IObject)
     */
    public boolean equals(IObject object)
    {
        boolean result = false;
        if (object.getType() == getType()) {
            SimpleStruct other = (SimpleStruct) object;
            String[] otherNames = other.getFieldNames();
            if (otherNames.length == _fields.size()) {
                result = true;
                for (String name : otherNames) {
                    IObject toCompare = other.getField(name);
                    IObject base = getField(name);
                    if ((base == null) || !(base.equals(toCompare))) {
                        result = false;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * @see salomon.platform.serialization.IStruct#getField(java.lang.String)
     */
    public IObject getField(String name)
    {
        return _fields.get(name);
    }

    /**
     * @see salomon.platform.serialization.IStruct#getFieldNames()
     */
    public String[] getFieldNames()
    {
        Set<String> keys = _fields.keySet();

        return keys.toArray(new String[keys.size()]);
    }

    /**
     * @see salomon.platform.serialization.IObject#getType()
     */
    public IObject.Types getType()
    {
        return IObject.Types.STRUCT;
    }

    /**
     * @see salomon.platform.serialization.IStruct#setField(java.lang.String,
     *      salomon.platform.serialization.IObject)
     */
    public void setField(String name, IObject value)
    {
        _fields.put(name, value);
    }

    public void setField(String name, String value)
    {
        _fields.put(name, new SimpleString(value));
    }

    public void setField(String name, Object[] values)
    {
        IObject[] objects = new IObject[values.length];
        for (int i = 0; i < values.length; i++) {
            objects[i] = createObject(values[i]);
        }
        _fields.put(name, new SimpleArray(objects));
    }

    public void setField(String name, IObject[] values) {
        setField(name, new SimpleArray(values));
    }

    private static IObject createObject(Object value) {
        if (value instanceof String) {
            return new SimpleString((String) value);
        }

        if (value == null) {
            throw new UnsupportedOperationException("Cannot create IObjec for null");
        }

        throw new UnsupportedOperationException("Cannot create IObject for class: " + value.getClass());
    }

    public void setField(String name, int value)
    {
        setField(name, new SimpleInteger(value));
    }

	public Object getValue() {
		throw new UnsupportedOperationException(
				"Method SimpleStruct.getValue() is not implemented yet!");
	}
}
