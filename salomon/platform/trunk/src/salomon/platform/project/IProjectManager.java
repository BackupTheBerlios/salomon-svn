
package salomon.platform.project;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Interface implemented by ProjectManager, which manages with projects.
 *  
 */
public interface IProjectManager
{
    /**
     * Creates new, empty project     
     *
     */
	public void newProject();

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public void loadProject(int projectID) throws Exception;

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject() throws Exception, ClassNotFoundException;
	
    /**
     * Returns collection of available projects.
     * 
     * TODO: change it 
	 * Returns collection od arrays of object
     * 1 row is an array of column names
     * next are data.
     * If there is no data, only column names are returned
     * 
	 * @return
     * @throws ClassNotFoundException
     * @throws SQLException
	 */
    public Collection getAvailableProjects() throws SQLException, ClassNotFoundException; 

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject();
}