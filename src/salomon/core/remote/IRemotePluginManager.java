
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import salomon.plugin.Description;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemotePluginManager extends Remote
{
	Collection getAvailablePlugins() throws RemoteException;

	public boolean savePlugin(Description description) throws RemoteException;

	public boolean removePlugin(Description description) throws RemoteException;
	//    public IRemotePlugin getPlugin(URL url) throws RemoteException;
}