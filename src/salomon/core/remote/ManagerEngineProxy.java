
package salomon.core.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

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
public final class ManagerEngineProxy implements IManagerEngine
{
	private IPluginManager _pluginManager;

	private IProjectManager _projectManager;

	private ITaskManager _taskManager;

	public ManagerEngineProxy(IRemoteManagerEngine remoteManagerEngine)
	{
		try {
			_taskManager = new TaskManagerProxy(
					remoteManagerEngine.getTasksManager());
			_projectManager = new ProjectMangerProxy(
					remoteManagerEngine.getProjectManager());
			_pluginManager = new PluginManagerProxy(
					remoteManagerEngine.getPluginManager());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IManagerEngine#getTasksManager()
	 */
	public ITaskManager getTasksManager()
	{
		return _taskManager;
	}

	private static Logger _logger = Logger.getLogger(ManagerEngineProxy.class);

}