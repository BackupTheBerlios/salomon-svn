
package salomon.core.remote;

import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * @author krzychu
 * 
 */
public interface IRemoteTask extends Remote
{
    /**
     * @return Returns the name.
     */
    public String getName() throws RemoteException;

    /**
     * @return Returns the _plugin.
     */
    public URL getPlugin() throws RemoteException;

    /**
     * @return Returns the _result.
     */
    public IResult getResult() throws RemoteException;

    /**
     * @return Returns the _settings.
     */
    public ISettings getSettings() throws RemoteException;

    /**
     * @return Returns the status.
     */
    public String getStatus() throws RemoteException;

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) throws RemoteException;

    /**
     * @param _plugin
     *            The _plugin to set.
     */
    public void setPlugin(URL url) throws RemoteException;

    /**
     * @param _result
     *            The _result to set.
     */
    public void setResult(IResult result) throws RemoteException;

    /**
     * @param _settings
     *            The _settings to set.
     */
    public void setSettings(ISettings settings) throws RemoteException;

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) throws RemoteException;

    /**
     * @return Returns the taksId.
     */
    public int getTaskId() throws RemoteException;

    /**
     * @param taksId
     *            The taksId to set.
     */
    public void setTaskId(int taksId) throws RemoteException;
}
