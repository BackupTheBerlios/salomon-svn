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

import salomon.engine.controller.ServantController;
import salomon.engine.controller.IController;
import salomon.engine.controller.LocalController;
import salomon.engine.controller.MasterController;
import salomon.engine.platform.Config;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.platform.project.ProjectManager;
import salomon.engine.platform.task.TaskManager;

/**
 * Class starts application execution.
 *  
 */
public final class Starter
{
	private IController _contoroller;

	private IManagerEngine _managerEngine;

	private ProjectManager _projectManager = null;

	private Starter()
	{
		PropertyConfigurator.configure("log.conf");
		LOGGER.info("###  Application started  ###");

	}

	public static void exit()
	{
		getInstance().exitImpl();
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
		_managerEngine = new ManagerEngine();
		((TaskManager) _managerEngine.getTasksManager()).setProjectManger((ProjectManager) _managerEngine.getProjectManager());
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

	private static void startLocal()
	{
		getInstance().startLocalImpl();
	}

	private static void startServer()
	{
		getInstance().startServerImpl();
	}

	private static Starter _instance;


	private static final Logger LOGGER = Logger.getLogger(Starter.class);
}
