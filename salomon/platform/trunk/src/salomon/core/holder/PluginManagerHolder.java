
package salomon.core.holder;

import java.util.Collection;

import salomon.core.plugin.IPluginManager;
import salomon.plugin.Description;

/**
 * Holds pluginManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 *  
 */
final class PluginManagerHolder implements IPluginManager
{
	private IPluginManager _currentPluginManager;

	PluginManagerHolder(IPluginManager pluginManager)
	{
		_currentPluginManager = pluginManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.plugin.IPluginManager#getAvailablePlugins()
	 */
	public Collection getAvailablePlugins()
	{
		return _currentPluginManager.getAvailablePlugins();
	}

	void setCurrent(IPluginManager pluginManager)
	{
		_currentPluginManager = pluginManager;
	}

	/* (non-Javadoc)
	 * @see salomon.core.plugin.IPluginManager#addPlugin(salomon.plugin.Description)
	 */
	public boolean savePlugin(Description description)
	{
        return _currentPluginManager.savePlugin(description);
	}

	/* (non-Javadoc)
	 * @see salomon.core.plugin.IPluginManager#removePlugin(salomon.plugin.Description)
	 */
	public boolean removePlugin(Description description)
	{
		return _currentPluginManager.removePlugin(description);
	}

	//	/* (non-Javadoc)
	//	 * @see salomon.core.plugin.IPluginManager#getPlugin(java.net.URL)
	//	 */
	//	public IPlugin getPlugin(URL url)
	//	{
	//		return _currentPluginManager.getPlugin(url);
	//	}
}