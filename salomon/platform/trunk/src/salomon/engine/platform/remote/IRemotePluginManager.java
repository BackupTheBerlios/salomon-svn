
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import salomon.plugin.Description;

/**
 * Remote version of IPluginManager interface. It has all methods from
 * IPluginManager interface, but adds throwing RemoteException declaration to
 * each of methods.
 *  
 */
public interface IRemotePluginManager extends Remote
{
	Collection getAvailablePlugins() throws RemoteException;

	public boolean savePlugin(Description description) throws RemoteException;

	public boolean removePlugin(Description description) throws RemoteException;
	//    public IRemotePlugin getPlugin(URL url) throws RemoteException;
}