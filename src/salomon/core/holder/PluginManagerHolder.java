
package salomon.core.holder;

import java.util.Collection;

import salomon.core.plugin.IPluginManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
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

//	/* (non-Javadoc)
//	 * @see salomon.core.plugin.IPluginManager#getPlugin(java.net.URL)
//	 */
//	public IPlugin getPlugin(URL url)
//	{		
//		return _currentPluginManager.getPlugin(url);
//	}
}