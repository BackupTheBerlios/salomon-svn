
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
	IRemoteTaskManager getTasksManager() throws RemoteException;

	/**
     * @return Returns the pluginManager.
     * @pre $none
     * @post $result != null
     */
	IRemotePluginManager getPluginManager() throws RemoteException;

	/**
     * @return Returns the projectManager.
     * @pre $none
     * @post $result != null
     */
	IRemoteProjectManager getProjectManager() throws RemoteException;

}