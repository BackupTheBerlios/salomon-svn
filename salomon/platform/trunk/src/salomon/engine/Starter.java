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
		_logger.info("###  Application started  ###");

	}

	public static void exit()
	{
		getInstance().exitImpl();
	}

	private void exitImpl()
	{
		_logger.debug("controller: " + _contoroller.getClass());
		_contoroller.exit();
		_logger.info("###  Application exited  ###");
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
		_logger.debug("starting ServantController");
		_contoroller = new ServantController();
		start();
	}

	private void startLocalImpl()
	{
		_logger.debug("starting LocalController");
		_contoroller = new LocalController();
		start();
	}

	private void startServerImpl()
	{
		_logger.debug("starting MasterController");
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
					_logger.error("Wrong argument");
					Starter.startLocal();
				}

			} catch (MissingResourceException e) {
				_logger.fatal("", e);
				_logger.warn("No argument choosen");
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


	private static Logger _logger = Logger.getLogger(Starter.class);
}
