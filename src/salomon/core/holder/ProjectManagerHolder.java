
package salomon.core.holder;

import salomon.core.project.IProjectManager;
import salomon.core.project.Project;

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
	public Project newProject()
	{
		return _currentProjectManager.newProject();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#loadProject(int)
	 */
	public Project loadProject(int projectID) throws Exception
	{
		return _currentProjectManager.loadProject(projectID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#saveProject(salomon.core.Project)
	 */
	public void saveProject(Project project) throws Exception,
			ClassNotFoundException
	{
		_currentProjectManager.saveProject(project);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IProjectManager#getCurrentProject()
	 */
	public Project getCurrentProject()
	{
		return _currentProjectManager.getCurrentProject();
	}

	void setCurrent(IProjectManager projectManager)
	{
		_currentProjectManager = projectManager;
	}
}