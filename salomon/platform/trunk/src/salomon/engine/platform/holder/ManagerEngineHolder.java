
package salomon.engine.platform.holder;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.plugin.IPluginManager;
import salomon.engine.platform.project.IProjectManager;
import salomon.engine.platform.task.ITaskManager;

/**
 * Holds all other holders.
 * It manges with swithing between connected clients.
 *  
 */
public final class ManagerEngineHolder implements IManagerEngine
{
	private IManagerEngine _currentManagerEngine;

	private PluginManagerHolder _pluginManagerHolder;

	private ProjectManagerHolder _projectManagerHolder;

	private TaskManagerHolder _taskManagerHolder;

	/**
	 * @param currentManagerEngine
	 */
	public ManagerEngineHolder(IManagerEngine managerEngine)
	{
		_currentManagerEngine = managerEngine;
		_pluginManagerHolder = new PluginManagerHolder(
				_currentManagerEngine.getPluginManager());
		_projectManagerHolder = new ProjectManagerHolder(
				_currentManagerEngine.getProjectManager());
		_taskManagerHolder = new TaskManagerHolder(
				_currentManagerEngine.getTasksManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManagerHolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManagerHolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IManagerEngine#getTasksManager()
	 */
	public ITaskManager getTasksManager()
	{
		return _taskManagerHolder;
	}

	public void setCurrentManager(IManagerEngine managerEngine)
	{
		_currentManagerEngine = managerEngine;
		_pluginManagerHolder.setCurrent(_currentManagerEngine.getPluginManager());
		_projectManagerHolder.setCurrent(_currentManagerEngine.getProjectManager());
		_taskManagerHolder.setCurrent(_currentManagerEngine.getTasksManager());
	}
}