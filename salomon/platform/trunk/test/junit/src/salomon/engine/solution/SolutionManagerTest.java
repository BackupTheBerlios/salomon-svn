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

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.ManagerEngine;

public class SolutionManagerTest extends TestCase
{

    /**
     * 
     * @uml.property name="_solutionManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private SolutionManager _solutionManager;

    /**
     * 
     */
    public void testCreateSolution()
    {
        LOGGER.info("SolutionManager.testCreateSolution()");
        ISolution solution = null;
        try {
            solution = _solutionManager.createSolution();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        assertNotNull(solution);

    }

    /**
     * 
     */
    //	public void testGetAddSolution()
    //	{
    //		LOGGER.info("SolutionManager.testGetAddSolution()");
    //
    //		//boolean result = false ; 
    //		//ISolution solution = Solution.getInstance() ;
    //		//_solutionManager.addSolution(solution) ;
    //
    //		ISolution solutions[] = _solutionManager.getSolutions();
    //		assertNotNull(solutions);
    //
    //		/*
    //		 for (int i = 0; i < solutions.length; i++) {
    //		 if (solutions[i].equals(solution)) {
    //		 result = true ;
    //		 break ;
    //		 }
    //		 }
    //		 
    //		 assertTrue(result);
    //		 */
    //
    //	}
    public void testGetDataEngine()
    {
        ISolution solution = null;
        try {
            solution = _solutionManager.getSolution(1);
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        assertNotNull(solution);
        IDataEngine dataEngine = null;
        try {
            dataEngine = solution.getDataEngine();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        IDataSet dataSet = null;
        try {
            dataSet = dataEngine.getDataSetManager().getMainDataSet();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        assertNotNull(dataSet);
        //		SQLSelect select = new SQLSelect();
        //		select.addTable("persons");
        //		ResultSet resultSet = null;
        //		try {
        ////			resultSet = dataSet.selectData(select);
        //			Utils.createResultTable(resultSet);
        //		} catch (PlatformException e) {
        //			LOGGER.fatal("", e);
        //		} catch (SQLException e) {
        //			LOGGER.fatal("", e);
        //		}
    }

    /**
     * 
     */
    public void testGetSolution()
    {
        LOGGER.info("SolutionManager.testGetSolution()");
        try {
            ISolution solution = _solutionManager.createSolution();
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
        }
        assertTrue("TEST AWAITING FOR Solution INTERFACE", false);

        //ISolution gotsolution = _solutionManager.getSolution(null) ;
        //assertNotNull(gotsolution) ;

    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        PropertyConfigurator.configure("log.conf");
        ManagerEngine engine = new ManagerEngine();
        _solutionManager = (SolutionManager) engine.getSolutionManager();

    }

    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(SolutionManagerTest.class);
    }

    private static Logger LOGGER = Logger.getLogger(SolutionManager.class);
}
