/*
 * Created on 2004-05-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package salomon.core.data.common;

import org.apache.log4j.Logger;

/**
 * @author nico
 * 
 * Class represents condition used in SQL queries
 */
public class DBCondition
{
	public static final int NUMBERIC = -1;

	public static final int TEXT = -2;

	public static final int JOIN = -3;

	public static final int REL_NEQ = 0; // not equal

	public static final int REL_LEQ = 1; // less or equal

	public static final int REL_MEQ = 2; // more or equal

	public static final int REL_EQ = 3; // equal

	public static final int REL_L = 4; // less

	public static final int REL_M = 5; //more
	
	public static final int REL_LIKE = 6; //more
	
	private DBColumnName _columnName = null;

	private Object _value = null;

	private String _relation = null;

	private int _type = 0;

	private static String[] relations = {"<>", "<=", ">=", "=", "<", ">", "LIKE"};
	
	private static Logger _logger = Logger.getLogger(DBCondition.class);

	/**
	 * @param _tableName
	 * @param value
	 * @param type
	 */
	public DBCondition(DBColumnName columnName, int relation, Object value,
			int type)
	{
		_columnName = columnName;
		_relation = relations[relation];
		_value = value;
		_type = type;
	}

	/**
	 * Initializes condition basing on given query. example: table name ==
	 * car_sales whereClause == payment_type = 'card'
	 * 
	 * @param query
	 *            TODO: JOIN support
	 */
	public DBCondition(String strTableName, String whereClause)
	{
		String columnValue = null;
		String columnName = null;
		//parsing query, trying to get column name and its value
		for (int i = 0; i < relations.length; i++) {
			int index = whereClause.indexOf(relations[i]);
			if (index != -1) {
				_relation = relations[i];
				columnName = whereClause.substring(0, index).trim();
				columnValue = whereClause.substring(
						index + relations[i].length()).trim();
				break;
			}
		}
	
		// setting type
		if (columnValue.startsWith("'")) {
			_type = TEXT;
			// removing ' from begining and from the end
			_value = columnValue.substring(1, columnValue.length() - 1);
		} else {
			_type = NUMBERIC;
			_value = new Integer(columnValue);
		}
		DBTableName dbTableName = new DBTableName(strTableName);
		_columnName = new DBColumnName(dbTableName, columnName);
	}
	// returns table name of the left side of condition 
	public DBTableName getTableName()
	{
		return _columnName.getTableName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		//TODO:
		String condition = "";
		condition += _columnName.toString() + " " + _relation + " ";
		switch (_type) {
			case NUMBERIC :
				condition += _value;
				break;
			case TEXT :
				condition += "'" + _value + "'";
				break;
			case JOIN :
				condition += _value;
				break;
			default :
				_logger.error("Invalid condition type: " + _type);
				return "";
		}
		return condition;
	}
}
