package salomon.engine.platform.data;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface to make database support easier.
 * Classes which implement it can save/load/delete 
 * appropriate records from data base. 
 * 
 */
public interface IDBSupporting
{
	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	public void load(ResultSet resultSet) throws MalformedURLException,
			SQLException;

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int save() throws SQLException, ClassNotFoundException;

	/**
	 * Removes itself from database.
	 * After successsful finish object should be destroyed.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean delete() throws SQLException, ClassNotFoundException;
}