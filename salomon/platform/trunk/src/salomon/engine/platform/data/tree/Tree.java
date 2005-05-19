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

import java.util.Date;

import salomon.platform.data.tree.IDataSource;
import salomon.platform.data.tree.INode;
import salomon.platform.data.tree.ITree;

/**
 * 
 */
public class Tree implements ITree
{

    public Date getCreateDate() {
        throw new UnsupportedOperationException("Method getCreateDate() not implemented yet!");
    }

    public IDataSource getDataSource() {
        throw new UnsupportedOperationException("Method getDataSource() not implemented yet!");
    }

    public int getId() {
        throw new UnsupportedOperationException("Method getId() not implemented yet!");
    }

    public String getInfo() {
        throw new UnsupportedOperationException("Method getInfo() not implemented yet!");
    }

    public String getName() {
        throw new UnsupportedOperationException("Method getName() not implemented yet!");
    }

    public INode getRoot() {
        throw new UnsupportedOperationException("Method getRoot() not implemented yet!");
    }

}
