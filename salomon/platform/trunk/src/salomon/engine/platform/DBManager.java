/*
 * Created on 2004-05-04
 *
 */

package salomon.engine.platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import salomon.engine.platform.data.common.SQLDelete;
import salomon.engine.platform.data.common.SQLInsert;
import salomon.engine.platform.data.common.SQLSelect;
import salomon.engine.platform.data.common.SQLUpdate;
import salomon.util.gui.Utils;

/**
 * Class is responsible for data base operations. It enables executing SQL
 * queries and transaction managing.
 * 
 * @author nico
 */
public class DBManager
{
	private Connection _connection = null;

	private String _dataBasePath = null;

	private String _hostName = null;

	private boolean _isEmbedded = false;

	private String _passwd = null;

	private Statement _statement = null;

	private String _user = null;

	private DBManager()
	{
		_hostName = Config.getString("HOSTNAME"); //$NON-NLS-1$
		_dataBasePath = Config.getString("DB_PATH"); //$NON-NLS-1$
		_user = Config.getString("USER"); //$NON-NLS-1$
		_passwd = Config.getString("PASSWD"); //$NON-NLS-1$
		_isEmbedded = Config.getString("EMBEDDED").equalsIgnoreCase("Y");
	}

	/**
	 * Commits current transaction. Exception is caught, if occurs it indicates
	 * critical error.
	 */
	public void commit()
	{
		_logger.info("Commit");
		try {
			_connection.commit();
		} catch (SQLException e) {
			_logger.fatal("!!!COMMIT FAILED!!!");
			Utils.showErrorMessage("ERR_CRITICAL");
			_logger.fatal("", e);
		}
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
		_logger.info("query = " + query); //$NON-NLS-1$
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
	 * @return @throws SQLException
	 */
	public boolean executeQuery(String query) throws SQLException
	{
		_logger.info("Executing query = " + query); //$NON-NLS-1$
		return _statement.execute(query);
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
		_logger.info("query = " + query); //$NON-NLS-1$	
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
		_logger.debug("autoIncrement: " + autoIncrement);
		ResultSet resultSet = _statement.executeQuery(autoIncrement);
		resultSet.next();
		int primaryKeyID = resultSet.getInt(1);
		_logger.debug("primaryKeyID: " + primaryKeyID);
		resultSet.close();

		// Inserting data
		//(primary key will be at the end of query, it's not nice
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
		_logger.debug("autoIncrement: " + autoIncrement);
		ResultSet resultSet = _statement.executeQuery(autoIncrement);
		resultSet.next();
		int primaryKeyID = resultSet.getInt(1);
		_logger.debug("primaryKeyID: " + primaryKeyID);
		resultSet.close();

		// Inserting data
		//(primary key will be at the end of query, it's not nice
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
			_logger.info("New record inserted, id = " + foundID);
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
    public int insertOrUpdate(SQLUpdate updateObject, String primaryKey, int id, String generator)
            throws SQLException
    {
        int foundID = updateIfExists(updateObject, primaryKey, id);
        if (foundID < 0) {            
            foundID = insert(updateObject.updateToInsert(), primaryKey, generator);
            _logger.info("New record inserted, id = " + foundID);
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
		_logger.info("Rollback");
		try {
			_connection.rollback();
		} catch (SQLException e) {
			_logger.fatal("!!!ROLLBACK FAILED!!!");
			Utils.showErrorMessage("ERR_CRITICAL");
			_logger.fatal("", e);
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
		_logger.info("query = " + query); //$NON-NLS-1$
		return _statement.executeQuery(query);
	}

	/**
	 * Updates records to values specified in SQLUpdate object.
	 * 
	 * @param values values to set
	 * @param conditions conditions of query (if null all data are updated)
	 * @return number of updated rows
	 * @throws SQLException
	 */
	public int update(SQLUpdate updateObject) throws SQLException
	{
		String query = updateObject.getQuery();
		_logger.info("query = " + query); //$NON-NLS-1$
		return _statement.executeUpdate(query);
	}

	private void connect() throws SQLException, ClassNotFoundException
	{
		Class.forName("org.firebirdsql.jdbc.FBDriver"); //$NON-NLS-1$
		String connectString = "jdbc:firebirdsql:";
		if (_isEmbedded) {
			connectString += "embedded:" + Config.CURR_DIR
					+ Config.FILE_SEPARATOR;
		} else {
			connectString += _hostName + ":" + Config.CURR_DIR
					+ Config.FILE_SEPARATOR;
		}
		connectString += _dataBasePath;
		_logger.info("connectString: " + connectString);
		_connection = DriverManager.getConnection(connectString, _user, _passwd);
		// setting auto commit off
		_connection.setAutoCommit(false);
		_statement = _connection.createStatement();
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
	private int updateIfExists(SQLUpdate updateObject, String primaryKey, int id) throws SQLException
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
			_logger.info("Record found, id = " + foundID);
            updateObject.addCondition(primaryKey + " =", foundID);
			resultSet.close();			
			int updatedRows = update(updateObject);
            if (updatedRows > 1) {
            	_logger.error("TOO MANY ROWS: " + updatedRows);
                foundID = -1;
            }
			_logger.info("Record updated");
		}
		return foundID;
	}

	/**
	 * Method returns instance of DBManager. It ensures that exactly one
	 * instance of class will be created.
	 * 
	 * @return @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static DBManager getInstance() throws SQLException,
			ClassNotFoundException
	{
		if (_instance == null) {
			_instance = new DBManager();
			_instance.connect();
		}
		return _instance;
	}

	private static DBManager _instance = null;

	private static Logger _logger = Logger.getLogger(DBManager.class);
}