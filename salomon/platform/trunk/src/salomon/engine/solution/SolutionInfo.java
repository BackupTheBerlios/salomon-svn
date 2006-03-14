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

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;

import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

public final class SolutionInfo implements IInfo
{

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	private String _host;

	private String _info;

	private String _name;

	private String _passwd;

	private String _path;

	private int _solutionID = 0;

	private String _user;

	public SolutionInfo(DBManager manager)
	{
		_dbManager = manager;
	}

	public boolean delete() throws DBException
	{
		SQLDelete delete = new SQLDelete(TABLE_NAME);
		delete.addCondition("solution_id =", _solutionID);
		try {
			return (_dbManager.delete(delete) > 0);
		} catch (SQLException e) {
			LOGGER.fatal("Exception was thrown!", e);
			throw new DBException("Cannot delete solution info!", e);
		}
	}

	public Date getCreationDate() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getCreationDate() not implemented yet!");
	}

	/**
	 * @return Returns the host.
	 */
	public String getHost()
	{
		return _host;
	}

	public int getId()
	{
		return _solutionID;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	public Date getLastModificationDate() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getLastModificationDate() not implemented yet!");
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the pass.
	 */
	public String getPasswd()
	{
		return _passwd;
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
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws DBException 
	 */
	public void load(ResultSet resultSet) throws DBException
	{
		try {
			_solutionID = resultSet.getInt("solution_id");
			_name = resultSet.getString("solution_name");
			_info = resultSet.getString("solution_info");
			_host = resultSet.getString("hostname");
			_path = resultSet.getString("db_path");
			_user = resultSet.getString("username");
			_passwd = resultSet.getString("passwd");
		} catch (SQLException e) {
			LOGGER.fatal("Exception was thrown!", e);
			throw new DBException("Cannot load solution info!", e);
		}
	}

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 */
	public int save() throws DBException
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
		if (_passwd != null) {
			update.addValue("passwd", _passwd);
		}
		update.addValue("lm_date", new Date(System.currentTimeMillis()));
		try {
			_solutionID = _dbManager.insertOrUpdate(update, "solution_id",
					_solutionID, GEN_NAME);
		} catch (SQLException e) {
			LOGGER.fatal("Exception was thrown!", e);
			throw new DBException("Cannot save solution info!", e);
		}
		return _solutionID;
	}

	/**
	 * @param host The host to set.
	 */
	public void setHost(String host)
	{
		_host = host;
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
	 * @param pass The pass to set.
	 */
	public void setPasswd(String pass)
	{
		_passwd = pass;
	}

	/**
	 * @param path The path to set.
	 */
	public void setPath(String path)
	{
		_path = path;
	}

	/**
	 * @param solutionID The solutionID to set.
	 */
	public void setSolutionID(int solutionID)
	{
		_solutionID = solutionID;
	}

	/**
	 * @param user The user to set.
	 */
	public void setUser(String user)
	{
		_user = user;
	}

	public String toString()
	{
		return "[" + _solutionID + ", " + _name + ", " + _info + ", " + _host
				+ ", " + _path + ", " + _user + ", " + _passwd + "]";
	}

	public static final String TABLE_NAME = "solutions";
    
    public static final String VIEW_NAME = "solutions_view";

	private static final String GEN_NAME = "gen_solution_id";

	private static final Logger LOGGER = Logger.getLogger(SolutionInfo.class);

}
