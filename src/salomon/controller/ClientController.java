
package salomon.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

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
		_serverHost = "localhost";
		_serverPort = 4321;
		initRMI();
	}

	private void initRMI()
	{
		try {
			_remoteController = new RemoteController(_managerEngine);
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
