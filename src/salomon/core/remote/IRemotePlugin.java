
package salomon.core.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.core.data.DataEngine;
import salomon.core.data.Environment;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IRemotePlugin extends Remote
{
	IResult doJob(DataEngine engine, Environment environment, ISettings settings)
			throws RemoteException;
}