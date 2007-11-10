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
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.domain.ShortSolutionInfo;

import salomon.engine.platform.data.dataset.condition.AbstractCondition;
import salomon.engine.platform.data.dataset.condition.AndCondition;
import salomon.engine.platform.data.dataset.condition.ConditionParser;
import salomon.engine.platform.data.dataset.condition.EqualsCondition;
import salomon.engine.platform.data.dataset.condition.GreaterCondition;
import salomon.engine.platform.data.dataset.condition.LowerCondition;
import salomon.engine.platform.data.dataset.condition.OrCondition;
import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Class manages with datasets.
 */
public final class DataSetManager implements IDataSetManager
{
    private DBManager _dbManager;

    private ExternalDBManager _externalDBManager;

    private ShortSolutionInfo _solutionInfo;

    public DataSetManager(DBManager dbManager, ShortSolutionInfo solutionInfo,
            ExternalDBManager externalDBManager)
    {
        _dbManager = dbManager;
        _externalDBManager = externalDBManager;
        _solutionInfo = solutionInfo;
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#add(salomon.platform.data.dataset.IDataSet)
     */
    public void add(IDataSet dataSet) throws PlatformException
    {
        try {
            ((DataSet) dataSet).getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new DBException(e);
        }
    }

    public ICondition createAndCondition(ICondition condition,
            ICondition... conditions) throws PlatformException
    {
        return new AndCondition((AbstractCondition) condition,
                (AbstractCondition[]) conditions);
    }

    public ICondition createCondition(String stringCondition)
            throws PlatformException
    {
        ICondition condition = null;
        try {
            condition = ConditionParser.parse(_externalDBManager.getMetaData(),
                    stringCondition);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        }
        return condition;
    }

    public ICondition createEqualsCondition(IColumn column, Object value)
            throws PlatformException
    {
        return new EqualsCondition(column, value);
    }

    public ICondition createGreaterCondition(IColumn column, Object value)
            throws PlatformException
    {
        return new GreaterCondition(column, value);
    }

    public ICondition createLowerCondition(IColumn column, Object value)
            throws PlatformException
    {
        return new LowerCondition(column, value);
    }

    public ICondition createOrCondition(ICondition condition,
            ICondition... conditions) throws PlatformException
    {
        return new OrCondition((AbstractCondition) condition,
                (AbstractCondition[]) conditions);
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#getAll()
     */
    public IDataSet[] getAll() throws PlatformException
    {
        List<IDataSet> dataSets = new LinkedList<IDataSet>();

        SQLSelect select = new SQLSelect();
        select.addTable(DataSetInfo.TABLE_NAME + " d");
        select.addTable(DataSetInfo.ITEMS_TABLE_NAME + " di");
        select.addColumn("d.dataset_id");
        select.addColumn("d.dataset_name");
        select.addColumn("d.info");
        select.addColumn("di.table_name");
        select.addColumn("di.condition");
        select.addCondition("d.dataset_id = di.dataset_id");
        select.addCondition("d.solution_id = ", _solutionInfo.getId());
        select.addOrderBy("d.dataset_id");

        ResultSet resultSet;
        int dataSetID = -1;
        try {
            resultSet = _dbManager.select(select);
            IDataSet dataSet = null;
            while (resultSet.next()) {
                int tmpDataSetID = resultSet.getInt("dataset_id");
                // when dataset_id changes, creating new DataSet object
                if (tmpDataSetID != dataSetID) {
                    dataSetID = tmpDataSetID;
                    dataSet = this.getMainDataSet();
                    ((DataSet) dataSet).getInfo().load(resultSet);
                    dataSets.add(dataSet);
                }
                // loading items
                ((DataSetInfo) ((DataSet) dataSet).getInfo()).loadItems(resultSet);
            }
            _dbManager.closeResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        }
        LOGGER.info("DataSet list successfully loaded.");
        IDataSet[] dataSetsArray = new IDataSet[dataSets.size()];
        return dataSets.toArray(dataSetsArray);
    }

    /**
     * Returns DataSet object basing on given id.
     * 
     */
    public IDataSet getDataSet(int id) throws PlatformException
    {
        IDataSet dataSet = this.getMainDataSet();
        // selecting DataSet header
        SQLSelect dataSetSelect = new SQLSelect();
        dataSetSelect.addTable(DataSetInfo.TABLE_NAME);
        dataSetSelect.addCondition("dataset_id =", id);
        // to ensure solution_id consistency
        dataSetSelect.addCondition("solution_id =",
                ((DataSetInfo) ((DataSet) dataSet).getInfo()).getSolutionID());

        return getDataSet(dataSet, dataSetSelect);
    }

    public IDataSet getDataSet(String name) throws PlatformException
    {
        IDataSet dataSet = this.getMainDataSet();
        // selecting DataSet header
        SQLSelect dataSetSelect = new SQLSelect();
        dataSetSelect.addTable(DataSetInfo.TABLE_NAME);
        dataSetSelect.addCondition("dataset_name =", name);
        // to ensure solution_id consistency
        dataSetSelect.addCondition("solution_id =",
                ((DataSetInfo) ((DataSet) dataSet).getInfo()).getSolutionID());

        return getDataSet(dataSet, dataSetSelect);
    }

    public IDataSet getMainDataSet() throws PlatformException
    {
        IDataSet dataSet = new DataSet(this, _dbManager, _externalDBManager);
        ((DataSetInfo) ((DataSet) dataSet).getInfo()).setSolutionID(_solutionInfo.getId());
        return dataSet;
    }

    public void remove(IDataSet dataSet) throws PlatformException
    {
        try {
            ((DataSet) dataSet).getInfo().delete();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
        }
    }

    private IDataSet getDataSet(IDataSet dataSet, SQLSelect dataSetSelect)
            throws DBException
    {
        ResultSet resultSet = null;
        try {
            DataSetInfo dataSetInfo = (DataSetInfo) ((DataSet) dataSet).getInfo();

            resultSet = _dbManager.select(dataSetSelect);
            if (!resultSet.next()) {
                LOGGER.warn("DataSet doesn't exist");
                return null;
            }
            // loading data set header
            dataSetInfo.load(resultSet);

            // one row should be found, if found more, the first is got
            if (resultSet.next()) {
                LOGGER.warn("TOO MANY ROWS");
            }
            _dbManager.closeResultSet(resultSet);

            // loading items
            SQLSelect dataSetItemsSelect = new SQLSelect();
            dataSetItemsSelect.addTable(DataSetInfo.ITEMS_TABLE_NAME);
            dataSetItemsSelect.addColumn("dataset_id");
            dataSetItemsSelect.addColumn("table_name");
            dataSetItemsSelect.addColumn("condition");
            dataSetItemsSelect.addCondition("dataset_id =",
                    ((DataSet) dataSet).getInfo().getId());

            resultSet = _dbManager.select(dataSetItemsSelect);
            while (resultSet.next()) {
                dataSetInfo.loadItems(resultSet);
            }
            _dbManager.closeResultSet(resultSet);

        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        LOGGER.info("DataSet successfully loaded.");

        return dataSet;
    }

    private static final Logger LOGGER = Logger.getLogger(DataSetManager.class);

}