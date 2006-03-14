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

package salomon.engine.project;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.solution.SolutionManager;
import salomon.platform.IUniqueId;
import salomon.plugin.DescriptionTest;

public class ProjectTest extends TestCase
{

	/**
	 * 
	 * @uml.property name="_manager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _manager;

	private ProjectManager _projectManager;

	private SolutionManager _solutionManager;

	public void testDelete()
	{
		LOGGER.debug("ProjectTest.testDelete()");
		boolean success = false;
		try {
			_solutionManager.getSolution(new IUniqueId() {
				public int getId()
				{
					return 1;
				}
			});

			Project project = (Project) _projectManager.getProject(new IUniqueId() {
				public int getId()
				{
					return 65;
				}
			});

			// constraint - FIXME - add cascade delete for projects
			//project.getInfo().delete();
			success = true;
			// to keep DB anaffected
			_manager.rollback();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			_manager.rollback();
		}
		assertTrue(success);
	}

	public void testSave()
	{
		LOGGER.debug("ProjectTest.testSave()");
		boolean success = false;
		try {
			_solutionManager.getSolution(new IUniqueId() {
				public int getId()
				{
					return 1;
				}
			});			
			
			Project project = (Project) _projectManager.getProject(new IUniqueId() {
				public int getId()
				{
					return 65;
				}
			});
			project.getInfo().setEnvironment("test env");
			project.getInfo().save();
			success = true;
			// to keep DB anaffected
			_manager.rollback();
		} catch (Exception e) {
			LOGGER.fatal("", e);
			_manager.rollback();
		}
		assertTrue(success);
	}

	protected void setUp() throws Exception
	{
		PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$   

		ManagerEngine managerEngine = new ManagerEngine();
		_solutionManager = (SolutionManager) managerEngine.getSolutionManager();
		_projectManager = (ProjectManager) managerEngine.getProjectManager();
		// to force plugin loading - FIXME - fix loading tasks without loaded plugins
		// to see this error - comment out the line below
		managerEngine.getPluginManager().getPlugins();
		_manager = managerEngine.getDbManager();

	}

	private static Logger LOGGER = Logger.getLogger(DescriptionTest.class);

}