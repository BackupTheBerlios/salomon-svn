/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-17 20:37:13
 */

package salomon.engine.serialization;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import junit.framework.TestCase;

import org.apache.log4j.PropertyConfigurator;

import salomon.engine.platform.serialization.XMLSerializer;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * 
 * TODO: add comment.
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
    
private static final String TEST_FILE_NAME = "test/junit/res/struct.xml";
    
	/**
	 * tests deserialization of xml
	 * 
	 */
	public void testDeserialize()
    {
        SimpleStruct struct = null;
        try {
			FileInputStream is = new FileInputStream(TEST_FILE_NAME);
             struct= XMLSerializer.deserialize(is);
		} catch (FileNotFoundException e) {
            assertTrue("File not found", false);
		}
            SimpleString string =(SimpleString) struct.getField("string1"); 
            assertEquals(string.getValue(), "test_val_1");
            SimpleInteger integer = (SimpleInteger)struct.getField("int1");
            assertEquals(integer.getValue(), 2301);
            SimpleStruct innerStruct = (SimpleStruct)struct.getField("str1_2");
            SimpleString innerString =(SimpleString) innerStruct.getField("string2"); 
            assertEquals(innerString.getValue(), "val2");
            SimpleInteger innerInteger = (SimpleInteger)innerStruct.getField("int2");
            assertEquals(innerInteger.getValue(), 666);
            
    }

    /**
     * tests if the element is serialized properly
     */
    public void testSerialize() {
    	SimpleStruct object = null;
        try {
            FileInputStream is = new FileInputStream(TEST_FILE_NAME);
            object= XMLSerializer.deserialize(is);
        } catch (FileNotFoundException e) {
            assertTrue("File not found", false);
        }
        assertNotNull(object);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        XMLSerializer.serialize(object, os);
        assertNotNull(os.toByteArray());
        assertTrue(os.toByteArray().length > 0);
        //TODO: add more exact test
    }

    /**
     * test serialization/deserializtion
     */
    public void testSymetric() {
    	//TODO: add test which checks serialization/deserialization
    }
    
    
    
}
