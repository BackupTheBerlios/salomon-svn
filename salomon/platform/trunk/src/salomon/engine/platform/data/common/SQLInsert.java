
package salomon.engine.platform.data.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class is responsible for building INSERT query.
 */
public final class SQLInsert
{
	private String _tableName;

	private List<SQLPair> _values;
    
	public SQLInsert()
	{
		_values = new LinkedList<SQLPair>();
	}

	/**
	 * 
	 * @param tableName table name
	 */
	public SQLInsert(String tableName)
	{
		_tableName = tableName;
		_values = new LinkedList<SQLPair>();
	}
    
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Date value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }

	/**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, double value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, int value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, String value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}
    
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Time value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }
    
    /**
	 * Adds value to be inserted.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
    public void addValue(String columnName, Timestamp value)
    {
        SQLHelper.addValue(_values, columnName, value);
    }
    
	/**
	 * Method returns INSERT query.
	 * 
	 * @return INSERT query
	 */
	public String getQuery()
	{
		String query = "INSERT INTO " + _tableName;
		String colNames = " (";
		String colValues = " VALUES (";

		Iterator colIter = _values.iterator();
		// first column is added without comma
		SQLPair pair = (SQLPair) colIter.next();
		colNames += pair.columnName;
		colValues += pair.value;

		// rest of column - with comma
		while (colIter.hasNext()) {
			pair = (SQLPair) colIter.next();
			colNames += ", " + pair.columnName;
			colValues += ", " + pair.value;
		}
		colNames += ")";
		colValues += ")";

		return query + colNames + colValues;
	}

	/**
	 * Returns table name.
	 * 
	 * @return Returns the tableName.
	 */
	public String getTableName()
	{
		return _tableName;
	}
    
	/**
	 * Sets table which values will be inserted to
	 * 
	 * @param tableName table name
	 */
	public void setTable(String tableName)
	{
		_tableName = tableName;
	}
	
    protected void addAllValues(Collection<SQLPair> values)
    {
        _values.addAll(values);
    }
}