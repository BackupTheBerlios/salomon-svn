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

package salomon.platform.data.tree;

import java.util.List;

import salomon.engine.solution.ISolution;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.data.DBTable;

/**
 * 
 */
public interface ITreeManager
{

	/**
	 * Zwraca tablice wszystkich tabel w zewnetrzenej bazie danych
	 * @return
	 */
	DBTable [] getAllTables() throws PlatformException;

	/**
	 * Metoda zwraca liste wierszy bedacych wynikiem pobrania danych z zewnetrznej bazy
	 * na podstawie IDataSource.
	 * 
	 * @param dataSource
	 * @return
	 */
	List<Object []> getTreeDataSourceData(IDataSource dataSource) throws PlatformException;
	/**
	 * Tworzy instancje IDataSource
	 * @return
	 * @throws PlatformException
	 */
	IDataSource createTreeDataSource() throws PlatformException;
	
	/**
	 * Dodaje IDataSource do bazy danych 
	 * @param dataSource
	 * @throws PlatformException
	 */
	void addTreeDataSource(IDataSource dataSource) throws PlatformException;;
	
	IDataSource[] getAllTreeDataSources() throws PlatformException;
	
	void removeTreeDataSource(IDataSource dataSource) throws PlatformException;
	
	
	
	ITree[] getAllTrees() throws PlatformException;

	ITree[] getTrees(ISolution solution) throws PlatformException;

	ITree getTree(int treeId) throws PlatformException;

	void addTree(ITree tree) throws PlatformException;

	void removeTree(ITree tree) throws PlatformException;

	
	IDataSource[] getTreeDataSources(ISolution solution)
			throws PlatformException;

	IDataSource getTreeDataSource(int treeDataSourceId)
			throws PlatformException;;

	

	
}
