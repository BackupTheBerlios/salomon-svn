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
import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.tree.ITree;
import salomon.platform.data.tree.ITreeNode;

/**
 *
 */
public final class Tree implements ITree
{
    private TreeInfo _info;

    private ITreeNode _rootNode;

    private IAttributeSet _attributeSet;

    /**
     * @param rootNode
     */
    public Tree(ITreeNode rootNode, DBManager dbManager)
    {
        _rootNode = rootNode;
        _info = new TreeInfo(dbManager);
    }

    public ITreeNode evaluate(IAttribute[] attribute)
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
    }
}
