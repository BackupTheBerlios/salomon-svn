
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
}