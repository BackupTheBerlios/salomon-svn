
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote version of IManagerEngine interface. It has all methods from
 * IManagerEngine interface, but adds throwing RemoteException declaration to
 * each of methods.
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