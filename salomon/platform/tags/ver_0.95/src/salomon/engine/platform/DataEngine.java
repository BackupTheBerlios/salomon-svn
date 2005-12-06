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

package salomon.engine.platform;

import java.sql.SQLException;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.solution.ShortSolutionInfo;

import salomon.platform.IDataEngine;
import salomon.platform.data.IMetaData;
import salomon.platform.data.tree.ITreeManager;

import salomon.engine.platform.data.DBMetaData;
import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.dataset.DataSetManager;
import salomon.engine.platform.data.rule.RuleSetManager;
import salomon.engine.platform.data.tree.TreeManager;

/**
 * Class holds  TreeManager, DataSetManager, RuleSetManager and AttributeManager instances.
 */
public final class DataEngine implements IDataEngine
{
	/**
	 * 
	 * @uml.property name="_attributeManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private AttributeManager _attributeManager;

	/**
	 * 
	 * @uml.property name="_dataSetManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DataSetManager _dataSetManager;

	/**
	 * 
	 * @uml.property name="_ruleSetManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private RuleSetManager _ruleSetManager;

	/**
	 * 
	 * @uml.property name="_treeManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private TreeManager _treeManager;
	
	private DBMetaData _metaData;

	public DataEngine(DBManager dbManager, ExternalDBManager externalDBManager, ShortSolutionInfo solutionInfo) throws SQLException
	{
		_attributeManager = new AttributeManager(dbManager, solutionInfo);
		_dataSetManager = new DataSetManager(dbManager, solutionInfo, externalDBManager);
		_ruleSetManager = new RuleSetManager(dbManager, solutionInfo);
		_treeManager = new TreeManager(dbManager, solutionInfo, externalDBManager);
		_metaData = externalDBManager.getMetaData();
	}

	/**
	 * 
	 */
	public AttributeManager getAttributeManager()
	{
		return _attributeManager;
	}

	/**
	 * 
	 */
	public DataSetManager getDataSetManager()
	{
		return _dataSetManager;
	}

	/**
	 * 
	 */
	public RuleSetManager getRuleSetManager()
	{
		return _ruleSetManager;
	}

	/**
	 * @see salomon.platform.IDataEngine#getTreeManager()
	 */
	public ITreeManager getTreeManager()
	{
		return _treeManager;
	}

	public IMetaData getMetaData()
	{
		return _metaData;
	}
}
