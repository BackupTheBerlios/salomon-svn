
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
public interface IMasterController extends Remote
{
	void register(IRemoteController remoteController) throws RemoteException;

	void unregister(IRemoteController remoteController) throws RemoteException;
}