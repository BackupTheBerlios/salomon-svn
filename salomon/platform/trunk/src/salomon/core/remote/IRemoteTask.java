
package salomon.core.remote;

import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Remote version of ITask interface. It has all methods from ITask interface,
 * but adds throwing RemoteException declaration to each of methods.
 *  
 */
public interface IRemoteTask extends Remote
{
	/**
	 * @return Returns the name.
	 * @pre $none
	 * @post $none
	 */
	String getName() throws RemoteException;

	/**
	 * @return Returns the _plugin.
	 * @pre $none
	 * @post $none
	 */
	URL getPlugin() throws RemoteException;

	/**
	 * @return Returns the _result.
	 * @pre $none
	 * @post $none
	 */
	IResult getResult() throws RemoteException;

	/**
	 * @return Returns the _settings.
	 * @pre $none
	 * @post $none
	 */
	ISettings getSettings() throws RemoteException;

	/**
	 * @return Returns the status.
	 * @pre $none
	 * @post $none
	 */
	String getStatus() throws RemoteException;

	/**
	 * @param name The name to set.
	 * @pre $none
	 * @post $none
	 */
	void setName(String name) throws RemoteException;

	/**
	 * @param _plugin The _plugin to set.
	 * @pre $none
	 * @post $none
	 */
	void setPlugin(URL url) throws RemoteException;

	/**
	 * @param _result The _result to set.
	 * @pre $none
	 * @post $none
	 */
	void setResult(IResult result) throws RemoteException;

	/**
	 * @param _settings The _settings to set.
	 * @pre $none
	 * @post $none
	 */
	void setSettings(ISettings settings) throws RemoteException;

	/**
	 * @param status The status to set.
	 * @pre $none
	 * @post $none
	 */
	void setStatus(String status) throws RemoteException;

	/**
	 * @return Returns the taksId.
	 * @pre $none
	 * @post $none
	 */
	int getTaskId() throws RemoteException;

	/**
	 * @param taksId The taksId to set.
	 * @pre $none
	 * @post $none
	 */
	void setTaskId(int taksId) throws RemoteException;
}