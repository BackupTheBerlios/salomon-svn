
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
public interface IRemoteProjectManager extends Remote
{
	void newProject() throws RemoteException;

	/**
     * Method loads project from data base.
     *
     * @param projectID
     * @return loaded project
     * @throws Exception
     * @pre $none
     * @post $none
     */
	void loadProject(int projectID) throws Exception, RemoteException;

	/**
     * Method saves project in data base - project header, plugins and tasks are
     * saved.
     *
     * @throws ClassNotFoundException
     * @throws Exception
     * @pre $none
     * @post $none
     */
	void saveProject() throws Exception, RemoteException,
			ClassNotFoundException;

	/**
     * @return Returns the currentProject.
     * @pre $none
     * @post $result != null
     */
	IRemoteProject getCurrentProject() throws RemoteException;

}