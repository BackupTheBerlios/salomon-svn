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

package salomon.platform.data.dataset;

import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IDataSetManager
{
	/**
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 */
	void add(IDataSet dataSet) throws PlatformException;

	/**
	 * TODO: add comment.
	 * 
	 * @param name
	 * @return
	 */
	IDataSet getDataSet(String name) throws PlatformException;

	/**
	 * TODO: add comment.
	 * 
	 * @return
	 */
	IDataSet[] getDataSets() throws PlatformException;

	IDataSet intersection(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException;

	IDataSet minus(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException;

	/**
	 * TODO: add comment.
	 * 
	 * @param firstDataSet
	 * @param secondDataSet
	 * @return
	 */
	IDataSet union(IDataSet firstDataSet, IDataSet secondDataSet)
			throws PlatformException;
}