
package salomon.engine.platform.data.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * Class helps converting conditions and values to form accepted by data base.
 *  
 */
final class SQLHelper
{

	public static void addCondition(Collection<String> conditions, String condition,
			double value)
	{
		addConditionImpl(conditions, condition, String.valueOf(value));
	}

	public static void addCondition(Collection<String> conditions, String condition,
			int value)
	{
		addConditionImpl(conditions, condition, String.valueOf(value));
	}

	public static void addCondition(Collection<String> conditions, String condition,
			String value)
	{
		addConditionImpl(conditions, condition, "'"
				+ value.replaceAll("'", "''") + "'");
	}

	public static void addValue(Collection<SQLPair> values, String columnName,
			double value)
	{
		addValueImpl(values, columnName, String.valueOf(value));
	}

	public static void addValue(Collection<SQLPair> values, String columnName, int value)
	{
		addValueImpl(values, columnName, String.valueOf(value));
	}
    
    public static void addValue(Collection<SQLPair> values, String columnName, Date value)
    {
    	addValue(values, columnName, DATE_FORMAT.format(value).toString());
    }
    
    public static void addValue(Collection<SQLPair> values, String columnName, Timestamp value)
    {
        addValue(values, columnName, TIMESTAMP_FORMAT.format(value).toString());
    }
    
    public static void addValue(Collection<SQLPair> values, String columnName, Time value)
    {
        addValue(values, columnName, TIME_FORMAT.format(value).toString());
    }

	public static void addValue(Collection<SQLPair> values, String columnName,
			String value)
	{
		addValueImpl(values, columnName, "'" + value.replaceAll("'", "''")
				+ "'");
	}

	private static void addConditionImpl(Collection<String> conditions,
			String condition, String value)
	{
		conditions.add(condition + " " + value);
	}

	private static void addValueImpl(Collection<SQLPair> values, String columnName,
			String value)
	{
		values.add(new SQLPair(columnName, value));
	}
    
    private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private final static DateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
    
    private final static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss:SS");

}