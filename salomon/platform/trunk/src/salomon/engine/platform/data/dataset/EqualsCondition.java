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

package salomon.engine.platform.data.dataset;

import salomon.platform.data.IColumn;

import salomon.engine.platform.data.DBColumn;
import salomon.engine.platform.data.DBTable;

/**
 * 
 */
final class EqualsCondition extends AbstractCondition
{
	EqualsCondition(IColumn column, Object value)
	{
		super(column, value);
	}

	/**
	 * @see salomon.engine.platform.data.dataset.AbstractCondition#getSQL()
	 */
	@Override
	String getSQL()
	{
		DBColumn column = (DBColumn) getColumn();
		String columnName = column.getName();
		DBTable table = column.getTable();
		String tableName = table.getName();
		String value = null;

		String result = String.format("$s.$s=$s", tableName, columnName, value);

		return result;
	}

}
