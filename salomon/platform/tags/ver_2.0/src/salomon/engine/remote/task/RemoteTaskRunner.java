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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import salomon.engine.task.ITaskRunner;
import salomon.platform.exception.PlatformException;

/**
 * @see salomon.engine.task.ITaskRunner
 */
public final class RemoteTaskRunner extends UnicastRemoteObject
        implements IRemoteTaskRunner
{

    /**
     * 
     * @uml.property name="_taskRunner"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ITaskRunner _taskRunner;

    /**
     * 
     */
    public RemoteTaskRunner(ITaskRunner taskRunner) throws RemoteException
    {
        _taskRunner = taskRunner;
    }

    /**
     * @see salomon.engine.remote.task.IRemoteTaskRunner#pause()
     */
    public void pause() throws PlatformException, RemoteException
    {
        _taskRunner.pause();
    }

    /**
     * @see salomon.engine.remote.task.IRemoteTaskRunner#resume()
     */
    public void resume() throws PlatformException, RemoteException
    {
        _taskRunner.resume();
    }

    /**
     * @see salomon.engine.remote.task.IRemoteTaskRunner#start()
     */
    public void start() throws PlatformException, RemoteException
    {
        _taskRunner.start();
    }

    /**
     * @see salomon.engine.remote.task.IRemoteTaskRunner#stop()
     */
    public void stop() throws PlatformException, RemoteException
    {
        _taskRunner.stop();
    }

}
