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

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote version of IManagerEngine interface. It has all methods from
 * IManagerEngine interface, but adds throwing RemoteException declaration to
 * each of methods.
 *  
 */
public interface IRemoteManagerEngine extends Remote
{
	IRemoteTaskManager getTasksManager() throws RemoteException;

	/**
	 * @return Returns the pluginManager.
	 * @pre $none
	 * @post $result != null
	 */
	IRemotePluginManager getPluginManager() throws RemoteException;

	/**
	 * @return Returns the projectManager.
	 * @pre $none
	 * @post $result != null
	 */
	IRemoteProjectManager getProjectManager() throws RemoteException;
}