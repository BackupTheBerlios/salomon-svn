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

package salomon.engine.platform.remote.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.engine.project.IProject;

import salomon.platform.exception.PlatformException;

/** 
 * Class representing remote instance of IProject.
 * 
 * @see salomon.engine.project.IProject
 */
public final class RemoteProject extends UnicastRemoteObject
		implements IRemoteProject
{
	private IProject _project;

	/**
	 * @pre project != null
	 * @post $none
	 */
	public RemoteProject(IProject project) throws RemoteException
	{
		_project = project;
	}

	/**
	 * @see IRemoteProject#getInfo()
	 */
	public String getInfo() throws RemoteException, PlatformException
	{
		return _project.getInfo();
	}

	/**
	 * @see IRemoteProject#getName()
	 */
	public String getName() throws RemoteException, PlatformException
	{
		return _project.getName();
	}

	/**
	 * @see IRemoteProject#getProjectID()
	 */
	public int getProjectID() throws RemoteException, PlatformException
	{
		return _project.getProjectID();
	}

	/**
	 * @see IRemoteProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info) throws RemoteException, PlatformException
	{
		_project.setInfo(info);
	}

	/**
	 * @see IRemoteProject#setName(java.lang.String)
	 */
	public void setName(String name) throws RemoteException, PlatformException
	{
		_project.setName(name);
	}

	/**
	 * @see IRemoteProject#setProjectID(int)
	 */
	public void setProjectID(int projectId) throws RemoteException,
			PlatformException
	{
		_project.setProjectID(projectId);
	}

	IProject getProject()
	{
		return _project;
	}
}