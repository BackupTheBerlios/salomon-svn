
package salomon.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import salomon.platform.remote.event.IMasterControllerListener;
import salomon.platform.remote.event.RemoteControllerEvent;

/**
 * Class implements ICentralController interface. It is responsible for
 * establishing connection and disconnecting of remote clients.
 *  
 */
public final class CentralController extends UnicastRemoteObject
		implements ICentralController
{

	private List _listeners = new LinkedList();

	private Set _remoteControllers = new HashSet();

	/**
	 * @throws RemoteException
	 * @pre $none
	 * @post $none
	 */
	public CentralController() throws RemoteException
	{
	}

	public void addMasterControllerListener(IMasterControllerListener listener)
	{
		_listeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.ICentralController#register()
	 */
	public void register(IRemoteController remoteController)
			throws RemoteException
	{
		_remoteControllers.add(remoteController);
		fireControllerAdded(remoteController);
	}

	public void removeMasterControllerListener(
			IMasterControllerListener listener)
	{
		_listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.platform.ICentralController#unregister()
	 */
	public void unregister(IRemoteController remoteController)
			throws RemoteException
	{
		_remoteControllers.remove(remoteController);
		//commented to avoid loop while finishing client application
		//fireControllerRemoved(remoteController);
	}

	private void fireControllerAdded(IRemoteController controller)
	{
		RemoteControllerEvent event = new RemoteControllerEvent(this,
				controller);

		for (Iterator iter = _listeners.iterator(); iter.hasNext();) {
			IMasterControllerListener listener = (IMasterControllerListener) iter.next();
			listener.controllerAdded(event);
		}
	}

	private void fireControllerRemoved(IRemoteController controller)
	{
		RemoteControllerEvent event = new RemoteControllerEvent(this,
				controller);

		for (Iterator iter = _listeners.iterator(); iter.hasNext();) {
			IMasterControllerListener listener = (IMasterControllerListener) iter.next();
			listener.controllerRemoved(event);
		}
	}
}