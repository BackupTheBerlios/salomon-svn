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

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.project.IProject;
import salomon.engine.remote.task.TaskManagerProxy;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;


/**
 * Class is a sever side wrapper of IRemoteProject object. It implements
 * IProject interface and delegates methods execution to remote object catching
 * all RemoteExceptions.
 * 
 * @see salomon.engine.remote.project.IRemoteProject
 */
public final class ProjectProxy implements IProject
{
	private IRemoteProject _remoteProject;

	private ITaskManager _taskManagerProxy;

	/**
	 * @pre $none
	 * @post $none
	 */
	public ProjectProxy(IRemoteProject remoteProject)
	{
		_remoteProject = remoteProject;
	}

	/**
	 * @see IProject#getInfo()
	 */
	public String getInfo() throws PlatformException
	{
		String info = null;
		try {
			info = _remoteProject.getInfo();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return info;
	}

	/**
	 * @see IProject#getName()
	 */
	public String getName() throws PlatformException
	{
		String name = null;
		try {
			name = _remoteProject.getName();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return name;
	}

	/**
	 * @see IProject#getProjectID()
	 */
	public int getProjectID() throws PlatformException
	{
		int id = -1;
		try {
			id = _remoteProject.getProjectID();
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return id;
	}

	/**
	 * @see salomon.engine.project.IProject#getTaskManager()
	 */
	public ITaskManager getTaskManager() throws PlatformException
	{
		if (_taskManagerProxy == null) {
			try {
				_taskManagerProxy = new TaskManagerProxy(
						_remoteProject.getTaskManager());
			} catch (RemoteException e) {
				LOGGER.error("Remote error!");
				throw new PlatformException(e.getLocalizedMessage());
			}
		}

		return _taskManagerProxy;
	}

	/**
	 * @see IProject#setInfo(java.lang.String)
	 */
	public void setInfo(String info) throws PlatformException
	{
		try {
			_remoteProject.setInfo(info);
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see IProject#setName(java.lang.String)
	 */
	public void setName(String name) throws PlatformException
	{
		try {
			_remoteProject.setName(name);
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see IProject#setProjectID(int)
	 */
	public void setProjectID(int projectId) throws PlatformException
	{
		try {
			_remoteProject.setProjectID(projectId);
		} catch (RemoteException e) {
			LOGGER.fatal("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

	}

	IRemoteProject getRemoteProject()
	{
		return _remoteProject;
	}

	private static final Logger LOGGER = Logger.getLogger(ProjectProxy.class);

}