
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.core.project.Project;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemoteProjectManager extends Remote
{
	public Project newProject() throws RemoteException;

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public Project loadProject(int projectID) throws Exception, RemoteException;

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @param project
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject(Project project) throws Exception, RemoteException,
			ClassNotFoundException;

	/**
	 * @return Returns the currentProject.
	 */
	public Project getCurrentProject() throws RemoteException;

}