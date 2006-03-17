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

package salomon.platform.data.attribute;

import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.PlatformException;

public interface IAttributeSet
{
    /**
     * Closes the IAttributeSet
     * 
     * @throws PlatformException
     */
    void close() throws PlatformException;

    /**
     * Returns all attributes in current "line" od IAttributeSet
     * 
     * @return attributes
     * @throws PlatformException
     */
    IAttribute[] getAttributes() throws PlatformException;

    /**
     * Returns specified attribute in current "line" od IAttributeSet
     * 
     * @param attributeDescription
     * @return
     * @throws PlatformException
     */
    IAttribute getAttribute(IAttributeDescription attributeDescription)
            throws PlatformException;

    /**
     * Returns all the IAttributeDescriptions associated with this IAttributeSet
     * 
     * @return
     * @throws PlatformException
     */
    IAttributeDescription[] getDesciptions() throws PlatformException;

    /**
     * Iterates through IAttributeSet till it's possible to iterate (there are
     * any more items)
     * 
     * @return value indicating if there the "row" was changed
     * @throws PlatformException
     */
    boolean next() throws PlatformException;

    /**
     * Adds "row" to the IAttributeSet
     * 
     * @param attributes
     * @throws PlatformException
     */
    void add(IAttribute[] attributes) throws PlatformException;

    /**
     * Gets the IAttributeSet name
     * 
     * @return name
     */
    String getName();

    /**
     * Sets the IAttributeSet name
     * 
     * @param name
     */
    void setName(String name);

    /**
     * Gets the IAttributeSet info.
     * 
     * @return info
     */
    String getInfo();

    /**
     * Sets the IAttributeSet info
     * 
     * @param info
     */
    void setInfo(String info);

    /**
     * Gets the IAttributeSet id
     * 
     * @return id
     */
    public int getAttributeSetId();

}
