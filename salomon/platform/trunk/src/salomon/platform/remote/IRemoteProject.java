
package salomon.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote version of IProject interface. It has all methods from IProject
 * interface, but adds throwing RemoteException declaration to each of methods.
 *  
 */
public interface IRemoteProject extends Remote
{
	/**
	 * @return Returns the info.
	 * @pre $none
	 * @post $result != null
	 */
	String getInfo() throws RemoteException;

	/**
	 * @return Returns the name.
	 * @pre $none
	 * @post $result != null
	 */
	String getName() throws RemoteException;

	/**
	 * @return Returns the projectID.
	 * @pre $none
	 * @post $none
	 */
	int getProjectID() throws RemoteException;

	/**
	 * @param info The info to set.
	 * @pre info != null
	 * @post $none
	 */
	void setInfo(String info) throws RemoteException;

	/**
	 * @param name The name to set.
	 * @pre name != null
	 * @post $none
	 */
	void setName(String name) throws RemoteException;

	/**
	 * @param projectId The projectID to set.
	 * @pre $none
	 * @post $none
	 */
	void setProjectID(int projectId) throws RemoteException;
}