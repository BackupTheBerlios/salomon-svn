
package salomon.core.remote;

import java.net.URL;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.core.plugin.PluginLoader;
import salomon.core.task.ITask;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class TaskProxy implements ITask
{
	private IRemoteTask _remoteTask;

	/**
	 *  
	 */
	public TaskProxy(IRemoteTask remoteTask)
	{
		_remoteTask = remoteTask;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getName()
	 */
	public String getName()
	{
		String name = null;
		try {
			name = _remoteTask.getName();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getPlugin()
	 */
	public IPlugin getPlugin()
	{
		IPlugin plugin = null;
		try {
			URL pluginLocation = _remoteTask.getPlugin();
			try {
				plugin = PluginLoader.loadPlugin(pluginLocation);
			} catch (Exception e1) {
				_logger.fatal("", e1);
			}
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return plugin;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getResult()
	 */
	public IResult getResult()
	{
		IResult result = null;
		try {
			result = _remoteTask.getResult();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getSettings()
	 */
	public ISettings getSettings()
	{
		ISettings settings = null;
		try {
			settings = _remoteTask.getSettings();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getStatus()
	 */
	public String getStatus()
	{
		String status = null;
		try {
			status = _remoteTask.getStatus();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#getTaskId()
	 */
	public int getTaskId()
	{
		int id = -1;
		try {
			id = _remoteTask.getTaskId();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		try {
			_remoteTask.setName(name);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setPlugin(salomon.plugin.IPlugin)
	 */
	public void setPlugin(IPlugin plugin)
	{
		try {
			_remoteTask.setPlugin(PluginLoader.getPluginLocation(plugin));
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setResult(salomon.plugin.IResult)
	 */
	public void setResult(IResult result)
	{
		try {
			_remoteTask.setResult(result);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setSettings(salomon.plugin.ISettings)
	 */
	public void setSettings(ISettings settings)
	{
		try {
			_remoteTask.setSettings(settings);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setStatus(java.lang.String)
	 */
	public void setStatus(String status)
	{
		try {
			_remoteTask.setStatus(status);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITask#setTaskId(int)
	 */
	public void setTaskId(int taskId)
	{
		try {
			_remoteTask.setTaskId(taskId);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	private static Logger _logger = Logger.getLogger(TaskProxy.class);

}