/*
 * Created on 2004-05-07
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package ks.core.event;

import java.util.EventObject;
import java.util.List;

/**
 * @author nico
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
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
	 * @param list The _taskList to set.
	 */
	public void setTaskList(List tasks)
	{
		_taskList = tasks;
	}
}
