package salomon.core.data.common;

/**
 * 
 * Class represents table in SQL queries
 */
public class DBTableName
{

	private String _tableAlias = null;

	private String _tableName = null;

	public DBTableName(String name)
	{
		_tableName = name;
	}

	/**
	 * @param _tableName table _tableName
	 * @param _tableAlias table _tableAlias
	 */
	public DBTableName(String name, String alias)
	{
		_tableName = name;
		_tableAlias = alias;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object tableName)
	{
		return _tableName.equalsIgnoreCase(((DBTableName) tableName)._tableName);
	}

	// returns e.g "users" u ("name" alias)
	public String getForSelect()
	{
		String forSelect = "\"" + _tableName + "\"";
		if (_tableAlias != null) {
			forSelect += " " + _tableAlias;
		}
		return forSelect;
	}

	public String getForUpdate()
	{
		return "\"" + _tableName + "\"";
	}

	public String getTableAlias()
	{
		return _tableAlias;
	}

	public String getTableName()
	{
		return _tableName;
	}

	public void setTableAlias(String alias)
	{
		_tableAlias = alias;
	}

	public void setTableName(String name)
	{
		_tableName = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String name = null;
		if (_tableAlias != null) {
			name = _tableAlias;
		} else {
			name = "\"" + _tableName + "\"";
		}
		return name;
	}
}