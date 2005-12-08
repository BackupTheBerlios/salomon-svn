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

package salomon.engine.platform.data;

import salomon.platform.data.IColumn;

/**
 * 
 */
public final class DBColumn implements IColumn
{
	private String _name;

	private String _type;

	/**
	 * @param name
	 * @param type
	 */
	public DBColumn(String name, String type)
	{
		_name = name;
		_type = type;
	}

	/**
	 * Returns the name.
	 * @return The name
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * Returns the type.
	 * @return The type
	 */
	public String getType()
	{
		return _type;
	}

	@Override
	public String toString()
	{
		return _name + " (" + _type + ")";
	}

	DBTable getTable()
	{
		throw new UnsupportedOperationException(
				"Method DBColumn.getTable() not implemented yet!");;
	}
}
