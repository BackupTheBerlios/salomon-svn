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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.datasetcreator.result.CreatorResult;
import pl.edu.agh.iisg.salomon.plugin.datasetcreator.result.CreatorResultComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetcreator.settings.CreatorSettingComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetcreator.settings.CreatorSettings;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public final class DataSetCreatorPlugin implements IPlugin
{

    private ISettingComponent _settingComponent;

    private IResultComponent _resultComponent;

    /**
     * 
     */
    public IResult doJob(IDataEngine dataEngine, IEnvironment env,
            ISettings settings)
    {
        CreatorSettings cSettings = (CreatorSettings) settings;
        String dataSetName = cSettings.getDataSetName();
        IDataSetManager dataSetManager = dataEngine.getDataSetManager();

        boolean isSuccessfull = false;

        try {
            String[] strConds = cSettings.getConditions();
            ICondition[] conditions = new ICondition[strConds.length];
            int i = 0;
            for (String cond : strConds) {
                if (cond != null) {
                    conditions[i] = dataSetManager.createCondition(cond);
                    ++i;
                }
            }

            IDataSet mainDataSet = dataSetManager.getMainDataSet();
            IDataSet subSet = mainDataSet.createSubset(conditions);
            subSet.setName(dataSetName);
            dataSetManager.add(subSet);

            isSuccessfull = true;
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }

        CreatorResult result = new CreatorResult();
        result.setSuccessful(isSuccessfull);
        String resultDataSetName = null;
        if (isSuccessfull) {
            resultDataSetName = dataSetName;
        } else {
            resultDataSetName = "ERROR";
        }
        result.setDataSetName(dataSetName);
        return result;
    }

    /**
     * 
     */
    public ISettingComponent getSettingComponent()
    {
        if (_settingComponent == null) {
            _settingComponent = new CreatorSettingComponent();
        }
        return _settingComponent;
    }

    /**
     * 
     */
    public IResultComponent getResultComponent()
    {
        if (_resultComponent == null) {
            _resultComponent = new CreatorResultComponent();
        }
        return _resultComponent;
    }

    private static final Logger LOGGER = Logger.getLogger(DataSetCreatorPlugin.class);
}
