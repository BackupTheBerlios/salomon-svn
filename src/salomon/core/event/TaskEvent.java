/*
 * Created on 2004-05-07
 *
 */

package salomon.core.event;

import java.util.EventObject;
import java.util.List;

/**
 * @author nico
 * 
 */
public class TaskEvent extends EventObject
{
	private List _taskList = null;

	/**
	 * Stub
	 */
	public TaskEvent()
	{
		super("TaskEvent");
	}

	public TaskEvent(Object object)
	{
		super(object);
	}

	/**
	 * @return Returns the _taskList.
	 */
	public List getTaskList()
	{
		return _taskList;
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