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

package salomon.platform.data.attribute.description;

import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.IAttribute;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IAttributeDescription
{

    /**
     * Creates new attribute with given value.
     * @return the new attribute
     */
    IAttribute createAttribute(Object value) throws PlatformException;

    /**
     * Returns the column associated with the attribute.
     * 
     * @return the column
     */
    IColumn getColumn();

    /**
     * Returns the name of the attribute.
     * @return the name
     */
    String getName();

    /**
     * Returns the type of the attribute description.
     * 
     * @return the type
     */
    AttributeType getType();
    
    /**
     * Returns whether the attribute is output attribute.
     * 
     * @return
     */
    boolean isOutput();
}
