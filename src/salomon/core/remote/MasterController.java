
package salomon.core.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import salomon.core.remote.event.RemoteControllerEvent;
import salomon.core.remote.event.IMasterControllerListener;


/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public final class MasterController extends UnicastRemoteObject
		implements IMasterController
{
	private Set _remoteControllers = new HashSet();
    private List _listeners = new LinkedList();

	/**
	 * @throws RemoteException
	 *  
	 */
	public MasterController() throws RemoteException
	{
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IMasterController#register()
	 */
	public void register(IRemoteController remoteController)
			throws RemoteException
	{
		_remoteControllers.add(remoteController);
        fireControllerAdded(remoteController);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.core.IMasterController#unregister()
	 */
	public void unregister(IRemoteController remoteController)
			throws RemoteException
	{
		_remoteControllers.remove(remoteController);
        fireControllerRemoved(remoteController);
	}
    
    private void fireControllerAdded(IRemoteController controller)
    {
        RemoteControllerEvent event = new RemoteControllerEvent(this, controller);
        
        for (Iterator iter = _listeners.iterator(); iter.hasNext(); ) {
        	IMasterControllerListener listener = (IMasterControllerListener) iter.next();
            listener.controllerAdded(event);
        }
    }
    
    private void fireControllerRemoved(IRemoteController controller)
    {
        RemoteControllerEvent event = new RemoteControllerEvent(this, controller);
        
        for (Iterator iter = _listeners.iterator(); iter.hasNext(); ) {
            IMasterControllerListener listener = (IMasterControllerListener) iter.next();
            listener.controllerRemoved(event);
        }
    }
}