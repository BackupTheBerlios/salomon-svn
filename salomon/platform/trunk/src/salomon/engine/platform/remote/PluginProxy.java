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

package salomon.engine.platform.remote;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;
import salomon.plugin.Description;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * Class is a sever side wrapper of IPluginProxy object. It implements IPlugin
 * interface and delegates methods execution to remote object catching all
 * RemoteExceptions.
 *  
 */
public final class PluginProxy implements IPlugin
{
	private IRemotePlugin _remotePlugin;

	private IPlugin _localPlugin;

	/**
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.IPlugin#initizalize()
	 */
	public void initizalize()
	{
		_localPlugin.initizalize();
	}

	/**
	 * @see salomon.plugin.IPlugin#destroy()
	 */
	public void destroy()
	{
		_localPlugin.destroy();
	}

	/**
	 * @see salomon.plugin.IPlugin#getDescription()
	 */
	public Description getDescription()
	{

        //FIXME
        throw new UnsupportedOperationException(
                "Method getResultComponent() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IDataPlugin#doJob(salomon.engine.platform.data.DataEngine,
	 *      salomon.engine.platform.data.Environment, salomon.plugin.ISettings)
	 */
	public IResult doJob(IDataEngine engine, IEnvironment environment,
			ISettings settings)
	{
        //FIXME
        throw new UnsupportedOperationException(
                "Method getResultComponent() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getSettingComponent()
	 */
	public ISettingComponent getSettingComponent()
	{
        //FIXME
        throw new UnsupportedOperationException(
                "Method getResultComponent() not implemented yet!");
	}

	/**
	 * @see salomon.plugin.IGraphicPlugin#getResultComponent()
	 */
	public IResultComponent getResultComponent()
	{
        //FIXME
        throw new UnsupportedOperationException(
				"Method getResultComponent() not implemented yet!");
	}
}