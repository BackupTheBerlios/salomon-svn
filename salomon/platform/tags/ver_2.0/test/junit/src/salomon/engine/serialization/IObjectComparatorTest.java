/*
 *
 * Author: Jakub Pawlowski
 * Created on:2004-12-19 12:50:16
 */

package salomon.engine.serialization;

import junit.framework.TestCase;
import salomon.util.serialization.SimpleInteger;
import salomon.util.serialization.SimpleString;
import salomon.util.serialization.SimpleStruct;

/**
 * 
 * testst equals() method in IObjects
 * 
 * 
 * 
 */
public class IObjectComparatorTest extends TestCase
{

    /**
     * compares SimpleInteger
     * 
     */
    public void testCompareInt()
    {
        SimpleInteger int1 = new SimpleInteger();
        int1.setValue(2301);
        SimpleInteger int2 = new SimpleInteger();
        int2.setValue(2301);
        assertTrue(int1.equals(int2));

        SimpleInteger int3 = new SimpleInteger();
        int2.setValue(666);
        assertFalse(int1.equals(int3));
    }

    /**
     * compares SimpleString
     * 
     */
    public void testCompareString()
    {
        SimpleString string1 = new SimpleString();
        string1.setValue("test");
        SimpleString string2 = new SimpleString();
        string2.setValue("test");
        assertTrue(string1.equals(string2));

        SimpleString string3 = new SimpleString();
        string3.setValue("test2");
        assertFalse(string1.equals(string3));

    }

    /**
     * compares SimpleStruct
     * 
     */

    public void testCompareStruct()
    {
        SimpleInteger int1 = new SimpleInteger();
        int1.setValue(2301);
        SimpleInteger int2 = new SimpleInteger();
        int2.setValue(2301);

        SimpleString string1 = new SimpleString();
        string1.setValue("test");
        SimpleString string2 = new SimpleString();
        string2.setValue("test");

        SimpleStruct struct1 = new SimpleStruct();
        struct1.setField("int", int1);
        struct1.setField("string", string1);

        SimpleStruct struct2 = new SimpleStruct();
        struct2.setField("int", int2);
        struct2.setField("string", string2);

        assertTrue(struct1.equals(struct2));

        SimpleStruct struct3 = new SimpleStruct();
        assertFalse(struct1.equals(struct3));

    }
}
