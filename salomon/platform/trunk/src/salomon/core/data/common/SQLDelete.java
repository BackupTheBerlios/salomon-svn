
package salomon.core.data.common;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class is responsible for building DELETE query.
 *  
 */
public final class SQLDelete
{

	private List<String> _conditions;

	private String _tableName;

	public SQLDelete()
	{        
		_conditions = new LinkedList<String>();
	}

	/**
	 * 
	 * @param tableName table name
	 */
	public SQLDelete(String tableName)
	{
		_tableName = tableName;
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
	 * Method returns SELECT query.
	 * 
	 * @return SELECT query
	 */
    public String getQuery()
    {
        String query = "DELETE FROM " + _tableName; //$NON-NLS-1$
        
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
	 * Sets table which values will be inserted to
	 * 
	 * @param tableName table name
	 */
	public void setTable(String tableName)
	{
		_tableName = tableName;
	}
}