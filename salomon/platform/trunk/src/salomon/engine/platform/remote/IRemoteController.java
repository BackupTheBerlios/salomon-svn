
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface corresponding to IController interface.   
 *  
 */
public interface IRemoteController extends Remote
{
	IRemoteManagerEngine getManagerEngine() throws RemoteException;

	String getName() throws RemoteException;

	String getDescription() throws RemoteException;
       
    void exit() throws RemoteException;
}