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

package salomon.engine;

import java.util.MissingResourceException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.controller.IController;
import salomon.engine.controller.LibraryController;
import salomon.engine.controller.LocalController;
import salomon.engine.controller.MasterController;
import salomon.engine.controller.ServantController;

import salomon.util.gui.Utils;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;

/**
 * Class starts application execution.
 */
public final class Starter
{

	/**
	 * 
	 * @uml.property name="_contoroller"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IController _contoroller;

	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _managerEngine;

	// private ProjectManager _projectManager = null;

	private Starter()
	{
		PropertyConfigurator.configure("log.conf");
		LOGGER.info("###  Application started  ###");

	}

	private void exitImpl()
	{
		LOGGER.debug("controller: " + _contoroller.getClass());
		_contoroller.exit();
		LOGGER.info("###  Application exited  ###");
		System.exit(0);
	}

	private void initManagers()
	{
		try {
			_managerEngine = new ManagerEngine();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			Utils.showErrorMessage("ERR_CONNECTION_ERROR");
		}
	}

	private void start()
	{
		initManagers();
		_contoroller.start(_managerEngine);
	}

	private void startClientImpl()
	{
		LOGGER.debug("starting ServantController");
		_contoroller = new ServantController();
		start();
	}

	private LibraryController startLibraryImpl()
	{
		LOGGER.debug("starting MasterController");
		_contoroller = new LibraryController();
		start();

		return (LibraryController) _contoroller;
	}

	private void startLocalImpl()
	{
		LOGGER.debug("starting LocalController");
		_contoroller = new LocalController();
		start();
	}

	private void startServerImpl()
	{
		LOGGER.debug("starting MasterController");
		_contoroller = new MasterController();
		start();
	}

	/**
	 * creates library controller
	 * 
	 * @return the created LibraryController
	 */
	public static LibraryController createLibraryController()
	{
		return Starter.startLibrary();
	}

	/**
	 * performs clean-up and exits
	 */
	public static void exit()
	{
		getInstance().exitImpl();
	}

	/**
	 * main method for starting Salomon
	 * 
	 * @param args parameters from the command line
	 */
	public static void main(String[] args)
	{
		if (args.length > 0) {
			if ("--local".equals(args[0])) {
				Starter.startLocal();
			} else if ("--server".equals(args[0])) {
				Starter.startServer();
			} else if ("--client".equals(args[0])) {
				Starter.startClient();
			}
		} else {
			String mode = null;
			try {
				mode = Config.getString("MODE");
				if ("local".equals(mode)) {
					Starter.startLocal();
				} else if ("server".equals(mode)) {
					Starter.startServer();
				} else if ("client".equals(mode)) {
					Starter.startClient();
				} else {
					LOGGER.error("Wrong argument");
					Starter.startLocal();
				}

			} catch (MissingResourceException e) {
				LOGGER.fatal("", e);
				LOGGER.warn("No argument choosen");
				Starter.startLocal();
			}
		}
	}

	private static Starter getInstance()
	{
		if (_instance == null) {
			_instance = new Starter();
		}

		return _instance;
	}

	private static void startClient()
	{
		getInstance().startClientImpl();
	}

	private static LibraryController startLibrary()
	{
		return getInstance().startLibraryImpl();
	}

	private static void startLocal()
	{
		getInstance().startLocalImpl();
	}

	private static void startServer()
	{
		getInstance().startServerImpl();
	}

	/**
	 * 
	 * @uml.property name="_instance"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private static Starter _instance;

	private static final Logger LOGGER = Logger.getLogger(Starter.class);
}
