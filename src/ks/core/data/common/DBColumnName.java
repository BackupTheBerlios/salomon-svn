/*
 * Created on 2004-05-04
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package ks.core.data.common;

/**
 * @author nico
 * 
 * Class represents column _tableName in SQL queries
 */
public class DBColumnName
{
	private DBTableName _tableName;

	private String _columnName;

	private String _columnAlias;

	/**
	 * @param _tableName
	 *            table name
	 * @param _columnName
	 *            column name
	 * @param _columnAlias
	 *            column alias
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String name =  _tableName + ".\"" + _columnName + "\"";
		if (_columnAlias != null) {
			name += " " + _columnAlias;
		}
		return name;
	}
	/**
	 * @return Returns the _tableName.
	 */
	public DBTableName getTableName()
	{
		return _tableName;
	}
}
