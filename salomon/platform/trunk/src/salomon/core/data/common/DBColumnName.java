
package salomon.core.data.common;

/**
 * Class represents data base table column in SQL queries.
 *
 */
public class DBColumnName
{
	private DBTableName _tableName;

	private String _columnName;

	private String _columnAlias;

	/**
	 * @param tableName table name
	 * @param columnName column name
	 * @param columnAlias column alias
	 */
	public DBColumnName(DBTableName tableName, String columnName,
			String columnAlias)
	{
		_tableName = tableName;
		_columnName = columnName;
		_columnAlias = columnAlias;
	}

	public DBColumnName(DBTableName tableName, String columnName)
	{
		_tableName = tableName;
		_columnName = columnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String name = _tableName + ".\"" + _columnName + "\"";
		if (_columnAlias != null) {
			name += " " + _columnAlias;
		}
		return name;
	}

	/**
	 * Method returns qouted column name without table name or table alias. Some
	 * statements don't accept table name as a prefix
	 * 
	 * @return qouted column name
	 */
	public String getForUpdate()
	{
		return "\"" + _columnName + "\"";
	}

	/**
	 * Method returns table name.
	 * 
	 * @return the table name.
	 */
	public DBTableName getTableName()
	{
		return _tableName;
	}
}