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

package salomon.engine.holder;

import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class SolutionManagerHolder implements ISolutionManager
{
	private ISolutionManager _currentSolutionManager;

	SolutionManagerHolder(ISolutionManager currentSolutionManager)
	{
		_currentSolutionManager = currentSolutionManager;
	}

	/**
	 * @see salomon.engine.solution.ISolutionManager#addSolution(salomon.engine.solution.ISolution)
	 */
	public void addSolution(ISolution solution) throws PlatformException
	{
		_currentSolutionManager.addSolution(solution);
	}

	/**
	 * @see salomon.engine.solution.ISolutionManager#createSolution()
	 */
	public ISolution createSolution() throws PlatformException
	{
		return _currentSolutionManager.createSolution();
	}

	/**
	 * @see salomon.engine.solution.ISolutionManager#getSolutions()
	 */
	public ISolution[] getSolutions() throws PlatformException
	{
		return _currentSolutionManager.getSolutions();
	}

	void setCurrent(ISolutionManager currentSolutionManager)
	{
		_currentSolutionManager = currentSolutionManager;
	}

	public ISolution getSolution(int id) throws PlatformException
	{
		return _currentSolutionManager.getSolution(id);
	}

	public ISolution getCurrentSolution() throws PlatformException
	{
		return _currentSolutionManager.getCurrentSolution();
	}

}
