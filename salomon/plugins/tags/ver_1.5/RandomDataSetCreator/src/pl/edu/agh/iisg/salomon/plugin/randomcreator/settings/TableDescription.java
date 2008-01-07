/*
 * Copyright (C) 2006 RandomDSCreator Team
 *
 * This file is part of RandomDSCreator.
 *
 * RandomDSCreator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * RandomDSCreator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with RandomDSCreator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.randomcreator.settings;

/**
 * 
 */
public final class TableDescription
{
    private int _rowCount;

    private String _tableName;

    /**
     * @param tableName
     * @param rowCount
     */
    public TableDescription(String tableName, int rowCount)
    {
        _tableName = tableName;
        _rowCount = rowCount;
    }

    public int getRowCount()
    {
        return _rowCount;
    }

    public String getTableName()
    {
        return _tableName;
    }
    
    @Override
    public String toString()
    {
        return _tableName + " [" + _rowCount + "]";
    }
}
