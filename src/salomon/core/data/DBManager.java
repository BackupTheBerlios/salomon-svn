/*
 * Created on 2004-05-04
 *
 */

package salomon.core.data;

import java.sql.*;
import salomon.core.Config;
import salomon.core.data.common.*;
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
			connectString += "embedded:" + Config.CURR_DIR + Config.FILE_SEPARATOR;
		} else {
			connectString += _hostName + ":";
		}
		connectString += _dataBasePath;
		_logger.info("connectString: " + connectString);
		_connection = DriverManager
				.getConnection(connectString, _user, _passwd);		
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
			query += tableNames[i].getFromQuery() + ", "; //$NON-NLS-1$
		}
		query += tableNames[tableNames.length - 1].getFromQuery();
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
