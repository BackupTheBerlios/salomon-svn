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

import java.rmi.Remote;
import java.rmi.RemoteException;

import salomon.engine.task.ITask;

import salomon.platform.exception.PlatformException;


/**
 * Remote version of <code>ITaskManager</code> interface. It has all methods from
 * ITaskManager interface, but adds throwing RemoteException declaration to each
 * of methods.
 * 
 * @see salomon.engine.task.ITaskManager
 */
public interface IRemoteTaskManager extends Remote
{
    /**
     * @see salomon.engine.task.ITaskManager#addTask(ITask)
     * 
     * @param task
     * @throws PlatformException
     */
    void addTask(IRemoteTask task) throws RemoteException, PlatformException;
    
    /**
     * @see salomon.engine.task.ITaskManager#clearTaskList()
     * 
     * @throws RemoteException
     * @throws PlatformException
     */
	void clearTaskList() throws RemoteException, PlatformException;

    /**
     * @see salomon.engine.task.ITaskManager#createTask()
     */
	IRemoteTask createTask() throws RemoteException, PlatformException;

    /**
     * @see salomon.engine.task.ITaskManager#getCurrentTask()
     */
	IRemoteTask getCurrentTask() throws RemoteException, PlatformException;


    /**
     * @see salomon.engine.task.ITaskManager#getRunner()
     */
    IRemoteTaskRunner getRunner() throws RemoteException, PlatformException;

    /**
     * @see salomon.engine.task.ITaskManager#getTasks()
     */
	IRemoteTask[] getTasks() throws RemoteException, PlatformException;

    /**
     * @see salomon.engine.task.ITaskManager#start()
     */
	void start() throws RemoteException, PlatformException;
}