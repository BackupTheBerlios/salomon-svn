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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.exception.PlatformException;

/**
 * ^(\w+)\.(\w+)\s([=><])\s(.+)$
 */
public final class ConditionParser
{
	private static final Pattern PATTERN = Pattern.compile("^(\\w+)\\.(\\w+)\\s([=><])\\s(.+)$");
	
	public static ICondition parse(IMetaData metaData, String sql) throws PlatformException
	{
		if (metaData == null) {
			throw new PlatformException("Meta data cannot be null!");
		}
		if (sql == null) {
			throw new PlatformException("Sql cannot be null!");
		}
		
		Matcher matcher = PATTERN.matcher(sql);
		if (!matcher.matches()) {
			throw new PlatformException("Cannot parse sql! Invalid format! " + sql);			
		}
		int groupCount = matcher.groupCount();
		if (groupCount != 4) {
			throw new PlatformException("Cannot parse sql! Invalid format! " + sql);
		}
		String tableName = matcher.group(1);
		String columnName = matcher.group(2);
		String operator = matcher.group(3);
		String valueString = matcher.group(4);
		Object value = getValue(valueString);
		
		ITable table = metaData.getTable(tableName);
		IColumn column = table.getColumn(columnName);
		ICondition result = null;
		
		if (">".equals(operator)) {
			result = new GreaterCondition(column, value);
		} else if ("<".equals(operator)) {
			result = new LowerCondition(column, value);
		} else if ("=".equals(operator)) {
			result = new EqualsCondition(column, value);
		} else {
			throw new PlatformException("Invalid operator! \'" + operator + "\'");
		}
		
		return result;
	}
	
	public static Object getValue(String valueString) throws PlatformException
	{
		Object result = null;
		if (valueString.startsWith("\'")) {
			if (valueString.endsWith("\'")) {
				// string value
				result = valueString.substring(1, valueString.length() - 2);
			} else {
				throw new PlatformException("Invalid value: " + valueString);
			}
		} else {
			if (!valueString.endsWith("\'")) {
				result = Integer.parseInt(valueString);
			} else {
				throw new PlatformException("Invalid value: " + valueString);
			}
		}
		
		return result;
	}
}
