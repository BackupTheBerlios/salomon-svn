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

import salomon.engine.platform.ManagerEngine;

public class SolutionManagerTest extends TestCase {
    private SolutionManager _solutionManager ;
    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(SolutionManagerTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        PropertyConfigurator.configure("log.conf");
        ManagerEngine engine = new ManagerEngine();
        _solutionManager = (SolutionManager) engine.getSolutionManager();
        
    }
    
    /**
     * 
     */
    public void testCreateSolution() {
        LOGGER.info("SolutionManager.testCreateSolution()");
        ISolution solution = _solutionManager.createSolution() ;
        assertNotNull(solution);
        
    }
    
    /**
     * 
     */
    public void testGetAddSolution() {
        LOGGER.info("SolutionManager.testGetAddSolution()");
        
        boolean result = false ; 
        ISolution solution = Solution.getInstance() ;
        _solutionManager.addSolution(solution) ;
        
        ISolution solutions[] = _solutionManager.getSolutions() ;
        assertNotNull(solutions) ;
        
        /*
        for (int i = 0; i < solutions.length; i++) {
            if (solutions[i].equals(solution)) {
                result = true ;
                break ;
            }
        }
        
        assertTrue(result);
        */
        
    }

    /**
     * 
     */
    public void testGetSolution() {
       LOGGER.info("SolutionManager.testGetSolution()");
       ISolution solution = _solutionManager.createSolution() ;
       assertTrue("TEST AWAITING FOR Solution INTERFACE", false) ;
 
       ISolution gotsolution = _solutionManager.getSolution(null) ;
       assertNotNull(gotsolution) ;

    }
    
    
    private static Logger LOGGER = Logger.getLogger(SolutionManager.class);
}
