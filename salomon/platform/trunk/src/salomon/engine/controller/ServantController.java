package salomon.engine.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import salomon.engine.platform.Config;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.remote.ICentralController;
import salomon.engine.platform.remote.IRemoteController;
import salomon.engine.platform.remote.RemoteController;

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
		_logger.info("Exiting client...");
		try {
			_masterController.unregister(_remoteController);
		} catch (RemoteException e) {
			_logger.fatal("", e);
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
		try {
			System.setSecurityManager(new RMISecurityManager());
			String hostName = "remote";

			try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				_logger.fatal("", e);
			}

			_remoteController = new RemoteController(_managerEngine, hostName);
			// new project is created at the beggining
			_remoteController.getManagerEngine().getProjectManager().newProject();
			Registry registry = LocateRegistry.getRegistry(_serverHost,
					_serverPort);
			try {
				_masterController = (ICentralController) registry.lookup("CentralController");
				_masterController.register(_remoteController);
			} catch (NotBoundException e) {
				_logger.fatal("", e);
			}

		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	private static Logger _logger = Logger.getLogger(ServantController.class);
} // end ClientManager
