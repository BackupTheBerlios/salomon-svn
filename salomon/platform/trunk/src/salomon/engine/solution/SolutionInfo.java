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

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import salomon.platform.IInfo;
import salomon.platform.IUniqueId;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class SolutionInfo implements IInfo
{

	public Date getCreationDate() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getCreationDate() not implemented yet!");
	}

	public String getDescription() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getDescription() not implemented yet!");
	}

	public Date getLastModificationDate() throws PlatformException
	{
		throw new UnsupportedOperationException("Method getLastModificationDate() not implemented yet!");
	}

	public void setDescription(String description) throws PlatformException
	{
		throw new UnsupportedOperationException("Method setDescription() not implemented yet!");
	}

	public boolean delete() throws SQLException, ClassNotFoundException
	{
		throw new UnsupportedOperationException("Method delete() not implemented yet!");
	}

	public void load(ResultSet resultSet) throws MalformedURLException, SQLException
	{
		throw new UnsupportedOperationException("Method load() not implemented yet!");
	}

	public int save() throws SQLException, ClassNotFoundException
	{
		throw new UnsupportedOperationException("Method save() not implemented yet!");
	}

	public int getId()
	{
		throw new UnsupportedOperationException("Method getId() not implemented yet!");
	}
}
