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

package salomon.engine.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.serialization.IObject;

import salomon.engine.platform.serialization.XMLSerializer;

/**
 * 
 * TODO: add comment.
 * 
 * @author kuba
 * 
 */
public class SerializerTest extends TestCase
{

	/**
	 * Creates new <code>SerializerTest</code> object
	 * 
	 */
	public SerializerTest()
	{
		PropertyConfigurator.configure("log.conf");
	}

	/**
	 * tests deserialization of xml
	 * 
	 */
	public void testDeserialize()
	{
		SimpleStruct struct = null;
		try {
			FileInputStream is = new FileInputStream(INPUT_FILE_NAME);
			struct = XMLSerializer.deserialize(is);
		} catch (FileNotFoundException e) {
			assertTrue("File not found", false);
		}
		SimpleString string = (SimpleString) struct.getField("string1");
		assertEquals(string.getValue(), "test_val_1");
		SimpleInteger integer = (SimpleInteger) struct.getField("int1");
		assertEquals(integer.getValue(), 2301);
		SimpleStruct innerStruct = (SimpleStruct) struct.getField("str1_2");
		SimpleString innerString = (SimpleString) innerStruct.getField("string2");
		assertEquals(innerString.getValue(), "val2");
		SimpleInteger innerInteger = (SimpleInteger) innerStruct.getField("int2");
		assertEquals(innerInteger.getValue(), 666);
	}

	public void testDeserializeArray()
	{
		SimpleStruct struct = null;
		try {
			FileInputStream is = new FileInputStream(INPUT_ARRAY_FILE_NAME);
			struct = XMLSerializer.deserialize(is);
		} catch (FileNotFoundException e) {
			assertTrue("File not found", false);
		}
		SimpleString dataSetName = (SimpleString) struct.getField("dataSetName");
		LOGGER.debug("dataSetaName: " + dataSetName);
		SimpleArray simpleArray = (SimpleArray) struct.getField("array");

		IObject[] items = simpleArray.getValue();
		assertEquals(4, items.length);
		for (int i = 0; i < 4; ++i) {
			assertEquals(items[i].getClass().getName(),
					SimpleString.class.getName());
			assertEquals("cond" + (i + 1), ((SimpleString) items[i]).getValue());
		}
	}

	/**
	 * tests if the element is serialized properly
	 */
	public void testSerialize()
	{
		SimpleStruct struct = new SimpleStruct();
		SimpleString name = new SimpleString("nico");
		SimpleInteger age = new SimpleInteger(22);
		struct.setField("name", name);
		struct.setField("age", age);

		SimpleInteger one = new SimpleInteger(1);
		SimpleInteger five = new SimpleInteger(5);
		SimpleInteger seven = new SimpleInteger(7);
		IObject[] objArray = new IObject[]{one, five, seven};
		SimpleArray array = new SimpleArray(objArray);
		struct.setField("intArray", array);

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(OUTPUT_FILE_NAME);
			XMLSerializer.serialize(struct, os);
		} catch (FileNotFoundException e) {
			LOGGER.fatal("", e);
		}
	}

	/**
	 * tests if the element is serialized properly
	 * @throws Exception 
	 */
	public void testSerializeArray() throws Exception
	{
		SimpleStruct struct = new SimpleStruct();
		SimpleString dataSetName = new SimpleString("dataset");

		SimpleString elem1 = new SimpleString("cond1");
		SimpleString elem2 = new SimpleString("cond2");
		SimpleString elem3 = new SimpleString("cond3");
		SimpleString elem4 = new SimpleString("cond4");
		SimpleString elem5 = new SimpleString("cond5");
		IObject[] items = new IObject[]{elem1, elem2, elem3, elem4, elem5};
		SimpleArray simpleArray = new SimpleArray(items);

		struct.setField("dataSetName", dataSetName);
		struct.setField("conditions", simpleArray);

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(OUTPUT_ARRAY_FILE_NAME);
			XMLSerializer.serialize(struct, os);
		} finally {
			if (os != null) {
				os.close();
			}
		}

		FileInputStream is = null;
		SimpleStruct outStruct = null;
		try {
			is = new FileInputStream(OUTPUT_ARRAY_FILE_NAME);
			outStruct = XMLSerializer.deserialize(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		assertNotNull(outStruct);
		assertTrue(struct.equals(outStruct));
	}

	/**
	 * test serialization/deserializtion
	 */
	public void testSymetric()
	{
		// TODO: add test which checks serialization/deserialization
	}

	private static final String INPUT_ARRAY_FILE_NAME = "test/junit/res/array.xml";

	private static final String INPUT_FILE_NAME = "test/junit/res/struct.xml";

	private static final Logger LOGGER = Logger.getLogger(SerializerTest.class);

	private static final String OUTPUT_ARRAY_FILE_NAME = "test/junit/res/array_out.xml";

	private static final String OUTPUT_FILE_NAME = "test/junit/res/struct_out.xml";
}
