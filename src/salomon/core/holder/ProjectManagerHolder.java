
package salomon.core.holder;

import java.sql.SQLException;
import java.util.Collection;

import salomon.core.project.IProject;
import salomon.core.project.IProjectManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
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
	 * @see salomon.core.IProjectManager#newProject()
	 */
	public void newProject()
	{
		_currentProjectManager.newProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#loadProject(int)
	 */
	public void loadProject(int projectID) throws Exception
	{
		_currentProjectManager.loadProject(projectID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject() throws Exception, ClassNotFoundException
	{
		_currentProjectManager.saveProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#getCurrentProject()
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
	 * @see salomon.core.project.IProjectManager#getAvailableProjects()
	 */
	public Collection getAvailableProjects() throws SQLException, ClassNotFoundException
	{
		return _currentProjectManager.getAvailableProjects();
	}
}