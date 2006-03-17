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

package salomon.engine.remote.plugin;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.plugin.ILocalPlugin;
import salomon.engine.plugin.IPluginManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;

/**
 * Class is a sever side wrapper of IRemotePluginManager object. It implements
 * IPluginManager interface and delegates methods execution to remote object
 * catching all RemoteExceptions.
 * 
 * @see salomon.engine.remote.plugin.IRemotePluginManager
 */
public final class PluginManagerProxy implements IPluginManager
{

    /**
     * 
     * @uml.property name="_remotePluginManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemotePluginManager _remotePluginManager;

    /**
     * @pre $none
     * @post $none
     */
    public PluginManagerProxy(IRemotePluginManager remotePluginManager)
    {
        _remotePluginManager = remotePluginManager;
    }

    public void addPlugin(ILocalPlugin plugin) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method addPlugin() not implemented yet!");
    }

    /**
     * @see salomon.engine.plugin.IPluginManager#addPlugin(salomon.plugin.IPlugin)
     */
    public void addPlugin(IPlugin plugin)
    {
        throw new UnsupportedOperationException(
                "Method addPlugin() not implemented yet!");
    }

    public void clearPluginList() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method clearPluginList() not implemented yet!");
    }

    public ILocalPlugin createPlugin() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method createPlugin() not implemented yet!");
    }

    public ILocalPlugin getPlugin(int id) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getPlugin() not implemented yet!");
    }

    /**
     * @see IPluginManager#getPlugins()
     */
    public ILocalPlugin[] getPlugins() throws PlatformException
    {
        ILocalPlugin[] result = null;
        try {
            result = (ILocalPlugin[]) _remotePluginManager.getPlugins();
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return result;
    }

    public boolean removeAll() throws PlatformException
    {
        boolean result = true;
        for (ILocalPlugin p : this.getPlugins()) {
            if (!this.removePlugin(p))
                result = false;
        }

        return result;
    }

    public boolean removePlugin(ILocalPlugin plugin) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method removePlugin() not implemented yet!");
    }

    /**
     * @see IPluginManager#removePlugin(IPlugin)
     */
    public boolean removePlugin(IPlugin plugin) throws PlatformException
    {

        boolean result = false;
        try {
            result = _remotePluginManager.removePlugin(plugin);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return result;
    }

    public boolean savePlugin(ILocalPlugin plugin) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method savePlugin() not implemented yet!");
    }

    /**
     * @see IPluginManager#savePlugin(IPlugin)
     */
    public boolean savePlugin(IPlugin plugin) throws PlatformException
    {
        boolean result = false;
        try {
            result = _remotePluginManager.savePlugin(plugin);
        } catch (RemoteException e) {
            LOGGER.fatal("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return result;
    }

    private static final Logger LOGGER = Logger.getLogger(PluginManagerProxy.class);

}