/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.platform.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import salomon.engine.platform.remote.event.IMasterControllerListener;
import salomon.engine.platform.remote.event.RemoteControllerEvent;

/**
 * Class implements ICentralController interface. It is responsible for
 * establishing connection and disconnecting of remote clients.
 * 
 */
public final class CentralController extends UnicastRemoteObject
		implements ICentralController
{
	private List<IMasterControllerListener> _listeners = new LinkedList<IMasterControllerListener>();

	private Set _remoteControllers = new HashSet();

	/**
	 * @throws RemoteException
	 * @pre $none
	 * @post $none
	 */
	public CentralController() throws RemoteException
	{
		// empty body
	}

	public void addMasterControllerListener(IMasterControllerListener listener)
	{
		_listeners.add(listener);
	}

	/**
	 * @see ICentralController#register(IRemoteController)
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

	/**
	 * @see ICentralController#unregister(IRemoteController)
	 */
	public void unregister(IRemoteController remoteController)
			throws RemoteException
	{
		_remoteControllers.remove(remoteController);
		// commented to avoid loop while finishing client application
		// fireControllerRemoved(remoteController);
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