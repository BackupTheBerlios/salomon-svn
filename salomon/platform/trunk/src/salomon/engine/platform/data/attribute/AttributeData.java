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

package salomon.engine.platform.data.attribute;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.dataset.IData;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AttributeData implements IAttributeData
{
    private IData _data;

    private IAttributeDescription[] _descriptions;

    public AttributeData(IAttributeDescription[] descriptions, IData data)
            throws PlatformException
    {
        _descriptions = descriptions;
        _data = data;
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeData#close()
     */
    public void close() throws PlatformException
    {
        _data.close();
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeData#getAttribute(java.lang.String)
     */
    public IAttribute getAttribute(String attributeName)
            throws PlatformException
    {
        IAttribute attribute = null;
        String columnName = null;
        int index = 0;
        for (; index < _descriptions.length; ++index) {
            if (_descriptions[index].getName().equals(attributeName)) {
                break;
            }
        }

        if (columnName != null) {
            Object[] data = _data.getData();
            attribute = new Attribute(_descriptions[index], data[index]);
        }
        return attribute;
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeData#getAttributes()
     */
    public IAttribute[] getAttributes() throws PlatformException
    {
        // assuming, that data selected match the description table
        Object[] data = _data.getData();
        IAttribute[] attributes = new IAttribute[_descriptions.length];
        for (int i = 0; i < _descriptions.length; ++i) {
            attributes[i] = new Attribute(_descriptions[i], data[i]);
        }
        return attributes;
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeData#next()
     */
    public boolean next() throws PlatformException
    {
        return _data.next();
    }

}
