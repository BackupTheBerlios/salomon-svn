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

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
final class TreeNodeInfo implements IInfo
{
    public static final String GEN_NAME = "gen_tree_nodes_id";

    public static final String TABLE_NAME = "tree_nodes";

    private static final Logger LOGGER = Logger.getLogger(TreeNodeInfo.class);

    private int _attributeItemID;

    private DBManager _dbManager;

    private int _nodeID;

    private String _nodeValue;

    private String _parentEdgeValue;

    private int _parentNodeID;

    private int _treeID;

    /**
     * @param dbManager
     * @throws DBException 
     */
    public TreeNodeInfo(DBManager dbManager, boolean generateID)
            throws DBException
    {
        _dbManager = dbManager;
        if (generateID) {
            try {
                _nodeID = _dbManager.generateID(GEN_NAME);
            } catch (SQLException e) {
                LOGGER.fatal("", e);
                throw new DBException(e.getLocalizedMessage());
            }
        }
    }

    /**
     * @see salomon.platform.IInfo#delete()
     */
    public boolean delete() throws PlatformException, DBException
    {
        SQLDelete delete = new SQLDelete();
        delete.setTable(TABLE_NAME);
        delete.addCondition("node_id = ", _nodeID);
        int rows;
        try {
            rows = _dbManager.delete(delete);
        } catch (SQLException e) {
            LOGGER.fatal("!", e);
            throw new DBException("Cannot delete!", e);
        }
        LOGGER.debug("rows deleted: " + rows);
        return (rows > 0);
    }

    /**
     * Returns the attributeItemID.
     * @return The attributeItemID
     */
    public final int getAttributeItemID()
    {
        return _attributeItemID;
    }

    /**
     * @see salomon.platform.IInfo#getCreationDate()
     */
    public Date getCreationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeNodeInfo.getCreationDate() not implemented yet!");
    }

    /**
     * @see salomon.platform.IInfo#getId()
     */
    public final int getId()
    {
        return _nodeID;
    }

    /**
     * @see salomon.platform.IInfo#getLastModificationDate()
     */
    public Date getLastModificationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method TreeNodeInfo.getLastModificationDate() not implemented yet!");
    }

    /**
     * Returns the nodeValue.
     * @return The nodeValue
     */
    public final String getNodeValue()
    {
        return _nodeValue;
    }

    /**
     * Returns the parentEdgeValue.
     * @return The parentEdgeValue
     */
    public final String getParentEdgeValue()
    {
        return _parentEdgeValue;
    }

    /**
     * Returns the parentNodeID.
     * @return The parentNodeID
     */
    public final int getParentNodeID()
    {
        return _parentNodeID;
    }

    /**
     * Returns the treeID.
     * @return The treeID
     */
    public final int getTreeID()
    {
        return _treeID;
    }

    /**
     * @see salomon.platform.IInfo#load(java.sql.ResultSet)
     */
    public void load(ResultSet resultSet) throws PlatformException, DBException
    {
        try {
            _treeID = resultSet.getInt("tree_id");
            _nodeID = resultSet.getInt("node_id");
            _attributeItemID = resultSet.getInt("attribute_item_id");
            _parentNodeID = resultSet.getInt("parent_node_id");
            _parentEdgeValue = resultSet.getString("parent_edge_value");
            _nodeValue = resultSet.getString("node_value");
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }
    }

    /**
     * @see salomon.platform.IInfo#save()
     */
    public int save() throws PlatformException, DBException
    {

        SQLInsert insert = new SQLInsert(TABLE_NAME);
        insert.addValue("node_id", _nodeID);
        insert.addValue("tree_id", _treeID);
        insert.addValue("attribute_item_id", _attributeItemID);
        insert.addValue("parent_node_id", _parentNodeID);
        insert.addValue("parent_edge_value", _parentEdgeValue);
        insert.addValue("node_value", _nodeValue);

        try {
            _dbManager.insert(insert);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException("Cannot save item!", e);
        }

        return _nodeID;
    }

    /**
     * Set the value of attributeItemID field.
     * @param attributeItemID The attributeItemID to set
     */
    public final void setAttributeItemID(int attributeItemID)
    {
        _attributeItemID = attributeItemID;
    }

    /**
     * Set the value of nodeID field.
     * @param nodeID The nodeID to set
     */
    public final void setID(int nodeID)
    {
        _nodeID = nodeID;
    }

    /**
     * Set the value of nodeValue field.
     * @param nodeValue The nodeValue to set
     */
    public final void setNodeValue(String nodeValue)
    {
        _nodeValue = nodeValue;
    }

    /**
     * Set the value of parentEdgeValue field.
     * @param parentEdgeValue The parentEdgeValue to set
     */
    public final void setParentEdgeValue(String parentEdgeValue)
    {
        _parentEdgeValue = parentEdgeValue;
    }

    /**
     * Set the value of parentNodeID field.
     * @param parentNodeID The parentNodeID to set
     */
    public final void setParentNodeID(int parentNodeID)
    {
        _parentNodeID = parentNodeID;
    }

    /**
     * Set the value of treeID field.
     * @param treeID The treeID to set
     */
    public final void setTreeID(int treeID)
    {
        _treeID = treeID;
    }

    @Override
    public String toString()
    {
        String value = "[node_id: " + _nodeID;
        //        value += " ,tree_id: " + _treeID;
        value += " ,parent_id: " + _parentNodeID;
        //        value += " ,attr_id: " + _attributeItemID;
        //        value += " ,parent_edge: " + _parentEdgeValue;
        //        value += " ,value: " + _nodeValue;
        value += "]";
        return value;
    }

}
