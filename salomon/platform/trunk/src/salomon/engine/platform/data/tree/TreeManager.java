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

package salomon.engine.platform.data.tree;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class TreeManager implements ITreeManager
{
	private DBManager _dbManager;
	private ExternalDBManager _externalDBManager;
	private ShortSolutionInfo _solutionInfo;
	
	public TreeManager(DBManager dbManager, ShortSolutionInfo solutionInfo,
			ExternalDBManager externalDBManager)
	{
		_dbManager = dbManager;
		_solutionInfo = solutionInfo;
		_externalDBManager = externalDBManager;
	}

	
	
	
	public IDataSource createTreeDataSource() throws PlatformException {
		return new DataSource(_solutionInfo);
	}
	
	
	public void addTreeDataSource(IDataSource dataSource)throws PlatformException
	{
		SQLInsert insert = new SQLInsert("TREE_DATA_SOURCES");
		insert.addValue("TDS_NAME",dataSource.getName());
		insert.addValue("TDS_INFO",dataSource.getInfo());
		insert.addValue("TDS_SOLUTION_ID",dataSource.getSolution().getId());
		insert.addValue("TDS_TABLE ",dataSource.getTableName());
		insert.addValue("TDS_DECISIONED_COLUMN",dataSource.getDecisionedColumn());
		StringBuffer buff = new StringBuffer();
		for (String item : dataSource.getDecioningColumns()) buff.append(item+",");
		if (buff.length() == 0) throw new PlatformException("Decisioning columns are empty.");
		insert.addValue("TDS_DECISIONING_COLUMNS",buff.substring(0,buff.length()-1));
		try {
			_dbManager.insert(insert);
			_dbManager.commit();
		} catch (SQLException e) {
			_dbManager.rollback();
			throw new PlatformException("Insert "+insert.getQuery()+" has errors: "+e.getLocalizedMessage());
		}
	}
	
	public List<Object []> getTreeDataSourceData(IDataSource dataSource) throws PlatformException {
		List<Object []> returnList = new ArrayList<Object []>(100);
		int columns = dataSource.getDecisionedColumn().length()+1;
		List<Object> row = null;
		SQLSelect select = new SQLSelect();
		select.addTable(dataSource.getTableName());
		select.addColumn(dataSource.getDecisionedColumn());
		for (String columnName : dataSource.getDecioningColumns()) select.addColumn(columnName);
		ResultSet resultSet = null;
		try {
			resultSet = _externalDBManager.select(select);
			while (resultSet.next()) {
				row = new ArrayList<Object>(columns);
				for (int i=1;i<=columns;i++) row.add(resultSet.getObject(i));
				returnList.add(row.toArray(new Object [row.size()]));
			}
		} catch (SQLException e) {
			throw new PlatformException("Metoda getTreeDataSourceData() failed quering: " + select.getQuery()
					+ " Errors: " + e.getLocalizedMessage());
		} finally {
			//try {_externalDBManager.disconnect();} catch (SQLException e1) {};
			try {if (resultSet != null) resultSet.close();}catch (SQLException e1) {};
		}
		
		return returnList;
	}
	
	public IDataSource[] getTreeDataSources() throws PlatformException
	{
		List<IDataSource> dataSources = new LinkedList<IDataSource>();
		SQLSelect select = new SQLSelect();
		select.addTable("TREE_DATA_SOURCES");
		select.addColumn("TDS_ID");
		select.addColumn("TDS_NAME");
		select.addColumn("TDS_INFO");
		select.addColumn("TDS_TABLE");
		select.addColumn("TDS_DECISIONED_COLUMN");
		select.addColumn("TDS_DECISIONING_COLUMNS");
		select.addColumn("TDS_CREATE_DATE");
		select.addCondition("TDS_SOLUTION_ID = ",_solutionInfo.getId());
		ResultSet resultSet = null;
		try {
			resultSet = _externalDBManager.select(select);
			while (resultSet.next()) {
				int i = 1;
				DataSource dataSource = new DataSource(_solutionInfo);
				dataSource.setId(resultSet.getInt(i++));
				dataSource.setName(resultSet.getString(i++));
				dataSource.setInfo(resultSet.getString(i++));
				dataSource.setTableName(resultSet.getString(i++));
				dataSource.setDecisionedColumn(resultSet.getString(i++));
				dataSource.setDecioningColumns(resultSet.getString(i++).split(","));
				dataSource.setCreateDate(resultSet.getDate(i++));
				dataSources.add(dataSource);
			}
		} catch (SQLException e) {
			throw new PlatformException("Metoda getTreeDataSources() failed quering: " + select.getQuery()
					+ " Errors: " + e.getLocalizedMessage());
		} finally {
			try {if (resultSet != null) resultSet.close();}catch (SQLException e1) {};
		}
		return dataSources.toArray(new IDataSource[dataSources.size()]);
	}
	
	
	public IDataSource getTreeDataSource(int treeDataSourceId)throws PlatformException
	{
		SQLSelect select = new SQLSelect();
		IDataSource returnDataSource = null;
		select.addTable("TREE_DATA_SOURCES");
		select.addColumn("TDS_ID");
		select.addColumn("TDS_NAME");
		select.addColumn("TDS_INFO");
		select.addColumn("TDS_TABLE");
		select.addColumn("TDS_DECISIONED_COLUMN");
		select.addColumn("TDS_DECISIONING_COLUMNS");
		select.addColumn("TDS_CREATE_DATE");
		select.addCondition("TDS_SOLUTION_ID = ",_solutionInfo.getId());
		select.addCondition("TDS_ID = ",treeDataSourceId);
		ResultSet resultSet = null;
		try {
			resultSet = _externalDBManager.select(select);
			if(resultSet.next()) {
				int i = 1;
				DataSource dataSource = new DataSource(_solutionInfo);
				dataSource.setId(resultSet.getInt(i++));
				dataSource.setName(resultSet.getString(i++));
				dataSource.setInfo(resultSet.getString(i++));
				dataSource.setTableName(resultSet.getString(i++));
				dataSource.setDecisionedColumn(resultSet.getString(i++));
				dataSource.setDecioningColumns(resultSet.getString(i++).split(","));
				dataSource.setCreateDate(resultSet.getDate(i++));
				returnDataSource = dataSource;
			}
		} catch (SQLException e) {
			throw new PlatformException("Metoda getTreeDataSource() failed quering: " + select.getQuery()
					+ " Errors: " + e.getLocalizedMessage());
		} finally {
			try {if (resultSet != null) resultSet.close();}catch (SQLException e1) {};
		}
		return returnDataSource;
	}
	
	
	public void removeTreeDataSource(IDataSource dataSource) throws PlatformException
	{
		SQLDelete delete = new SQLDelete("TREE_DATA_SOURCES");
		delete.addCondition("TDS_SOLUTION_ID = ",_solutionInfo.getId());
		delete.addCondition("TDS_ID = ",dataSource.getId());
		try {
			_dbManager.delete(delete);
			_dbManager.commit();
		} catch (SQLException e) {
			_dbManager.rollback();
			throw new PlatformException("Delete "+delete.getQuery()+" has errors: "+e.getLocalizedMessage());
		}
		
	}
	
	public ITree createTree() throws PlatformException {
		return null;
	}
	
	public void addTree(ITree tree) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method addTree() not implemented yet!");
	}




	public ITree[] getAllTrees() throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getAllTrees() not implemented yet!");
	}

	public ITree getTree(int treeId) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTree() not implemented yet!");
	}





	public ITree[] getTrees(ISolution solution) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTrees() not implemented yet!");
	}

	public void removeTree(ITree tree) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeTree() not implemented yet!");
	}



}
