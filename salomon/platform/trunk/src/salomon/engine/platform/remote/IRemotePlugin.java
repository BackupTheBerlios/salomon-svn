
package salomon.engine.platform.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.engine.platform.data.DataEngine;
import salomon.engine.platform.data.Environment;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Remote version of IDataPlugin interface. It has all methods from IDataPlugin
 * interface, but adds throwing RemoteException declaration to each of methods.
 *  
 */
public interface IRemotePlugin extends Remote
{
	IResult doJob(DataEngine engine, Environment environment, ISettings settings)
			throws RemoteException;
}