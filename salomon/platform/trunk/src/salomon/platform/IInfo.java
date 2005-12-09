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

package salomon.platform;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IInfo extends IUniqueId
{
	int getId();

	Date getCreationDate() throws PlatformException;

	Date getLastModificationDate() throws PlatformException;

	/**
	 * Removes itself from database. After successsful finish object should be
	 * destroyed.
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	boolean delete() throws SQLException, ClassNotFoundException;

	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	void load(ResultSet resultSet) throws MalformedURLException, SQLException;

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	int save() throws SQLException, ClassNotFoundException;
}
