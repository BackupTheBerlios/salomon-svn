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

package salomon.platform.data.attribute;

import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IAttributeManager
{
    /**
     * Adds the attribute set to the storage.
     * 
     * @param attributeSet the attribute set to add
     * 
     */
    void add(IAttributeSet attributeSet) throws PlatformException;

    /**
     * Creates a new condition basing on its string representation.
     * 
     * @param attributeDescription string representing condition
     * @return a new attribute description
     * @throws PlatformException if any error occurs (e.g. invalid attribute description)
     */
    IAttributeDescription createAttributeDescription(String attributeName,
            String tableName, String columnName, String type)
            throws PlatformException;

    /**
     * Creates a new empty attribute set.
     * @return the new attribute set
     */
    IAttributeSet createAttributeSet(IAttributeDescription[] descriptions);

    /**
     * Creates a new date attribute description
     * 
     * @param name the name
     * @param column the column name
     * @return a new date arribute description
     */
    IAttributeDescription createDateAttributeDescription(String name,
            IColumn column);

    /**
     * Creates a new enum attribute description.
     * 
     * @param name the name
     * @param column the column name
     * @param possibleValues the possible values
     * @return a new enum attribute description
     */
    IAttributeDescription createEnumAttributeDescription(String name,
            IColumn column, Object[] possibleValues);

    /**
     * Creates a new integer attribute description
     * 
     * @param name the name
     * @param column the column name
     * @return a new integer arribute description
     */
    IAttributeDescription createIntegerAttributeDescription(String name,
            IColumn column);

    /**
     * Creates a new real attribute description
     * 
     * @param name the name
     * @param column the column name
     * @return a new real arribute description
     */
    IAttributeDescription createRealAttributeDescription(String name,
            IColumn column);

    /**
     * Creates a new string attribute description
     * 
     * @param name the name
     * @param column the column name
     * @return a new string arribute description
     */
    IAttributeDescription createStringAttributeDescription(String name,
            IColumn column);

    /**
     * Gets all attribute sets.
     * 
     * @return the all attribute sets
     */
    IAttributeSet[] getAll() throws PlatformException;

    /**
     * Gets a attribute set with the given id.
     * 
     * @param id attribute set id
     * @return wanted attribute set
     */
    IAttributeSet getAttributeSet(int id) throws PlatformException;

    /**
     * Gets a attribute set with the given name.
     * 
     * @param name attribute set name
     * @return
     * @throws PlatformException
     */
    IAttributeSet getAttributeSet(String name) throws PlatformException;

    /**
     * Removes the given attribute set from the storage.
     * 
     * @param attributeSet the attribute set to remove
     * @throws PlatformException
     */
    void remove(IAttributeSet attributeSet) throws PlatformException;

}
