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

package salomon.engine.platform.remote.plugin;

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.platform.IDataEngine;
import salomon.platform.IEnvironment;

import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Remote version of IDataPlugin interface. It has all methods from IDataPlugin
 * interface, but adds throwing RemoteException declaration to each of methods.
 * 
 * @see salomon.plugin.IDataPlugin
 */
public interface IRemotePlugin extends Remote
{
    /**
     * @see salomon.plugin.IDataPlugin#doJob(IDataEngine, IEnvironment, ISettings)
     * @param engine
     * @param environment
     * @param settings
     * @return The task exect
     * @throws RemoteException
     */
	IResult doJob(IDataEngine engine, IEnvironment environment, ISettings settings)
			throws RemoteException;
}