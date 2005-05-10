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

package salomon.engine.solution;

import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

/**
 * Manager of solutions.
 * 
 * @see salomon.engine.solution.ISolution
 */
public interface ISolutionManager
{
	/**
	 * Returns all solutions.
	 * 
	 * @return The array of all solutions
     * @throws PlatformException
	 */
	ISolution[] getSolutions() throws PlatformException;

	/**
	 * Returns solution with given identifier.
	 * 
	 * @param id identifier of solution
	 * @return The solution
	 * @throws PlatformException
	 */
	ISolution getSolution(int id) throws PlatformException;

	/**
	 * Creates an empty solution, but doesn't add it to database.
     * Use <code>addSolution</code> method to store this solution in the database. 
	 * @see #addSolution(ISolution)
	 * @return The empty solution
	 * @throws PlatformException
	 */
	ISolution createSolution() throws PlatformException;

    /**
     * Stores the solution in the database.
     * @see #createSolution()
     * 
     * @param solution The solution
     * @throws PlatformException
     */
	void addSolution(ISolution solution) throws PlatformException;
	
	/**
	 * Returns current solution.
	 * 
	 * @return
	 * @throws PlatformException
	 */
	ISolution getCurrentSolution() throws PlatformException;
}
