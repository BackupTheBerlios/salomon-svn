
package salomon.core.project;

/**
 * Represents a project, it is an implementation of IProject interface.
 *  
 */
final class Project implements IProject
{
	private String _info = null;

	private String _name = null;

	private int _projectID = 0;

	public Project()
	{
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID()
	{
		return _projectID;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param projectId The projectID to set.
	 */
	public void setProjectID(int projectId)
	{
		_projectID = projectId;
	}

	public String toString()
	{
		return "[" + _projectID + ", " + _name + ", " + _info + "]";
	}
}