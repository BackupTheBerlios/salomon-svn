
package salomon.platform.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.platform.IManagerEngine;
import salomon.platform.plugin.IPluginManager;
import salomon.platform.project.IProjectManager;
import salomon.platform.task.ITaskManager;

/**
 * Class is a sever side wrapper of IRemoteManagerEngine object. It implements
 * IManagerEngine interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
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
			_projectManager = new ProjectManagerProxy(
					remoteManagerEngine.getProjectManager());
			_pluginManager = new PluginManagerProxy(
					remoteManagerEngine.getPluginManager());
		} catch (RemoteException e) {
			_logger.error(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IManagerEngine#getPluginManager()
	 */
	public IPluginManager getPluginManager()
	{
		return _pluginManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IManagerEngine#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		return _projectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.IManagerEngine#getTasksManager()
	 */
	public ITaskManager getTasksManager()
	{
		return _taskManager;
	}

	private static final Logger _logger = Logger.getLogger(ManagerEngineProxy.class);

}