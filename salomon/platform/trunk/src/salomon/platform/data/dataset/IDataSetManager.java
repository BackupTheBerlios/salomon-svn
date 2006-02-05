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

import salomon.platform.IUniqueId;
import salomon.platform.data.IColumn;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public interface IDataSetManager
{
	/**
	 * Adds the data set to the storage.
	 * 
	 * @param dataSet the data set to add
	 * 
	 */
	void add(IDataSet dataSet) throws PlatformException;

	/**
	 * TODO: service OR and AND statements
	 */
	ICondition createAndCondition(ICondition condition,
			ICondition... conditions) throws PlatformException;

	ICondition createCondition(String stringCondition) throws PlatformException;

	ICondition createEqualsCondition(IColumn column, Object value)
			throws PlatformException;

	ICondition createGreaterCondition(IColumn column, Object value)
			throws PlatformException;

	ICondition createLowerCondition(IColumn column, Object value)
			throws PlatformException;

	ICondition createOrCondition(ICondition condition, ICondition... conditions)
			throws PlatformException;

	/**
	 * Gets all data sets.
	 * 
	 * @return the all data sets
	 */
	IDataSet[] getAll() throws PlatformException;

	/**
	 * Gets a data set with the given id.
	 * 
	 * @param id data set id
	 * @return wanted data set
	 */
	IDataSet getDataSet(IUniqueId id) throws PlatformException;

	IDataSet getDataSet(String name) throws PlatformException;
	
	/**
	 * Returns the data set that describes all data base.
	 * 
	 * @return the main data set
	 */
	IDataSet getMainDataSet() throws PlatformException;

	/**
	 * Removes the given data set from the storage.
	 * 
	 * @param dataSet the data set to remove
	 * @throws PlatformException
	 */
	void remove(IDataSet dataSet) throws PlatformException;

}