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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import salomon.engine.database.DBManager;
import salomon.engine.database.IDBSupporting;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;
import salomon.engine.task.ITaskManager;
import salomon.platform.exception.PlatformException;


/**
 * Represents a project, it is an implementation of IProject interface.
 */
public final class Project implements IProject, IDBSupporting
{
	private String _info;

	private String _name;

	private int _projectID = 0;

	/**
	 * Removes itself from database. After successsful finish object should be
	 * destroyed.
	 * 
	 * @return @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean delete() throws SQLException, ClassNotFoundException
	{
		SQLDelete delete = new SQLDelete(TABLE_NAME);
		delete.addCondition("project_id =", _projectID);
		return (DBManager.getInstance().delete(delete) > 0);
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID()
	{
		return _projectID;
	}

	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void load(ResultSet resultSet) throws SQLException
	{
		_projectID = resultSet.getInt("project_id");
		_name = resultSet.getString("project_name");
		_info = resultSet.getString("project_info");
	}

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int save() throws SQLException, ClassNotFoundException
	{
		SQLUpdate update = new SQLUpdate(TABLE_NAME);
        if (_name != null) {
        	update.addValue("project_name", _name);
        }
        if (_info != null) {
        	update.addValue("project_info", _info);
        }		
		update.addValue("lm_date", new Date(System.currentTimeMillis()));
		_projectID = DBManager.getInstance().insertOrUpdate(update, "project_id",
				_projectID, GEN_NAME);
		return _projectID;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param projectId The projectID to set.
	 */
	public void setProjectID(int projectId)
	{
		_projectID = projectId;
	}

	public String toString()
	{
		return "[" + _projectID + ", " + _name + ", " + _info + "]";
	}
        
	public static final String TABLE_NAME = "projects";
    
    private static final String GEN_NAME = "gen_project_id";

	/**
	 * @see salomon.engine.project.IProject#getTaskManager()
	 */
	public ITaskManager getTaskManager() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getTaskManager() not implemented yet!");
	}

}