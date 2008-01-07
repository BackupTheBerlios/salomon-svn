/*
 * Copyright (C) 2006 TreeVis Team
 *
 * This file is part of TreeVis.
 *
 * TreeVis is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeVis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeVis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treevis.result;

import salomon.platform.data.tree.ITreeNode;

import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;

public class TreeNodeVertex  extends DirectedSparseVertex
{
    private ITreeNode _node;

    /**
     */
    public TreeNodeVertex(ITreeNode node)
    {
        _node = node;
    }

    public ITreeNode getNode()
    {
        return _node;
    }
    
    @Override
    public String toString()
    {
        if (_node.isLeaf()) {
            return _node.getAttributeDescription().getName() + "=" + _node.getLeafValue();
        } else {
            return _node.getAttributeDescription().getName();
        }
    }

}
