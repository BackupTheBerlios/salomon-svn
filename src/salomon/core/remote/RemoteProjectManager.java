
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Collection;

import salomon.core.project.IProject;
import salomon.core.project.IProjectManager;

/**
 * Class representing remote instance of IProjectManager.
 */
public final class RemoteProjectManager extends UnicastRemoteObject
		implements IRemoteProjectManager
{

	private IProjectManager _projectManager;

	private IRemoteProject _currentRemoteProject;

	private IProject _currentProject;

	/**
	 * @throws RemoteException
	 */
	protected RemoteProjectManager(IProjectManager projectManager)
			throws RemoteException
	{
		_projectManager = projectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#newProject()
	 */
	public void newProject() throws RemoteException
	{
		_projectManager.newProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#loadProject(int)
	 */
	public void loadProject(int projectID) throws Exception, RemoteException
	{
		_projectManager.loadProject(projectID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject() throws Exception, RemoteException,
			ClassNotFoundException
	{
		_projectManager.saveProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IRemoteProjectManager#getCurrentProject()
	 */
	public IRemoteProject getCurrentProject() throws RemoteException
	{
		IProject project = _projectManager.getCurrentProject();
		if (project != _currentProject) {
			_currentProject = project;
			_currentRemoteProject = new RemoteProject(_currentProject);
		}

		return _currentRemoteProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.remote.IRemoteProjectManager#getAvailableProjects()
	 */
	public Collection getAvailableProjects() throws RemoteException,
			SQLException, ClassNotFoundException
	{
		return _projectManager.getAvailableProjects();
	}
}