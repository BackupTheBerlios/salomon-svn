
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
public interface IRemotePluginManager extends Remote
{
	public Collection getAvailablePlugins() throws RemoteException;

	//    public IRemotePlugin getPlugin(URL url) throws RemoteException;
}