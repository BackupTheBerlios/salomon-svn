/*
 * Created on 2004-05-28
 *
 */

package salomon.core.data.common;

import org.apache.log4j.Logger;

/**
 * Class represents column value
 * 
 * @author nico
 */
public class DBValue
{
	private static Logger _logger = Logger.getLogger(DBValue.class);

	private DBColumnName _columnName = null;

	private Object _value = null;

	private int _type = 0;

	public static final int NUMBERIC = 1;

	public static final int TEXT = 2;

	/**
	 * @param columnName
	 * @param value
	 * @param type
	 */
	public DBValue(DBColumnName columnName, Object value, int type)
	{
		_columnName = columnName;
		_value = value;
		_type = type;
	}

	/**
	 * @return Returns the columnName.
	 */
	public DBColumnName getColumnName()
	{
		return _columnName;
	}

	/**
	 * @param name
	 *            The columnName to set.
	 */
	public void setColumnName(DBColumnName name)
	{
		_columnName = name;
	}

	/**
	 * @return Returns the value.
	 */
	public Object getValue()
	{
		return _value;
	}

	/**
	 * @param value
	 *            The value to set.
	 */
	public void setValue(Object value)
	{
		_value = value;
	}

	public String getStringValue()
	{
		String value = "";
		switch (_type) {
			case NUMBERIC :
				value += _value;
				break;
			case TEXT :
				value += "'" + ((_value == null) ? "" : _value ) + "'";
				break;
			default :
				_logger.error("Invalid condition type: " + _type);
				return "";
		}
		return value;
	}
}