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

import org.apache.log4j.Logger;

import pl.edu.agh.iisg.salomon.plugin.randomcreator.result.RandomDSResultComponent;
import pl.edu.agh.iisg.salomon.plugin.randomcreator.settings.RandomDSSettingComponent;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
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
        //		RandomDSSettings cSettings = (RandomDSSettings) settings;
        //
        //		SimpleString dataSetName = (SimpleString) cSettings.getField(RandomDSSettings.DATA_SET_NAME);
        //		// FIXME:
        //		// SimpleArray condArray = (SimpleArray)
        //		// cSettings.getField(CreatorSettings.CONDITIONS);
        //
        //		SimpleStruct condStruct = (SimpleStruct) cSettings.getField(RandomDSSettings.CONDITIONS);
        //
        //		SimpleString elem1 = (SimpleString) condStruct.getField(RandomDSSettings.ELEM1);
        //		SimpleString elem2 = (SimpleString) condStruct.getField(RandomDSSettings.ELEM2);
        //		SimpleString elem3 = (SimpleString) condStruct.getField(RandomDSSettings.ELEM3);
        //		SimpleString elem4 = (SimpleString) condStruct.getField(RandomDSSettings.ELEM4);
        //		SimpleString elem5 = (SimpleString) condStruct.getField(RandomDSSettings.ELEM5);
        //
        //		IDataSetManager dataSetManager = dataEngine.getDataSetManager();
        //
        //		boolean isSuccessfull = false;
        //
        //		try {
        //			ICondition[] conditions = new ICondition[5];
        //			int i = 0;
        //			IObject[] values = new IObject[]{elem1, elem2, elem3, elem4, elem5};
        //			for (IObject object : values) {
        //				if (object != null) {
        //					String value = ((SimpleString) object).getValue();
        //
        //					conditions[i] = dataSetManager.createCondition(value);
        //
        //					++i;
        //				}
        //			}
        //
        //			IDataSet mainDataSet = dataSetManager.getMainDataSet();
        //			IDataSet subSet = mainDataSet.createSubset(conditions);
        //			subSet.setName(dataSetName.getValue());
        //			dataSetManager.add(subSet);
        //			
        //			isSuccessfull = true;
        //		} catch (PlatformException e) {
        //			LOGGER.fatal("", e);
        //		}
        //
        //		RandomDSResult result = new RandomDSResult();
        //		result.setSuccessful(isSuccessfull);
        //		String resultDataSetName = null;
        //		if (isSuccessfull) {
        //			resultDataSetName = dataSetName.getValue();
        //		} else {
        //			resultDataSetName = "ERROR";
        //		}
        //		result.setField(RandomDSResult.DATA_SET_NAME, new SimpleString(resultDataSetName));
        //		return result;
        return null;
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


    // FIXME:
    public ISettingComponent getSettingComponent(IPlatformUtil platformUtil)
    {
        if (_settingComponent == null) {
            _settingComponent = new RandomDSSettingComponent();
        }
        return _settingComponent;
    }
}
