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

package salomon.engine.platform.data.tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ShortSolutionInfo;

import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class TreeManager implements ITreeManager
{
	private DBManager _dbManager;

	private ExternalDBManager _externalDBManager;

	private ShortSolutionInfo _solutionInfo;

	public TreeManager(DBManager dbManager, ShortSolutionInfo solutionInfo,
			ExternalDBManager externalDBManager)
	{
		_dbManager = dbManager;
		_solutionInfo = solutionInfo;
		_externalDBManager = externalDBManager;
	}

	public void addTree(ITree tree) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method addTree() not implemented yet!");
	}

	public void addTreeDataSource(IDataSource dataSource)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method addTreeDataSource() not implemented yet!");
	}

	public boolean checkTableAndColumns(String tableName,
			Collection<String> columnNames) throws PlatformException
	{
		SQLSelect select = new SQLSelect();
		select.addTable(tableName);
		for (String columnName : columnNames)
			select.addColumn(columnName);
		ResultSet resultSet = null;
		try {
			resultSet = _externalDBManager.select(select);
			if (!resultSet.next())
				return false;
		} catch (SQLException e) {
			throw new PlatformException("Select: " + select.getQuery()
					+ " has errors: " + e.getLocalizedMessage());
		} finally {
			try {
				_externalDBManager.disconnect();
			} catch (SQLException e1) {
				// do nothing
			};
		}
		return true;
	}

	public IDataSource[] getAllTreeDataSources() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getAllTreeDataSources() not implemented yet!");
	}

	public ITree[] getAllTrees() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getAllTrees() not implemented yet!");
	}

	public ITree getTree(int treeId) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTree() not implemented yet!");
	}

	public IDataSource getTreeDataSource(int treeDataSourceId)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTreeDataSource() not implemented yet!");
	}

	public IDataSource[] getTreeDataSources(ISolution solution)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTreeDataSources() not implemented yet!");
	}

	public ITree[] getTrees(ISolution solution) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTrees() not implemented yet!");
	}

	public void removeTree(ITree tree) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeTree() not implemented yet!");
	}

	public void removeTreeDataSource(IDataSource dataSource)
			throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeTreeDataSource() not implemented yet!");
	}

	private static final Logger LOGGER = Logger.getLogger(TreeManager.class);

}
