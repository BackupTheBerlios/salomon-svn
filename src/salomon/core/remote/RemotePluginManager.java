
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import salomon.core.plugin.IPluginManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class RemotePluginManager extends UnicastRemoteObject
		implements IRemotePluginManager
{
	private IPluginManager _pluginManager;

	/**
	 *  
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
	 * @see salomon.core.IRemotePluginManager#getAvailablePlugins()
	 */
	public Collection getAvailablePlugins() throws RemoteException
	{
		return _pluginManager.getAvailablePlugins();
	}

	//	/* (non-Javadoc)
	//	 * @see salomon.core.remote.IRemotePluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IRemotePlugin getPlugin(URL url) throws RemoteException
	//	{
	//		return _pluginManager.get;
	//	}

}