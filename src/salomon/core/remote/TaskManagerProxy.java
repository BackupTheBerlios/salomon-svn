
package salomon.core.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.core.task.ITask;
import salomon.core.task.ITaskManager;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class TaskManagerProxy implements ITaskManager
{
    private Map _proxies = new HashMap();
	private IRemoteTaskManager _remoteTaskManager;

	/**
	 *  
	 */
	public TaskManagerProxy(IRemoteTaskManager remoteTaskManager)
	{
		_remoteTaskManager = remoteTaskManager;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#clearTaskList()
	 */
	public void clearTaskList()
	{
		try {
			_remoteTaskManager.clearTaskList();
		} catch (RemoteException e) {
			_logger.error(e);
		}
	}


	/* (non-Javadoc)
	 * @see salomon.core.task.ITaskManager#createTask()
	 */
	public ITask createTask()
	{
        ITask task = null;
        try {
            task = getTaskProxy(_remoteTaskManager.createTask());
        } catch (RemoteException e) {
            _logger.error(e);
        }
		return task;
	}
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getCurrentTask()
	 */
	public ITask getCurrentTask()
	{
		ITask task = null;
		try {
			task = getTaskProxy(_remoteTaskManager.getCurrentTask());
		} catch (RemoteException e) {			
			_logger.error(e);
		}
		return task;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#getTasks()
	 */
	public Collection getTasks()
	{
		List tasks = new ArrayList();
		try {
			Collection remoteTasks = _remoteTaskManager.getTasks();
            for (Iterator iter = remoteTasks.iterator(); iter.hasNext();) {
				IRemoteTask remoteTask = (IRemoteTask) iter.next();
                tasks.add(getTaskProxy(remoteTask));
			}
		} catch (RemoteException e) {			
			_logger.error(e);
		}
        
		return Collections.unmodifiableCollection(tasks);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.task.ITaskManager#start()
	 */
	public void start()
	{
		try {
			_remoteTaskManager.start();
		} catch (RemoteException e) {
			_logger.error(e);
		}
	}

    private ITask getTaskProxy(IRemoteTask remoteTask) {
        ITask task = null;
    	if (_proxies.containsKey(remoteTask)) {
    		task = (ITask) _proxies.get(remoteTask); 
        } else {
            task = new TaskProxy(remoteTask);
            _proxies.put(remoteTask, task);            
        }
        return task;
    }
	private static Logger _logger = Logger.getLogger(TaskManagerProxy.class);
}