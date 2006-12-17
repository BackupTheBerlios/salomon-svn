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

import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.plugin.IResult;

public final class TreeVisResult extends SimpleStruct implements IResult
{
    private String _treeName;

    public String getTreeName()
    {
        return _treeName;
    }

    public void init(IObject object)
    {
        SimpleStruct struct = (SimpleStruct) object;

        // setting struct fields
        SimpleString treeName = (SimpleString) struct.getField(TREE_NAME);
        setField(TREE_NAME, treeName);
        _treeName = treeName.getValue();
    }

    public boolean isSuccessful()
    {
        return true;
    }

    public void setTreeName(String treeName)
    {
        _treeName = treeName;
        setField(TREE_NAME, _treeName);
    }

    private static final String TREE_NAME = "tree_name";
}
