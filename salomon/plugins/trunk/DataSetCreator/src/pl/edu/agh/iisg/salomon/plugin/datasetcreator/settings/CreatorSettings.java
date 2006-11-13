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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator.settings;

import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * @author nico
 */
public final class CreatorSettings extends SimpleStruct implements ISettings
{

    private static final String DATA_SET_NAME = "dataSetName";

    private static final String CONDITIONS = "conditions";

    private String _dataSetName;

    private String[] _conditions;

    public CreatorSettings(String dataSetName)
    {
        setDataSetName(dataSetName);
        setConditions(new String[0]);
    }

    public String[] getConditions()
    {
        return _conditions;
    }

    public String getDataSetName()
    {
        return _dataSetName;
    }

    public void setConditions(String[] conditions)
    {
        _conditions = conditions;
        SimpleArray array = SimpleArray.createArray(conditions);
        setField(CONDITIONS, array);
    }

    public void setDataSetName(String dataSetName)
    {
        _dataSetName = dataSetName;
        setField(DATA_SET_NAME, new SimpleString(dataSetName));
    }

    public void init(IObject o)
    {
        SimpleStruct struct = (SimpleStruct) o;

        // setting struct fields
        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET_NAME);
        SimpleArray condArray = (SimpleArray) struct.getField(CONDITIONS);
        setField(DATA_SET_NAME, dataSetName);
        setField(CONDITIONS, condArray);

        _dataSetName = dataSetName.getValue();
        IObject[] objects = condArray.getValue();
        _conditions = new String[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            _conditions[i] = ((SimpleString) objects[i]).getValue();
        }
    }
}
