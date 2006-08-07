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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Class helps converting conditions and values to form accepted by data base.
 *  
 */
final class SQLHelper
{

    public static void addCondition(Collection<String> conditions,
            String condition, double value)
    {
        addConditionImpl(conditions, condition, String.valueOf(value));
    }

    public static void addCondition(Collection<String> conditions,
            String condition, int value)
    {
        addConditionImpl(conditions, condition, String.valueOf(value));
    }

    public static void addCondition(Collection<String> conditions,
            String condition, String value)
    {
        addConditionImpl(conditions, condition, "'"
                + value.replaceAll("'", "''") + "'");
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            double value)
    {
        addValueImpl(values, columnName, String.valueOf(value));
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            int value)
    {
        addValueImpl(values, columnName, String.valueOf(value));
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            Date value)
    {
        addValue(values, columnName, DATE_FORMAT.format(value).toString());
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            Timestamp value)
    {
        addValue(values, columnName, TIMESTAMP_FORMAT.format(value).toString());
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            Time value)
    {
        addValue(values, columnName, TIME_FORMAT.format(value).toString());
    }

    public static void addValue(Collection<SQLPair> values, String columnName,
            String value)
    {
        if (value != null) {
            addValueImpl(values, columnName, "'" + value.replaceAll("'", "''")
                    + "'");
        } else {
            addValueImpl(values, columnName, "''");
        }
    }

    private static void addConditionImpl(Collection<String> conditions,
            String condition, String value)
    {
        conditions.add(condition + " " + value);
    }

    private static void addValueImpl(Collection<SQLPair> values,
            String columnName, String value)
    {
        values.add(new SQLPair(columnName, value));
    }

    private final static DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    private final static DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss:SS");

    private final static DateFormat TIME_FORMAT = new SimpleDateFormat(
            "HH:mm:ss:SS");

}