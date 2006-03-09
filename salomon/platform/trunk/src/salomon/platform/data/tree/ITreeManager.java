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
 * Interfejs managera umo¿liwiaj¹cy operacje na drzewach
 * 
 * @author Mateusz Nowakowski
 *
 */
public interface ITreeManager
{
	
	/**
	 * Metoda zwraca ilosc wierszy tabeli,ktorej nazwa jest podana jako parametr
	 * 
	 * @param tableName
	 * @return
	 * @throws PlatformException
	 */
	int getTableSize(String tableName)throws PlatformException;
	/**
	 * Metoda zwraca liste wierszy bedacych wynikiem pobrania danych z zewnetrznej bazy
	 * na podstawie IDataSource.
	 * 
	 * @param dataSource
	 * @return
	 */
	List<Object []> getTreeDataSourceData(IDataSource dataSource) throws PlatformException;
	/**
	 * Metoda zwraca liste pozostalych wierszy nie wskazywanych przez IDataSource
	 * 
	 * @param dataSource
	 * @return
	 */	
	List<Object []> getRestTreeDataSourceRows(IDataSource dataSource) throws PlatformException;
	/**
	 * Tworzy instancje IDataSource
	 * @return
	 * @throws PlatformException
	 */
	IDataSource createTreeDataSource() throws PlatformException;
	
	/**
	 * Dodaje IDataSource do bazy danych 
	 * @param dataSource
	 * @return id stworzonego data sourca 
	 * @throws PlatformException
	 */
	int addTreeDataSource(IDataSource dataSource) throws PlatformException;;
	
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
	 * Usuwa z bazy danych  zrodlo danych o danym id 
	 * @param dataSourceId
	 * @throws PlatformException
	 */
	void removeTreeDataSource(int dataSourceId) throws PlatformException;
	
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
	int addTree(ITree tree) throws PlatformException;

	
	
	/**
	 * Zwraca drzewa zwiazane z danym solutionem
	 * @return
	 * @throws PlatformException
	 */
	ITree[] getTrees() throws PlatformException;

	/**
	 * Zwraca drzewo zwiazane z obecnym solutionem i posiadajace podane id. W przeciwnym razie rzuca PlatformException
	 * @param treeId
	 * @return
	 * @throws PlatformException
	 */
	ITree getTree(int treeId) throws PlatformException;


	/**
	 * Usuwa drzewo o podanym id z bazy danych.
	 * @param treeId
	 * @throws PlatformException
	 */
	void removeTree(int treeId) throws PlatformException;
}
