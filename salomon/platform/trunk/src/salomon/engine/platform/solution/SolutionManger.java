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

package salomon.engine.platform.solution;

import salomon.platform.solution.ISolution;
import salomon.platform.solution.ISolutionManager;

/**
 * 
 */
public final class SolutionManger implements ISolutionManager
{

	/**
	 * @see salomon.platform.solution.ISolutionManager#getSolutions()
	 */
	public ISolution[] getSolutions()
	{
		throw new UnsupportedOperationException("Method getSolutions() not implemented yet!");
	}

	/**
	 * @see salomon.platform.solution.ISolutionManager#getSolution(java.lang.String)
	 */
	public ISolution getSolution(String name)
	{
		throw new UnsupportedOperationException("Method getSolution() not implemented yet!");
	}

	/**
	 * @see salomon.platform.solution.ISolutionManager#createSolution()
	 */
	public ISolution createSolution()
	{
		throw new UnsupportedOperationException("Method createSolution() not implemented yet!");
	}

	/**
	 * @see salomon.platform.solution.ISolutionManager#addSolution(salomon.platform.solution.ISolution)
	 */
	public void addSolution(ISolution solution)
	{
		throw new UnsupportedOperationException("Method addSolution() not implemented yet!");
	}
}
