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

package pl.edu.agh.iisg.salomon.plugin.datasetunion;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.serialization.IObject;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

/**
 * 
 */
public final class UPlugin implements IPlugin
{


	/**
	 * 
	 */
	public UPlugin()
	{
		System.out.println("sssss");
	}

	/**
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.IDataEngine,
	 *      salomon.platform.IEnvironment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
		IDataSetManager dataSetManager = engine.getDataSetManager();
		USettings uSettings = (USettings) settings;
		UResult uResult = new UResult();
		IDataSet firstDataSet;
		try {
			// firstDataSet =
			// dataSetManager.getDataSet(uSettings.getFirstDataSet());
			// IDataSet secondDataSet =
			// dataSetManager.getDataSet(uSettings.getSecondDataSet());
			// IDataSet result = dataSetManager.union(firstDataSet,
			// secondDataSet);
			// result.setName(uSettings.getResultDataSet());
			// dataSetManager.add(result);
			// TODO: implement
			IObject resultObject = uSettings.getField(USettings.RESULT_DATA_SET);
			uResult.setResult(((SimpleString)resultObject).getValue());
			uResult.setSuccessfull(true);
		} catch (Exception e) {
			uResult.setSuccessfull(false);
		}
		return uResult;
	}


	/**
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
		UResultComponent result = new UResultComponent();
		return result;
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
		return new USettingComponent();
	}

	private static final String DESCRIPTION = "Creates dataset which is a union of two other datasets";
}