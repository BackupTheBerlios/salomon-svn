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

package salomon.engine.platform;

import salomon.platform.IVariable;
import salomon.platform.serialization.IObject;

/**
 * Realization of IVariable interface.
 * 
 */
final class Variable implements IVariable {
	private String _name;

	private IObject _value;

	public Variable(String name) {
		_name = name;
	}

	/**
	 * Returns the name of this variable.
	 * 
	 * @return the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Returns current value of this variable.
	 * 
	 * @return the value
	 */
	public IObject getValue() {
		return _value;
	}

	/**
	 * Sets the value of this variable.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(IObject value) {
		_value = value;
	}

	@Override
	public String toString() {
		return _name + "=" + _value;
	}

}
