
package salomon.engine.platform.plugin;

import java.util.Collection;

import salomon.plugin.Description;

/**
 * Interface implemented by PluginManager, which manages with editing plugins.
 */
public interface IPluginManager
{
	/**
	 * Returns collection of plugin descriptions
	 * 
	 * @return plugin descriptions
	 */
	public Collection getAvailablePlugins();

	/**
	 * Saves plugin description.
	 * 
	 * @return true if successfully saved, false otherwise
	 */
	public boolean savePlugin(Description description);

	/**
	 * Removes plugin corresponding to given plugin description.
	 * 
	 * @param description description of plugin to be removed
	 * @return true if successfully removed, false otherwise
	 */
	public boolean removePlugin(Description description);
	//    public IPlugin getPlugin(URL url);
}