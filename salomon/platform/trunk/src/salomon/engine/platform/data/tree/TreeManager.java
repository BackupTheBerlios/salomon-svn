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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.data.attribute.AttributeDescription;
import salomon.engine.platform.data.attribute.AttributeManager;
import salomon.engine.platform.data.attribute.AttributeSet;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeManager;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Implementacja Tree manager`a
 * 
 * @author Mateusz Nowakowski
 *
 */
public final class TreeManager implements ITreeManager
{

    private static final Logger LOGGER = Logger.getLogger(TreeManager.class);

    private DBManager _dbManager;

    private ExternalDBManager _externalDBManager;

    private ShortSolutionInfo _solutionInfo;

    private AttributeManager _attributeManager;

    //    private DBManager _dbManager;
    //
    //    private ExternalDBManager _externalDBManager;
    //
    //    private ShortSolutionInfo _solutionInfo;
    //
    //    /**
    //     * Inicjalizuje tree managera. 
    //     * 
    //     * @param dbManager dostep do wewnetrzna baza salomona
    //     * @param solutionInfo identyfikator rozwi¹zania
    //     * @param externalDBManager dostep do zewnetrznej baza salomona zdefiniowanej przez rozwi¹zanie
    //     */
    public TreeManager(AttributeManager attributeManager, DBManager dbManager,
            ShortSolutionInfo solutionInfo, ExternalDBManager externalDBManager)
    {
        _attributeManager = attributeManager;
        _dbManager = dbManager;
        _solutionInfo = solutionInfo;
        _externalDBManager = externalDBManager;
    }

    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTableSize(java.lang.String)
    //     */
    //    public int getTableSize(String tableName) throws PlatformException
    //    {
    //        if ((tableName == null) || tableName.equals(""))
    //            throw new PlatformException("Nazwa tabeli nie moze byc pusta");
    //
    //        SQLSelect select = new SQLSelect();
    //        select.addTable(tableName);
    //        select.addColumn("count(*)");
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _externalDBManager.select(select);
    //            if (resultSet.next())
    //                return resultSet.getInt(1);
    //            else
    //                throw new PlatformException(
    //                        "Metoda getTableSize() failed quering: "
    //                                + select.getQuery()
    //                                + " Errors: Brak zwroconego kursora");
    //
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTableSize() failed quering: "
    //                            + select.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#createTreeDataSource()
    //     */
    //    public IDataSource createTreeDataSource() throws PlatformException
    //    {
    //        return new DataSource(_solutionInfo);
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#addTreeDataSource(salomon.platform.data.tree.IDataSource)
    //     */
    //    public int addTreeDataSource(IDataSource dataSource)
    //            throws PlatformException
    //    {
    //        int treeDataSourceId = 0;
    //        SQLInsert insert = new SQLInsert("TREE_DATA_SOURCES");
    //        insert.addValue("TDS_NAME", dataSource.getName());
    //        insert.addValue("TDS_INFO", dataSource.getInfo());
    //        insert.addValue("TDS_SOLUTION_ID", dataSource.getSolution().getId());
    //        insert.addValue("TDS_TABLE ", dataSource.getTableName());
    //        insert.addValue("TDS_DECISIONED_COLUMN",
    //                dataSource.getDecisionedColumn());
    //        insert.addValue("TDS_FIRST_ROW", dataSource.getFirstRowIndex());
    //        insert.addValue("TDS_LAST_ROW", dataSource.getLastRowIndex());
    //        StringBuffer buff = new StringBuffer();
    //        for (String item : dataSource.getDecioningColumns())
    //            buff.append(item + ",");
    //        if (buff.length() == 0)
    //            throw new PlatformException("Decisioning columns are empty.");
    //        insert.addValue("TDS_DECISIONING_COLUMNS", buff.substring(0,
    //                buff.length() - 1));
    //        try {
    //            treeDataSourceId = _dbManager.insert(insert, "TDS_ID",
    //                    "GEN_TREE_DATA_SOURCES_ID");
    //            _dbManager.commit();
    //        } catch (SQLException e) {
    //            _dbManager.rollback();
    //            throw new PlatformException("Insert " + insert.getQuery()
    //                    + " has errors: " + e.getLocalizedMessage());
    //        }
    //        return treeDataSourceId;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTreeDataSourceData(salomon.platform.data.tree.IDataSource)
    //     */
    //    public List<Object[]> getTreeDataSourceData(IDataSource dataSource)
    //            throws PlatformException
    //    {
    //        if (dataSource == null)
    //            return null;
    //        List<Object[]> returnList = new ArrayList<Object[]>();
    //        int columns = dataSource.getDecioningColumns().length + 1;
    //        List<Object> row = null;
    //        SQLSelect select = new SQLSelect();
    //        select.addTable(dataSource.getTableName());
    //        select.addColumn(dataSource.getDecisionedColumn());
    //        for (String columnName : dataSource.getDecioningColumns())
    //            select.addColumn(columnName);
    //        //Nie moge zastosowac konstrukcji SELECT [FIRST (<integer expr m>)] [SKIP (<integer expr n>)]
    //        //SQLSelect bardziej przeszkadza niz pomaga
    //        int first = dataSource.getFirstRowIndex();
    //        int last = dataSource.getLastRowIndex();
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _externalDBManager.select(select);
    //            int j = 1;
    //            while (resultSet.next() && j <= last) {
    //                if (j >= first) {
    //                    row = new ArrayList<Object>(columns);
    //                    for (int i = 1; i <= columns; i++)
    //                        row.add(resultSet.getObject(i));
    //                    returnList.add(row.toArray(new Object[row.size()]));
    //                }
    //                j++;
    //            }
    //            if (j < last)
    //                throw new PlatformException(
    //                        "Metoda getTreeDataSourceData() failed due to: Definicja datasource nie zgadza sie ze stanem faktycznym. Parametry firstRow i lastRow nie sa zgodne z prawda.");
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeDataSourceData() failed quering: "
    //                            + select.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //
    //        return returnList;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getRestTreeDataSourceRows(salomon.platform.data.tree.IDataSource)
    //     */
    //    public List<Object[]> getRestTreeDataSourceRows(IDataSource dataSource)
    //            throws PlatformException
    //    {
    //        if (dataSource == null)
    //            return null;
    //        List<Object[]> returnList = new ArrayList<Object[]>();
    //        int columns = dataSource.getDecioningColumns().length + 1;
    //        List<Object> row = null;
    //        SQLSelect select = new SQLSelect();
    //        select.addTable(dataSource.getTableName());
    //        select.addColumn(dataSource.getDecisionedColumn());
    //        for (String columnName : dataSource.getDecioningColumns())
    //            select.addColumn(columnName);
    //        //Nie moge zastosowac konstrukcji SELECT [FIRST (<integer expr m>)] [SKIP (<integer expr n>)]
    //        //SQLSelect bardziej przeszkadza niz pomaga
    //        int first = dataSource.getFirstRowIndex();
    //        int last = dataSource.getLastRowIndex();
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _externalDBManager.select(select);
    //            int j = 1;
    //            while (resultSet.next()) {
    //                if ((j < first) || (j > last)) {
    //                    row = new ArrayList<Object>(columns);
    //                    row.add(j);
    //                    for (int i = 1; i <= columns; i++)
    //                        row.add(resultSet.getObject(i));
    //                    returnList.add(row.toArray(new Object[row.size()]));
    //                }
    //                j++;
    //            }
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeDataSourceData() failed quering: "
    //                            + select.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //
    //        return returnList;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTreeDataSources()
    //     */
    //    public IDataSource[] getTreeDataSources() throws PlatformException
    //    {
    //        List<IDataSource> dataSources = new LinkedList<IDataSource>();
    //        SQLSelect select = new SQLSelect();
    //        select.addTable("TREE_DATA_SOURCES");
    //        select.addColumn("TDS_ID");
    //        select.addColumn("TDS_NAME");
    //        select.addColumn("TDS_INFO");
    //        select.addColumn("TDS_TABLE");
    //        select.addColumn("TDS_DECISIONED_COLUMN");
    //        select.addColumn("TDS_DECISIONING_COLUMNS");
    //        select.addColumn("TDS_FIRST_ROW");
    //        select.addColumn("TDS_LAST_ROW");
    //        select.addColumn("TDS_CREATE_DATE");
    //        select.addCondition("TDS_SOLUTION_ID = ", _solutionInfo.getId());
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _dbManager.select(select);
    //            while (resultSet.next()) {
    //                int i = 1;
    //                DataSource dataSource = new DataSource(_solutionInfo);
    //                dataSource.setId(resultSet.getInt(i++));
    //                dataSource.setName(resultSet.getString(i++));
    //                dataSource.setInfo(resultSet.getString(i++));
    //                dataSource.setTableName(resultSet.getString(i++));
    //                dataSource.setDecisionedColumn(resultSet.getString(i++));
    //                dataSource.setDecioningColumns(resultSet.getString(i++).split(
    //                        ","));
    //                dataSource.setFirstRowIndex(resultSet.getInt(i++));
    //                dataSource.setLastRowIndex(resultSet.getInt(i++));
    //                dataSource.setCreateDate(resultSet.getDate(i++));
    //                dataSources.add(dataSource);
    //            }
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeDataSources() failed quering: "
    //                            + select.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //        return dataSources.toArray(new IDataSource[dataSources.size()]);
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTreeDataSource(int)
    //     */
    //    public IDataSource getTreeDataSource(int treeDataSourceId)
    //            throws PlatformException
    //    {
    //        SQLSelect select = new SQLSelect();
    //        IDataSource returnDataSource = null;
    //        select.addTable("TREE_DATA_SOURCES");
    //        select.addColumn("TDS_ID");
    //        select.addColumn("TDS_NAME");
    //        select.addColumn("TDS_INFO");
    //        select.addColumn("TDS_TABLE");
    //        select.addColumn("TDS_DECISIONED_COLUMN");
    //        select.addColumn("TDS_DECISIONING_COLUMNS");
    //        select.addColumn("TDS_FIRST_ROW");
    //        select.addColumn("TDS_LAST_ROW");
    //        select.addColumn("TDS_CREATE_DATE");
    //        select.addCondition("TDS_SOLUTION_ID = ", _solutionInfo.getId());
    //        select.addCondition("TDS_ID = ", treeDataSourceId);
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _dbManager.select(select);
    //            if (resultSet.next()) {
    //                int i = 1;
    //                DataSource dataSource = new DataSource(_solutionInfo);
    //                dataSource.setId(resultSet.getInt(i++));
    //                dataSource.setName(resultSet.getString(i++));
    //                dataSource.setInfo(resultSet.getString(i++));
    //                dataSource.setTableName(resultSet.getString(i++));
    //                dataSource.setDecisionedColumn(resultSet.getString(i++));
    //                dataSource.setDecioningColumns(resultSet.getString(i++).split(
    //                        ","));
    //                dataSource.setFirstRowIndex(resultSet.getInt(i++));
    //                dataSource.setLastRowIndex(resultSet.getInt(i++));
    //                dataSource.setCreateDate(resultSet.getDate(i++));
    //                returnDataSource = dataSource;
    //            }
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeDataSource() failed quering: "
    //                            + select.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //        return returnDataSource;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#removeTreeDataSource(int)
    //     */
    //    public void removeTreeDataSource(int dataSourceId) throws PlatformException
    //    {
    //        IDataSource dataSource = this.getTreeDataSource(dataSourceId);
    //        SQLDelete delete = new SQLDelete("TREE_DATA_SOURCES");
    //        delete.addCondition("TDS_SOLUTION_ID = ", _solutionInfo.getId());
    //        delete.addCondition("TDS_ID = ", dataSource.getId());
    //        try {
    //            _dbManager.delete(delete);
    //            _dbManager.commit();
    //        } catch (SQLException e) {
    //            _dbManager.rollback();
    //            throw new PlatformException("Delete " + delete.getQuery()
    //                    + " has errors: " + e.getLocalizedMessage());
    //        }
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#createTree()
    //     */
    //    public ITree createTree(ITreeNode rootNote) throws PlatformException
    //    {
    //        return new Tree();
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#createTree(int, java.lang.String, java.lang.String, salomon.platform.data.tree.INode)
    //     */
    //    public ITree createTree(int dataSourceId, String info, String name,
    //            ITreeNode root) throws PlatformException
    //    {
    //        IDataSource dataSource = getTreeDataSource(dataSourceId);
    //        if (dataSource == null)
    //            throw new PlatformException(
    //                    "There isn`t tree data source with id: " + dataSourceId
    //                            + " in database.");
    //        if (root == null)
    //            throw new PlatformException("Root cannot be null.");
    //        return new Tree(dataSource, info, name, root);
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#createNode(salomon.platform.data.tree.INode, java.lang.String, salomon.platform.data.tree.INode.Type, java.lang.String)
    //     */
    //    public ITreeNode createNode(ITreeNode parentNode, String edge, Type type,
    //            String value)
    //    {
    //        return new TreeNode(parentNode, edge, type, value);
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#addTree(salomon.platform.data.tree.ITree)
    //     */
    //    public void add(ITree tree) throws PlatformException
    //    {
    //        int returnCreatedTreeId = 0;
    //        if ((tree == null) || (tree.getRootNode() == null))
    //            throw new PlatformException("Tree must by initialised.");
    //        int trnId = this.insertNode(tree.getRootNode(), null);
    //        SQLInsert insert = new SQLInsert("TREES");
    //        insert.addValue("TRE_NAME ", (tree.getName() == null
    //                ? ""
    //                : tree.getName()));
    //        insert.addValue("TRE_INFO ", (tree.getInfo() == null
    //                ? ""
    //                : tree.getInfo()));
    //        insert.addValue("TRE_TDS_ID ", tree.getDataSource().getId());
    //        insert.addValue("TRE_TRN_ID ", trnId);
    //        try {
    //            returnCreatedTreeId = _dbManager.insert(insert, "TRE_ID",
    //                    " GEN_TREES_ID");
    //            _dbManager.commit();
    //        } catch (SQLException e) {
    //            _dbManager.rollback();
    //            throw new PlatformException("Insert " + insert.getQuery()
    //                    + " has errors: " + e.getLocalizedMessage());
    //        }
    //        return returnCreatedTreeId;
    //    }
    //
    //    /**
    //     * Metoda pomocnicza ktora zapisuje wszystkie nody do bazy do tabeli TREE_NODES
    //     * Wykonuje tyle zapisow rekursywnych ile trzeba (dla kazdego dziecka odpala siebie).
    //     * Nalezy ja odpalac tylko raz dla korzenia drzewa oraz dla null dla nodeTableId
    //     * Metoda nie wykonuje commit, zatwierdzenie transakcji ma wykonac metoda wyzej
    //     * @param in
    //     * @param nodeTableId
    //     * @return
    //     * @throws PlatformException
    //     */
    //    protected int insertNode(ITreeNode in, Integer nodeTableId)
    //            throws PlatformException
    //    {
    //        int returnId;
    //        SQLInsert insert = new SQLInsert("TREE_NODES");
    //        insert.addValue("TRN_PARENT_NODE_ID", (nodeTableId == null
    //                ? 0
    //                : nodeTableId));
    //        //FIXME:
    ////        insert.addValue("TRN_PARENT_EDGE", (in.getParentEdge() == null
    ////                ? ""
    ////                : in.getParentEdge()));
    //        insert.addValue("TRN_TYPE", (in.getType().equals(Type.COLUMN)
    //                ? "C"
    //                : "V"));
    //        insert.addValue("TRN_VALUE ", (in.getValue() == null
    //                ? ""
    //                : in.getValue()));
    //
    //        try {
    //            returnId = _dbManager.insert(insert, "TRN_ID", " GEN_TREE_NODES_ID");
    //            if (!in.isLeaf())
    //                for (ITreeNode node : in.getChildren()) {
    //                    insertNode(node, returnId);
    //                }
    //        } catch (SQLException e) {
    //            _dbManager.rollback();
    //            throw new PlatformException("Insert " + insert.getQuery()
    //                    + " has errors: " + e.getLocalizedMessage());
    //        }
    //        return returnId;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTrees()
    //     */
    //    public ITree[] getAll() throws PlatformException
    //    {
    //        List<ITree> trees = new LinkedList<ITree>();
    //        SQLSelect select = new SQLSelect();//select TRE_ID, TRE_NAME, TRE_INFO, TRE_TDS_ID,TRE_TRN_ID from TREES, TREE_DATA_SOURCES where TRE_TDS_ID = TDS_ID and TDS_SOLUTION_ID = <sol_id>
    //        select.addTable("TREES");
    //        select.addTable("TREE_DATA_SOURCES");
    //        select.addColumn("TRE_ID");
    //        select.addColumn("TRE_NAME");
    //        select.addColumn("TRE_INFO");
    //        select.addColumn("TRE_TDS_ID");
    //        select.addColumn("TRE_TRN_ID");
    //        select.addColumn("TRE_CREATE_DATE");
    //        select.addColumn("TRE_MODIFY_DATE");
    //        select.addCondition("TRE_TDS_ID = TDS_ID");
    //        select.addCondition("TDS_SOLUTION_ID = ", _solutionInfo.getId());
    //        ResultSet resultSet = null;
    //        try {
    //            List<Integer> rootIds = new LinkedList<Integer>();
    //            List<Integer> tdsIds = new LinkedList<Integer>();
    //            resultSet = _dbManager.select(select);
    //            while (resultSet.next()) {
    //                int i = 1;
    //                ITree tree = new Tree();
    //                tree.setId(resultSet.getInt(i++));
    //                tree.setName(resultSet.getString(i++));
    //                tree.setInfo(resultSet.getString(i++));
    //                tdsIds.add(resultSet.getInt(i++)); // potem sciagam dane data sourca, pomysl chlopakow z jednym connectionem + statementem ogranicza jak cholera :[
    //                rootIds.add(resultSet.getInt(i++)); // potem tworze same drzewo
    //                tree.setCreateDate(resultSet.getDate(i++));
    //                trees.add(tree);
    //            }
    //            resultSet.close();
    //            Iterator<Integer> tdsIt = tdsIds.iterator();
    //            Iterator<Integer> rootIt = rootIds.iterator();
    //            for (ITree tree : trees) {
    //                int rootId = rootIt.next();
    //                int tdsId = tdsIt.next();
    //                tree.setRoot(getTreeNodes(rootId));
    //                tree.setDataSource(getTreeDataSource(tdsId));
    //            }
    //
    //        } catch (SQLException e) {
    //            throw new PlatformException("Metoda getTrees() failed quering: "
    //                    + select.getQuery() + " Errors: " + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //        return trees.toArray(new ITree[trees.size()]);
    //    }
    //
    //    /**
    //     * Sciaga cale drzewo dla podanego id node`a ktory jest korzeniem
    //     * @param rootId
    //     * @return
    //     */
    //    protected ITreeNode getTreeNodes(int rootId) throws PlatformException
    //    {
    //        ITreeNode rootNode = null;
    //        SQLSelect tselect = new SQLSelect();
    //        tselect.addTable("TREE_NODES");
    //        tselect.addColumn("TRN_TYPE");
    //        tselect.addColumn("TRN_VALUE");
    //        tselect.addCondition("TRN_ID =", rootId);
    //        try {
    //            ResultSet resultSet = _dbManager.select(tselect);
    //            if (resultSet.next()) {
    //                int i = 1;
    //                Type type = (resultSet.getString(i++).equals("C")
    //                        ? Type.COLUMN
    //                        : Type.VALUE);
    //                String value = resultSet.getString(i++);
    //                rootNode = new TreeNode(rootId, null, null, type, value);
    //                setChildren(rootNode);
    //            } else
    //                throw new PlatformException("Cannot find root with id: "
    //                        + rootId);
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeNodes() failed quering: "
    //                            + tselect.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        }
    //        return rootNode;
    //    }
    //
    //    /**
    //     * Sciaga z bazy cale drzewo dzieci dla podanego noda
    //     * @param root
    //     * @throws PlatformException
    //     */
    //    protected void setChildren(ITreeNode root) throws PlatformException
    //    {
    //        SQLSelect tselect = new SQLSelect();
    //        tselect.addTable("TREE_NODES");
    //        tselect.addColumn("TRN_ID");
    //        tselect.addColumn("TRN_PARENT_EDGE");
    //        tselect.addColumn("TRN_TYPE");
    //        tselect.addColumn("TRN_VALUE");
    //        tselect.addCondition("TRN_PARENT_NODE_ID = ", root.getId());
    //        try {
    //            ResultSet resultSet = _dbManager.select(tselect);
    //            List<ITreeNode> childs = new ArrayList<ITreeNode>();
    //            while (resultSet.next()) {
    //                int i = 1;
    //                int id = resultSet.getInt(i++);
    //                String parentEdge = resultSet.getString(i++);
    //                Type type = (resultSet.getString(i++).equals("C")
    //                        ? Type.COLUMN
    //                        : Type.VALUE);
    //                String value = resultSet.getString(i++);
    //                childs.add(new TreeNode(id, root, parentEdge, type, value));
    //            }
    //            resultSet.close();
    //            for (ITreeNode child : childs) {
    //                this.setChildren(child);
    //            }
    //        } catch (SQLException e) {
    //            throw new PlatformException(
    //                    "Metoda getTreeNodes() failed quering: "
    //                            + tselect.getQuery() + " Errors: "
    //                            + e.getLocalizedMessage());
    //        }
    //
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#getTree(int)
    //     */
    //    public ITree getTree(int treeId) throws PlatformException
    //    {
    //        ITree returnTree = null;
    //        SQLSelect select = new SQLSelect();
    //        select.addTable("TREES");
    //        select.addTable("TREE_DATA_SOURCES");
    //        select.addColumn("TRE_NAME");
    //        select.addColumn("TRE_INFO");
    //        select.addColumn("TRE_TDS_ID");
    //        select.addColumn("TRE_TRN_ID");
    //        select.addColumn("TRE_CREATE_DATE");
    //        select.addColumn("TRE_MODIFY_DATE");
    //        select.addCondition("TRE_TDS_ID = TDS_ID");
    //        select.addCondition("TDS_SOLUTION_ID = ", _solutionInfo.getId());
    //        select.addCondition("TRE_ID = ", treeId);
    //        ResultSet resultSet = null;
    //        try {
    //            resultSet = _dbManager.select(select);
    //            String name;
    //            String info;
    //            int tdsId;
    //            int rootNodeId;
    //            Date createDate;
    //
    //            if (resultSet.next()) {
    //                int i = 1;
    //                name = resultSet.getString(i++);
    //                info = resultSet.getString(i++);
    //                tdsId = resultSet.getInt(i++);
    //                rootNodeId = resultSet.getInt(i++);
    //                createDate = resultSet.getDate(i++);
    //            } else
    //                throw new PlatformException(
    //                        "Nie znaleziona drzewka o id:"
    //                                + treeId
    //                                + " ktore jest zwiazane z aktualnym rozwiazaniem o id: "
    //                                + _solutionInfo.getId());
    //            resultSet.close();
    //            returnTree = new Tree(treeId, createDate, getTreeDataSource(tdsId),
    //                    info, name, getTreeNodes(rootNodeId));
    //
    //        } catch (SQLException e) {
    //            throw new PlatformException("Metoda getTrees() failed quering: "
    //                    + select.getQuery() + " Errors: " + e.getLocalizedMessage());
    //        } finally {
    //            try {
    //                if (resultSet != null)
    //                    resultSet.close();
    //            } catch (SQLException e1) {
    //            };
    //        }
    //        return returnTree;
    //    }
    //
    //    /* (non-Javadoc)
    //     * @see salomon.platform.data.tree.ITreeManager#removeTree(int)
    //     */
    //    public void remove(int treeId) throws PlatformException
    //    {
    //        ITree tree = this.getTree(treeId);
    //        int rootId = tree.getRootNode().getId();
    //        SQLDelete delete = new SQLDelete("TREE_NODES");
    //        delete.addCondition("TRN_ID = ", rootId);
    //        try {
    //            _dbManager.delete(delete);
    //            _dbManager.commit();
    //        } catch (SQLException e) {
    //            _dbManager.rollback();
    //            throw new PlatformException("Delete " + delete.getQuery()
    //                    + " has errors: " + e.getLocalizedMessage());
    //        }
    //    }

    public void add(ITree tree) throws PlatformException
    {
        try {
            ((Tree) tree).getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new DBException(e);
        }
    }

    public ITree createTree(ITreeNode rootNode) throws PlatformException
    {
        Tree tree = new Tree(rootNode, _dbManager);
        tree.getInfo().setSolutionID(_solutionInfo.getId());
        return tree;
    }

    public ITree[] getAll() throws PlatformException
    {
        // enforcing getting all attribute sets
        return getTrees(-1);
    }

    public ITree getTree(int treeId) throws PlatformException
    {
        ITree[] trees = getTrees(treeId);
        ITree tree = trees.length > 0 ? trees[0] : null;
        return tree;
    }

    public ITree getTree(String name) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeManager.getTree() not implemented yet!");
    }

    public void remove(ITree tree) throws PlatformException
    {
        // deleting tree set
        // others should be deleted cascadly
        try {
            ((Tree) tree).getInfo().delete();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
        }
    }

    private ITree[] getTrees(int treeID) throws PlatformException
    {
        List<ITree> trees = new LinkedList<ITree>();

        SQLSelect select = new SQLSelect();
        select.addTable(TreeInfo.TABLE_NAME + " t");
        select.addTable(TreeNodeInfo.TABLE_NAME + " tn");
        select.addColumn("t.tree_id");
        select.addColumn("t.attributeset_id");
        select.addColumn("t.root_node_id");
        select.addColumn("t.tree_name");
        select.addColumn("t.tree_info");
        select.addColumn("tn.node_id");
        select.addColumn("tn.parent_node_id");
        select.addColumn("tn.attribute_item_id");
        select.addColumn("tn.parent_edge_value");
        select.addColumn("tn.node_value");
        select.addCondition("t.tree_id = tn.tree_id");
        select.addCondition("t.solution_id = ", _solutionInfo.getId());
        // to avoid selecting default tree
        select.addCondition("t.tree_id > ", 0);
        // if attributeSetID >= 0 it means, that certain attribute set should be selected
        if (treeID > 0) {
            select.addCondition("t.tree_id = ", treeID);
        }
        select.addOrderBy("tn.tree_id");
        select.addOrderBy("tn.node_id");
        select.addOrderBy("tn.parent_node_id");

        ResultSet resultSet = null;
        // always set default
        int firstTreeID = -1;
        try {
            resultSet = _dbManager.select(select);
            Tree tree = null;
            while (resultSet.next()) {
                int tmpTreeID = resultSet.getInt("tree_id");
                // when tree_id changes, creating new Tree object
                if (tmpTreeID != firstTreeID) {
                    // processing next tree 
                    firstTreeID = tmpTreeID;
                    tree = (Tree) this.createTree(null);
                    tree.getInfo().load(resultSet);
                    trees.add(tree);
                }
                // loading nodes
                ((TreeInfo) tree.getInfo()).loadNodes(resultSet);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.fatal("", e);
                }
            }
        }

        // building trees
        for (ITree tree : trees) {
            LOGGER.debug("building tree: " + tree.getName());
            buildTree((Tree) tree);
        }

        LOGGER.info("Trees list successfully loaded.");
        ITree[] treesArray = new ITree[trees.size()];
        return trees.toArray(treesArray);
    }

    /**
     * Method builds the tree basing on its database definition.
     * 
     * @param tree
     * @throws PlatformException
     */
    private void buildTree(Tree tree) throws PlatformException
    {
        TreeInfo treeInfo = (TreeInfo) tree.getInfo();

        // loading nodes
        List<TreeNodeInfo> nodeInfos = treeInfo.getNodes();

        // loading attribute set
        AttributeSet attributeSet = (AttributeSet) _attributeManager.getAttributeSet(treeInfo.getAttributeSetID());
        // getting attribute desciptions
        AttributeDescription[] descriptions = attributeSet.getDesciptions();

        // putting attributes to the map to speed up searching
        Map<Integer, AttributeDescription> attributeMap = new HashMap<Integer, AttributeDescription>();
        for (AttributeDescription description : descriptions) {
            attributeMap.put(description.getDescriptionID(), description);
        }

        // getting root node
        // according to the sorting order, it is the first node in the list od nodes
        // it is removed to avoid complications while building tree
        TreeNodeInfo rootNodeInfo = nodeInfos.remove(0);
        TreeNode rootNode = new TreeNode(attributeMap.get(rootNodeInfo),
                rootNodeInfo);
        // setting root node for given tree
        tree.setRootNode(rootNode);

        // creating nodes
        MultiMap nodesMap = new MultiHashMap();

        for (TreeNodeInfo nodeInfo : nodeInfos) {
            AttributeDescription description = attributeMap.get(nodeInfo.getAttributeItemID());
            TreeNode treeNode = new TreeNode(description, nodeInfo);
            // put it in map?
            // putting node to the map
            // it is indexed by parent node id (thats why root node was removed, cause it's parent id == 0
            nodesMap.put(treeNode.getInfo().getParentNodeID(), treeNode);
        }

        // getting all child nodes starting from the root node
        connectNodes(nodesMap, rootNode);
    }

    @SuppressWarnings("unchecked")
    private void connectNodes(MultiMap nodesMap, TreeNode parentNode)
    {
        LOGGER.info("TreeManager.connectNodes()");
        if (!nodesMap.isEmpty()) {
            int parentNodeID = parentNode.getInfo().getId();
            LOGGER.debug("parentNodeID: " + parentNodeID);
            Collection<TreeNode> childNodes = (Collection<TreeNode>) nodesMap.remove(parentNodeID);
            if (childNodes != null) {
                for (Iterator iter = childNodes.iterator(); iter.hasNext();) {
                    TreeNode childNode = (TreeNode) iter.next();
                    LOGGER.debug("childNodeID: " + childNode.getInfo().getId());
                    TreeEdge edge = new TreeEdge(parentNode, childNode);
                    edge.setValue(childNode.getInfo().getParentEdgeValue());
                    childNode.setParentEdge(edge);
                    parentNode.addChildEdge(edge);
                    connectNodes(nodesMap, childNode);
                }
            }
        }
    }
}
