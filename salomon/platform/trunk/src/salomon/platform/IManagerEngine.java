
package salomon.platform;

import salomon.platform.plugin.IPluginManager;
import salomon.platform.project.IProjectManager;
import salomon.platform.task.ITaskManager;

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