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

public abstract class AbstractLogicalCondition extends AbstractCondition
{
    private AbstractCondition[] _conditions;

    private AbstractCondition _firstCondition;

    AbstractLogicalCondition(AbstractCondition codition,
            AbstractCondition... conditions)
    {
        super(null);

        _firstCondition = codition;
        _conditions = conditions;
    }

    @Override
    public String toSQL()
    {
        StringBuilder result = new StringBuilder();
        String operator = getOperator();
        result.append('(').append(_firstCondition).append(')');
        for (AbstractCondition currentCondition : _conditions) {
            result.append(' ').append(operator).append(' ');
            result.append('(').append(currentCondition.toSQL()).append(')');
        }

        return result.toString();
    }

}
