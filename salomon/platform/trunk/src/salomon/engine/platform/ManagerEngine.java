
package salomon.engine.platform;

import salomon.engine.platform.plugin.IPluginManager;
import salomon.engine.platform.plugin.PluginManager;
import salomon.engine.platform.project.IProjectManager;
import salomon.engine.platform.project.ProjectManager;
import salomon.engine.platform.task.ITaskManager;
import salomon.engine.platform.task.TaskManager;

/**
 * Class creates and holds all managers used by plugins. They are created only
 * in this class to avoid multiple instances.
 *  
 */
public final class ManagerEngine implements IManagerEngine
{
	private ITaskManager _taskManager = null;

	private IProjectManager _projectManager = null;

	private IPluginManager _pluginManager = null;

	public ManagerEngine()
	{
		_taskManager = new TaskManager();
		_projectManager = new ProjectManager(this);
		_pluginManager = new PluginManager();

	}

	public ManagerEngine(ITaskManager taskManager,
			IProjectManager projectManager, IPluginManager pluginManager)
	{
		_taskManager = taskManager;
		_projectManager = projectManager;
		_pluginManager = pluginManager;
	}

	public ITaskManager getTasksManager()
	{
		return _taskManager;
	}

	/**
	 * @return Returns the pluginManager.
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManager;
	}

	/**
	 * @return Returns the projectManager.
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManager;
	}
} // end ManagerEngine
