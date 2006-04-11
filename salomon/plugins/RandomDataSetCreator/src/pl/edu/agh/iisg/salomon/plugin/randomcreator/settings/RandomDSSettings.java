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

package pl.edu.agh.iisg.salomon.plugin.randomcreator.settings;

import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * @author nico
 */
public final class RandomDSSettings extends SimpleStruct implements ISettings
{
    private static final String DATA_SET_NAME = "dataSetName";

    private static final String DEFINITIONS = "definitions";

    private static final String ROW_COUNT = "rowCount";

    private static final String TABLE_NAME = "tableName";

    private String _dataSetName;

    private TableDescription[] _tableDesctiptions;

    public RandomDSSettings(String dataSetName)
    {
        setDataSetName(dataSetName);
        setTableDesctiptions(new TableDescription[0]);
    }
    
    /**
     * Returns the dataSetName.
     * @return The dataSetName
     */
    public String getDataSetName()
    {
        return _dataSetName;
    }

    /**
     * Returns the tableDesctiptions.
     * @return The tableDesctiptions
     */
    public TableDescription[] getTableDesctiptions()
    {
        return _tableDesctiptions;
    }

    public void init(IObject o)
    {
        SimpleStruct struct = (SimpleStruct) o;

        // setting struct fields
        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET_NAME);
        SimpleArray descArray = (SimpleArray) struct.getField(DEFINITIONS);
        setField(DATA_SET_NAME, dataSetName);
        setField(DEFINITIONS, descArray);

        _dataSetName = dataSetName.getValue();
        IObject[] objects = descArray.getValue();
        _tableDesctiptions = new TableDescription[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            SimpleStruct tableDef = ((SimpleStruct) objects[i]);
            SimpleString tableName = (SimpleString) tableDef.getField(TABLE_NAME);
            SimpleInteger rowCount = (SimpleInteger) tableDef.getField(ROW_COUNT);
            _tableDesctiptions[i] = new TableDescription(
                    tableName.getValue(), rowCount.getValue());
        }
    }

    /**
     * Set the value of dataSetName field.
     * @param dataSetName The dataSetName to set
     */
    public void setDataSetName(String dataSetName)
    {
        _dataSetName = dataSetName;
        setField(DATA_SET_NAME, new SimpleString(dataSetName));
    }

    /**
     * Set the value of tableDesctiptions field.
     * @param tableDesctiptions The tableDesctiptions to set
     */
    public void setTableDesctiptions(TableDescription[] tableDesctiptions)
    {
        _tableDesctiptions = tableDesctiptions;

        SimpleStruct[] tabDefs = new SimpleStruct[tableDesctiptions.length];
        for (int i = 0; i < tableDesctiptions.length; ++i) {
            SimpleStruct def = new SimpleStruct();
            def.setField(TABLE_NAME, new SimpleString(
                    tableDesctiptions[i].getTableName()));
            def.setField(ROW_COUNT, new SimpleInteger(
                    tableDesctiptions[i].getRowCount()));
            tabDefs[i] = def;
        }

        SimpleArray defArray = new SimpleArray(tabDefs);
        setField(DEFINITIONS, defArray);
    }

}
