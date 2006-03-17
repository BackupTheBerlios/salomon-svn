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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

/**
 * Class is responsible for building SELECT query. It does not support multiple
 * select from the same table.
 * 
 */
public final class SQLSelect implements Cloneable
{
    private Collection<String> _columns;

    private Collection<String> _conditions;

    private Collection<String> _orderBy;

    private Collection<String> _tables;

    public SQLSelect()
    {
        _columns = new LinkedList<String>();
        _tables = new HashSet<String>();
        _conditions = new HashSet<String>();
        _orderBy = new LinkedList<String>();
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

    /**
     * Method adds column to ORDER BY statement.
     * 
     * @param orderColumn column to be added
     */
    public void addOrderBy(String orderColumn)
    {
        _orderBy.add(orderColumn);
    }

    /**
     * Method adds table to FROM section of select statement.
     * If table already exists in this section it is not added.
     * 
     * @param tableName
     */
    public void addTable(String tableName)
    {
        _tables.add(tableName);
    }

    /**
     * Method removes all conditions.
     * It prepares select to be called again, but with another conditions.
     * 
     */
    public void clearConditions()
    {
        _conditions.clear();
    }

    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone()
    {
        SQLSelect object = null;
        try {
            object = (SQLSelect) super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.fatal("", e);
        }
        // deep copy of collections
        object._columns = (Collection<String>) ((HashSet) object._columns).clone();
        object._tables = (Collection<String>) ((HashSet) object._tables).clone();
        object._conditions = (Collection<String>) ((HashSet) object._conditions).clone();
        return object;
    }

    /**
     * Method returns SELECT query.
     * 
     * @return SELECT query
     */
    public String getQuery()
    {
        StringBuilder query = new StringBuilder("SELECT "); //$NON-NLS-1$

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

        // adding ORDER BY
        if (!_orderBy.isEmpty()) {
            query.append(" ORDER BY "); //$NON-NLS-1$
            Iterator<String> orderIter = _orderBy.iterator();
            query.append(orderIter.next());
            while (orderIter.hasNext()) {
                query.append(", ").append(orderIter.next());
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

    private static final Logger LOGGER = Logger.getLogger(SQLSelect.class);
}