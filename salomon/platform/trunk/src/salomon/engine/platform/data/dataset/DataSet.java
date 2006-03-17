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

package salomon.engine.platform.data.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;

import salomon.platform.IInfo;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.data.dataset.condition.AbstractCondition;

/**
 * Class represents data set. Data set is a subset of data stored in tables, its
 * conditions specifies how to get this subset.
 * 
 * TODO: add join between tables support
 */
public class DataSet implements IDataSet
{
    DataSetManager _dataSetManager;

    private ExternalDBManager _externalDBManager;

    private DataSetInfo _info;

    private String name;

    /**
     * Creates data set. This constructor can be used only from DataSetManager.
     */
    protected DataSet(DataSetManager dataSetManager, DBManager manager,
            ExternalDBManager externalDBManager)
    {
        _dataSetManager = dataSetManager;
        _externalDBManager = externalDBManager;
        _info = new DataSetInfo(manager, externalDBManager);
    }

    public IDataSet createSubset(ICondition[] conditions)
            throws PlatformException
    {
        DataSet dataSet = (DataSet) _dataSetManager.getMainDataSet();
        dataSet._info.setConditions(conditions);
        return dataSet;
    }

    public ICondition[] getConditions()
    {
        return _info.getConditions();
    }

    public IInfo getInfo() throws PlatformException
    {
        return _info;
    }

    public String getName() throws PlatformException
    {
        return (_info == null ? null : _info.getName());
    }

    public IDataSet intersection(IDataSet dataSet) throws PlatformException
    {
        DataSet newDataSet = (DataSet) _dataSetManager.getMainDataSet();
        Set<AbstractCondition> currentConditons = _info.getConditionSet();
        Set<AbstractCondition> newConditons = ((DataSet) dataSet)._info.getConditionSet();

        Set<AbstractCondition> conditions = new HashSet<AbstractCondition>();
        conditions.addAll(currentConditons);
        conditions.retainAll(newConditons);
        newDataSet._info.setConditions(conditions);
        return newDataSet;
    }

    public IDataSet intersection(IDataSet dataSet, int id)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method intersection() not implemented yet!");
    }

    public IDataSet minus(IDataSet dataSet) throws PlatformException
    {
        DataSet newDataSet = (DataSet) _dataSetManager.getMainDataSet();
        Set<AbstractCondition> currentConditons = _info.getConditionSet();
        Set<AbstractCondition> newConditons = ((DataSet) dataSet)._info.getConditionSet();

        Set<AbstractCondition> conditions = new HashSet<AbstractCondition>();
        conditions.addAll(currentConditons);
        conditions.removeAll(newConditons);
        newDataSet._info.setConditions(conditions);
        return newDataSet;
    }

    public IDataSet minus(IDataSet dataSet, int id) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method minus() not implemented yet!");
    }

    public IData selectData(IColumn[] columns, ICondition[] conditions)
            throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        // adding columns to be selected
        if (columns != null) {
            for (IColumn column : columns) {
                select.addColumn(column.getName());
            }
        }

        // setting data set conditions
        AbstractCondition[] dataSetConditions = _info.getConditions();
        for (AbstractCondition condition : dataSetConditions) {
            select.addCondition(condition.toSQL());

            // adding table (tables are kept is set
            // so it doesnt have to be checked if table exists in query
            select.addTable(condition.getColumn().getTable().getName());
        }

        // adding additional conditions
        if (conditions != null) {
            for (ICondition condition : conditions) {
                select.addCondition(((AbstractCondition) condition).toSQL());
                // adding table
                select.addTable(((AbstractCondition) condition).getColumn().getTable().getName());
            }
        }

        // executing query
        ResultSet resultSet = null;
        try {
            resultSet = _externalDBManager.select(select);
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return new Data(resultSet);
    }

    public void setName(String name) throws PlatformException
    {
        if (_info != null) {
            _info.setName(name);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return _info.toString();
    }

    public IDataSet union(IDataSet dataSet) throws PlatformException
    {
        DataSet newDataSet = (DataSet) _dataSetManager.getMainDataSet();
        Set<AbstractCondition> currentConditons = _info.getConditionSet();
        Set<AbstractCondition> newConditons = ((DataSet) dataSet)._info.getConditionSet();

        Set<AbstractCondition> conditions = new HashSet<AbstractCondition>();
        conditions.addAll(currentConditons);
        conditions.addAll(newConditons);
        newDataSet._info.setConditions(conditions);
        return newDataSet;
    }

    public IDataSet union(IDataSet dataSet, int id) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method union() not implemented yet!");
    }

    private static final Logger LOGGER = Logger.getLogger(DataSet.class);
}