
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface including methods to establish remote connction.  
 *  
 */
public interface IMasterController extends Remote
{
	void register(IRemoteController remoteController) throws RemoteException;

	void unregister(IRemoteController remoteController) throws RemoteException;
}