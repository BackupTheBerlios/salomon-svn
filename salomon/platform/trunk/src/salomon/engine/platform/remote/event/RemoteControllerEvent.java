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

package salomon.engine.platform.remote.event;

import java.util.EventObject;

import salomon.engine.platform.remote.IRemoteController;
import salomon.engine.platform.remote.CentralController;

/**
 * An event used to notify about client's connection or disconnection from
 * server.
 */
public final class RemoteControllerEvent extends EventObject
{
	private IRemoteController _controller;

	/**
	 * @pre $none
	 * @post $none
	 */
	public RemoteControllerEvent(CentralController source,
			IRemoteController controller)
	{
		super(source);
		_controller = controller;

	}

	public IRemoteController getController()
	{
		return _controller;
	}
}