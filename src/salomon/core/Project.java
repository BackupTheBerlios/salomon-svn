/** Java class "KnowledgeSystem.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */

package salomon.core;

/**
 *  
 */
public class Project
{
	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID()
	{
		return _projectID;
	}

	/**
	 * @param projectId
	 *            The projectID to set.
	 */
	public void setProjectID(int projectId)
	{
		_projectID = projectId;
	}

	private int _projectID = 0;

	private String _name = null;

	private String _info = null;

	private ManagerEngine _managerEngine = null;

	public Project(int projectID)
	{
		_projectID = projectID;
	}

	public Project()
	{
	}

	/**
	 * @return Returns the managerEngine.
	 */
	public ManagerEngine getManagerEngine()
	{
		return _managerEngine;
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
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
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
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}
}