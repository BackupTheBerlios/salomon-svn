
package salomon.core.plugin;

import java.util.Collection;

import salomon.plugin.Description;

/**
 * Interface implemented by PluginManager, which manages with editing plugins.
 */
public interface IPluginManager
{
	public Collection getAvailablePlugins();

	public boolean savePlugin(Description description);

	public boolean removePlugin(Description description);
	//    public IPlugin getPlugin(URL url);
}