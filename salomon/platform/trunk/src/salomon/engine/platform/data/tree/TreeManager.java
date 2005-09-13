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
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.INode.Type;
import salomon.platform.exception.PlatformException;

/**
 * 
 * 
 * @author Mateusz Nowakowski
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
			_dbManager.insert(insert,"TDS_ID","GEN_TREE_DATA_SOURCES_ID");
			_dbManager.commit();
		} catch (SQLException e) {
			_dbManager.rollback();
			throw new PlatformException("Insert "+insert.getQuery()+" has errors: "+e.getLocalizedMessage());
		}
	}
	
	public List<Object []> getTreeDataSourceData(IDataSource dataSource) throws PlatformException {
		if (dataSource == null) return null;
		List<Object []> returnList = new ArrayList<Object []>();
		int columns = dataSource.getDecioningColumns().length+1;
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
			resultSet = _dbManager.select(select);
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
			resultSet = _dbManager.select(select);
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
		if (dataSource != null ){
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
	}
	
	public ITree createTree() throws PlatformException {
		return new Tree();
	}

	public ITree createTree(int dataSourceId, String info, String name, INode root) throws PlatformException {
		IDataSource dataSource = getTreeDataSource(dataSourceId);
		if (dataSource == null) throw new PlatformException("There isn`t tree data source with id: "+dataSourceId+" in database.");
		if (root == null) throw new PlatformException("Root cannot be null.");
		return new Tree(dataSource,info,name,root);
	}	
	
	public INode createNode(INode parentNode, String edge, Type type, String value){
		return new Node(parentNode,edge,type,value);
	}
	
	
	public void addTree(ITree tree) throws PlatformException
	{
		if ((tree == null)||(tree.getRoot() == null)) throw new PlatformException("Tree must by initialised.");
		int trnId = this.insertNode(tree.getRoot(),null);
		SQLInsert insert = new SQLInsert("TREES");
		insert.addValue("TRE_NAME ",(tree.getName() == null ? "" : tree.getName()));
		insert.addValue("TRE_INFO ",(tree.getInfo() == null ? "" : tree.getInfo()));
		insert.addValue("TRE_TDS_ID ",tree.getDataSource().getId());
		insert.addValue("TRE_TRN_ID ",trnId);
		try {
			_dbManager.insert(insert,"TRE_ID"," GEN_TREES_ID");
			_dbManager.commit();
		} catch (SQLException e) {
			_dbManager.rollback();
			throw new PlatformException("Insert "+insert.getQuery()+" has errors: "+e.getLocalizedMessage());
		}
	}

	/**
	 * Metoda pomocnicza ktora zapisuje wszystkie nody do bazy do tabeli TREE_NODES
	 * Wykonuje tyle zapisow rekursywnych ile trzeba (dla kazdego dziecka odpala siebie).
	 * Nalezy ja odpalac tylko raz dla korzenia drzewa oraz dla null dla nodeTableId
	 * Metoda nie wykonuje commit, zatwierdzenie transakcji ma wykonac metoda wyzej
	 * @param in
	 * @param nodeTableId
	 * @return
	 * @throws PlatformException
	 */
	protected int insertNode(INode in, Integer nodeTableId)throws PlatformException{
		int returnId;
		SQLInsert insert = new SQLInsert("TREE_NODES");
		insert.addValue("TRN_PARENT_NODE_ID",(nodeTableId == null ? 0 : nodeTableId));
		insert.addValue("TRN_PARENT_EDGE",(in.getParentEdge() == null ? "" : in.getParentEdge()));
		insert.addValue("TRN_TYPE",(in.getType().equals(Type.COLUMN) ? "C" : "V"));
		insert.addValue("TRN_VALUE ",(in.getValue() == null ? "" : in.getValue()));

		try {
			returnId = _dbManager.insert(insert,"TRN_ID"," GEN_TREE_NODES_ID");
			if (!in.isLeaf())
			for (INode node: in.getChildren()){
				insertNode(node,returnId);
			}
		} catch (SQLException e) {
			_dbManager.rollback();
			throw new PlatformException("Insert "+insert.getQuery()+" has errors: "+e.getLocalizedMessage());
		}
		return returnId;
	}

	public ITree[] getTrees() throws PlatformException
	{
		List<ITree> trees = new LinkedList<ITree>();
		SQLSelect select = new SQLSelect();//select TRE_ID, TRE_NAME, TRE_INFO, TRE_TDS_ID,TRE_TRN_ID from TREES, TREE_DATA_SOURCES where TRE_TDS_ID = TDS_ID and TDS_SOLUTION_ID = <sol_id>
		select.addTable("TREES");
		select.addTable("TREE_DATA_SOURCES");
		select.addColumn("TRE_ID");
		select.addColumn("TRE_NAME");
		select.addColumn("TRE_INFO");
		select.addColumn("TRE_TDS_ID");
		select.addColumn("TRE_TRN_ID");
		select.addColumn("TRE_CREATE_DATE");
		select.addColumn("TRE_MODIFY_DATE");
		select.addCondition("TRE_TDS_ID = TDS_ID");
		select.addCondition("TDS_SOLUTION_ID = ",_solutionInfo.getId());
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(select);
			while (resultSet.next()) {
				int i = 1;
				ITree tree = new Tree();
				tree.setId(resultSet.getInt(i++));
				tree.setName(resultSet.getString(i++));
				tree.setInfo(resultSet.getString(i++));
				tree.setDataSource(this.getTreeDataSource(resultSet.getInt(i++)));
				//TODO
				//tree.setRoot());
				trees.add(tree);
			}
		} catch (SQLException e) {
			throw new PlatformException("Metoda getTrees() failed quering: " + select.getQuery()
					+ " Errors: " + e.getLocalizedMessage());
		} finally {
			try {if (resultSet != null) resultSet.close();}catch (SQLException e1) {};
		}
		return trees.toArray(new ITree[trees.size()]);		
	}
	
	/**
	 * 
	 * @param rootId
	 * @return
	 */
	protected INode getTreeNodes(int rootId){
		
		return null;
	}
	
	
	
	public ITree getTree(int treeId) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method getTree() not implemented yet!");
	}




	public void removeTree(ITree tree) throws PlatformException
	{
		throw new UnsupportedOperationException(
				"Method removeTree() not implemented yet!");
	}



}
