
package salomon.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.core.IManagerEngine;
import salomon.core.remote.IMasterController;
import salomon.core.remote.IRemoteController;
import salomon.core.remote.RemoteController;

/**
 *  
 */
public final class ClientController implements IController
{
	private static Logger _logger = Logger.getLogger(ClientController.class);

	private String _serverHost;

	private int _serverPort;

	private IRemoteController _remoteController;

	private IManagerEngine _managerEngine;

	private IMasterController _masterController;

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
            //System.setSecurityManager(new RMISecurityManager());
            String hostName = "remote";
            
            try {
				hostName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e2) {
				_logger.fatal("", e2);
			}
            
			_remoteController = new RemoteController(_managerEngine, hostName);
            // new project is created at the beggining
            _remoteController.getManagerEngine().getProjectManager().newProject();
			Registry registry = LocateRegistry.getRegistry(_serverHost,
					_serverPort);
			try {
				_masterController = (IMasterController) registry.lookup("MasterController");
				_masterController.register(_remoteController);
			} catch (NotBoundException e1) {
				_logger.error(e1);
			}

		} catch (RemoteException e) {
			_logger.error(e);
		}
	}
} // end ClientManager
