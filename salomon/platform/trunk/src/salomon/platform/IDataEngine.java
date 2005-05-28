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

package salomon.platform;

import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.data.rule.IRuleSetManager;
import salomon.platform.data.tree.ITreeManager;

/**
 * 
 */
public interface IDataEngine
{
	/**
	 * Return the TreeManager.
	 * @return The TreeManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	ITreeManager getTreeManager();

	/**
	 * Return the DataSetManager.
	 * @return The DataSetManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	IDataSetManager getDataSetManager();

	/**
	 * Return the RuleSetManager.
	 * @return The RuleSetManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	IRuleSetManager getRuleSetManager();

	/**
	 * Return the AttributeManager.
	 * @return The AttributeManager
	 * 
	 * @pre $none
	 * @post $result != null
	 */
	IAttributeManager getAttributeManager();
}