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
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;
import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

public final class SolutionManagerProxy implements ISolutionManager
{
	private Map<IRemoteSolution, ISolution> _remotes = new HashMap<IRemoteSolution, ISolution>();

	/**
	 * 
	 * @uml.property name="_remoteSolutionManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IRemoteSolutionManager _remoteSolutionManager;

	/**
	 * 
	 */
	public SolutionManagerProxy(IRemoteSolutionManager remoteSolutionManager)
	{
		_remoteSolutionManager = remoteSolutionManager;
	}

	/**
	 * @see ISolutionManager#addSolution(salomon.engine.solution.ISolution)
	 * @pre solution instanceof SolutionProxy 
	 */
	public void addSolution(ISolution solution) throws PlatformException
	{
		SolutionProxy solutionProxy = (SolutionProxy) solution;

		try {
			_remoteSolutionManager.addSolution(solutionProxy.getRemoteSolution());
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}
	}

	/**
	 * @see ISolutionManager#createSolution()
	 */
	public ISolution createSolution() throws PlatformException
	{
		ISolution solution = null;
		try {
			IRemoteSolution remoteSolution = _remoteSolutionManager.createSolution();
			solution = getSolution(remoteSolution);
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return solution;
	}

	/**
	 * @see ISolutionManager#getSolution(java.lang.String)
	 */
	public ISolution getSolution(String name) throws PlatformException
	{
		ISolution solution = null;
		try {
			IRemoteSolution remoteSolution = _remoteSolutionManager.getSolution(name);
			solution = getSolution(remoteSolution);
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return solution;
	}

	/**
	 * @see ISolutionManager#getSolutions()
	 */
	public ISolution[] getSolutions() throws PlatformException
	{
		ISolution[] solutions = null;
		try {
			IRemoteSolution[] remoteSolutions = _remoteSolutionManager.getSolutions();
            solutions = new ISolution[remoteSolutions.length];
            for (int i = 0; i < remoteSolutions.length; i++) {
                solutions[i] = getSolution(remoteSolutions[i]);				
			}
		} catch (RemoteException e) {
			LOGGER.error("Remote error!", e);
			throw new PlatformException(e.getLocalizedMessage());
		}

		return solutions;
	}

	private ISolution getSolution(IRemoteSolution remoteSolution)
	{
		ISolution solution = null;
		if (_remotes.containsKey(remoteSolution)) {
			solution = _remotes.get(remoteSolution);
		} else {
			solution = new SolutionProxy(remoteSolution);
			_remotes.put(remoteSolution, solution);
		}

		return solution;
	}

	private static final Logger LOGGER = Logger.getLogger(SolutionManagerProxy.class);

	public ISolution getSolution(int id) throws PlatformException 
	{
		throw new UnsupportedOperationException(
		"Method salomon.engine.remote.solution::SolutionManagerProxy::getSolution()not implemented yet!");
	}

	public ISolution getCurrentSolution() throws PlatformException 
	{
		throw new UnsupportedOperationException(
		"Method salomon.engine.remote.solution::SolutionManagerProxy::getCurrentSolution()not implemented yet!");
	}

	public ISolution getSolution(IUniqueId id) throws PlatformException 
	{
		throw new UnsupportedOperationException(
		"Method salomon.engine.remote.solution::SolutionManagerProxy::getSolution()not implemented yet!");
	}
}
