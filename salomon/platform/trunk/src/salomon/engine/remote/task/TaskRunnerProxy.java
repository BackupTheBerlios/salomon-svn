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

import org.apache.log4j.Logger;

import salomon.engine.task.ITaskRunner;
import salomon.platform.exception.PlatformException;

/**
 * @see salomon.engine.remote.task.IRemoteTaskRunner
 */
public final class TaskRunnerProxy implements ITaskRunner
{
	private IRemoteTaskRunner _remoteTaskRunner;

	/**
	 * 
	 */
	public TaskRunnerProxy(IRemoteTaskRunner remoteTaskRunner)
	{
		_remoteTaskRunner = remoteTaskRunner;
	}

	/**
	 * @see salomon.engine.task.ITaskRunner#pause()
	 */
	public void pause() throws PlatformException
	{
        try {
            _remoteTaskRunner.pause();
        } catch (RemoteException e) {
            LOGGER.error("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
	}

	/**
	 * @see salomon.engine.task.ITaskRunner#resume()
	 */
	public void resume() throws PlatformException
	{
        try {
            _remoteTaskRunner.resume();
        } catch (RemoteException e) {
            LOGGER.error("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
	}

	/**
	 * @see salomon.engine.task.ITaskRunner#start()
	 */
	public void start() throws PlatformException
	{
		try {
			_remoteTaskRunner.start();
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see salomon.engine.task.ITaskRunner#stop()
	 */
	public void stop() throws PlatformException
	{
        try {
            _remoteTaskRunner.stop();
        } catch (RemoteException e) {
            LOGGER.error("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
	}

	private static final Logger LOGGER = Logger.getLogger(TaskRunnerProxy.class);

}
