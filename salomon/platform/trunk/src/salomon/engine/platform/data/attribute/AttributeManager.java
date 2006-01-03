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

package salomon.engine.platform.data.attribute;

import salomon.engine.database.DBManager;
import salomon.engine.solution.ShortSolutionInfo;

import salomon.platform.IUniqueId;
import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.attribute.description.IDateAttributeDescription;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.data.attribute.description.IIntegerAttributeDescription;
import salomon.platform.data.attribute.description.IRealAttributeDescription;
import salomon.platform.data.attribute.description.IStringAttributeDescription;
import salomon.platform.exception.PlatformException;

/**
 *  Not used yet.
 */
public final class AttributeManager implements IAttributeManager
{
	private DBManager _dbManager;

	private ShortSolutionInfo _solutionInfo;

	public AttributeManager(DBManager dbManager, ShortSolutionInfo solutionInfo)
	{
		_dbManager = dbManager;
		_solutionInfo = solutionInfo;
	}

	public void add(IAttributeSet attributeSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.add() not implemented yet!");
	}

	public IAttributeSet createAttributeSet(IAttributeDescription[] descriptions)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createAttributeSet() not implemented yet!");
	}

	public IAttributeSet[] getAll() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.getAll() not implemented yet!");
	}

	public IAttributeSet getAttributeSet(IUniqueId id) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.getAttributeSet() not implemented yet!");
	}

	public void remove(IAttributeSet attributeSet) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.remove() not implemented yet!");
	}

	public IDateAttributeDescription createDateAttributeDescription(String name)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createDateAttributeDescription() not implemented yet!");
	}

	public IEnumAttributeDescription createEnumAttributeDescription(
			String name, Object[] possibleValues)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createEnumAttributeDescription() not implemented yet!");
	}

	public IIntegerAttributeDescription createIntegerAttributeDescription(
			String name)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createIntegerAttributeDescription() not implemented yet!");
	}

	public IRealAttributeDescription createRealAttributeDescription(String name)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createRealAttributeDescription() not implemented yet!");
	}

	public IStringAttributeDescription createStringAttributeDescription(
			String name)
	{
		throw new UnsupportedOperationException(
				"Method AttributeManager.createStringAttributeDescription() not implemented yet!");
	}

} // end AttributeManager
