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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.engine.Starter;
import salomon.engine.platform.IManagerEngine;

/**
 * An implementation of IRemoteController interface.
 * Its represents remote IController instance.    
 *  
 */
public final class RemoteController extends UnicastRemoteObject
		implements IRemoteController
{
	private String _description;

	private String _name;

	private IRemoteManagerEngine _remoteManagerEngine;

	/**
     * @throws RemoteException
     * @pre $none
     * @post $none
     */
	public RemoteController(IManagerEngine managerEngine, String name)
			throws RemoteException
	{
		_remoteManagerEngine = new RemoteManagerEngine(managerEngine);
		_name = name;
	}

	/**
	 * @see salomon.engine.platform.IRemoteController#getDescription()
	 */
	public String getDescription() throws RemoteException
	{

		return _description;
	}

	/**
	 * @see salomon.engine.platform.IRemoteController#getManagerEngine()
	 */
	public IRemoteManagerEngine getManagerEngine() throws RemoteException
	{
		return _remoteManagerEngine;
	}

	/**
	 * @see salomon.engine.platform.IRemoteController#getName()
	 */
	public String getName() throws RemoteException
	{
		return _name;
	}

	/**
	 * @see salomon.engine.platform.remote.IRemoteController#exit(int)
	 */
	public void exit() throws RemoteException
	{
		LOGGER.debug("RemoteController.exit()");
		Starter.exit();
    }
    
    private static final Logger LOGGER = Logger.getLogger(RemoteController.class);
}