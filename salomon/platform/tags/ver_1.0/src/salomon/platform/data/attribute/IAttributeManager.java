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

import salomon.platform.IUniqueId;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.attribute.description.IDateAttributeDescription;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.data.attribute.description.IIntegerAttributeDescription;
import salomon.platform.data.attribute.description.IRealAttributeDescription;
import salomon.platform.data.attribute.description.IStringAttributeDescription;
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
	 * Creates a new empty attribute set.
	 * @return the new attribute set
	 */
	IAttributeSet createAttributeSet(IAttributeDescription[] descriptions);

	/**
	 * Creates a new date attribute description
	 * 
	 * @param name the name
	 * @return a new date arribute description
	 */
	IDateAttributeDescription createDateAttributeDescription(String name);

	/**
	 * Creates a new enum attribute description.
	 * 
	 * @param name the name
	 * @param possibleValues the possible values
	 * @return a new enum attribute description
	 */
	IEnumAttributeDescription createEnumAttributeDescription(String name,
			Object[] possibleValues);

	/**
	 * Creates a new integer attribute description
	 * 
	 * @param name the name
	 * @return a new integer arribute description
	 */
	IIntegerAttributeDescription createIntegerAttributeDescription(String name);

	/**
	 * Creates a new real attribute description
	 * 
	 * @param name the name
	 * @return a new real arribute description
	 */
	IRealAttributeDescription createRealAttributeDescription(String name);

	/**
	 * Creates a new string attribute description
	 * 
	 * @param name the name
	 * @return a new string arribute description
	 */
	IStringAttributeDescription createStringAttributeDescription(String name);

	/**
	 * Gets all attribute sets.
	 * 
	 * @return the all attribute sets
	 */
	IAttributeSet[] getAll() throws PlatformException;

	/**
	 * Gets a attribute set with the given id.
	 * 
	 * @param id data set id
	 * @return wanted attribute set
	 */
	IAttributeSet getAttributeSet(IUniqueId id) throws PlatformException;

	/**
	 * Removes the given attribute set from the storage.
	 * 
	 * @param attributeSet the attribute set to remove
	 * @throws PlatformException
	 */
	void remove(IAttributeSet attributeSet) throws PlatformException;
	
	/**
	 * Sets restrictive type checking (slower)
	 * @param restrictiveTypeCheck
	 */
	void setRestrictiveTypeCheck(boolean restrictiveTypeCheck);
	
	/**
	 * @return restrictive type checking switch
	 */
	boolean getRestrictiveTypeCheck();

}
