
package salomon.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.platform.IManagerEngine;

/**
 * Class representing remote instance of IManagerEngine.
 */

public final class RemoteManagerEngine extends UnicastRemoteObject
		implements IRemoteManagerEngine
{
	private IManagerEngine _managerEngine;

	private IRemotePluginManager _remotePluginManager;

	private IRemoteProjectManager _remoteProjectManager;

	private IRemoteTaskManager _remoteTaskManager;

	/**
	 * @throws RemoteException
	 * @pre managerEngine != null
	 * @post $none
	 */
	public RemoteManagerEngine(IManagerEngine managerEngine)
			throws RemoteException
	{
		_remotePluginManager = new RemotePluginManager(
				managerEngine.getPluginManager());
		_remoteProjectManager = new RemoteProjectManager(
				managerEngine.getProjectManager());
		_remoteTaskManager = new RemoteTaskManager(
				managerEngine.getTasksManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteManagerEngine#getPluginManager()
	 */
	public IRemotePluginManager getPluginManager() throws RemoteException
	{
		return _remotePluginManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteManagerEngine#getProjectManager()
	 */
	public IRemoteProjectManager getProjectManager() throws RemoteException
	{
		return _remoteProjectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemoteManagerEngine#getTasksManager()
	 */
	public IRemoteTaskManager getTasksManager() throws RemoteException
	{
		return _remoteTaskManager;
	}
}