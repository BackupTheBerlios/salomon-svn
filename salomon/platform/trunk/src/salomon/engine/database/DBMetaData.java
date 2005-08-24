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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 
 */
public final class DBMetaData
{
	private DBTable[] _tables;
	
	private DBManager _manager;

	public DBMetaData(DBManager manager)
	{
		_manager = manager;
	}
	
	/**
	 * Returns the tables.
	 * @return The tables
	 */
	public DBTable[] getTables()
	{
		return _tables;
	}
	
	void init() throws SQLException
	{
		// getting table names		
		String[] types = {"TABLE"};		
		ResultSet resultSet = null; 
		resultSet = _manager.getDatabaseMetaData().getTables(null, null, null, types);
		LinkedList<String> tables = new LinkedList<String>();		
		while (resultSet.next()) {						
			tables.add(resultSet.getString("table_name"));
		}
		resultSet.close();			
		
		_tables = new DBTable[tables.size()];
		
		// getting column for tables
		int i = 0;
		LinkedList<DBColumn> columns = new LinkedList<DBColumn>();
		for (String tableName : tables) {
			_tables[i] = new DBTable(tableName);
			columns.clear();
			resultSet = _manager.getDatabaseMetaData().getColumns(null, null, tableName, null);
			while (resultSet.next()) {
				String colName = resultSet.getString("column_name");
				String colType = resultSet.getString("type_name");
				DBColumn column = new DBColumn(colName, colType);
				columns.add(column);
			}			
			resultSet.close();
			
			DBColumn[] colArray = new DBColumn[columns.size()];
			colArray = columns.toArray(colArray);			
			_tables[i].setColumns(colArray);
			++i;
		}		
	}
	
}
