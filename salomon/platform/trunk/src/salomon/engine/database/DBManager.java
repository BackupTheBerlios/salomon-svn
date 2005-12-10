/*
 * Copyright (C) 2004 Salomon Team
 *
 * This file is part of Salomon.
 *
 * Salomon is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * Salomon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Salomon; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import salomon.engine.Config;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.database.queries.SQLUpdate;
import salomon.engine.platform.data.DBMetaData;
import salomon.util.gui.Utils;

/**
 * Class is responsible for data base operations. It enables executing SQL
 * queries and transaction managing.
 */
public final class DBManager
{
	private Connection _connection;

	private DBMetaData _metaData;

	private Statement _statement;

	public DBManager()
	{
		//empty body
	}

	/**
	 * Commits current transaction. Exception is caught, if occurs it indicates
	 * critical error.
	 */
	public void commit()
	{
		LOGGER.info("Commit");
		try {
			_connection.commit();
		} catch (SQLException e) {
			LOGGER.fatal("!!!COMMIT FAILED!!!");
			Utils.showErrorMessage("ERR_CRITICAL");
			LOGGER.fatal("", e);
		}
	}

	/**
	 * Method connects to data base. 
	 * It uses configuration file to get connection parameters.
	 * It should be used only by platform, connection is established to main Salomon database. 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * 
	 */
	public void connect() throws SQLException, ClassNotFoundException
	{
		String hostName = Config.getString("HOSTNAME"); //$NON-NLS-1$
		String dataBasePath = Config.getString("DB_PATH"); //$NON-NLS-1$
		String user = Config.getString("USER"); //$NON-NLS-1$
		String passwd = Config.getString("PASSWD"); //$NON-NLS-1$	
		this.connect(hostName, dataBasePath, user, passwd);
	}

	/**
	 * Method connects to data base.
	 * 
	 * @param host host name or IP address. If empty it means, that it is EMBEDDED mode.
	 * @param dataBasePath relative (if EMBEDDED mode) or absolute path to database file
	 * @param user user name
	 * @param passwd password
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void connect(String host, String dataBasePath, String user,
			String passwd) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver"); //$NON-NLS-1$
		StringBuilder connectString = new StringBuilder("jdbc:firebirdsql:");
		if (host == null || "".equals(host)) {
			connectString.append("embedded:");
			connectString.append(Config.CURR_DIR);
			connectString.append(Config.FILE_SEPARATOR);
		} else {
			connectString.append(host).append(":");
		}
		connectString.append(dataBasePath);
		LOGGER.info("connectString: " + connectString);
		_connection = DriverManager.getConnection(connectString.toString(),
				user, passwd);
		// setting auto commit off
		_connection.setAutoCommit(false);
		_statement = _connection.createStatement();
		LOGGER.info("connected to database");
	}

	/**
	 * Deletes records basing on given SQLDelete object.
	 * 
	 * @param deleteObject object holding DELETE query
	 * @return number of deleted records
	 * @throws SQLException
	 */
	public int delete(SQLDelete deleteObject) throws SQLException
	{
		String query = deleteObject.getQuery();
		LOGGER.info("query = " + query); //$NON-NLS-1$
		return _statement.executeUpdate(query);
	}

	/**
	 * Disconnects from data base.
	 * 
	 * @throws SQLException
	 */
	public void disconnect() throws SQLException
	{
		if (_statement != null) {
			_statement.close();
		}
		if (_connection != null) {
			_connection.close();
		}
	}

	/**
	 * Executed given SQL query.
	 * 
	 * @param query to execute
	 * @return result of execution
	 * @throws SQLException
	 */
	public boolean executeQuery(String query) throws SQLException
	{
		LOGGER.info("Executing query = " + query); //$NON-NLS-1$
		return _statement.execute(query);
	}

	public DatabaseMetaData getDatabaseMetaData() throws SQLException
	{
		return _connection.getMetaData();
	}

	/**
	 * Returns DBMetaData object.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public DBMetaData getMetaData() throws SQLException
	{
		if (_metaData == null) {
			_metaData = new DBMetaData(this);
			_metaData.init();
		}
		return _metaData;
	}

	/**
	 * Returns result set for last executed query.
	 * 
	 * @return result set or null if query was INSERT, UPDATE or DELETE
	 * @throws SQLException
	 */
	public ResultSet getResultSet() throws SQLException
	{
		return _statement.getResultSet();
	}

	/**
	 * Returns count of records updated by last executed query.
	 * 
	 * @return count of updated records
	 * @throws SQLException
	 */
	public int getUpdateCount() throws SQLException
	{
		return _statement.getUpdateCount();
	}

	/**
	 * Inserts record specified SQLInsert object.
	 * 
	 * @param insertObject object holding INSERT query
	 * @return always false
	 * @throws SQLException
	 */
	public boolean insert(SQLInsert insertObject) throws SQLException
	{
		String query = insertObject.getQuery();
		LOGGER.info("query = " + query); //$NON-NLS-1$	
		return _statement.execute(query);
	}

	/**
	 * Inserts record specified SQLInsert object. Method autoincrement value in
	 * column primaryKey.
	 * 
	 * @param insertObject object holding INSERT query
	 * @param primaryKey primary key column name
	 * @return generated primary key
	 * @throws SQLException
	 */
	public int insert(SQLInsert insertObject, String primaryKey)
			throws SQLException
	{
		// selecting value of primary key
		String autoIncrement = "SELECT coalesce(max(" + primaryKey
				+ "), 0) + 1 ";
		autoIncrement += "FROM " + insertObject.getTableName();
		LOGGER.debug("autoIncrement: " + autoIncrement);
		ResultSet resultSet = _statement.executeQuery(autoIncrement);
		resultSet.next();
		int primaryKeyID = resultSet.getInt(1);
		LOGGER.debug("primaryKeyID: " + primaryKeyID);
		resultSet.close();

		// Inserting data
		// (primary key will be at the end of query, it's not nice
		// but doesn't requeire any changes in SQLInsert class
		insertObject.addValue(primaryKey, primaryKeyID);
		insert(insertObject);
		return primaryKeyID;
	}

	/**
	 * Inserts record specified SQLInsert object. Method autoincrement value in
	 * column primaryKey using specified generator.
	 * 
	 * @param insertObject object holding INSERT query
	 * @param primaryKey primary key column name
	 * @param generator generator name
	 * @return generated primary key
	 * @throws SQLException
	 */
	public int insert(SQLInsert insertObject, String primaryKey,
			String generator) throws SQLException
	{
		// selecting value of primary key
		String autoIncrement = "SELECT GEN_ID(" + generator
				+ ", 1) FROM RDB$DATABASE";
		LOGGER.debug("autoIncrement: " + autoIncrement);
		ResultSet resultSet = _statement.executeQuery(autoIncrement);
		resultSet.next();
		int primaryKeyID = resultSet.getInt(1);
		LOGGER.debug("primaryKeyID: " + primaryKeyID);
		resultSet.close();

		// Inserting data
		// (primary key will be at the end of query, it's not nice
		// but doesn't requeire any changes in SQLInsert class
		insertObject.addValue(primaryKey, primaryKeyID);
		insert(insertObject);
		return primaryKeyID;
	}

	/**
	 * Method inserts new record if hasn't exist one with given id yet,
	 * otherwise performs update.
	 * 
	 * @param updateObject object to insert/update
	 * @param primaryKey name of primary key of table
	 * @param id id of record to upddate/insert
	 * @return id of inserted/updated record
	 * @throws SQLException
	 */
	public int insertOrUpdate(SQLUpdate updateObject, String primaryKey, int id)
			throws SQLException
	{
		int foundID = updateIfExists(updateObject, primaryKey, id);
		if (foundID < 0) {
			foundID = insert(updateObject.updateToInsert(), primaryKey);
			LOGGER.info("New record inserted, id = " + foundID);
		}

		return foundID;
	}

	/**
	 * Method inserts new record if hasn't exist one with given id yet,
	 * otherwise performs update.
	 * 
	 * @param updateObject object to insert/update
	 * @param primaryKey name of primary key of table
	 * @param id id of record to upddate/insert
	 * @param generator name of generator used to generate primary key
	 * @return id of inserted/updated record
	 * @throws SQLException
	 */
	public int insertOrUpdate(SQLUpdate updateObject, String primaryKey,
			int id, String generator) throws SQLException
	{
		int foundID = updateIfExists(updateObject, primaryKey, id);
		if (foundID < 0) {
			foundID = insert(updateObject.updateToInsert(), primaryKey,
					generator);
			LOGGER.info("New record inserted, id = " + foundID);
		}

		return foundID;
	}

	/**
	 * Annuls current transaction. Exception is caught, if occurs it indicates
	 * critical error.
	 * 
	 */

	public void rollback()
	{
		LOGGER.info("Rollback");
		try {
			_connection.rollback();
		} catch (SQLException e) {
			LOGGER.fatal("!!!ROLLBACK FAILED!!!");
			Utils.showErrorMessage("ERR_CRITICAL");
			LOGGER.fatal("", e);
		}
	}

	/**
	 * Method selects data from data base getting query from given SQLInsert
	 * object.
	 * 
	 * @param selectObject object holgind SELECT query
	 * @return selected result set
	 * @throws SQLException
	 */
	public ResultSet select(SQLSelect selectObject) throws SQLException
	{
		String query = selectObject.getQuery();
		LOGGER.info("query = " + query); //$NON-NLS-1$
		return _statement.executeQuery(query);
	}

	/**
	 * Updates records to values specified in SQLUpdate object.
	 * @param updateObject 
	 * 
	 * @return number of updated rows
	 * @throws SQLException
	 */
	public int update(SQLUpdate updateObject) throws SQLException
	{
		String query = updateObject.getQuery();
		LOGGER.info("query = " + query); //$NON-NLS-1$

		return _statement.executeUpdate(query);
	}

	/**
	 * Method checks if given query return any result.
	 * 
	 * @param select query to be selected
	 * @return true if query returns anything, false otherwise
	 * @throws SQLException
	 */
	public boolean existsSelect(SQLSelect select) throws SQLException
	{
		boolean exists = false;
		ResultSet resultSet = _statement.executeQuery(select.getQuery());
		exists = resultSet.next();
		resultSet.close();
		return exists;
	}

	/**
	 * Updates record if exists in data base.
	 * 
	 * @param updateObject
	 * @param primaryKey
	 * @param id
	 * @return id of found record or -1 if record does not exist
	 * @throws SQLException
	 */
	private int updateIfExists(SQLUpdate updateObject, String primaryKey, int id)
			throws SQLException
	{
		int foundID = -1;
		SQLSelect select = new SQLSelect();
		select.addTable(updateObject.getTableName());
		select.addCondition(primaryKey + " =", id);

		ResultSet resultSet = null;
		resultSet = select(select);
		// if record exists in base its id is selected
		// otherwise new id is generated
		if (resultSet.next()) {
			foundID = resultSet.getInt(primaryKey);
			LOGGER.info("Record found, id = " + foundID);
			updateObject.addCondition(primaryKey + " =", foundID);
			resultSet.close();
			int updatedRows = update(updateObject);
			if (updatedRows > 1) {
				LOGGER.error("TOO MANY ROWS: " + updatedRows);
				foundID = -1;
			}
			LOGGER.info("Record updated");
		}

		return foundID;
	}

	private static final Logger LOGGER = Logger.getLogger(DBManager.class);
}