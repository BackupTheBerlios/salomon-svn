
package salomon.core;

import salomon.core.plugin.IPluginManager;
import salomon.core.project.IProjectManager;
import salomon.core.task.ITaskManager;

/**
 * Interface used to pass ManagerEngine to plugins. 
 *  
 */
public interface IManagerEngine
{
	public ITaskManager getTasksManager();

	/**
	 * @return Returns the pluginManager.
	 */
	public IPluginManager getPluginManager();

	/**
	 * @return Returns the projectManager.
	 */
	public IProjectManager getProjectManager();
}