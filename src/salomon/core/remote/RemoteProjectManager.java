
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.core.project.IProjectManager;
import salomon.core.project.Project;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class RemoteProjectManager extends UnicastRemoteObject
		implements IRemoteProjectManager
{

	private IProjectManager _projectManager;

	/**
	 * @throws RemoteException
	 */
	protected RemoteProjectManager(IProjectManager projectManager)
			throws RemoteException
	{
		super();
		_projectManager = projectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#newProject()
	 */
	public Project newProject() throws RemoteException
	{
		return _projectManager.newProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#loadProject(int)
	 */
	public Project loadProject(int projectID) throws Exception, RemoteException
	{
		return _projectManager.loadProject(projectID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject(Project project) throws Exception, RemoteException,
			ClassNotFoundException
	{
		_projectManager.saveProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#getCurrentProject()
	 */
	public Project getCurrentProject() throws RemoteException
	{
		return _projectManager.getCurrentProject();
	}
}