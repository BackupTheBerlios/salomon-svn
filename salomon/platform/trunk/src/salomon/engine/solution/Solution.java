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

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

import salomon.engine.database.IDBSupporting;
import salomon.engine.platform.DataEngine;
import salomon.engine.project.IProjectManager;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class Solution implements ISolution, IDBSupporting
{

	private DataEngine _dataEngine;

	private Solution()
	{
		_dataEngine = new DataEngine();
	}

	/**
	 * @see salomon.engine.database.IDBSupporting#delete()
	 */
	public boolean delete() throws SQLException, ClassNotFoundException
	{
		throw new UnsupportedOperationException(
				"Method delete() not implemented yet!");
	}

	/**
	 * @see salomon.engine.solution.ISolution#getDataEngine()
	 */
	public IDataEngine getDataEngine() throws PlatformException
	{
		return _dataEngine;
	}

	/**
	 * @see salomon.engine.solution.ISolution#getProjectManager()
	 */
	public IProjectManager getProjectManager()
	{
		throw new UnsupportedOperationException(
				"Method getProjectManager() not implemented yet!");
	}

	/**
	 * @see salomon.engine.database.IDBSupporting#load(java.sql.ResultSet)
	 */
	public void load(ResultSet resultSet) throws MalformedURLException,
			SQLException
	{
		throw new UnsupportedOperationException(
				"Method load() not implemented yet!");
	}

	/**
	 * @see salomon.engine.database.IDBSupporting#save()
	 */
	public int save() throws SQLException, ClassNotFoundException
	{
		throw new UnsupportedOperationException(
				"Method save() not implemented yet!");
	}

	/**
	 * Returns instance.
	 * 
	 * @return The _instance
	 */
	public static ISolution getInstance()
	{
		if (_instance == null) {
			_instance = new Solution();
		}
		return _instance;
	}

	static ISolution _instance;
}
