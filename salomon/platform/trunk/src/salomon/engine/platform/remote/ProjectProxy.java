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

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;


/**
 * Class is a sever side wrapper of IRemoteProject object. It implements
 * IProject interface and delegates methods execution to remote object catching
 * all RemoteExceptions.
 */
public final class ProjectProxy implements IProject
{
	private IRemoteProject _remoteProject;

	/**
	 * @pre $none
	 * @post $none
	 */
	public ProjectProxy(IRemoteProject remoteProject)
	{
		_remoteProject = remoteProject;
	}

	/**
	 * @see salomon.engine.platform.project.IProject#getInfo()
	 */
	public String getInfo()
	{
		String info = null;
		try {
			info = _remoteProject.getInfo();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return info;
	}

	/**
	 * @see salomon.engine.platform.project.IProject#getName()
	 */
	public String getName()
	{
		String name = null;
		try {
			name = _remoteProject.getName();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return name;
	}

	/**
	 * @see salomon.engine.platform.project.IProject#getProjectID()
	 */
	public int getProjectID()
	{
		int id = -1;
		try {
			id = _remoteProject.getProjectID();
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
		return id;
	}

	/**
	 * @see salomon.engine.platform.project.IProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info)
	{
		try {
			_remoteProject.setInfo(info);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.project.IProject#setName(java.lang.String)
	 */
	public void setName(String name)
	{
		try {
			_remoteProject.setName(name);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * @see salomon.engine.platform.project.IProject#setProjectID(int)
	 */
	public void setProjectID(int projectId)
	{
		try {
			_remoteProject.setProjectID(projectId);
		} catch (RemoteException e) {
			LOGGER.fatal("", e);
		}

	}

	private static final Logger LOGGER = Logger.getLogger(ProjectProxy.class);

}