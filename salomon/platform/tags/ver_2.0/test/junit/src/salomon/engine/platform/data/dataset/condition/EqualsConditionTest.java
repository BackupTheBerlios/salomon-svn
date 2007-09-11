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

import salomon.platform.data.IColumn;

public class EqualsConditionTest extends AbstractConditionTest
{

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.EqualsCondition.getOperator()'
     */
    public void testGetOperator()
    {
        AbstractCondition condition = getCondition();
        String operator = condition.getOperator();

        assertEquals("=", operator);
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.AbstractCondition.getValueString()'
     */
    public void testGetValueString()
    {
        AbstractOperatorCondition condition = (AbstractOperatorCondition) getCondition();
        String valueString = condition.getValueString();

        assertEquals("10", valueString);
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.AbstractOperatorCondition.toSQL()'
     */
    public void testToSQL()
    {
        AbstractCondition condition = getCondition();
        String sqlString = condition.toSQL();

        assertEquals("persons.person_id = 10", sqlString);
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.EqualsCondition.equals()'
     */
    public void testEquals()
    {
        AbstractCondition condition = getCondition();
        IColumn column = condition.getColumn();
        EqualsCondition equalsCondition = new EqualsCondition(column, 10);

        assertEquals(condition, equalsCondition);
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.EqualsCondition.equals()'
     */
    public void testEquals2()
    {
        AbstractCondition condition = getCondition();
        IColumn column = condition.getColumn();
        EqualsCondition equalsCondition = new EqualsCondition(column, 11);

        assertTrue("Euals should return false!",
                !condition.equals(equalsCondition));
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.condition.EqualsCondition.equals()'
     */
    public void testEquals3()
    {
        AbstractCondition condition = getCondition();
        IColumn column = condition.getColumn();
        GreaterCondition greaterCondition = new GreaterCondition(column, 10);

        assertTrue("Euals should return false!",
                !condition.equals(greaterCondition));
    }

    @Override
    protected AbstractCondition createCondition(IColumn column, Object value)
    {
        return new EqualsCondition(column, value);
    }
}
