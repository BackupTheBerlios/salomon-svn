
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
public interface IRemoteProject extends Remote
{
	/**
	 * @return Returns the info.
	 */
	public String getInfo() throws RemoteException;

	/**
	 * @return Returns the name.
	 */
	public String getName() throws RemoteException;

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID() throws RemoteException;

	/**
	 * @param info
	 *            The info to set.
	 */
	public void setInfo(String info) throws RemoteException;

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) throws RemoteException;

	/**
	 * @param projectId
	 *            The projectID to set.
	 */
	public void setProjectID(int projectId) throws RemoteException;
}