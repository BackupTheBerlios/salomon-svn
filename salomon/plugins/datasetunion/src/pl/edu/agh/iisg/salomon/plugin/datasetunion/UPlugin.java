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

import pl.edu.agh.iisg.salomon.plugin.datasetunion.result.UResult;
import pl.edu.agh.iisg.salomon.plugin.datasetunion.result.UResultComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetunion.settings.USettingComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetunion.settings.USettings;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.plugin.*;
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
		
		try {
             
             String first  = ((SimpleString)uSettings.getField(USettings.FIRST_DATA_SET)).getValue() ;
             String second = ((SimpleString)uSettings.getField(USettings.SECOND_DATA_SET)).getValue();
             IDataSet firstDataSet = null ;
             IDataSet secondDataSet = null ; 
             boolean foundFirst = false ;
             boolean foundSecond = false ;
             
             IDataSet[] datasets =  dataSetManager.getAll() ;
            
             for (int i = 0; i < datasets.length; i++) {
                if (datasets[i].getName().equals(first)) {
                 
                    firstDataSet = datasets[i] ;
                    foundFirst = true; 
                }
                if (datasets[i].getName().equals(second)) {
                    secondDataSet = datasets[i] ;
                    foundSecond = true; 
                }
                if (foundFirst && foundSecond) {
                    break ;
                }
             }
             
             if (!(foundFirst && foundSecond)) {
                 uResult.setField(UResult.DATA_SET_NAME, new SimpleString("ERROR"));
                 uResult.setField(UResult.ERROR_MESSAGE, new SimpleString("Failed to find one of DataSets!!: " + first + " " + second));
                 uResult.setSuccessful(false);
                 return uResult;
             }
      
             IDataSet result = firstDataSet.union(secondDataSet) ;
             result.setName(((SimpleString)uSettings.getField(USettings.RESULT_DATA_SET)).getValue()) ;
             dataSetManager.add(result) ;
            
            uResult.setField(UResult.DATA_SET_NAME, new SimpleString(result.getName()));
            uResult.setSuccessful(true) ;
            return uResult ;
		} catch (Exception e) {
            uResult.setField(UResult.DATA_SET_NAME, new SimpleString("ERROR"));
            uResult.setField(UResult.ERROR_MESSAGE, new SimpleString(e.getClass().getName() + ":"+e.getMessage()));
            e.printStackTrace() ;
			uResult.setSuccessful(false);
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
}