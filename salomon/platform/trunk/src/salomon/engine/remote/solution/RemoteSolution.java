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

package salomon.engine.remote.solution;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.engine.remote.project.IRemoteProjectManager;
import salomon.engine.remote.project.RemoteProjectManager;
import salomon.engine.solution.ISolution;
import salomon.platform.exception.PlatformException;


/**
 * @see salomon.engine.solution.ISolution
 */
public final class RemoteSolution extends UnicastRemoteObject
		implements IRemoteSolution
{
	private IRemoteProjectManager _remoteProjectManager;

	private ISolution _solution;

	/**
	 * @throws RemoteException
	 */
	RemoteSolution(ISolution solution) throws RemoteException
	{
		_solution = solution;
	}

	/**
	 * @see IRemoteSolution#getProjectManager()
	 */
	public IRemoteProjectManager getProjectManager() throws PlatformException,
			RemoteException
	{
		if (_remoteProjectManager == null) {
			_remoteProjectManager = new RemoteProjectManager(
					_solution.getProjectManager());
		}

		return _remoteProjectManager;
	}
    
    ISolution getSolution()
    {
    	return _solution;
    }

}
