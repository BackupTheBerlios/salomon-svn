
package salomon.engine.platform.holder;

import java.sql.SQLException;
import java.util.Collection;

import salomon.engine.platform.project.IProject;
import salomon.engine.platform.project.IProjectManager;

/**
 * Holds projectManager of current client.
 * It is used by ManagerEngineHolder to switch between connected clients.
 *  
 */
final class ProjectManagerHolder implements IProjectManager
{
	private IProjectManager _currentProjectManager;

	/**
	 *  
	 */
	ProjectManagerHolder(IProjectManager projectManager)
	{
		_currentProjectManager = projectManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IProjectManager#newProject()
	 */
	public void newProject()
	{
		_currentProjectManager.newProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IProjectManager#loadProject(int)
	 */
	public void loadProject(int projectID) throws Exception
	{
		_currentProjectManager.loadProject(projectID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IProjectManager#saveProject(salomon.engine.platform.Project)
	 */
	public void saveProject() throws Exception, ClassNotFoundException
	{
		_currentProjectManager.saveProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.engine.platform.IProjectManager#getCurrentProject()
	 */
	public IProject getCurrentProject()
	{
		return _currentProjectManager.getCurrentProject();
	}

	void setCurrent(IProjectManager projectManager)
	{
		_currentProjectManager = projectManager;
	}

	/* (non-Javadoc)
	 * @see salomon.engine.platform.project.IProjectManager#getAvailableProjects()
	 */
	public Collection getAvailableProjects() throws SQLException, ClassNotFoundException
	{
		return _currentProjectManager.getAvailableProjects();
	}
}