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

package salomon.engine.remote.solution;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import salomon.engine.project.IProjectManager;
import salomon.engine.remote.project.ProjectManagerProxy;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

public final class SolutionProxy implements ISolution
{

    /**
     * 
     * @uml.property name="_projectManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IProjectManager _projectManager;

    /**
     * 
     * @uml.property name="_remoteSolution"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IRemoteSolution _remoteSolution;

    /**
     * 
     */
    public SolutionProxy(IRemoteSolution remoteSolution)
    {
        _remoteSolution = remoteSolution;
    }

    /**
     * @see salomon.engine.solution.ISolution#getDataEngine()
     */
    public IDataEngine getDataEngine() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getManagerEngine() not implemented yet!");
    }

    /**
     * @see ISolution#getProjectManager()
     */
    public IProjectManager getProjectManager() throws PlatformException
    {
        try {
            if (_projectManager == null) {
                _projectManager = new ProjectManagerProxy(
                        _remoteSolution.getProjectManager());
            }
        } catch (RemoteException e) {
            LOGGER.error("Remote error!", e);
            throw new PlatformException(e.getLocalizedMessage());
        }

        return _projectManager;
    }

    IRemoteSolution getRemoteSolution()
    {
        return _remoteSolution;
    }

    private static final Logger LOGGER = Logger.getLogger(SolutionProxy.class);

    public IInfo getInfo() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method salomon.engine.remote.solution::SolutionProxy::getInfo()not implemented yet!");
    }

}
