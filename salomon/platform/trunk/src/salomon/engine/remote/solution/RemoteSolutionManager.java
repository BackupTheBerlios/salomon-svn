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
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;

import salomon.platform.exception.PlatformException;

/**
 * @see salomon.engine.solution.ISolutionManager
 */
public final class RemoteSolutionManager extends UnicastRemoteObject
		implements IRemoteSolutionManager
{
	private Map<ISolution, IRemoteSolution> _proxies = new HashMap<ISolution, IRemoteSolution>();
    
	private ISolutionManager _solutionManager;

	/**
	 * @throws RemoteException
	 */
	public RemoteSolutionManager(ISolutionManager solutionManager)
			throws RemoteException
	{
		_solutionManager = solutionManager;
	}

	/**
	 * @see IRemoteSolutionManager#addSolution(salomon.engine.platform.remote.solution.IRemoteSolution)
	 */
	public void addSolution(IRemoteSolution solution) throws PlatformException,
			RemoteException
	{
        RemoteSolution remoteSolution = (RemoteSolution) solution;
        _solutionManager.addSolution(remoteSolution.getSolution());
	}

	/**
	 * @see IRemoteSolutionManager#createSolution()
	 */
	public IRemoteSolution createSolution() throws PlatformException,
			RemoteException
	{
		return getRemoteSolution(_solutionManager.createSolution());
	}

	/**
	 * @see IRemoteSolutionManager#getSolution(java.lang.String)
	 */
	public IRemoteSolution getSolution(String name) throws PlatformException,
			RemoteException
	{
		return getRemoteSolution(_solutionManager.getSolution(name));
	}

	/**
	 * @see IRemoteSolutionManager#getSolutions()
	 */
	public IRemoteSolution[] getSolutions() throws PlatformException,
			RemoteException
	{
		ISolution[] solutions = _solutionManager.getSolutions();
		IRemoteSolution[] remoteSolutions = new IRemoteSolution[solutions.length];
		for (int i = 0; i < solutions.length; i++) {
			remoteSolutions[i] = getRemoteSolution(solutions[i]);
		}

		return remoteSolutions;
	}

	private IRemoteSolution getRemoteSolution(ISolution solution)
			throws PlatformException
	{
		IRemoteSolution remoteSolution = null;
		if (_proxies.containsKey(solution)) {
			remoteSolution = _proxies.get(solution);
		} else {
			try {
				remoteSolution = new RemoteSolution(solution);
				_proxies.put(solution, remoteSolution);
			} catch (RemoteException e) {
				LOGGER.error("Remote error!", e);
				throw new PlatformException(e.getLocalizedMessage());
			}
		}

		return remoteSolution;
	}

	private static final Logger LOGGER = Logger.getLogger(RemoteSolutionManager.class);
}
