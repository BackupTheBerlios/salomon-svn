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
	public void testParse() throws PlatformException
	{
		String sqlInput = "persons.person_id = 10";
		ICondition condition = ConditionParser.parse(_metaData, sqlInput);
		assertTrue("Invalid condition type", condition instanceof EqualsCondition);
		EqualsCondition equalsCondition = (EqualsCondition) condition;
		String returnSql = equalsCondition.toSQL();
		assertTrue("Invalid sql! " + returnSql, sqlInput.equals(returnSql));
	}

	@Override
	protected void setUp() throws Exception
	{
		_metaData = TestObjectFactory.getMetaData();
	}
}
