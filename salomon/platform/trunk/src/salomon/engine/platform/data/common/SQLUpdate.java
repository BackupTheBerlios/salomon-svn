
package salomon.engine.platform.data.common;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class is responsible for building UPDATE query.
 *  
 */
public final class SQLUpdate
{

	private List<String> _conditions;

	private String _tableName;

	private List<SQLPair> _values;    

	public SQLUpdate()
	{
		_values = new LinkedList<SQLPair>();
		_conditions = new LinkedList<String>();
	}

	/**
	 * 
	 * @param tableName table name
	 */
	public SQLUpdate(String tableName)
	{
		_tableName = tableName;
        _values = new LinkedList<SQLPair>();
        _conditions = new LinkedList<String>();
	}

	/**
	 * Method adds join condition to query, e.g: p.person_id = u.user_id
	 * 
	 * @param joinCondition condition which joins two tables
	 */
	public void addCondition(String joinCondition)
	{
		_conditions.add(joinCondition);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, double value)
	{
		SQLHelper.addCondition(_conditions, condition, value);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, int value)
	{
		SQLHelper.addCondition(_conditions, condition, value);
	}

	/**
	 * Methods adds condition to SQL query. First argument is column name with
	 * relation, second is a value
	 * 
	 * @param condition column name with relation
	 * @param value value of condition
	 */
	public void addCondition(String condition, String value)
	{
		SQLHelper.addCondition(_conditions, condition, value);
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
	 * Adds value to be updated.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, double value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be updated.
	 * 
	 * @param columnName column name
	 * @param value value of element correspodning to given column name
	 */
	public void addValue(String columnName, int value)
	{
		SQLHelper.addValue(_values, columnName, value);
	}

	/**
	 * Adds value to be updated.
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
	 * Method returns UPDATE query.
	 * 
	 * @return UPDATE query
	 */
	public String getQuery()
	{
		String query = "UPDATE " + _tableName + " SET ";

		Iterator colIter = _values.iterator();
		// first column is added without comma
		SQLPair pair = (SQLPair) colIter.next();
		query += pair.columnName + " = " + pair.value;

		// rest of column - with comma
		while (colIter.hasNext()) {
			pair = (SQLPair) colIter.next();
			query += ", " + pair.columnName + " = " + pair.value;
		}

		// adding conditions
		if (!_conditions.isEmpty()) {
			query += " WHERE "; //$NON-NLS-1$
			Iterator condIter = _conditions.iterator();
			query += condIter.next();
			while (condIter.hasNext()) {
				query += " AND " + condIter.next();
			}
		}

		return query;
	}

	/**
	 * Returns name of updated table.
	 * 
	 * @return Returns the tableName.
	 */
	public String getTableName()
	{
		return _tableName;
	}

	/**
	 * Sets table which values will be updated to
	 * 
	 * @param tableName table name
	 */
	public void setTable(String tableName)
	{
		_tableName = tableName;
	}	

	
	/**
	 * Makes SQLInsert object basing on update values.
	 * 
	 * @return object representing INSERT query
	 */
    public SQLInsert updateToInsert()
    {
    	SQLInsert insert = new SQLInsert(_tableName);
        insert.addAllValues(_values);
        return insert;
    }
	
}

