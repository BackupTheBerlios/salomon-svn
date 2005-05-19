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

import salomon.engine.task.Task;

public class TaskTest extends TestCase {

	/**
	 * 
	 * @uml.property name="_Task"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private Task _Task;

    
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TaskTest.class);
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        PropertyConfigurator.configure("log.conf");
        
    }

    /**
     * @return
     */
    public void testCreateTask() {
        LOGGER.info("TaskTest.testCreateTask");
        Task result = new Task();
        result.setName("test task") ;
        result.setStatus(Task.FINISHED) ;
        result.setProjectID(0xCAFE) ;
        result.setTaskId(0xF00D);
        
        // Need this if we wanna realy test sth 
        //result.setPlugin(null) ; 
        //result.setSettings(null) ;
        //result.setResult(null);
        _Task = result ;
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        _Task.delete() ;
    }

    private static Logger LOGGER = Logger.getLogger(TaskTest.class);
}
