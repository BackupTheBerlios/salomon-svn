/*
 * Copyright (C) 2005 Salomon Team
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

import java.util.LinkedList;
import java.util.List;

import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;

/**
 *
 */
public final class TreeNode implements ITreeNode
{
    private IAttributeDescription _attributeDescription;

    private List<ITreeEdge> _childEdges;

    private TreeNodeInfo _info;

    private ITreeEdge _parentEdge;

    private String _value;

    /**
     * @param attributeDescription
     * @param nodeID
     */
    public TreeNode(IAttributeDescription attributeDescription,
            TreeNodeInfo info)
    {
        _attributeDescription = attributeDescription;
        _info = info;
        _childEdges = new LinkedList<ITreeEdge>();
    }

    public void addChildEdge(ITreeEdge edge)
    {
        _childEdges.add(edge);
    }

    /**
     * Returns the attributeDescription.
     * @return The attributeDescription
     */
    public final IAttributeDescription getAttributeDescription()
    {
        return _attributeDescription;
    }

    public ITreeEdge[] getChildrenEdges()
    {
        return _childEdges.toArray(new ITreeEdge[_childEdges.size()]);
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final TreeNodeInfo getInfo()
    {
        return _info;
    }

    public String getLeafValue()
    {
        return _value;
    }

    /**
     * Returns the parentEdge.
     * @return The parentEdge
     */
    public final ITreeEdge getParentEdge()
    {
        return _parentEdge;
    }

    public boolean isLeaf()
    {
        return (_childEdges.size() == 0);
    }

    /**
     * Set the value of attributeDescription field.
     * @param attributeDescription The attributeDescription to set
     */
    public final void setAttributeDescription(
            IAttributeDescription attributeDescription)
    {
        _attributeDescription = attributeDescription;
    }

    /**
     * Set the value of parentEdge field.
     * @param parentEdge The parentEdge to set
     */
    public final void setParentEdge(ITreeEdge parentEdge)
    {
        _parentEdge = parentEdge;
    }

    @Override
    public String toString()
    {
        return "" + _info.getId();
    }

}
