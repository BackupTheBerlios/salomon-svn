
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.Starter;
import salomon.core.IManagerEngine;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
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
	 *  
	 */
	public RemoteController(IManagerEngine managerEngine, String name)
			throws RemoteException
	{
		_remoteManagerEngine = new RemoteManagerEngine(managerEngine);
		_name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteController#getDescription()
	 */
	public String getDescription() throws RemoteException
	{

		return _description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteController#getManagerEngine()
	 */
	public IRemoteManagerEngine getManagerEngine() throws RemoteException
	{
		return _remoteManagerEngine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteController#getName()
	 */
	public String getName() throws RemoteException
	{
		return _name;
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteController#exit(int)
	 */
	public void exit() throws RemoteException
	{
		_logger.debug("RemoteController.exit()");
		Starter.exit();
	}	
    private static Logger _logger = Logger.getLogger(RemoteController.class);
}