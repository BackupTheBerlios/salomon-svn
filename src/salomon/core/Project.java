
package salomon.core;

/**
 *  
 */
public class Project
{
	private String _info = null;

	private ManagerEngine _managerEngine = null;

	private String _name = null;

	private int _projectID = 0;

	public Project(ManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	/**
	 * @return Returns the managerEngine.
	 */
	public ManagerEngine getManagerEngine()
	{
		return _managerEngine;
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
	 * @param info
	 *            The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param managerEngine
	 *            The managerEngine to set.
	 */
	public void setManagerEngine(ManagerEngine managerEngine)
	{
		_managerEngine = managerEngine;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param projectId
	 *            The projectID to set.
	 */
	public void setProjectID(int projectId)
	{
		_projectID = projectId;
	}
}