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
import salomon.engine.database.ExternalDBManager;
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

    // TODO:
    //    private int _attributeSetID;

    private static final Logger LOGGER = Logger.getLogger(TreeInfo.class);

    private int _attributeSetID;

    private DBManager _dbManager;

    private ExternalDBManager _externalDBManager;

    private String _info;

    private String _name;

    private List<TreeNodeInfo> _nodes;

    private int _rootNodeID;

    private int _solutionID;

    private int _treeID;

    private Date _cDate;

    private Date _lmDate;

    TreeInfo(DBManager dbManager)
    {
        _dbManager = dbManager;
        _nodes = new LinkedList<TreeNodeInfo>();
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
        _lmDate = new Date(System.currentTimeMillis());
        update.addValue("lm_date", _lmDate);

        try {
            _treeID = _dbManager.insertOrUpdate(update, "tree_id", _treeID,
                    GEN_NAME);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save!", e);
        }

        // saving items
        for (TreeNodeInfo node : _nodes) {
            node.save();
        }

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
        TreeNodeInfo nodeInfo = new TreeNodeInfo();
        nodeInfo.load(resultSet);
        _nodes.add(nodeInfo);
    }
}
