
package salomon.core.plugin;

import java.util.Collection;

import salomon.plugin.Description;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IPluginManager
{
	public Collection getAvailablePlugins();
    
    public boolean savePlugin(Description description);
    
    public boolean removePlugin(Description description);
	//    public IPlugin getPlugin(URL url);
}