/*
 * Created on 2004-05-04
 *
 */

package salomon.core.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import salomon.core.Config;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;
import salomon.core.data.common.DBValue;

/**
 * @author nico
 *  
 */
public class DBManager
{
	private Connection _connection = null;

	private Statement _statement = null;

	private static DBManager _instance = null;

	private static Logger _logger = Logger.getLogger(DBManager.class);

	private String _hostName = null;

	private String _dataBasePath = null;

	private String _user = null;

	private String _passwd = null;

	private boolean _isEmbedded = false;

	private DBManager()
	{
		_hostName = Config.getString("HOSTNAME"); //$NON-NLS-1$
		_dataBasePath = Config.getString("DB_PATH"); //$NON-NLS-1$
		_user = Config.getString("USER"); //$NON-NLS-1$
		_passwd = Config.getString("PASSWD"); //$NON-NLS-1$
		_isEmbedded = Config.getString("EMBEDDED").equalsIgnoreCase("Y");
	}

	public static DBManager getInstance() throws SQLException,
			ClassNotFoundException
	{
		if (_instance == null) {
			_instance = new DBManager();
			_instance.connect();
		}
		return _instance;
	}

	public void commit() throws SQLException
	{
		_connection.commit();
	}

	private void connect() throws SQLException, ClassNotFoundException
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver"); //$NON-NLS-1$
		String connectString = "jdbc:firebirdsql:";
		if (_isEmbedded) {
			connectString += "embedded:" + Config.CURR_DIR
					+ Config.FILE_SEPARATOR;
		} else {
			connectString += _hostName + ":";
		}
		connectString += _dataBasePath;
		_logger.info("connectString: " + connectString);
		_connection = DriverManager.getConnection(connectString, _user, _passwd);
		_connection.setAutoCommit(false);
		_statement = _connection.createStatement();
	}

	public void disconnect() throws SQLException
	{
		if (_statement != null) {
			_statement.close();
		}
		if (_connection != null) {
			_connection.close();
		}
	}

	public boolean executeQuery(String query) throws SQLException
	{
		return _statement.execute(query);
	}

	/**
	 * Method selects data from data base. It selects columns specified in
	 * columnNames or all columns if columnNames are null. Selection is made
	 * from tables basing on tableNames using conditions specified in
	 * conditions. If conditions is null, all data is selected.
	 * 
	 * @param columnNames
	 *            column names to select (if null all columns are selected)
	 * @param tableNames
	 *            tables used in conditions (not null)
	 * @param conditions
	 *            conditions of query (if null all data are selected)
	 * @return selected result set
	 * @throws SQLException
	 */
	public ResultSet select(DBColumnName[] columnNames,
			DBTableName[] tableNames, DBCondition[] conditions)
			throws SQLException
	{
		//TODO: synchronizacja aliasow
		String query = "SELECT "; //$NON-NLS-1$
		if (columnNames == null) {
			query += "*"; //$NON-NLS-1$
		} else {
			for (int i = 0; i < columnNames.length - 1; i++) {
				query += columnNames[i] + ", "; //$NON-NLS-1$
			}
			query += columnNames[columnNames.length - 1];
		}
		_logger.debug("query = " + query); //$NON-NLS-1$
		query += " FROM "; //$NON-NLS-1$
		for (int i = 0; i < tableNames.length - 1; i++) {
			query += tableNames[i].getForSelect() + ", "; //$NON-NLS-1$
		}
		query += tableNames[tableNames.length - 1].getForSelect();
		_logger.debug("query = " + query); //$NON-NLS-1$
		if (conditions != null) {
			query += " WHERE "; //$NON-NLS-1$
			for (int i = 0; i < conditions.length - 1; i++) {
				query += conditions[i] + " AND "; //$NON-NLS-1$
			}
			query += conditions[conditions.length - 1];
		}
		_logger.info("query = " + query); //$NON-NLS-1$
		return _statement.executeQuery(query);
	}

	/**
	 * Inserts record specified in DBValue array. Table name is taken from the
	 * first element from array.
	 * 
	 * @param values
	 *            values to insert
	 * @return always false
	 * @throws SQLException
	 */
	public boolean insert(DBValue[] values) throws SQLException
	{
		String query = "INSERT INTO ";
		String tableName = values[0].getColumnName().getTableName().toString();
		query += tableName + "(";
		for (int i = 0; i < values.length - 1; i++) {
			query += values[i].getColumnName().getForUpdate() + ",";
		}
		query += values[values.length - 1].getColumnName().getForUpdate()
				+ ") VALUES (";
		for (int i = 0; i < values.length - 1; i++) {
			query += values[i].getStringValue() + ",";
		}
		query += values[values.length - 1].getStringValue() + ")";
		_logger.info("query = " + query); //$NON-NLS-1$	
		return _statement.execute(query);
	}

	/**
	 * Inserts record specified in DBValue array. Table name is taken from the
	 * first element from array. Method autoincrement value in column
	 * primaryKey.
	 * 
	 * @param values
	 *            values to insert
	 * @param primaryKey
	 *            primary key column name
	 * @return generated primary key
	 * @throws SQLException
	 */
	public int insert(DBValue[] values, String primaryKey) throws SQLException
	{
		String tableName = values[0].getColumnName().getTableName().toString();
		//
		// selecting value of primary key
		//
		String autoIncrement = "SELECT coalesce(max(\"" + primaryKey
				+ "\"), 0) + 1 ";
		autoIncrement += "FROM " + tableName;
		_logger.debug("autoIncrement: " + autoIncrement);
		ResultSet resultSet = _statement.executeQuery(autoIncrement);
		resultSet.next();
		int primaryKeyID = resultSet.getInt(1);
		_logger.debug("primaryKeyID: " + primaryKeyID);
		//
		// Inserting data
		//
		String query = "INSERT INTO ";
		//
		// first column name is primary key
		//
		query += tableName + "(\"" + primaryKey + "\"";
		for (int i = 0; i < values.length; i++) {
			query += "," + values[i].getColumnName().getForUpdate();
		}
		query += ") VALUES (" + primaryKeyID;
		for (int i = 0; i < values.length; i++) {
			query += "," + values[i].getStringValue();
		}
		query += ")";
		_logger.info("query = " + query); //$NON-NLS-1$
		_statement.execute(query);
		return primaryKeyID;
	}

	/**
	 * Deletes records basing on given conditions. Table name is taken from the
	 * first condition.
	 * 
	 * @param conditions
	 *            conditions of query
	 * @return number of deleted records
	 * @throws SQLException
	 */
	public int delete(DBCondition[] conditions) throws SQLException
	{
		String tableName = conditions[0].getTableName().getForUpdate();
		String query = "DELETE FROM " + tableName + " WHERE ";
		for (int i = 0; i < conditions.length - 1; i++) {
			query += conditions[i] + " AND "; //$NON-NLS-1$
		}
		query += conditions[conditions.length - 1];
		_logger.info("query = " + query); //$NON-NLS-1$
		return _statement.executeUpdate(query);
	}

	/**
	 * Updates records to values specified in values array basing on given
	 * conditions. Table name is taken from the first element of values array.
	 * If conditions array is null, all data from appropriate table are updated.
	 * 
	 * @param values
	 *            values to set
	 * @param conditions
	 *            conditions of query (if null all data are updated)
	 * @return number of updated rows
	 * @throws SQLException
	 */
	public int update(DBValue[] values, DBCondition[] conditions)
			throws SQLException
	{
		String tableName = values[0].getColumnName().getTableName().getForUpdate();
		String query = "UPDATE " + tableName + " SET ";
		for (int i = 0; i < values.length - 1; i++) {
			query += values[i].getColumnName().getForUpdate() + "=";
			query += values[i].getStringValue() + ",";
		}
		query += values[values.length - 1].getColumnName().getForUpdate() + "=";
		query += values[values.length - 1].getStringValue();
		if (conditions != null) {
			query += " WHERE "; //$NON-NLS-1$
			for (int i = 0; i < conditions.length - 1; i++) {
				query += conditions[i] + " AND "; //$NON-NLS-1$
			}
			query += conditions[conditions.length - 1];
		}
		_logger.info("query = " + query); //$NON-NLS-1$
		return _statement.executeUpdate(query);
	}

	/**
	 * @return result set or null if query was INSERT, UPDATE or DELETE
	 * @throws SQLException
	 */
	public ResultSet getResultSet() throws SQLException
	{
		return _statement.getResultSet();
	}

	public int getUpdateCount() throws SQLException
	{
		return _statement.getUpdateCount();
	}

	public void rollback() throws SQLException
	{
		_connection.rollback();
	}
}