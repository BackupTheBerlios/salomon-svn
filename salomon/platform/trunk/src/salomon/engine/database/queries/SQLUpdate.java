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

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class is responsible for building UPDATE query.
 */
public final class SQLUpdate
{
	private List<String> _conditions;

	private String _tableName;

	private List<SQLPair> _values;    

	public SQLUpdate()
	{
		_values = new LinkedList<SQLPair>();
		_conditions = new LinkedList<String>();
	}

	/**
	 * 
	 * @param tableName table name
	 */
	public SQLUpdate(String tableName)
	{
		_tableName = tableName;
        _values = new LinkedList<SQLPair>();
        _conditions = new LinkedList<String>();
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
      
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Date value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }

	/**
	 * Adds value to be updated.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, double value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be updated.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, int value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be updated.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, String value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}
    
    
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Time value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }
    
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Timestamp value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }

	/**
	 * Method returns UPDATE query.
	 * 
	 * @return UPDATE query
	 */
	public String getQuery()
	{
		StringBuffer query = new StringBuffer("UPDATE ").append(_tableName).append(" SET ");

		Iterator<SQLPair> colIter = _values.iterator();
		// first column is added without comma
		SQLPair pair = colIter.next();
		query.append(pair.getColumnName()).append(" = ").append(pair.getValue());

		// rest of column - with comma
		while (colIter.hasNext()) {
			pair = colIter.next();
			query.append(", ").append(pair.getColumnName()).append(" = ").append(pair.getValue());
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
	 * Returns name of updated table.
	 * 
	 * @return Returns the tableName.
	 */
	public String getTableName()
	{
		return _tableName;
	}

	/**
	 * Sets table which values will be updated to
	 * 
	 * @param tableName table name
	 */
	public void setTable(String tableName)
	{
		_tableName = tableName;
	}	

	
	/**
	 * Makes SQLInsert object basing on update values.
	 * 
	 * @return object representing INSERT query
	 */
    public SQLInsert updateToInsert()
    {
    	SQLInsert insert = new SQLInsert(_tableName);
        insert.addAllValues(_values);
        
        return insert;
    }
	
}

