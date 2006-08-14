/*
 * Copyright (C) 2006 Salomon Team
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;
import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
final class TreeInfo implements IInfo
{
    public static final String GEN_NAME = "gen_trees_id";

    public static final String TABLE_NAME = "trees";

    private static final Logger LOGGER = Logger.getLogger(TreeInfo.class);

    private int _attributeSetID;

    private Date _cDate;

    private DBManager _dbManager;

    private String _info;

    private Date _lmDate;

    private String _name;

    private List<TreeNodeInfo> _nodes;

    private int _rootNodeID;

    private int _solutionID;

    private int _treeID;

    TreeInfo(DBManager dbManager)
    {
        _dbManager = dbManager;
        _nodes = new LinkedList<TreeNodeInfo>();
    }

    public void addNode(TreeNodeInfo nodeInfo)
    {
        _nodes.add(nodeInfo);
    }

    /**
     * @see salomon.platform.IInfo#delete()
     */
    public boolean delete() throws PlatformException, DBException
    {
        throw new UnsupportedOperationException(
                "Method TreeInfo.delete() not implemented yet!");
    }

    /**
     * Returns the attributeSetID.
     * @return The attributeSetID
     */
    public final int getAttributeSetID()
    {
        return _attributeSetID;
    }

    /**
     * @see salomon.platform.IInfo#getCreationDate()
     */
    public Date getCreationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeInfo.getCreationDate() not implemented yet!");
    }

    /**
     * @see salomon.platform.IInfo#getId()
     */
    public int getId()
    {
        throw new UnsupportedOperationException(
                "Method TreeInfo.getId() not implemented yet!");
    }

    /**
     * @see salomon.platform.IInfo#getLastModificationDate()
     */
    public Date getLastModificationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeInfo.getLastModificationDate() not implemented yet!");
    }

    /**
     * Returns the name.
     * @return The name
     */
    public final String getName()
    {
        return _name;
    }

    /**
     * Returns the nodes.
     * @return The nodes
     */
    public final List<TreeNodeInfo> getNodes()
    {
        return _nodes;
    }

    /**
     * Returns the solutionID.
     * @return The solutionID
     */
    public final int getSolutionID()
    {
        return _solutionID;
    }

    /**
     * @see salomon.platform.IInfo#load(java.sql.ResultSet)
     */
    public void load(ResultSet resultSet) throws PlatformException, DBException
    {
        // do not load solution_id - it is set while creating attribute set
        try {
            _treeID = resultSet.getInt("tree_id");
            _attributeSetID = resultSet.getInt("attributeset_id");
            _rootNodeID = resultSet.getInt("root_node_id");
            _name = resultSet.getString("tree_name");
            _info = resultSet.getString("tree_info");
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException("Cannot load tree!", e);
        }
    }

    public int save() throws PlatformException, DBException
    {
        // saving tree header
        // removing old items (not using TreeNodeInfo.delete() method, cause this way is more efficient)
        // TODO: why not simply update?
        SQLDelete delete = new SQLDelete();
        delete.setTable(TreeNodeInfo.TABLE_NAME);
        delete.addCondition("tree_id = ", _treeID);
        int rows;
        try {
            rows = _dbManager.delete(delete);
        } catch (SQLException e) {
            LOGGER.fatal("!", e);
            throw new DBException("Cannot delete!", e);
        }
        LOGGER.debug("rows deleted: " + rows);

        // saving header
        SQLUpdate update = new SQLUpdate(TABLE_NAME);
        if (_name != null) {
            update.addValue("tree_name", _name);
        }
        if (_info != null) {
            update.addValue("tree_info", _info);
        }

        if (_treeID == 0) {
            _cDate = new Date(System.currentTimeMillis());
            update.addValue("c_date", _cDate);
        }

        update.addValue("solution_id", _solutionID);
        update.addValue("attributeset_id", _attributeSetID);
        // it must be performed after inserting nodes        
        //        update.addValue("root_node_id", _rootNodeID);

        _lmDate = new Date(System.currentTimeMillis());
        update.addValue("lm_date", _lmDate);

        try {
            _treeID = _dbManager.insertOrUpdate(update, "tree_id", _treeID,
                    GEN_NAME);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException("Cannot save!", e);
        }

        // ensure that parent is always inserted before the child
        _nodes = sortNodes(_nodes);

        // saving items
        for (TreeNodeInfo node : _nodes) {
            node.setTreeID(_treeID);
            node.save();
        }

        // updating root node
        update = new SQLUpdate(TABLE_NAME);
        update.addValue("root_node_id", _rootNodeID);
        update.addCondition("tree_id =", _treeID);

        return _treeID;
    }

    /**
     * Set the value of attributeSetID field.
     * @param attributeSetID The attributeSetID to set
     */
    public final void setAttributeSetID(int attributeSetID)
    {
        _attributeSetID = attributeSetID;
    }

    /**
     * Set the value of name field.
     * @param name The name to set
     */
    public final void setName(String name)
    {
        _name = name;
    }

    /**
     * Set the value of rootNodeID field.
     * @param rootNodeID The rootNodeID to set
     */
    public final void setRootNodeID(int rootNodeID)
    {
        _rootNodeID = rootNodeID;
    }

    /**
     * Set the value of solutionID field.
     * @param solutionID The solutionID to set
     */
    public final void setSolutionID(int solutionID)
    {
        _solutionID = solutionID;
    }

    @Override
    public String toString()
    {
        String value = "tree_id: " + _treeID;
        value += " ,name: " + _name;
        value += " ,root_node_id: " + _rootNodeID;
        value += _nodes;
        return value;
    }

    void loadNodes(ResultSet resultSet) throws DBException, PlatformException
    {
        TreeNodeInfo nodeInfo = new TreeNodeInfo(_dbManager, false);
        nodeInfo.load(resultSet);
        addNode(nodeInfo);
    }

    /**
     * Returns child nodes for given list of parent nodes.
     * The list of parent nodes is removed from list of all nodes after finding all children.
     * 
     * @param parentNodes list of parent nodes
     * @param nodes list of all nodes
     * @return list of parent nodes
     */
    private List<TreeNodeInfo> getChildNodes(List<TreeNodeInfo> parentNodes,
            List<TreeNodeInfo> nodes)
    {
        List<TreeNodeInfo> childNodes = new LinkedList<TreeNodeInfo>();
        for (TreeNodeInfo parentNode : parentNodes) {
            for (TreeNodeInfo node : nodes) {
                if (node.getParentNodeID() == parentNode.getId()) {
                    childNodes.add(node);
                }
            }
        }
        // removing child nodes from nodes collection, they are unnecessary there
        nodes.removeAll(childNodes);
        return childNodes;
    }

    /**
     * Method sorts list of nodes. It moves all parents before their children
     * to ensure inserting them before children and don't break DB constraints (FK).
     * 
     * @param nodes
     * @return
     */
    private List<TreeNodeInfo> sortNodes(List<TreeNodeInfo> nodes)
    {
        LinkedList<TreeNodeInfo> nodeList = new LinkedList<TreeNodeInfo>();
        // looking for root node
        List<TreeNodeInfo> parentNodes = new LinkedList<TreeNodeInfo>();
        for (TreeNodeInfo node : nodes) {
            if (node.getParentNodeID() == 0) {
                parentNodes.add(node);
            }
        }

        TreeNodeInfo rootNode = parentNodes.get(0);
        // removing root node
        nodes.remove(rootNode);
        nodeList.add(rootNode);

        while (!nodes.isEmpty()) {
            List<TreeNodeInfo> childNodes = getChildNodes(parentNodes, nodes);
            nodeList.addAll(childNodes);
            parentNodes = childNodes;
        }

        // pointing to the proper list
        return nodeList;
    }
}
