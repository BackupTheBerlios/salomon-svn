/*
 * Created on 2004-05-30
 *
 */
package salomon.core.event;

/**
 * @author nico
 *
 */
public interface ProjectListener
{
	/**
	 * Called when user wants to create new project
	 * @param e
	 */
	void newProject(ProjectEvent e);
	
	/**
	 * Called when user wants to save current project.
	 * @param e
	 */
	void saveProject(ProjectEvent e);
	
	/**
	 * Called when user wants to load existing project.
	 * @param e
	 */
	void loadProject(ProjectEvent e);
	
}
