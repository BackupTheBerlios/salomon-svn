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

import salomon.platform.exception.PlatformException;

/**
 * Interface implemented by ProjectManager, which manages with projects.
 */
public interface IProjectManager
{
    /**
     * Adds the project to the Solution. 
     * @param project The project which should be added
     * @throws PlatformException
     */
	void addProject(IProject project) throws PlatformException;

	/**
	 * Creates new, empty project
	 */
	IProject ceateProject() throws PlatformException;

	/**
	 * Returns collection of available projects.
	 * 
	 * TODO: change it Returns collection od arrays of object 1 row is an array
	 * of column names next are data. If there is no data, only column names are
	 * returned
	 * 
	 * @return The array of all projects
	 * @throws PlatformException
	 */
	IProject[] getProjects() throws PlatformException;

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @throws PlatformException
	 */
	IProject getProject(int projectID) throws PlatformException;

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 */
	void saveProject() throws PlatformException;
}