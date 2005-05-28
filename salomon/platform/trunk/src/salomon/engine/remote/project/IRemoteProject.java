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

package salomon.engine.remote.project;

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.engine.remote.task.IRemoteTaskManager;
import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/** * Remote version of <code>IProject</code> interface. * It has all methods from IProject interface,  * but adds throwing RemoteException declaration to each of methods. *  * @see salomon.engine.project.IProject */
public interface IRemoteProject extends Remote
{

	/**
	 * @see salomon.engine.project.IProject#getInfo()
	 * 
	 * @return Returns the info.
	 * @pre $none
	 * @post $result != null
	 * 
	 * @uml.property name="info"
	 */
	IInfo getInfo() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.project.IProject#getName()
	 * 
	 * @return Returns the name.
	 * @pre $none
	 * @post $result != null
	 * 
	 * @uml.property name="name"
	 */
	String getName() throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.project.IProject#getProjectID()
	 * 
	 * @return Returns the projectID.
	 * @pre $none
	 * @post $none
	 * 
	 * @uml.property name="projectID"
	 */
	int getProjectID() throws RemoteException, PlatformException;


	/**
	 * @see salomon.engine.project.IProject#getTaskManager()
	 */
	IRemoteTaskManager getTaskManager() throws RemoteException,
			PlatformException;

	/**
	 * @see salomon.engine.project.IProject#setInfo(String)
	 * 
	 * @param info The info to set.
	 * @pre info != null
	 * @post $none
	 * 
	 * @uml.property name="info"
	 */
	void setInfo(String info) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.project.IProject#setName(String)
	 * 
	 * @param name The name to set.
	 * @pre name != null
	 * @post $none
	 * 
	 * @uml.property name="name"
	 */
	void setName(String name) throws RemoteException, PlatformException;

	/**
	 * @see salomon.engine.project.IProject#setProjectID(int)
	 * 
	 * @param projectId The projectID to set.
	 * @pre $none
	 * @post $none
	 * 
	 * @uml.property name="projectID"
	 */
	void setProjectID(int projectId) throws RemoteException, PlatformException;

}