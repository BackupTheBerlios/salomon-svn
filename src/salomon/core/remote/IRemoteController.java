
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemoteController extends Remote
{
	IRemoteManagerEngine getManagerEngine() throws RemoteException;

	String getName() throws RemoteException;

	String getDescription() throws RemoteException;
}