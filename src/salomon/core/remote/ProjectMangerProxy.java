
package salomon.core.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.core.project.IProjectManager;
import salomon.core.project.Project;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class ProjectMangerProxy implements IProjectManager
{
	private static Logger _logger = Logger.getLogger(ProjectMangerProxy.class);

	private IRemoteProjectManager _remoteProjectManager;

	/**
	 *  
	 */
	public ProjectMangerProxy(IRemoteProjectManager remoteProjectManager)
	{
		_remoteProjectManager = remoteProjectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#newProject()
	 */
	public Project newProject()
	{
		Project newProject = null;
		try {
			newProject = _remoteProjectManager.newProject();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		return newProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#loadProject(int)
	 */
	public Project loadProject(int projectID) throws Exception
	{
		Project project = null;
		//TODO: Catch RemoteException
		_remoteProjectManager.loadProject(projectID);

		return project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject(Project project) throws Exception,
			ClassNotFoundException
	{
		//TODO: Catch RemoteException
		_remoteProjectManager.saveProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#getCurrentProject()
	 */
	public Project getCurrentProject()
	{
		Project currentProject = null;
		try {
			currentProject = _remoteProjectManager.getCurrentProject();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.error(e);
		}
		return currentProject;
	}
}