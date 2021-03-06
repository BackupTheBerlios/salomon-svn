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

import salomon.platform.data.tree.ITreeEdge;
import salomon.platform.data.tree.ITreeNode;

/**
 * 
 */
final class TreeEdge implements ITreeEdge
{
    private ITreeNode _childNode;

    private ITreeNode _parentNode;

    private String _value;

    /**
     *      
     * @param parentNode
     * @param childNode
     */
    public TreeEdge(ITreeNode parentNode, ITreeNode childNode)
    {
        _parentNode = parentNode;
        _childNode = childNode;
    }

    /**
     * Returns the childNode.
     * @return The childNode
     */
    public final ITreeNode getChildNode()
    {
        return _childNode;
    }

    /**
     * Returns the parentNode.
     * @return The parentNode
     */
    public final ITreeNode getParentNode()
    {
        return _parentNode;
    }

    /**
     * Returns the value.
     * @return The value
     */
    public final String getValue()
    {
        return _value;
    }

    /**
     * Set the value of value field.
     * @param value The value to set
     */
    public final void setValue(String value)
    {
        _value = value;
    }

    @Override
    public String toString()
    {
        return "" + _value;
    }

}
