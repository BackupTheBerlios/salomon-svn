/*
 * Created on 2004-05-30
 *
 */

package salomon.core.event;

import java.util.EventObject;
import java.util.List;

/**
 * @author nico
 *  
 */
public class ProjectEvent extends EventObject
{
	private int _projectID;

	private List _taskList = null;

	public ProjectEvent()
	{
		super("ProjectEvent");
	}

	/**
	 * @param source
	 */
	public ProjectEvent(Object object)
	{
		super(object);
	}

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID()
	{
		return _projectID;
	}

	/**
	 * @return Returns the _taskList.
	 */
	public List getTaskList()
	{
		return _taskList;
	}

	/**
	 * @param projectID
	 *            The projectID to set.
	 */
	public void setProjectID(int projectID)
	{
		_projectID = projectID;
	}

	/**
	 * @param list
	 *            The _taskList to set.
	 */
	public void setTaskList(List tasks)
	{
		_taskList = tasks;
	}
}