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

import salomon.platform.exception.PlatformException;

/**
 *
 */
public interface ITreeManager
{

    /**
     * Tworzy puste drzewo. Przed zapisem do bazy nalezy je zainicjowac korzeniem 
     * oraz zrodlem danych z ktorego powstalo.
     * 
     * @param rootNode TODO
     * @return
     * @throws PlatformException
     */
    public ITree createTree() throws PlatformException;

    /**
     * Dodaje drzewo do bazy danych
     */
    void add(ITree tree) throws PlatformException;

    /**
     * Zwraca drzewa zwiazane z danym solutionem
     * @return
     * @throws PlatformException
     */
    ITree[] getAll() throws PlatformException;

    /**
     * Zwraca drzewo zwiazane z obecnym solutionem i posiadajace podane id. W przeciwnym razie rzuca PlatformException
     * @param treeId
     * @return
     * @throws PlatformException
     */
    ITree getTree(int treeId) throws PlatformException;

    /**
     * Zwraca drzewo zwiazane z obecnym solutionem i posiadajace podane id. W przeciwnym razie rzuca PlatformException
     * @param treeId
     * @return
     * @throws PlatformException
     */
    ITree getTree(String name) throws PlatformException;

    /**
     * Usuwa drzewo o podanym id z bazy danych.
     * @param treeId
     * @throws PlatformException
     */
    void remove(ITree tree) throws PlatformException;
}
