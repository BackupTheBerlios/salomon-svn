
package salomon.core.holder;

import salomon.core.IManagerEngine;
import salomon.core.plugin.IPluginManager;
import salomon.core.project.IProjectManager;
import salomon.core.task.ITaskManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
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
        _pluginManagerHolder = new PluginManagerHolder(_currentManagerEngine.getPluginManager());
        _projectManagerHolder = new ProjectManagerHolder(_currentManagerEngine.getProjectManager());
        _taskManagerHolder = new TaskManagerHolder(_currentManagerEngine.getTasksManager());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManagerHolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManagerHolder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getTasksManager()
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