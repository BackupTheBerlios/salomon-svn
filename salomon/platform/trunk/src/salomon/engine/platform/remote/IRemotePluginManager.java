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
import java.util.Collection;

import salomon.plugin.Description;
import salomon.plugin.IPlugin;

/**
 * Remote version of IPluginManager interface. It has all methods from
 * IPluginManager interface, but adds throwing RemoteException declaration to
 * each of methods.
 *  
 */
public interface IRemotePluginManager extends Remote
{
    /**
     * FIXME
     * TODO: Return array of remote plugin representation.
     * @return The array of plugins
     * @throws RemoteException
     */
	IPlugin[] getPlugins() throws RemoteException;

	boolean savePlugin(Description description) throws RemoteException;

	boolean removePlugin(Description description) throws RemoteException;
	//    public IRemotePlugin getPlugin(URL url) throws RemoteException;
}