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

package salomon.engine.platform.data.dataset;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.platform.data.IColumn;
import salomon.platform.data.dataset.IData;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 *  Not used.
 */
class Data implements IData
{
	private int _columnCount;

	private ResultSet _resultSet;

	public Data(ResultSet resultSet) throws PlatformException
	{
		_resultSet = resultSet;
		ResultSetMetaData metaData;
		try {
			metaData = _resultSet.getMetaData();
			_columnCount = metaData.getColumnCount();
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new DBException(e);
		}
	}

	public void close() throws PlatformException
	{
		try {
			_resultSet.close();
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e);
		}
	}

	public Object[] getData() throws PlatformException
	{
		Object[] data = new Object[_columnCount];
		try {
			for (int i = 0; i < data.length; ++i) {
				data[i] = _resultSet.getObject(i + 1);
			}
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new DBException(e);
		}
		return data;
	}

	public Object getData(IColumn column) throws PlatformException
	{
		try {
			return _resultSet.getObject(column.getName());
		} catch (SQLException e) {
			LOGGER.fatal("", e);
			throw new PlatformException(e);
		}
	}

	public boolean next() throws PlatformException
	{
		try {
			return _resultSet.next();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	private static final Logger LOGGER = Logger.getLogger(Data.class);
}
