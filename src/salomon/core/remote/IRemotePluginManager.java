
package salomon.core.remote;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemotePluginManager extends Remote
{
	public File[] getAvailablePlugins() throws RemoteException;
}