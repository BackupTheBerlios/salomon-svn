
package salomon.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import salomon.platform.plugin.IPluginManager;
import salomon.plugin.Description;

/**
 * Class representing remote instance of IPluginManager.
 */
public final class RemotePluginManager extends UnicastRemoteObject
		implements IRemotePluginManager
{
	private IPluginManager _pluginManager;

	/**
     * @pre pluginManager != null
     * @post $none
     */
	public RemotePluginManager(IPluginManager pluginManager)
			throws RemoteException
	{
		_pluginManager = pluginManager;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IRemotePluginManager#getAvailablePlugins()
	 */
	public Collection getAvailablePlugins() throws RemoteException
	{
		return _pluginManager.getAvailablePlugins();
	}

	/* (non-Javadoc)
	 * @see salomon.platform.remote.IRemotePluginManager#addPlugin(salomon.plugin.Description)
	 */
	public boolean savePlugin(Description description) throws RemoteException
	{
		return _pluginManager.savePlugin(description);		
	}

	/* (non-Javadoc)
	 * @see salomon.platform.remote.IRemotePluginManager#removePlugin(salomon.plugin.Description)
	 */
	public boolean removePlugin(Description description) throws RemoteException
	{		
		return _pluginManager.removePlugin(description);
	}

	//	/* (non-Javadoc)
	//	 * @see salomon.platform.remote.IRemotePluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IRemotePlugin getPlugin(URL url) throws RemoteException
	//	{
	//		return _pluginManager.get;
	//	}

}