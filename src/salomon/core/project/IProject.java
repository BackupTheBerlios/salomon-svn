
package salomon.core.project;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IProject
{
	/**
	 * @return Returns the info.
	 */
	public String getInfo();

	/**
	 * @return Returns the name.
	 */
	public String getName();

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID();

	/**
	 * @param info
	 *            The info to set.
	 */
	public void setInfo(String info);

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name);

	/**
	 * @param projectId
	 *            The projectID to set.
	 */
	public void setProjectID(int projectId);
}