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

package salomon.engine.platform.data.attribute;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.solution.ISolution;
import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.attribute.IAttribute;
import salomon.platform.data.attribute.IAttributeData;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;

public class AttributeSetTest extends TestCase
{
    private AttributeManager _attributeManager;

    private IDataEngine _dataEngine;
    
    public AttributeSetTest() throws PlatformException
    {
        ISolution solution = TestObjectFactory.getSolution("Persons");
        _dataEngine = solution.getDataEngine();
        _attributeManager = (AttributeManager) _dataEngine.getAttributeManager();
        LOGGER.info("Connected");
    }
    
    public void testSelectAttributeData() throws PlatformException
    {
        LOGGER.info("AttributeSetTest.testSelectAttributeData()");
        // creating test dataset
        IDataSetManager dataSetManager = _dataEngine.getDataSetManager(); 
        IDataSet mainDataSet = dataSetManager.getMainDataSet();
        
        IColumn id = _dataEngine.getMetaData().getTable("persons").getColumn("id");
        IColumn id2 = _dataEngine.getMetaData().getTable("persons").getColumn("id");
        
        ICondition cond1 = dataSetManager.createLowerCondition(id, 4);
        ICondition cond2 = dataSetManager.createGreaterCondition(id2, 7);
        
        IDataSet dataSet = mainDataSet.createSubset(new ICondition[]{cond1, cond2});
        
        IData data = dataSet.selectData(null, null);

        LOGGER.debug("DATA SET:");
        while (data.next()) {
            Object[] dataArray = data.getData();
            StringBuilder dataString = new StringBuilder();
            for (Object d : dataArray) {
                dataString.append("|");
                dataString.append(d);
            }
            LOGGER.debug(dataString);
        }        
        data.close();
        
        // adding new dataSet to the storage
        dataSet.setName("test" + System.currentTimeMillis());
        dataSetManager.add(dataSet);
        
        // creating attributeSet
        IAttributeSet attributeSet = createTestAttributeSet();
        IAttributeData attrData = attributeSet.selectAttributeData(dataSet);
        
        LOGGER.debug("ATTRIBUTE SET DATA:");
        while (attrData.next()) {
            IAttribute[] attributes = attrData.getAttributes();
            StringBuilder dataString = new StringBuilder();
            for (IAttribute a : attributes) {
                dataString.append("|");
                dataString.append(a);
            }
            LOGGER.debug(dataString);
        }        
        attrData.close();
        
    }
    
    private IAttributeSet createTestAttributeSet()
    {
        IMetaData metaData = _dataEngine.getMetaData();

        AttributeDescription name = (AttributeDescription) _attributeManager.createIntegerAttributeDescription(
                "attr_name", metaData.getTable("persons").getColumn(
                        "first_name"));
        AttributeDescription surname = (AttributeDescription) _attributeManager.createStringAttributeDescription(
                "attr_surname", metaData.getTable("persons").getColumn(
                        "last_name"));

        AttributeDescription[] descriptions = new AttributeDescription[]{name,
                surname};

        IAttributeSet attributeSet = _attributeManager.createAttributeSet(descriptions);
        attributeSet.setName("test" + System.currentTimeMillis());
        return attributeSet;
    }

    private static final Logger LOGGER = Logger.getLogger(AttributeSetTest.class);
}
