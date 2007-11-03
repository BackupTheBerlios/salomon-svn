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

package salomon.engine.platform.data.dataset.condition;

import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;
import salomon.platform.data.IColumn;

abstract class AbstractOperatorCondition extends AbstractCondition
{

    private Object _value;

    protected AbstractOperatorCondition(IColumn column, Object value)
    {
        super(column);
        _value = value;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof AbstractOperatorCondition) {
            AbstractOperatorCondition condition = (AbstractOperatorCondition) obj;
            result = _value.equals(condition._value)
                    && getColumn().equals(condition.getColumn())
                    && getOperator().equals(condition.getOperator());
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        return getColumn().hashCode() ^ _value.hashCode()
                ^ getOperator().hashCode();
    }

    /**
     * @see salomon.engine.platform.data.dataset.condition.AbstractCondition#toSQL()
     */
    @Override
    public String toSQL()
    {
        DBColumn column = (DBColumn) getColumn();
        String columnName = column.getName();
        DBTable table = column.getTable();
        String tableName = table.getName();
        String value = getValueString();
        StringBuilder result = new StringBuilder();
        String operator = getOperator();
        result.append(tableName).append('.').append(columnName);
        result.append(" ").append(operator).append(" ").append(value);

        return result.toString();
    }

    /**
     * Returns the value.
     * @return The value
     */
    final Object getValue()
    {
        return _value;
    }

    @Override
    protected abstract String getOperator();

    protected final String getValueString()
    {
        //TODO:
        String result = null;
        if (_value instanceof String) {
            result = '\'' + (String) _value + '\'';
        } else {
            result = _value.toString();
        }

        return result;
    }
}
