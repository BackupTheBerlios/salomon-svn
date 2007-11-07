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

package salomon.engine.controller.gui;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.platform.IManagerEngine;
import salomon.engine.remote.IRemoteController;
import salomon.engine.remote.ManagerEngineProxy;

/**
 * Class is graphic representation of client controllers connected to server.
 * It is used to display them on the list of connected clients.
 */
public final class RemoteControllerGUI
{

    /**
     * 
     * @uml.property name="_managerEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IManagerEngine _managerEngine;

    /**
     * 
     * @uml.property name="_remoteController"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemoteController _remoteController;

    /**
     *  
     */
    public RemoteControllerGUI(IRemoteController remoteController)
    {
        _remoteController = remoteController;
        try {
            _managerEngine = new ManagerEngineProxy(
                    _remoteController.getManagerEngine());
        } catch (RemoteException e) {
            LOGGER.fatal("", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object)
    {
        boolean areEqual = false;
        if (object instanceof RemoteControllerGUI) {
            RemoteControllerGUI controllerGUI = (RemoteControllerGUI) object;
            areEqual = _remoteController.equals(controllerGUI._remoteController);
        } else {
            areEqual = false;
        }

        return areEqual;
    }

    /**
     * @return Returns the controller.
     */
    public IRemoteController getController()
    {
        return _remoteController;
    }

    public String getDescription()
    {
        String description = null;

        try {
            description = _remoteController.getDescription();
        } catch (RemoteException e) {
            LOGGER.fatal("", e);
            description = e.getLocalizedMessage();
        }

        return description;
    }

    public IManagerEngine getManagerEngine()
    {
        return _managerEngine;
    }

    public String getName()
    {
        String name = null;
        try {
            name = _remoteController.getName();
        } catch (RemoteException e) {
            LOGGER.fatal("", e);
            name = e.getLocalizedMessage();
        }

        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return _remoteController.hashCode();
    }

    @Override
    public String toString()
    {
        String name;
        try {
            name = _remoteController.getName();
        } catch (RemoteException e) {
            LOGGER.fatal("", e);
            name = e.getLocalizedMessage();
        }
        return name;
    }

    public void exit()
    {
        LOGGER.debug("RemoteController.exit()");
        try {
            _remoteController.exit();
        } catch (RemoteException e) {
            LOGGER.debug(e.getLocalizedMessage());
        }
    }

    private static final Logger LOGGER = Logger.getLogger(RemoteControllerGUI.class);
}