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

import salomon.engine.database.DBManager;
import salomon.engine.platform.ManagerEngine;
import salomon.platform.IUniqueId;

public class SolutionInfoTest extends TestCase
{
    private DBManager _manager;

    private ISolutionManager _solutionManager;

    protected void setUp() throws Exception
    {
        PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   

        ManagerEngine managerEngine = new ManagerEngine();
        _manager = managerEngine.getDbManager();
        _solutionManager = managerEngine.getSolutionManager();
    }

    public void testAll() throws Exception
    {
        SolutionInfo info = new SolutionInfo(_manager);
        info.setName("test plugin");
        info.setInfo("info");
        info.setHost("host");
        info.setPath("f:/sample/path");
        info.setUser("sample user");
        info.setPasswd("password");

        // starting transaction
        _manager.rollback();
        // saving plugin
        final int solutionID = info.save();

        // loading created plugin
        ISolution solution = _solutionManager.getSolution(new IUniqueId() {
            public int getId()
            {
                return solutionID;
            }
        });
        assertNotNull(solution);

        // deleting 
        assertTrue(solution.getInfo().delete());
        _manager.rollback();
    }

    private static Logger LOGGER = Logger.getLogger(SolutionInfoTest.class);
}
