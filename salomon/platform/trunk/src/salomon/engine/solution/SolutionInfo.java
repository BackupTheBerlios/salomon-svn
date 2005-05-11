/*
 * Copyright (C) 2005 Salomon Team
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;

import salomon.platform.IInfo;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class SolutionInfo implements IInfo
{
	private DBManager _dbManager;
	
	private String _name;
	
	private String _info;

	private String _host;
	
	private String _path;
	
	private String _user;
	
	private String _pass;

	private int _solutionID = 0;
	
	public SolutionInfo(DBManager manager)
	{
		_dbManager = manager;
	}
	
	public Date getCreationDate() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getCreationDate() not implemented yet!");
	}

	public Date getLastModificationDate() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getLastModificationDate() not implemented yet!");
	}

	public boolean delete() throws SQLException, ClassNotFoundException
	{
		SQLDelete delete = new SQLDelete(TABLE_NAME);
		delete.addCondition("solution_id =", _solutionID);
		return (_dbManager.delete(delete) > 0);
	}

	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void load(ResultSet resultSet) throws SQLException
	{
		_solutionID = resultSet.getInt("solution_id");
		_name = resultSet.getString("solution_name");
		_info = resultSet.getString("solution_info");
		_host = resultSet.getString("hostname");
		_path = resultSet.getString("db_path");
		_user = resultSet.getString("username");
		_path = resultSet.getString("passwd");		
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
			update.addValue("solution_name", _name);
		}
		if (_info != null) {
			update.addValue("solution_info", _info);
		}
		if (_host != null) {
			update.addValue("hostname", _host);
		}
		if (_path != null) {
			update.addValue("db_path", _path);
		}
		if (_user != null) {
			update.addValue("username", _user);
		}
		if (_pass != null) {
			update.addValue("passwd", _pass);
		}		
		update.addValue("lm_date", new Date(System.currentTimeMillis()));
		_solutionID = _dbManager.insertOrUpdate(update, "solution_id",
				_solutionID, GEN_NAME);
		return _solutionID;
	}

	public int getId()
	{
		return _solutionID;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}
	
	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}
	
	/**
	 * @return Returns the host.
	 */
	public String getHost()
	{
		return _host;
	}
	
	/**
	 * @return Returns the path.
	 */
	public String getPath()
	{
		return _path;
	}
	
	/**
	 * @return Returns the user.
	 */
	public String getUser()
	{
		return _user;
	}
	
	/**
	 * @return Returns the pass.
	 */
	public String getPass()
	{
		return _pass;
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
	 * @param host The host to set.
	 */
	public void setHost(String host)
	{
		_host = host;
	}

	/**
	 * @param path The path to set.
	 */
	public void setPath(String path)
	{
		_path = path;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(String user)
	{
		_user = user;
	}

	/**
	 * @param pass The pass to set.
	 */
	public void setPass(String pass)
	{
		_pass = pass;
	}

	
	/**
	 * @param solutionID The solutionID to set.
	 */
	public void setSolutionID(int solutionID)
	{
		_solutionID = solutionID;
	}

	public String toString()
	{
		return "[" + _solutionID + ", " + _name + ", " + _info + ", " + _host + ", " + _path +", " + _user +", " + _pass +"]";
	}
	
	public static final String TABLE_NAME = "solutions";

	private static final String GEN_NAME = "gen_solution_id";
	
}
