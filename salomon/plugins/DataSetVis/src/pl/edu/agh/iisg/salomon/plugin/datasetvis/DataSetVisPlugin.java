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

import pl.edu.agh.iisg.salomon.plugin.datasetvis.result.VisResultComponent;
import pl.edu.agh.iisg.salomon.plugin.datasetvis.settings.VisSettingComponent;
import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public class DataSetVisPlugin implements IPlugin
{

	private ISettingComponent _settingComponent;

	private IResultComponent _resultComponent;

	/**
	 * 
	 */
	public IResult doJob(IDataEngine arg0, IEnvironment arg1, ISettings arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	public ISettingComponent getSettingComponent()
	{
		if (_settingComponent == null) {
			_settingComponent = new VisSettingComponent();
		}
		return _settingComponent;
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

	private static final Logger LOGGER = Logger.getLogger(DataSetVisPlugin.class);
}
