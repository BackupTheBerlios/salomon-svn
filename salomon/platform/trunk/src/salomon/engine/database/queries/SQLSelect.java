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

package salomon.engine.database.queries;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class is responsible for building SELECT query.
 */
public final class SQLSelect
{
	private Collection<String> _columns;

	private Collection<String> _conditions;

	private Collection<String> _tables;
    
	public SQLSelect()
	{
		_columns = new LinkedList<String>();
		_tables = new LinkedList<String>();
		_conditions = new LinkedList<String>();
	}

	public void addColumn(String columnName)
	{
		_columns.add(columnName);
	}

	/**
	 * Method adds join condition to query, e.g: p.person_id = u.user_id
	 * 
	 * @param joinCondition condition which joins two tables
	 */
	public void addCondition(String joinCondition)
	{
		_conditions.add(joinCondition);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, double value)
	{
		SQLHelper.addCondition(_conditions, condition, value);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, int value)
	{
		SQLHelper.addCondition(_conditions, condition, value);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, String value)
	{		
		SQLHelper.addCondition(_conditions, condition, value);
	}

	public void addTable(String tableName)
	{
		_tables.add(tableName);
	}


	/**
	 * Method returns SELECT query.
	 * 
	 * @return SELECT query
	 */
	public String getQuery()
	{
		StringBuffer query = new StringBuffer("SELECT "); //$NON-NLS-1$

		// adding column to select
		if (_columns.isEmpty()) {
			query.append("*"); //$NON-NLS-1$
		} else {
			Iterator<String> colIter = _columns.iterator();
			// first column is added without comma
			query.append(colIter.next());
			// rest of column - with comma
			while (colIter.hasNext()) {
				query.append(", ").append(colIter.next());
			}
		}
		query.append(" FROM "); //$NON-NLS-1$

		// adding tables
		Iterator<String> tabIter = _tables.iterator();
		query.append(tabIter.next());
		while (tabIter.hasNext()) {
			query.append(", ").append(tabIter.next());
		}

		// adding conditions
		if (!_conditions.isEmpty()) {
			query.append(" WHERE "); //$NON-NLS-1$
			Iterator<String> condIter = _conditions.iterator();
			query.append(condIter.next());
			while (condIter.hasNext()) {
				query.append(" AND ").append(condIter.next());
			}
		}
        
		return query.toString();
    }
    
    /**
	 * 
	 * @return Returns the tables.
	 */
	public Collection<String> getTables() 
    {
		return _tables;
    }
}