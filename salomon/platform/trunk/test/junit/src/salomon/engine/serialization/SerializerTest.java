/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-17 20:37:13
 */

package salomon.engine.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import salomon.engine.platform.serialization.XMLSerializer;
import salomon.platform.serialization.IObject;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

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

	private static final String INPUT_FILE_NAME = "test/junit/res/struct.xml";

	private static final String OUTPUT_FILE_NAME = "test/junit/res/struct_out.xml";

	private static final String INPUT_ARRAY_FILE_NAME = "test/junit/res/array.xml";

	private static final String OUTPUT_ARRAY_FILE_NAME = "test/junit/res/array_out.xml";
	
	
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
		IObject[] objArray = new IObject[] {one, five, seven};
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
	 */
	public void testSerializeArray()
	{
		SimpleStruct struct = new SimpleStruct();
		SimpleString dataSetName = new SimpleString("dataset");
		
		SimpleStruct condStruct = new SimpleStruct();		
		SimpleString elem1 = new SimpleString("cond1");
		SimpleString elem2 = new SimpleString("cond2");
		SimpleString elem3 = new SimpleString("cond3");
		SimpleString elem4 = new SimpleString("cond4");
		SimpleString elem5 = new SimpleString("cond5");
		condStruct.setField("elem1", elem1);
		condStruct.setField("elem2", elem2);
		condStruct.setField("elem3", elem3);
		condStruct.setField("elem4", elem4);
		condStruct.setField("elem5", elem5);
		
		struct.setField("dataSetName", dataSetName);
		struct.setField("conditions", condStruct);

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(OUTPUT_ARRAY_FILE_NAME);
			XMLSerializer.serialize(struct, os);
		} catch (FileNotFoundException e) {
			LOGGER.fatal("", e);
		}
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
		SimpleStruct condStruct = (SimpleStruct) struct.getField("conditions");
		
		SimpleString elem1 = (SimpleString) condStruct.getField("elem1");
		LOGGER.debug("elem1: " + elem1);
		SimpleString elem2 = (SimpleString) condStruct.getField("elem2");
		LOGGER.debug("elem2: " + elem2);
		SimpleString elem3 = (SimpleString) condStruct.getField("elem3");
		LOGGER.debug("elem3: " + elem3);
		SimpleString elem4 = (SimpleString) condStruct.getField("elem4");
		LOGGER.debug("elem4: " + elem4);
		SimpleString elem5 = (SimpleString) condStruct.getField("elem5");
		LOGGER.debug("elem5: " + elem5);
		
	}	
	
	/**
	 * test serialization/deserializtion
	 */
	public void testSymetric()
	{
		// TODO: add test which checks serialization/deserialization
	}
	
	private static final Logger LOGGER = Logger.getLogger(SerializerTest.class);
}
