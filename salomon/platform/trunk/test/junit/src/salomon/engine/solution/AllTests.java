/*
 * Copyright (C) 2005 KnowledgeSystem Team
 *
 * This file is part of KnowledgeSystem.
 *
 * KnowledgeSystem is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * KnowledgeSystem is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with KnowledgeSystem; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.solution;

import junit.framework.TestSuite;

/**
 * 
 */
public class AllTests
{

    public static TestSuite suite()
    {
        TestSuite suite = new TestSuite("Test for salomon.engine.solution");
        //$JUnit-BEGIN$
        suite.addTestSuite(SolutionInfoTest.class);
        suite.addTestSuite(SolutionManagerTest.class);
        //$JUnit-END$
        return suite;
    }
}
