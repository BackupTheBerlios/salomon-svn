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

import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.PlatformException;

public abstract class AttributeDescription implements IAttributeDescription
{
    private IColumn _column;

    private int _descriptionID;

    private boolean _isOutput;

    private String _name;

    protected AttributeDescription(String name, IColumn column)
    {
        _name = name;
        _column = column;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.description.IAttributeDescription#createAttribute(java.lang.Object)
     */
    public IAttribute createAttribute(Object value) throws PlatformException
    {
        return new Attribute(this, value);
    }

    /**
     * Compares two instances of AttributeDescription 
     * @param a
     * @return
     */
    public boolean equals(AttributeDescription a)
    {
        return (_name.equals(a.getName()));
    }

    /**
     * Returns the column.
     * @return The column
     */
    public final IColumn getColumn()
    {
        return _column;
    }

    /**
     * Returns the descriptionID.
     * @return The descriptionID
     */
    public final int getDescriptionID()
    {
        return _descriptionID;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.description.IAttributeDescription#getName()
     */
    public String getName()
    {
        return _name;
    }

    /**
     * Returns the isOutput.
     * @return The isOutput
     */
    public final boolean isOutput()
    {
        return _isOutput;
    }

    /**
     * Set the value of descriptionID field.
     * @param descriptionID The descriptionID to set
     */
    public final void setDescriptionID(int descriptionID)
    {
        _descriptionID = descriptionID;
    }

    /**
     * Set the value of isOutput field.
     * @param isOutput The isOutput to set
     */
    public final void setOutput(boolean isOutput)
    {
        _isOutput = isOutput;
    }

    @Override
    public String toString()
    {
        return _name + " " + _column + " " + getType();
    }
}
