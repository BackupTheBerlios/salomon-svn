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

    private DBTable _table;

    private String _type;

    /**
     * @param name
     * @param type
     */
    public DBColumn(DBTable table, String name, String type)
    {
        _table = table;
        _name = name;
        _type = type;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof DBColumn) {
            DBColumn column = (DBColumn) obj;
            result = _name.equalsIgnoreCase(column._name)
                    && _type.equalsIgnoreCase(column._type)
                    && _table.equals(column._table);
        }

        return result;
    }

    /**
     * Returns the name.
     * @return The name
     */
    public String getName()
    {
        return _name;
    }

    public DBTable getTable()
    {
        return _table;
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
    public int hashCode()
    {
        return _name.hashCode() ^ _type.hashCode() ^ _table.hashCode();
    }

    @Override
    public String toString()
    {
        return _name + " (" + _type + ")";
    }
}
