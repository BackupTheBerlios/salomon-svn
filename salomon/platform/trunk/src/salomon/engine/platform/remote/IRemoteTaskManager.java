
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Remote version of ITaskManager interface. It has all methods from
 * ITaskManager interface, but adds throwing RemoteException declaration to each
 * of methods.
 *  
 */
public interface IRemoteTaskManager extends Remote
{
	void clearTaskList() throws RemoteException;

	IRemoteTask getCurrentTask() throws RemoteException;

	IRemoteTask[] getTasks() throws RemoteException;

	void start() throws RemoteException;

	IRemoteTask createTask() throws RemoteException;
}