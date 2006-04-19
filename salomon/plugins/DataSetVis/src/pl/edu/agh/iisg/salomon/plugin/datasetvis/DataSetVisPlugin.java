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

package pl.edu.agh.iisg.salomon.plugin.datasetvis;

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.datasetvis.result.VisResult;
import pl.edu.agh.iisg.salomon.plugin.datasetvis.result.VisResultComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetvis.settings.VisSettingComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetvis.settings.VisSettings;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

/**
 * @author nico
 */
public class DataSetVisPlugin implements IPlugin
{

    private static final Logger LOGGER = Logger.getLogger(DataSetVisPlugin.class);

    private IResultComponent _resultComponent;

    private ISettingComponent _settingComponent;

    /**
     * 
     */
    public IResult doJob(IDataEngine engine, IEnvironment env,
            ISettings settings)
    {
        LOGGER.info("DataSetVisPlugin.doJob()");
        VisResult result = new VisResult();
        boolean isSuccessful = false;
        // simply rewrite data set name from settings
        String dataSetName = ((VisSettings) settings).getDataSetName();
        LOGGER.debug("dataSetName: " + dataSetName);
        try {
            IDataSet dataSet = engine.getDataSetManager().getDataSet(
                    dataSetName);
            if (dataSet != null) {
                result.setDataSetName(dataSetName);
                isSuccessful = true;
            }
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            result.setDataSetName("ERROR");
        }
        result.setSuccessful(isSuccessful);
        return result;
    }

    /**
     * 
     */
    public IResultComponent getResultComponent()
    {
        if (_resultComponent == null) {
            _resultComponent = new VisResultComponent();
        }
        return _resultComponent;
    }

    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        if (_settingComponent == null) {
            _settingComponent = new VisSettingComponent();
        }
        return _settingComponent;
    }
}
