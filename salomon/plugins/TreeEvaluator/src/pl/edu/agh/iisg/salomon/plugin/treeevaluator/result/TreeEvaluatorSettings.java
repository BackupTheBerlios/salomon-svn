/*
 * Copyright (C) 2006 TreeEvaluator Team
 *
 * This file is part of TreeEvaluator.
 *
 * TreeEvaluator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeEvaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeEvaluator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treeevaluator.result;

import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.plugin.ISettings;

public final class TreeEvaluatorSettings extends SimpleStruct
        implements ISettings
{
    private String _dataSetName;

    private String _treeName;

    public String getDataSetName()
    {
        return _dataSetName;
    }

    public String getTreeName()
    {
        return _treeName;
    }

    public void init(IObject o)
    {
        SimpleStruct struct = (SimpleStruct) o;

        // setting struct fields
        SimpleString treeName = (SimpleString) struct.getField(TREE_NAME);
        setField(TREE_NAME, treeName);
        _treeName = treeName.getValue();

        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET_NAME);
        setField(DATA_SET_NAME, dataSetName);
        _dataSetName = dataSetName.getValue();
    }

    public void setDataSetName(String dataSetName)
    {
        _dataSetName = dataSetName;
    }

    public void setTreeName(String treeName)
    {
        _treeName = treeName;
    }

    private static final String DATA_SET_NAME = "data_set_name";

    private static final String TREE_NAME = "tree_name";

}
