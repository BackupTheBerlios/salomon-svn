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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.project.IProjectManager;

public class SolutionTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="_solution"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private Solution _solution;

	public void testEverything()
	{

	}

	public void testGetProjectManager()
	{
		LOGGER.info("SolutionTest.testGetProjectManager()");
		boolean success = false;
		try {
			IProjectManager pm = _solution.getProjectManager();
			assertFalse(pm == null);
			success = true;
		} catch (Exception e) {
			LOGGER.fatal("", e);
		}
		assertTrue(success);
	}

	protected void setUp() throws Exception
	{
		super.setUp();
		_solution = (Solution) Solution.getInstance();
		PropertyConfigurator.configure("log.conf");
	}

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(SolutionTest.class);
	}

	private static Logger LOGGER = Logger.getLogger(SolutionTest.class);
}
