/*
 * Copyright (C) 2007 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.communication;

import java.util.LinkedList;
import java.util.List;

import salomon.communication.ICommunicationBus;
import salomon.communication.ICommunicationEvent;
import salomon.communication.ICommunicationListener;

/**
 * 
 */
public class CommunicationBus implements ICommunicationBus
{
    private List<ICommunicationListener> _listeners;

    public CommunicationBus()
    {
        _listeners = new LinkedList<ICommunicationListener>();
    }

    /**
     * @see salomon.communication.ICommunicationBus#addCommunicationListener(salomon.communication.ICommunicationListener)
     */
    public void addCommunicationListener(ICommunicationListener listener)
    {
        _listeners.add(listener);
    }

    /**
     * @see salomon.communication.ICommunicationBus#fireCommunicationEvent(salomon.communication.ICommunicationEvent)
     */
    public void fireCommunicationEvent(ICommunicationEvent event)
    {
        for (ICommunicationListener listener : _listeners) {
            listener.onCommunicationEvent(event);
        }
    }

    /**
     * @see salomon.communication.ICommunicationBus#removeCommunicationListener(salomon.communication.ICommunicationListener)
     */
    public void removeCommunicationListener(ICommunicationListener listener)
    {
        _listeners.remove(listener);
    }

}
