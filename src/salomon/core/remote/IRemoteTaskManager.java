
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import salomon.core.task.Task;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemoteTaskManager extends Remote
{
	public void addAllTasks(List tasks) throws RemoteException;

	public void addTask(Task task) throws RemoteException;

	public void clearTaskList() throws RemoteException;

	public Task getCurrentTask() throws RemoteException;

	public List getTasks() throws RemoteException;

	public void start() throws RemoteException;
}