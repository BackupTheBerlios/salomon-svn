
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface including methods to establish remote connction.  
 *  
 */
public interface ICentralController extends Remote
{
	void register(IRemoteController remoteController) throws RemoteException;

	void unregister(IRemoteController remoteController) throws RemoteException;
}