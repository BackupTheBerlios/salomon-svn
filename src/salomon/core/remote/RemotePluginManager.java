
package salomon.core.remote;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
		super();
		_pluginManager = pluginManager;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemotePluginManager#getAvailablePlugins()
	 */
	public File[] getAvailablePlugins() throws RemoteException
	{
		return _pluginManager.getAvailablePlugins();
	}

}