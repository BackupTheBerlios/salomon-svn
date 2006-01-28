/*
 * Copyright (C) 2005 Salomon Team
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

package salomon.engine.platform.data.dataset.condition;

import junit.framework.TestCase;
import salomon.TestObjectFactory;

import salomon.platform.data.dataset.ICondition;
import salomon.platform.exception.PlatformException;

import salomon.engine.platform.data.DBMetaData;

public class ConditionParserTest extends TestCase
{
	private DBMetaData _metaData;

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse1() throws PlatformException
	{
		String sqlInput = "persons.id = 10";
		ICondition condition = ConditionParser.parse(_metaData, sqlInput);
		assertNotNull(condition);
		assertSame("Invalid condition type", condition.getClass(),
				EqualsCondition.class);
		EqualsCondition equalsCondition = (EqualsCondition) condition;
		String returnSql = equalsCondition.toSQL();
		assertTrue("Invalid sql! " + returnSql,
				sqlInput.equalsIgnoreCase(returnSql));
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse2() throws PlatformException
	{
		String sqlInput = "persons.id > 10";
		ICondition condition = ConditionParser.parse(_metaData, sqlInput);

		assertNotNull(condition);
		assertSame(condition.getClass(), GreaterCondition.class);
		GreaterCondition equalsCondition = (GreaterCondition) condition;
		String returnSql = equalsCondition.toSQL();
		assertTrue("Invalid sql! " + returnSql,
				sqlInput.equalsIgnoreCase(returnSql));
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse3() throws PlatformException
	{
		String sqlInput = "persons.id < 10";
		ICondition condition = ConditionParser.parse(_metaData, sqlInput);

		assertNotNull(condition);
		assertSame("Invalid condition type", condition.getClass(),
				LowerCondition.class);
		LowerCondition equalsCondition = (LowerCondition) condition;
		String returnSql = equalsCondition.toSQL();
		assertTrue("Invalid sql! " + returnSql,
				sqlInput.equalsIgnoreCase(returnSql));
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse4() throws PlatformException
	{
		try {
			String sqlInput = "< 10";
			ConditionParser.parse(_metaData, sqlInput);
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse5() throws PlatformException
	{
		try {
			String sqlInput = "persons.id <";
			ConditionParser.parse(_metaData, sqlInput);
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse6() throws PlatformException
	{
		try {
			String sqlInput = "persons < 10";
			ConditionParser.parse(_metaData, sqlInput);
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}
	
	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse7() throws PlatformException
	{
		try {
			ConditionParser.parse(_metaData, null);
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse8() throws PlatformException
	{
		try {
			ConditionParser.parse(null, "persons.id * 10");
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse9() throws PlatformException
	{
		try {
			ConditionParser.parse(null, "persons.id = '10");
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse10() throws PlatformException
	{
		try {
			ConditionParser.parse(null, "persons.id = 10'");
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}

	/*
	 * Test method for 'salomon.engine.platform.data.dataset.condition.ConditionParser.parse(String)'
	 */
	public void testParse11() throws PlatformException
	{
		try {
			ConditionParser.parse(null, "persons.id = ala");
			fail("Should raise an PlatformException");
		} catch (PlatformException e) {
			assertTrue(true);
		}
	}
	

	@Override
	protected void setUp() throws Exception
	{
		_metaData = (DBMetaData) TestObjectFactory.getSolution("Persons").getDataEngine().getMetaData();
	}
}
