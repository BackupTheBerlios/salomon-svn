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

package salomon.engine.controller;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.Config;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.remote.ICentralController;
import salomon.engine.platform.remote.IRemoteController;

/**
 * Class is a client implementation of IController interface.
 */
public final class ServantController implements IController
{

	private IManagerEngine _managerEngine;

	private ICentralController _masterController;

	private IRemoteController _remoteController;

	private String _serverHost;

	private int _serverPort;

	public void exit()
	{
		LOGGER.info("Exiting client...");
		try {
			_masterController.unregister(_remoteController);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	public void start(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		_serverHost = Config.getString("SERVER_HOST");
		_serverPort = Integer.parseInt(Config.getString("SERVER_PORT"));
		initRMI();
	}

	private void initRMI()
	{
        throw new UnsupportedOperationException(
				"Method initRMI() not implemented yet!");
//		try {
//			System.setSecurityManager(new RMISecurityManager());
//			String hostName = "remote";
//
//			try {
//				hostName = InetAddress.getLocalHost().getHostName();
//			} catch (UnknownHostException e) {
//				LOGGER.fatal("", e);
//			}
//
//			_remoteController = new RemoteController(_managerEngine, hostName);
//			// new project is created at the beggining
//			//FIXME _remoteController.getManagerEngine().getProjectManager().createProject();
//			Registry registry = LocateRegistry.getRegistry(_serverHost,
//					_serverPort);
//			try {
//				_masterController = (ICentralController) registry.lookup("CentralController");
//				_masterController.register(_remoteController);
//			} catch (NotBoundException e) {
//				LOGGER.fatal("", e);
//			}
//
//		} catch (RemoteException e) {
//			LOGGER.fatal("", e);
//		}
	}

	private static final Logger LOGGER = Logger.getLogger(ServantController.class);
    
} // end ClientManager
