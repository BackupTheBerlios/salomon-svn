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
	 * @pre $none
	 * @post $none
	 */
	void setPlugin(URL url) throws RemoteException;

	/**
	 * @pre $none
	 * @post $none
	 */
	void setResult(IResult result) throws RemoteException;

	/**
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