
package salomon.core.remote;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.core.project.IProject;
import salomon.core.project.IProjectManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class ProjectManagerProxy implements IProjectManager
{
	private IRemoteProjectManager _remoteProjectManager;
    
    private IRemoteProject _currentRemoteProject;
    private IProject _currentProject;

	/**
	 *  
	 */
	public ProjectManagerProxy(IRemoteProjectManager remoteProjectManager)
	{
		_remoteProjectManager = remoteProjectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#getCurrentProject()
	 */
	public IProject getCurrentProject()
	{
		try {
			IRemoteProject remoteProject = _remoteProjectManager.getCurrentProject();
            if (remoteProject != _currentRemoteProject) {
                _currentRemoteProject = remoteProject;
                _currentProject = new ProjectProxy(_currentRemoteProject);
            }
		} catch (RemoteException e) {
			_logger.error(e);
		}
		return _currentProject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#loadProject(int)
	 */
	public void loadProject(int projectID) throws Exception
	{
		try {
			_remoteProjectManager.loadProject(projectID);
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#newProject()
	 */
	public void newProject()
	{
		try {
			_remoteProjectManager.newProject();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			_logger.fatal("", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject() throws Exception, ClassNotFoundException
	{
		try {
			_remoteProjectManager.saveProject();
		} catch (RemoteException e) {
			_logger.fatal("", e);
		}
	}

	private static Logger _logger = Logger.getLogger(ProjectManagerProxy.class);
}