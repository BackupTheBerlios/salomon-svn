
package salomon.core.remote;

import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.core.plugin.IPluginManager;
import salomon.plugin.Description;

/**
 * Class is a sever side wrapper of IRemotePluginManager object. It implements
 * IPluginManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 *  
 */
public final class PluginManagerProxy implements IPluginManager
{

	private IRemotePluginManager _remotePluginManager;

	/**
	 * @pre $none
	 * @post $none
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
			_logger.fatal("", e);
		}
		return result;
	}

	private static final Logger _logger = Logger.getLogger(PluginManagerProxy.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.plugin.IPluginManager#addPlugin(salomon.plugin.Description)
	 */
	public boolean savePlugin(Description description)
	{
		boolean result = false;
		try {
			result = _remotePluginManager.savePlugin(description);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.plugin.IPluginManager#removePlugin(salomon.plugin.Description)
	 */
	public boolean removePlugin(Description description)
	{

		boolean result = false;
		try {
			result = _remotePluginManager.removePlugin(description);
		} catch (RemoteException e) {
			_logger.fatal("", e);
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