
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
	void clearTaskList() throws RemoteException;

	IRemoteTask getCurrentTask() throws RemoteException;

	Collection getTasks() throws RemoteException;

	void start() throws RemoteException;

	IRemoteTask createTask() throws RemoteException;
}