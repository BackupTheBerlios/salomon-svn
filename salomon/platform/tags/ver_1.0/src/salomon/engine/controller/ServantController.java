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

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.remote.ICentralController;
import salomon.engine.remote.IRemoteController;
import salomon.engine.remote.RemoteController;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.IManagerEngine;

/**
 * Class is a client implementation of IController interface.
 */
public final class ServantController implements IController
{

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _managerEngine;

	/**
	 * 
	 * @uml.property name="_masterController"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private ICentralController _masterController;

	/**
	 * 
	 * @uml.property name="_remoteController"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IRemoteController _remoteController;

	private String _serverHost;

	private int _serverPort;

	/**
	 * @see salomon.engine.controller.IController#exit()
	 */
	public void exit()
	{
		LOGGER.info("Exiting client...");
		try {
			_masterController.unregister(_remoteController);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.controller.IController#start(salomon.engine.platform.IManagerEngine)
	 */
	public void start(IManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
		_serverHost = Config.getString("SERVER_HOST");
		_serverPort = Integer.parseInt(Config.getString("SERVER_PORT"));
		initRMI();
	}

	private void initRMI()
	{
		// throw new UnsupportedOperationException(
		// "Method initRMI() not implemented yet!");
		try {
			System.setSecurityManager(new RMISecurityManager());
			_remoteController = new RemoteController(_managerEngine,
					_serverHost);
			// new project is created at the beggining
			// FIXME
			_remoteController.getManagerEngine().getProjectManager().createProject();
			Registry registry = LocateRegistry.getRegistry(_serverHost,
					_serverPort);
			LOGGER.debug("Registry at: " + _serverHost + ", on port: "
					+ _serverPort);
			try {
				_masterController = (ICentralController) registry.lookup("CentralController");
				_masterController.register(_remoteController);
			} catch (NotBoundException e) {
				LOGGER.fatal("", e);
			}

		} catch (RemoteException e) {
			LOGGER.fatal("Fatal error while initializing RMI."
					+ " Application will be terminated", e);
			System.exit(1);
		} catch (PlatformException e) {
			LOGGER.fatal("PlatformException while creating project!", e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(ServantController.class);

} // end ClientManager
