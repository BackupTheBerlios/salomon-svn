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

package salomon.engine;

import java.sql.SQLException;

import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.dataset.DataSetManager;
import salomon.engine.platform.data.rule.RuleSetManager;
import salomon.platform.IDataEngine;

/**
 *  Class holds  DataSetManager, RuleSetManager and AttributeManager instances.  
 */
public class DataEngine implements IDataEngine
{
	private AttributeManager _attributeManager;

	private DataSetManager _dataSetManager;

	private RuleSetManager _ruleSetManager;

	public DataEngine()
	{
		_dataSetManager = new DataSetManager();
	}

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public DataSetManager getDataSetManager()
	{
		return _dataSetManager;
	} 

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public RuleSetManager getRuleSetManager()
	{
		
		return null;
	} 

	/**
	 * Does ...
	 * 
	 * @return
	 */
	public AttributeManager getAttributeManager()
	{
		
		return null;
	} 
    
	public DBManager getDbManager() throws SQLException, ClassNotFoundException
	{
		return DBManager.getInstance();
	}	
} 
