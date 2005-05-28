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

import salomon.engine.database.DBManager;
import salomon.engine.solution.ISolution;

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

	public TreeManager(DBManager dbManager)
	{
		_dbManager = dbManager;
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

}
