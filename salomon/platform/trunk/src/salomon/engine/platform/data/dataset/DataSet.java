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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.dataset.condition.AbstractCondition;
import salomon.engine.platform.data.dataset.condition.OrCondition;
import salomon.platform.IInfo;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Class represents data set. Data set is a subset of data stored in tables, its
 * conditions specifies how to get this subset.
 * 
 * TODO: add join between tables support
 */
public class DataSet implements IDataSet
{
    private static final Logger LOGGER = Logger.getLogger(DataSet.class);

    private DataSetManager _dataSetManager;

    private ExternalDBManager _externalDBManager;

    private DataSetInfo _info;

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

    /**
     * @see salomon.platform.data.dataset.IDataSet#createSubset(salomon.platform.data.dataset.ICondition[])
     */
    public IDataSet createSubset(ICondition[] conditions)
            throws PlatformException
    {
        DataSet dataSet = (DataSet) _dataSetManager.getMainDataSet();
        dataSet._info.setConditions(conditions);
        return dataSet;
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#getConditions()
     */
    public ICondition[] getConditions()
    {
        return (_info == null ? null : _info.getConditions());
    }

    public DataSetInfo getInfo()
    {
        return _info;
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#getName()
     */
    public String getName() throws PlatformException
    {
        return (_info == null ? null : _info.getName());
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#intersection(salomon.platform.data.dataset.IDataSet)
     */
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

    /**
     * @see salomon.platform.data.dataset.IDataSet#intersection(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet intersection(IDataSet dataSet, int id)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method intersection() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#minus(salomon.platform.data.dataset.IDataSet)
     */
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

    /**
     * @see salomon.platform.data.dataset.IDataSet#minus(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet minus(IDataSet dataSet, int id) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method minus() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#selectData(salomon.platform.data.IColumn[], salomon.platform.data.dataset.ICondition[])
     */
    public IData selectData(IColumn[] columns, ICondition[] conditions)
            throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        // adding columns to be selected
        if (columns != null) {
            for (IColumn column : columns) {
                select.addColumn(column.getName());
                // adding table regarding this column name
                select.addTable(column.getTable().getName());
            }
        }

        // setting data set conditions
        AbstractCondition[] dataSetConditions = _info.getConditions();

        List<ICondition> conditionList = new LinkedList<ICondition>();
        for (AbstractCondition condtion : dataSetConditions) {
            conditionList.add(condtion);
        }

        // adding additional conditions
        if (conditions != null) {
            for (ICondition condition : conditions) {
                conditionList.add(condition);
            }
        }

        AbstractCondition selectCondition = null;
        // FIXME:
        // workaround - dataset supports only OR conditions
        if (conditionList.size() > 0) {
            if (conditionList.size() > 1) {
                Iterator iter = conditionList.iterator();
                AbstractCondition firstCondition = (AbstractCondition) iter.next();

                AbstractCondition[] subset = new AbstractCondition[conditionList.size() - 1];
                int i = 0;
                while (iter.hasNext()) {
                    AbstractCondition condition = (AbstractCondition) iter.next();
                    subset[i] = condition;
                    // adding table (tables are kept is set
                    // so it doesnt have to be checked if table exists in query
                    select.addTable(((AbstractCondition) condition).getColumn().getTable().getName());
                    ++i;
                }

                selectCondition = new OrCondition(firstCondition, subset);
            } else {
                selectCondition = (AbstractCondition) conditionList.iterator().next();
                select.addTable(selectCondition.getColumn().getTable().getName());
            }
            // adding conditions to the query
            select.addCondition(selectCondition.toSQL());
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

    /**
     * @see salomon.platform.data.dataset.IDataSet#setName(java.lang.String)
     */
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
    @Override
    public String toString()
    {
        return _info.toString();
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#union(salomon.platform.data.dataset.IDataSet)
     */
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

    /**
     * @see salomon.platform.data.dataset.IDataSet#union(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet union(IDataSet dataSet, int id) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method union() not implemented yet!");
    }
}