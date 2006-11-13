/*
 * Copyright (C) 2006 WekaTreeGenerator Team
 *
 * This file is part of WekaTreeGenerator.
 *
 * WekaTreeGenerator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * WekaTreeGenerator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WekaTreeGenerator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.settings;

import salomon.platform.serialization.IObject;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * 
 */
public final class WekaTreeGeneratorSettings extends SimpleStruct
        implements ISettings
{
    private String _algorithmName;

    private String _attributeSetName;

    private String _dataSetName;

    private String[] _options;

    private String _treeName;

    /**
     * Returns the algorithmName.
     *
     * @return The algorithmName
     */
    public final String getAlgorithmName()
    {
        return _algorithmName;
    }

    /**
     * Returns the attributeSetName.
     *
     * @return The attributeSetName
     */
    public final String getAttributeSetName()
    {
        return _attributeSetName;
    }

    /**
     * Returns the dataSetName.
     *
     * @return The dataSetName
     */
    public final String getDataSetName()
    {
        return _dataSetName;
    }

    /**
     * Returns the treeName.
     *
     * @return The treeName
     */
    public final String getTreeName()
    {
        return _treeName;
    }

    public void init(IObject o)
    {
        SimpleStruct struct = (SimpleStruct) o;

        // setting struct fields
        SimpleString attributeSetName = (SimpleString) struct.getField(ATTRIBUTE_SET);
        setField(ATTRIBUTE_SET, attributeSetName);
        _attributeSetName = attributeSetName.getValue();

        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET);
        setField(DATA_SET, dataSetName);
        _dataSetName = dataSetName.getValue();

        //     private static final String ALGORITHM = "algorithm";
        SimpleString algorithmName = (SimpleString) struct.getField(ALGORITHM);
        setField(ALGORITHM, algorithmName);
        _algorithmName = algorithmName.getValue();

        //        private static final String OPTIONS = "options";
        //        SimpleString dataSetName = (SimpleString) struct.getField(DATA_SET);
        //        setField(DATA_SET, attributeSetName);
        //        _dataSetName = dataSetName.getValue();
        SimpleString treeName = (SimpleString) struct.getField(TREE);
        setField(TREE, treeName);
        _treeName = treeName.getValue();
    }

    /**
     * Set the value of algorithmName field.
     *
     * @param algorithmName The algorithmName to set
     */
    public final void setAlgorithmName(String algorithmName)
    {
        _algorithmName = algorithmName;
        setField(ALGORITHM, new SimpleString(algorithmName));
    }

    /**
     * Set the value of attributeSetName field.
     *
     * @param attributeSetName The attributeSetName to set
     */
    public final void setAttributeSetName(String attributeSetName)
    {
        _attributeSetName = attributeSetName;
        setField(ATTRIBUTE_SET, new SimpleString(attributeSetName));

    }

    /**
     * Set the value of dataSetName field.
     *
     * @param dataSetName The dataSetName to set
     */
    public final void setDataSetName(String dataSetName)
    {
        _dataSetName = dataSetName;
        setField(DATA_SET, new SimpleString(dataSetName));
    }

    /**
     * Set the value of treeName field.
     *
     * @param treeName The treeName to set
     */
    public final void setTreeName(String treeName)
    {
        _treeName = treeName;
        setField(TREE, new SimpleString(treeName));

    }

    private static final String ALGORITHM = "algorithm";

    private static final String ATTRIBUTE_SET = "attribute_set";

    private static final String DATA_SET = "data_set";

    private static final String OPTIONS = "options";

    private static final String TREE = "tree";

}
