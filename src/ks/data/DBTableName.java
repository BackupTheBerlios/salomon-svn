/*
 * Created on 2004-05-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ks.data;

/**
 * @author nico
 *
 * Class represents table in SQL queries
 */
public class DBTableName
{
	private String _tableName = null;
	private String _tableAlias = null;
	
	
	/**
	 * @param _tableName table _tableName
	 * @param _tableAlias table _tableAlias
	 */
	public DBTableName(String name, String alias)
	{
		_tableName = name;
		_tableAlias = alias;
	}
	
	public DBTableName(String name)
	{
		_tableName = name;		
	}
	
	/* (non-Javadoc)
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
	// returns e.g "users" u ("name" alias)
	public String getFromQuery() {
		String fromQuery = "\"" + _tableName + "\"";
		if (_tableAlias != null) {
			fromQuery += " " + _tableAlias;
		}
		return fromQuery;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object tableName)
	{		
		return _tableName.equalsIgnoreCase(((DBTableName)tableName)._tableName);
	}
		
/*	public String getTableAlias()
	{
		return _tableAlias;
	}
	
	public void setTableAlias(String alias)
	{
		_tableAlias = alias;
	}
	
	public String getTableName()
	{
		return _tableName;
	}
	
	public void setTableName(String name)
	{
		_tableName = name;
	} */	
}
