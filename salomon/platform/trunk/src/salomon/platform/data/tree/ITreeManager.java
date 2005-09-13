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

import salomon.platform.data.tree.INode.Type;
import salomon.platform.exception.PlatformException;

/**
 * 
 * @author Mateusz Nowakowski
 *
 */
public interface ITreeManager
{


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
	
	/**
	 * Zwraca liste poprawnych DataSourcow dla aktualnego solutiona
	 * @return
	 * @throws PlatformException
	 */
	IDataSource[] getTreeDataSources() throws PlatformException;

	/**
	 * Zwraca IDataSource o podanym id pod warunkiem ze nalezy do obecnego solutiona
	 * @param treeDataSourceId
	 * @return
	 * @throws PlatformException
	 */
	IDataSource getTreeDataSource(int treeDataSourceId)	throws PlatformException;;

	
	/**
	 * Usuwa z bazy danych podane zrodlo danych 
	 * @param dataSource
	 * @throws PlatformException
	 */
	void removeTreeDataSource(IDataSource dataSource) throws PlatformException;
	
	/**
	 * Tworzy puste drzewo. Przed zapisem do bazy nalezy je zainicjowac korzeniem 
	 * oraz zrodlem danych z ktorego powstalo.
	 * @return
	 * @throws PlatformException
	 */
	public ITree createTree() throws PlatformException;

	/**
	 * Tworzy puste drzewo
	 * @param dataSourceId -id data sourca, jesli nie istnieje rzuca wyjatek
	 * @param info - info
	 * @param name - nazwa drzewka
	 * @param root - korzen drzewa, nie moze byc nullem
	 * @return
	 * @throws PlatformException
	 */
	public ITree createTree(int dataSourceId, String info, String name, INode root) throws PlatformException;
	
	/**
	 * Tworzy node dla drzewa. Nalezy dodawac dzieci metoda addChild(ren), setChildren na nodach
	 * @param parentNode - null oznacza ze jest pusty
	 * @param edge - w przypadku korzenia ignorowane
	 * @param type - jesli docelowo bedzie to node bedacy lisciem to nalezy ustawic typ V
	 * @param value
	 * @return
	 */
	public INode createNode(INode parentNode, String edge, Type type, String value);
	

	/**
	 * Dodaje drzewo do bazy danych
	 */
	void addTree(ITree tree) throws PlatformException;

	
	//TODO
	
	/**
	 * Zwraca drzewa zwiazane z danym solutionem
	 * @return
	 * @throws PlatformException
	 */
	ITree[] getTrees() throws PlatformException;

	ITree getTree(int treeId) throws PlatformException;



	void removeTree(ITree tree) throws PlatformException;
}
