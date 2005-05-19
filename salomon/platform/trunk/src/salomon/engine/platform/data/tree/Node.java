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

import salomon.platform.data.tree.INode;

public class Node implements INode {

    public Type getType() {
        throw new UnsupportedOperationException(
                "Method getType() not implemented yet!");
    }

    public String getValue() {
        throw new UnsupportedOperationException(
                "Method getValue() not implemented yet!");
    }

    public String getParentEdge() {
        throw new UnsupportedOperationException(
                "Method getParentEdge() not implemented yet!");
    }

    public boolean isRoot() {
        throw new UnsupportedOperationException(
                "Method isRoot() not implemented yet!");
    }

    public boolean isLeaf() {
        throw new UnsupportedOperationException(
                "Method isLeaf() not implemented yet!");
    }

    public INode getParent() {
        throw new UnsupportedOperationException(
                "Method getParent() not implemented yet!");
    }

    public INode getRoot() {
        throw new UnsupportedOperationException(
                "Method getRoot() not implemented yet!");
    }

    public INode[] getChilds() {
        throw new UnsupportedOperationException(
                "Method getChilds() not implemented yet!");
    }

    public INode[] getLeafs() {
        throw new UnsupportedOperationException(
                "Method getLeafs() not implemented yet!");
    }

}
