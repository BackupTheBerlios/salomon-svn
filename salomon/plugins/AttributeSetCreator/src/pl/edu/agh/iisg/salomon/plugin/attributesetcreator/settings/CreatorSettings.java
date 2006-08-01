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

package pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings;

import salomon.platform.serialization.IObject;
import salomon.platform.serialization.IStruct;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * @author nico
 */
public final class CreatorSettings extends SimpleStruct implements ISettings
{

    private static final String ATTRIBUTE_SET_NAME = "attributeSetName";

    private static final String DESCRIPTIONS = "descriptions";

    private String _attributeSetName;

    private Description[] _descriptions;

    public CreatorSettings(String dataSetName)
    {
        setAttributeSetName(dataSetName);
        setDescriptions(new Description[0]);
    }

    public String getAttributeSetName()
    {
        return _attributeSetName;
    }

    public Description[] getDescriptions()
    {
        return _descriptions;
    }

    public void init(IObject o)
    {
        SimpleStruct struct = (SimpleStruct) o;

        // setting struct fields
        SimpleString attributeSetName = (SimpleString) struct.getField(ATTRIBUTE_SET_NAME);
        SimpleArray descArray = (SimpleArray) struct.getField(DESCRIPTIONS);
        setField(ATTRIBUTE_SET_NAME, attributeSetName);
        setField(DESCRIPTIONS, descArray);

        _attributeSetName = attributeSetName.getValue();
        IObject[] objects = descArray.getValue();
        _descriptions = new Description[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            _descriptions[i] = new Description();
            _descriptions[i].setAttributeName(((SimpleString) ((IStruct) objects[i]).getField(Description.ATTR_ATTRIBUTE_NAME)).getValue());
            _descriptions[i].setTableName(((SimpleString) ((IStruct) objects[i]).getField(Description.ATTR_TABLE_NAME)).getValue());
            _descriptions[i].setColumnName(((SimpleString) ((IStruct) objects[i]).getField(Description.ATTR_COLUMN_NAME)).getValue());
            _descriptions[i].setType(((SimpleString) ((IStruct) objects[i]).getField(Description.ATTR_TYPE)).getValue());
        }
    }

    public void setAttributeSetName(String dataSetName)
    {
        _attributeSetName = dataSetName;
        setField(ATTRIBUTE_SET_NAME, new SimpleString(dataSetName));
    }

    public void setDescriptions(Description[] descriptions)
    {
        _descriptions = descriptions;
        SimpleStruct[] structArray = new SimpleStruct[_descriptions.length];
        for (int i = 0; i < descriptions.length; ++i) {
            structArray[i] = new SimpleStruct();
            structArray[i].setField(Description.ATTR_ATTRIBUTE_NAME,
                    new SimpleString(descriptions[i].getAttributeName()));
            structArray[i].setField(Description.ATTR_TABLE_NAME,
                    new SimpleString(descriptions[i].getTableName()));
            structArray[i].setField(Description.ATTR_COLUMN_NAME,
                    new SimpleString(descriptions[i].getColumnName()));
            structArray[i].setField(Description.ATTR_TYPE, new SimpleString(
                    descriptions[i].getType()));

        }
        SimpleArray array = new SimpleArray(structArray);
        setField(DESCRIPTIONS, array);
    }

    public class Description
    {
        public static final String ATTR_ATTRIBUTE_NAME = "attributeName";

        public static final String ATTR_COLUMN_NAME = "columnName";

        public static final String ATTR_TABLE_NAME = "tableName";

        public static final String ATTR_TYPE = "type";

        private String _attributeName;

        private String _columnName;

        private String _tableName;

        private String _type;

        /**
         * Returns the attributeName.
         * @return The attributeName
         */
        public final String getAttributeName()
        {
            return _attributeName;
        }

        /**
         * Returns the columnName.
         * @return The columnName
         */
        public final String getColumnName()
        {
            return _columnName;
        }

        /**
         * Returns the tableName.
         * @return The tableName
         */
        public final String getTableName()
        {
            return _tableName;
        }

        /**
         * Returns the type.
         * @return The type
         */
        public final String getType()
        {
            return _type;
        }

        /**
         * Set the value of attributeName field.
         * @param attributeName The attributeName to set
         */
        public final void setAttributeName(String attributeName)
        {
            _attributeName = attributeName;
        }

        /**
         * Set the value of columnName field.
         * @param columnName The columnName to set
         */
        public final void setColumnName(String columnName)
        {
            _columnName = columnName;
        }

        /**
         * Set the value of tableName field.
         * @param tableName The tableName to set
         */
        public final void setTableName(String tableName)
        {
            _tableName = tableName;
        }

        /**
         * Set the value of type field.
         * @param type The type to set
         */
        public final void setType(String type)
        {
            _type = type;
        }
    }
}
