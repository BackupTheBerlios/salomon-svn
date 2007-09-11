/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.platform.data.attribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.exception.PlatformException;

public class EnumAttributeDescription extends AttributeDescription
        implements IEnumAttributeDescription
{

    private static final String SEPARATOR = ";";

    private Set _possibleValuesSet = new HashSet();

    /**
     * @param name
     * @param column
     * @param possibleValuesSet
     */
    @SuppressWarnings("unchecked")
    protected EnumAttributeDescription(String name, IColumn column,
            Object[] possibleValuesSet)
    {
        super(name, column);
        _possibleValuesSet = new HashSet();
        if (possibleValuesSet != null)
            for (Object ob : possibleValuesSet)
                _possibleValuesSet.add(ob);
    }

    /**
     * @param name
     * @param column
     * @param possibleValuesSet
     */
    protected EnumAttributeDescription(String name, IColumn column,
            Set possibleValuesSet)
    {
        super(name, column);
        _possibleValuesSet = possibleValuesSet;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.description.IEnumAttributeDescription#addPossibleValue(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public void addPossibleValue(Object obj)
    {
        _possibleValuesSet.add(obj);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.description.IAttributeDescription#createAttribute(java.lang.Object)
     */
    @Override
    public IAttribute createAttribute(Object value) throws PlatformException
    {
        if (this._possibleValuesSet.contains(value.toString()))
            return new Attribute(this, value);
        else
            throw new PlatformException(
                    "Trying to insert not allowed enum value (" + value + ")");
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.description.IEnumAttributeDescription#getPossibleValues()
     */
    public Object[] getPossibleValues()
    {
        return _possibleValuesSet.toArray();
    }

    /**
     * @see salomon.platform.data.attribute.description.IAttributeDescription#getType()
     */
    public AttributeType getType()
    {
        return AttributeType.ENUM;
    }

    public String[] getValues()
    {
        return (String[]) _possibleValuesSet.toArray(new String[_possibleValuesSet.size()]);
    }

    public void parseValues(String values)
    {
        _possibleValuesSet.addAll(Arrays.asList(values.split(SEPARATOR)));
    }

    @Override
    public String toString()
    {
        return super.toString() + "(" + valuesToString() + ")";
    }

    /**
     * FIXME: workaround
     * 
     * @return
     */
    public String valuesToString()
    {
        String[] values = getValues();
        String valuesString = values[0];
        for (int i = 1; i < values.length; ++i) {
            valuesString += SEPARATOR + values[i];
        }
        return valuesString;
    }

}
