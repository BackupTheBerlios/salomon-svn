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

import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 *  
 */
public final class UPlugin implements IPlugin
{
	private Description _description;

	/**
	 *  
	 */
	public UPlugin()
	{
		_description = new Description("UPlugin", DESCRIPTION);
	}

	/**
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		// not used
	}

	/**
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.platform.IDataEngine, salomon.platform.IEnvironment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
		IDataSetManager dataSetManager = engine.getDataSetManager();
		USettings uSettings = (USettings) settings;
		UResult uResult = new UResult();
		IDataSet firstDataSet;
		try {
//			firstDataSet = dataSetManager.getDataSet(uSettings.getFirstDataSet());
//			IDataSet secondDataSet = dataSetManager.getDataSet(uSettings.getSecondDataSet());
//			IDataSet result = dataSetManager.union(firstDataSet, secondDataSet);
//			result.setName(uSettings.getResultDataSet());
//			dataSetManager.add(result);
			//TODO: implement
            
            uResult.setSuccessfull(true);
		} catch (Exception e) {
			uResult.setSuccessfull(false);
		}
		return uResult;
	}

	/**
	 * @see salomon.plugin.IPlugin#getDescription()
	 */
	public Description getDescription()
	{

		return _description;
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

	/**
	 * @see salomon.plugin.IPlugin#initizalize()
	 */
	public void initizalize()
	{
	}

	private static final String DESCRIPTION = "Creates dataset which is a union of two other datasets";

	/**
	 * @see salomon.plugin.IPlugin#setDescription(salomon.plugin.Description)
	 */
	public void setDescription(Description description)
	{
		_description = description;
		
	}

}