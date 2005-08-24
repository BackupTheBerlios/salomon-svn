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

package salomon.engine.database;

/**
 * 
 */
public final class DBTable
{
	private DBColumn[] _columns;

	private String _name;

	public DBTable(String name)
	{
		_name = name;
	}

	/**
	 * Returns column of given name.
	 * 
	 * @param name column name
	 * @return
	 */
	public DBColumn getColumn(String name)
	{
		DBColumn column = null;
		for (DBColumn col : _columns) {
			if (col.getName().equalsIgnoreCase(name)) {
				column = col;
				break;
			}
		}
		return column;
	}

	/**
	 * Returns the columns.
	 * 
	 * @return The columns
	 */
	public DBColumn[] getColumns()
	{
		return _columns;
	}

	/**
	 * Returns the name.
	 * @return The name
	 */
	public String getName()
	{
		return _name;
	}

	@Override
	public String toString()
	{
		return _name;
	}

	/**
	 * Set the value of columns field.
	 * @param columns The columns to set
	 */
	void setColumns(DBColumn[] columns)
	{
		_columns = columns;
	}

}
