
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemoteTaskManager extends Remote
{
	public void clearTaskList() throws RemoteException;

	public IRemoteTask getCurrentTask() throws RemoteException;

	public Collection getTasks() throws RemoteException;

	public void start() throws RemoteException;

	public IRemoteTask createTask() throws RemoteException;
}