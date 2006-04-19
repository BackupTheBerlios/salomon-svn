/*
 * Copyright (C) 2006 Salomon Team
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

package pl.edu.agh.iisg.salomon.plugin.randomcreator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.randomcreator.result.RandomDSResult;
import pl.edu.agh.iisg.salomon.plugin.randomcreator.result.RandomDSResultComponent;
import pl.edu.agh.iisg.salomon.plugin.randomcreator.settings.RandomDSSettingComponent;
import pl.edu.agh.iisg.salomon.plugin.randomcreator.settings.RandomDSSettings;
import pl.edu.agh.iisg.salomon.plugin.randomcreator.settings.TableDescription;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public final class RandomDSCreatorPlugin implements IPlugin
{

    private static final Logger LOGGER = Logger.getLogger(RandomDSCreatorPlugin.class);

    private IResultComponent _resultComponent;

    private ISettingComponent _settingComponent;

    /**
     * 
     */
    public IResult doJob(IDataEngine dataEngine, IEnvironment env,
            ISettings settings)
    {
        RandomDSSettings rSettings = (RandomDSSettings) settings;

        TableDescription[] descriptions = rSettings.getTableDesctiptions();
        String dataSetName = rSettings.getDataSetName();

        IDataSetManager dataSetManager = dataEngine.getDataSetManager();
        IMetaData metaData = dataEngine.getMetaData();
        boolean isSuccessfull = false;

        try {
            // getting information about tables
            // that should be used as conditions
            List<ICondition> conditions = new LinkedList<ICondition>();
            LOGGER.info("Creating dataset: " + dataSetName);
            for (TableDescription description : descriptions) {
                String tableName = description.getTableName();
                LOGGER.debug("Table name: " + tableName);
                ITable table = metaData.getTable(tableName);

                //FIXME: getPrimaryKeys()
                IColumn[] primaryKeys = table.getPrimaryKeys();
                conditions.addAll(generateConditionsForTable(dataSetManager,
                        primaryKeys, description.getRowCount()));
            }

            // creating data set basing on generated conditions
            IDataSet mainDataSet = dataSetManager.getMainDataSet();
            ICondition[] condArray = new ICondition[conditions.size()];
            condArray = conditions.toArray(condArray);

            IDataSet subSet = mainDataSet.createSubset(condArray);
            subSet.setName(dataSetName);
            dataSetManager.add(subSet);
            LOGGER.info("Dataset created");

            isSuccessfull = true;
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }

        //setting result
        RandomDSResult result = new RandomDSResult();
        result.setSuccessful(isSuccessfull);
        String resultDataSetName = null;
        if (isSuccessfull) {
            resultDataSetName = dataSetName;
        } else {
            resultDataSetName = "ERROR";
        }
        result.setDataSetName(resultDataSetName);
        return result;
    }

    /**
     * 
     */
    public IResultComponent getResultComponent()
    {
        if (_resultComponent == null) {
            _resultComponent = new RandomDSResultComponent();
        }
        return _resultComponent;
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        if (_settingComponent == null) {
            _settingComponent = new RandomDSSettingComponent(platformUtil);
        }
        return _settingComponent;
    }

    private Collection<ICondition> generateConditionsForTable(
            IDataSetManager dataManager, IColumn[] primaryKeys, int rowCount)
            throws PlatformException
    {
        
        List<ICondition> conditions = new LinkedList<ICondition>();

        // getting all data with from primary key columns
        IData data = dataManager.getMainDataSet().selectData(primaryKeys, null);
        List<Object> primaryKeyValues = new LinkedList<Object>();
        while (data.next()) {
            // TODO: add support for multicolumn primary keys
            // supports only tables with singlecolumn primary keys
            Object[] primaryColumn = data.getData();
            LOGGER.debug(primaryKeys[0] + " = " + primaryColumn[0]);
            primaryKeyValues.add(primaryColumn[0]);
        }
        data.close();
        
        // shuffling result list
        Collections.shuffle(primaryKeyValues, new Random(System.currentTimeMillis()));        

        // getting first rowCount elements from the list of values
        // and generating appropriate conditions
        int i = 0;
        for (Iterator iterator = primaryKeyValues.iterator(); iterator.hasNext() && i < rowCount; ++i) {
            ICondition condition = dataManager.createEqualsCondition(primaryKeys[0], iterator.next());
            LOGGER.debug("Adding condition: " + condition);
            conditions.add(condition);
        }

        return conditions;
    }
}
