/*
 * Created on 2004-05-04
 *
 */

package ks.core;

import java.sql.*;

import ks.data.*;

import org.apache.log4j.Logger;

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
	
	private DBManager()
	{
	}

	public static DBManager getInstance() throws SQLException, ClassNotFoundException 
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
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		_connection = DriverManager.getConnection(
				"jdbc:firebirdsql://127.0.0.1/c:/database/testbase.gdb",
				"SYSDBA", "masterkey");
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

	public ResultSet selectData(DBColumnName[] columnNames,
			DBTableName[] tableNames, DBCondition[] conditions) throws SQLException
	{
		//TODO: synchronizacja aliasow
		String query = "SELECT ";
		if (columnNames == null) {
			query += "*";
		} else {
			for (int i = 0; i < columnNames.length - 1; i++) {
				query += columnNames[i] + ", ";
			}
			query += columnNames[columnNames.length - 1];
		}
		_logger.debug("query = " + query);
		
		query += " FROM ";
		for (int i = 0; i < tableNames.length - 1; i++) {
			query += tableNames[i].getFromQuery() + ", ";
		}
		query += tableNames[tableNames.length - 1].getFromQuery();
		
		_logger.debug("query = " + query);
		
		if (conditions != null) {
			query += " WHERE ";
			for (int i = 0; i < conditions.length - 1; i++) {
				query += conditions[i] + " AND ";
			}
			query += conditions[conditions.length - 1];
		}
		_logger.info("query = " + query);
		
		return _statement.executeQuery(query);
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
