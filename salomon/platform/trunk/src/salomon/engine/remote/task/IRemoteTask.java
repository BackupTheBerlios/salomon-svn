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

package salomon.engine.remote.task;

import java.net.URL;
import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;

/**
 * Remote version of ITask interface. It has all methods from ITask interface,
 * but adds throwing RemoteException declaration to each of methods.
 * 
 * @see salomon.engine.task.ITask
 */
public interface IRemoteTask extends Remote
{
	/**
	 * @see salomon.engine.task.ITask#getName()
	 * 
	 * @return Returns the name.
	 * @pre $none
	 * @post $none
	 */
	String getName() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#getPlugin()
	 * 
	 * @return Returns the _plugin.
	 * @pre $none
	 * @post $none
	 */
	URL getPlugin() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#getResult()
	 * 
	 * @return Returns the _result.
	 * @pre $none
	 * @post $none
	 */
	IResult getResult() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#getSettings()
	 * 
	 * @return Returns the _settings.
	 * @pre $none
	 * @post $none
	 */
	ISettings getSettings() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#getStatus()
	 * 
	 * @return Returns the status.
	 * @pre $none
	 * @post $none
	 */
	String getStatus() throws RemoteException, PlatformException;

	/**
	 * @return Returns the taksId.
	 * @pre $none
	 * @post $none
	 */
	int getTaskId() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#setName(String)
	 * 
	 * @param name The name to set.
	 * @pre $none
	 * @post $none
	 */
	void setName(String name) throws RemoteException, PlatformException;

	/**
	 * 
	 * @pre $none
	 * @post $none
	 */
	void setPlugin(URL url) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#setResult(IResult)
	 * 
	 * @pre $none
	 * @post $none
	 */
	void setResult(IResult result) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#setSettings(ISettings)
	 * 
	 * @pre $none
	 * @post $none
	 */
	void setSettings(ISettings settings) throws RemoteException,
			PlatformException;

	void setSettings(String settings) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#setStatus(String)
	 * 
	 * @param status The status to set.
	 * @pre $none
	 * @post $none
	 */
	void setStatus(String status) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.task.ITask#setTaskId(int)
	 * 
	 * @param taksId The taksId to set.
	 * @pre $none
	 * @post $none
	 */
	void setTaskId(int taksId) throws RemoteException, PlatformException;
}