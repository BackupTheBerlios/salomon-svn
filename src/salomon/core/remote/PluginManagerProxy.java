
package salomon.core.remote;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.core.plugin.IPluginManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class PluginManagerProxy implements IPluginManager
{
	private static Logger _logger = Logger.getLogger(PluginManagerProxy.class);

	private IRemotePluginManager _remotePluginManager;

	/**
	 *  
	 */
	public PluginManagerProxy(IRemotePluginManager remotePluginManager)
	{
		_remotePluginManager = remotePluginManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.plugin.IPluginManager#getAvailablePlugins()
	 */
	public Collection getAvailablePlugins()
	{
		Collection result = null;
		try {
			result = _remotePluginManager.getAvailablePlugins();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		return result;
	}

//	/* (non-Javadoc)
//	 * @see salomon.core.plugin.IPluginManager#getPlugin(java.net.URL)
//	 */
//	public IPlugin getPlugin(URL url)
//	{		
//		return PluginLoader.loadPlugin(_remotePluginManager.getPlugin();
//	}
    

}