
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemoteManagerEngine extends Remote
{
	public IRemoteTaskManager getTasksManager() throws RemoteException;

	/**
	 * @return Returns the pluginManager.
	 */
	public IRemotePluginManager getPluginManager() throws RemoteException;

	/**
	 * @return Returns the projectManager.
	 */
	public IRemoteProjectManager getProjectManager() throws RemoteException;

}