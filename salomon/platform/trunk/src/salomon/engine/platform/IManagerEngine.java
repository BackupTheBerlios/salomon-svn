
package salomon.engine.platform;

import salomon.engine.platform.plugin.IPluginManager;
import salomon.engine.platform.project.IProjectManager;
import salomon.engine.platform.task.ITaskManager;

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