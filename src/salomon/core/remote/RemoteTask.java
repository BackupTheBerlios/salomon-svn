
package salomon.core.remote;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import salomon.core.plugin.PluginLoader;
import salomon.core.task.ITask;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public final class RemoteTask extends UnicastRemoteObject implements IRemoteTask
{
	private ITask _task;
    /**
	 * 
	 */
	public RemoteTask(ITask task) throws RemoteException
	{
		_task = task;
	}
	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getName()
	 */
	public String getName() throws RemoteException
	{	
		return _task.getName();
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getPlugin()
	 */
	public URL getPlugin() throws RemoteException
	{
		return PluginLoader.getPluginLocation(_task.getPlugin());
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getResult()
	 */
	public IResult getResult() throws RemoteException
	{
		return _task.getResult();
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getSettings()
	 */
	public ISettings getSettings() throws RemoteException
	{	
		return _task.getSettings();
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getStatus()
	 */
	public String getStatus() throws RemoteException
	{ 
		return _task.getStatus();
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#getTaksId()
	 */
	public int getTaskId() throws RemoteException
	{		
		return _task.getTaskId();
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException
	{
		_task.setName(name);
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setPlugin(salomon.plugin.IPlugin)
	 */
	public void setPlugin(URL plugin) throws RemoteException
	{
		try {
			_task.setPlugin(PluginLoader.loadPlugin(plugin));
		} catch (Exception e) {
			_logger.fatal("", e);
		}
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setResult(salomon.plugin.IResult)
	 */
	public void setResult(IResult result) throws RemoteException
	{
		_task.setResult(result);
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setSettings(salomon.plugin.ISettings)
	 */
	public void setSettings(ISettings settings) throws RemoteException
	{
		_task.setSettings(settings);
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setStatus(java.lang.String)
	 */
	public void setStatus(String status) throws RemoteException
	{
		_task.setStatus(status);
	}

	/* (non-Javadoc)
	 * @see salomon.core.remote.IRemoteTask#setTaksId(int)
	 */
	public void setTaskId(int taskId) throws RemoteException
	{
		_task.setTaskId(taskId);
	}
    private static Logger _logger = Logger.getLogger(RemoteTask.class);

}
