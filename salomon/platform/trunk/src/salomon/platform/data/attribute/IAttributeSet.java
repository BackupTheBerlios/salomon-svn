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
	void close() throws PlatformException;

	IAttribute[] getAttributes() throws PlatformException;

	IAttribute getAttribute(IAttributeDescription attributeDescription)
			throws PlatformException;

	IAttributeDescription[] getDesciptions() throws PlatformException;

	boolean next() throws PlatformException;
	
	void add(IAttribute[] attributes) throws PlatformException;

}
