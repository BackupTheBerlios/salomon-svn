/*
 * $RCSfile:$
 * $Revision:$
 *
 * Comments:
 *
 * (C) Copyright ParaSoft Corporation 2004.  All rights reserved.
 * THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF ParaSoft
 * The copyright notice above does not evidence any
 * actual or intended publication of such source code.
 *
 * $Author:$          $Locker:$
 * $Date:$
 * $Log:$
 */

package salomon.core.project;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 *  
 */
public interface IProjectManager
{
	public void newProject();

	/**
	 * Method loads project from data base.
	 * 
	 * @param projectID
	 * @return loaded project
	 * @throws Exception
	 */
	public void loadProject(int projectID) throws Exception;

	/**
	 * Method saves project in data base - project header, plugins and tasks are
	 * saved.
	 * 
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public void saveProject() throws Exception, ClassNotFoundException;

	/**
	 * @return Returns the currentProject.
	 */
	public IProject getCurrentProject();
}