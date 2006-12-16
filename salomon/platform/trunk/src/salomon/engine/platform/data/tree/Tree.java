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

import salomon.engine.database.DBManager;

import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeNode;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.data.attribute.AttributeSet;

/**
 *
 */
final class Tree implements ITree
{
    private IAttributeSet _attributeSet;

    private DBManager _dbManager;

    private TreeInfo _info;

    private ITreeNode _rootNode;

    public Tree(DBManager dbManager)
    {
        _dbManager = dbManager;
        _info = new TreeInfo(_dbManager);
    }

    public ITreeNode createNode(IAttributeDescription description)
            throws PlatformException
    {
        return new TreeNode(this, description, new TreeNodeInfo(_dbManager,
                true));
    }

    public ITreeNode evaluate(IDataSet[] attribute)
    {
        throw new UnsupportedOperationException(
                "Method Tree.evaluate() not implemented yet!");
    }

    public IAttributeSet getAttributeSet()
    {
        return _attributeSet;
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final TreeInfo getInfo()
    {
        return _info;
    }

    /**
     * @see salomon.platform.data.tree.ITree#getName()
     */
    public String getName()
    {
        return _info.getName();
    }

    /**
     * @see salomon.platform.data.tree.ITree#getRootNode()
     */
    public ITreeNode getRootNode()
    {
        return _rootNode;
    }

    /**
     * Set the value of attributeSet field.
     * @param attributeSet The attributeSet to set
     */
    public final void setAttributeSet(IAttributeSet attributeSet)
    {
        _attributeSet = attributeSet;
        _info.setAttributeSetID(((AttributeSet) _attributeSet).getInfo().getId());
    }

    public void setName(String name)
    {
        _info.setName(name);
    }

    /**
     * Set the value of rootNode field.
     * @param rootNode The rootNode to set
     */
    public final void setRootNode(ITreeNode rootNode)
    {
        _rootNode = rootNode;
        TreeNodeInfo rootNodeInfo = ((TreeNode) _rootNode).getInfo();
        // setting parent id for the root node
        rootNodeInfo.setParentNodeID(0);
        _info.setRootNodeID(rootNodeInfo.getId());

        // adding the node to the tree
        _info.addNode(rootNodeInfo);
    }

    public void addNode(ITreeNode childNode)
    {
        _info.addNode(((TreeNode) childNode).getInfo());
    }
}
