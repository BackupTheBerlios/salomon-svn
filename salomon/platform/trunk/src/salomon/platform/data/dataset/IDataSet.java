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

import salomon.platform.IInfo;
import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IDataSet
{
	/**
	 * Gets the info of this data set. TODO: remove this method from interface
	 * 
	 * @return the data set info
	 */
//	IInfo getInfo() throws PlatformException;

	String getName() throws PlatformException;

	void setName(String name) throws PlatformException;

	/**
	 * 
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 * @return
	 * @throws PlatformException
	 */
	IDataSet intersection(IDataSet dataSet) throws PlatformException;

	/**
	 * 
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	IDataSet intersection(IDataSet dataSet, IUniqueId id)
			throws PlatformException;

	/**
	 * 
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 * @return
	 * @throws PlatformException
	 */
	IDataSet minus(IDataSet dataSet) throws PlatformException;

	/**
	 * 
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	IDataSet minus(IDataSet dataSet, IUniqueId id) throws PlatformException;

	/**
	 * Gets union of this data set and given.
	 * 
	 * @param dataSet
	 * @return
	 */
	IDataSet union(IDataSet dataSet) throws PlatformException;

	/**
	 * 
	 * TODO: add comment.
	 * 
	 * @param dataSet
	 * @param id
	 * @return
	 * @throws PlatformException
	 */
	IDataSet union(IDataSet dataSet, IUniqueId id) throws PlatformException;

	IData selectData(IColumn[] columns, ICondition[] conditions)
			throws PlatformException;

	/**
	 * Method creates new data set that is a subset of the current one. DataSet
	 * object is immutable so the only way to add some conditions to data set is
	 * creating its subset.
	 * 
	 * @param conditions condtions of data set
	 * @return data set that is a subset of current one
	 * @throws PlatformException
	 */
	IDataSet createSubset(ICondition[] conditions) throws PlatformException;

	/**
	 * Returns conditions that determine data set.
	 * 
	 * @return conditions determinating data set
	 */
	ICondition[] getConditions();

}