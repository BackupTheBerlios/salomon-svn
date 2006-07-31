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
import salomon.platform.data.IMetaData;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.exception.PlatformException;

public class AttributeManagerTest extends TestCase
{
    private AttributeManager _attributeManager;

    private IDataEngine _dataEngine;

    public AttributeManagerTest() throws PlatformException
    {
        ISolution solution = TestObjectFactory.getSolution("Persons");
        _dataEngine = solution.getDataEngine();
        _attributeManager = (AttributeManager) _dataEngine.getAttributeManager();
        LOGGER.info("Connected");
    }

    /*
     * Test method for 'salomon.engine.platform.data.attribute.AttributeManager.add(IAttributeSet)'
     */
    public void testAdd() throws PlatformException
    {
        LOGGER.info("AttributeManagerTest.testAdd()");
        IAttributeSet attributeSet = createTestAttributeSet();
        LOGGER.info("adding: " + attributeSet.getName());
        _attributeManager.add(attributeSet);
    }
    
    private IAttributeSet createTestAttributeSet()
    {
        IMetaData metaData = _dataEngine.getMetaData();

        AttributeDescription name = (AttributeDescription) _attributeManager.createIntegerAttributeDescription(
                "attr_name", metaData.getTable("persons").getColumn("first_name"));
        AttributeDescription surname = (AttributeDescription) _attributeManager.createStringAttributeDescription(
                "attr_surname", metaData.getTable("persons").getColumn(
                        "last_name"));

        AttributeDescription[] descriptions = new AttributeDescription[]{name,
                surname};

        IAttributeSet attributeSet = _attributeManager.createAttributeSet(descriptions);
        attributeSet.setName("test" + System.currentTimeMillis());
        return attributeSet;
    }

 

    /*
     * Test method for 'salomon.engine.platform.data.attribute.AttributeManager.getAll()'
     */
    public void testGetAll()
    {

    }

    /*
     * Test method for 'salomon.engine.platform.data.attribute.AttributeManager.getAttributeSet(int)'
     */
    public void testGetAttributeSet()
    {

    }

    /*
     * Test method for 'salomon.engine.platform.data.attribute.AttributeManager.remove(IAttributeSet)'
     */
    public void testRemove() throws PlatformException
    {
        LOGGER.info("AttributeManagerTest.testRemove()");
        IAttributeSet attributeSet = createTestAttributeSet();
        _attributeManager.add(attributeSet);
        LOGGER.info("added...");
        LOGGER.info("removing: " + attributeSet.getName());
        _attributeManager.remove(attributeSet);        
    }

    private static final Logger LOGGER = Logger.getLogger(AttributeManagerTest.class);
}
