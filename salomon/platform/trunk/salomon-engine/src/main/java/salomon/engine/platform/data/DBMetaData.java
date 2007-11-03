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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.exception.DBException;

/**
 * 
 */
public final class DBMetaData implements IMetaData
{
    private static final Logger LOGGER = Logger.getLogger(DBMetaData.class);

    private DBManager _manager;

    private DBTable[] _tables;

    public DBMetaData(DBManager manager)
    {
        _manager = manager;
    }

    /**
     * Returns distinct values for given column.
     * 
     * FIXME: it's a workaround for attribute values choosing!
     * 
     * @param column
     * @return array of distinct values for the given column
     * @throws DBException 
     */
    public String[] getDistinctValues(IColumn column) throws DBException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(column.getTable().getName());
        select.addColumn("DISTINCT " + column.getName());
        ResultSet resultSet = null;
        String[] values = null;
        try {
            resultSet = _manager.select(select);

            LinkedList<Object> valuesList = new LinkedList<Object>();
            while (resultSet.next()) {
                valuesList.add(resultSet.getObject(1).toString());
            }

            // filling values array            
            values = valuesList.toArray(new String[valuesList.size()]);

        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        } finally {
            if (resultSet != null) {
                try {
                    _manager.closeResultSet(resultSet);
                } catch (SQLException e) {
                    LOGGER.fatal("", e);
                    throw new DBException(e.getLocalizedMessage());
                }
            }
        }
        return values;
    }

    /**
     * FIXME: temporary solution - for agents only
     * 
     * Returns rows count for specified table
     * 
     * @param table
     * @return
     */
    public int getCount(ITable table) throws DBException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(table.getName());
        select.addColumn("count(*)");

        ResultSet resultSet = null;
        int rowCount = 0;
        try {
            resultSet = _manager.select(select);
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        } finally {
            if (resultSet != null) {
                try {
                    _manager.closeResultSet(resultSet);
                } catch (SQLException e) {
                    LOGGER.fatal("", e);
                    throw new DBException(e.getLocalizedMessage());
                }
            }
        }
        return rowCount;
    }

    /**
     * Returns table basing on given name.
     * Method is case insensitive.
     *
     * @name table name
     * @return table object
     */
    public ITable getTable(String name)
    {
        ITable table = null;
        for (ITable t : _tables) {
            if (t.getName().equalsIgnoreCase(name)) {
                table = t;
                break;
            }
        }
        return table;
    }

    /**
     * Returns the tables.
     * @return The tables
     */
    public DBTable[] getTables()
    {
        return _tables;
    }

    public void init() throws SQLException
    {
        // getting table names		
        String[] types = {"TABLE"};
        ResultSet resultSet = null;
        resultSet = _manager.getDatabaseMetaData().getTables(null, null, null,
                types);
        LinkedList<String> tables = new LinkedList<String>();
        while (resultSet.next()) {
            tables.add(resultSet.getString("table_name"));
        }
        _manager.closeResultSet(resultSet);

        _tables = new DBTable[tables.size()];

        // getting column for tables
        int i = 0;
        LinkedList<DBColumn> columns = new LinkedList<DBColumn>();
        LinkedList<DBColumn> primaryKeys = new LinkedList<DBColumn>();
        for (String tableName : tables) {
            _tables[i] = new DBTable(tableName);
            columns.clear();
            primaryKeys.clear();

            // setting columns
            resultSet = _manager.getDatabaseMetaData().getColumns(null, null,
                    tableName, null);
            while (resultSet.next()) {
                String colName = resultSet.getString("column_name");
                String colType = resultSet.getString("type_name");
                DBColumn column = new DBColumn(_tables[i], colName, colType);
                columns.add(column);
            }
            _manager.closeResultSet(resultSet);

            DBColumn[] colArray = new DBColumn[columns.size()];
            colArray = columns.toArray(colArray);
            _tables[i].setColumns(colArray);

            // setting primary keys
            resultSet = _manager.getDatabaseMetaData().getPrimaryKeys(null,
                    null, tableName);
            while (resultSet.next()) {
                String colName = resultSet.getString("column_name");
                // searching for appropriate column
                for (DBColumn column : columns) {
                    if (column.getName().equals(colName)) {
                        primaryKeys.add(column);
                    }
                }
            }
            _manager.closeResultSet(resultSet);
            DBColumn[] keysArray = new DBColumn[primaryKeys.size()];
            keysArray = primaryKeys.toArray(keysArray);
            _tables[i].setPrimaryKeys(keysArray);

            ++i;
        }
    }
}
