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

package salomon.engine.platform.data.attribute;

import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.description.IAttributeDescription;

/**
 * Not used yet.
 */
public class Attribute implements IAttribute
{
	private IAttributeDescription _description;
	private Object _value;
	
	protected Attribute(IAttributeDescription description, Object value){
		_description=description;
		_value=value;
	}
	
	/* (non-Javadoc)
	 * @see salomon.platform.data.attribute.IAttribute#getDescription()
	 */
	public IAttributeDescription getDescription() {
		return _description;
	}

	/* (non-Javadoc)
	 * @see salomon.platform.data.attribute.IAttribute#getValue()
	 */
	public Object getValue() {
		return _value;
	}

} // end Attribute
