
package salomon.plugin;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import salomon.core.data.DBManager;
import salomon.core.data.common.DBColumnName;
import salomon.core.data.common.DBCondition;
import salomon.core.data.common.DBTableName;
import salomon.core.data.common.DBValue;

/**
 * Class represents plugin description.
 *
 */
public class Description implements Serializable
{
	public static final String TABLE_NAME = "plugins";

	private String _info;

	private String _input;

	private String _name;

	private String _output;

	private String _version;

	private int _pluginID;

	private URL _location;

	public Description()
	{
	};

	/**
	 * @param info
	 * @param name
	 */
	public Description(String name, String info)
	{
		_name = name;
		_info = info;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo()
	{
		return _info;
	}

	/**
	 * @return Returns the input.
	 */
	public String getInput()
	{
		return _input;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the output.
	 */
	public String getOutput()
	{
		return _output;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion()
	{
		return _version;
	}

	/**
	 * @param info The info to set.
	 */
	public void setInfo(String info)
	{
		_info = info;
	}

	/**
	 * @param input The input to set.
	 */
	public void setInput(String input)
	{
		_input = input;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		_name = name;
	}

	/**
	 * @param output The output to set.
	 */
	public void setOutput(String output)
	{
		_output = output;
	}

	/**
	 * @param version The version to set.
	 */
	public void setVersion(String version)
	{
		_version = version;
	}

	/**
	 * @return Returns the location.
	 */
	public URL getLocation()
	{
		return _location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(URL location)
	{
		_location = location;
	}

	/**
	 * @return Returns the pluginID.
	 */
	public int getPluginID()
	{
		return _pluginID;
	}

	/**
	 * @param pluginID The pluginID to set.
	 */
	public void setPluginID(int pluginID)
	{
		this._pluginID = pluginID;
	}

	public String toString()
	{
		return "" + _pluginID + "," + _name + "," + _location + "," + _info;
	}

	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	public void init(ResultSet resultSet) throws MalformedURLException,
			SQLException
	{
		_pluginID = resultSet.getInt("plugin_id");
		_name = resultSet.getString("name");
		_location = new URL(resultSet.getString("location"));
		_info = resultSet.getString("info");
	}

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int save() throws SQLException, ClassNotFoundException
	{
		DBTableName[] tableNames = {new DBTableName(TABLE_NAME)};
		DBValue[] values = {
				new DBValue(new DBColumnName(tableNames[0], "name"), _name,
						DBValue.TEXT),
				new DBValue(new DBColumnName(tableNames[0], "info"), _info,
						DBValue.TEXT),
				new DBValue(new DBColumnName(tableNames[0], "location"),
						_location, DBValue.TEXT)};
		_pluginID = DBManager.getInstance().insertOrUpdate(values, "plugin_id",
				_pluginID);
		return _pluginID;
	}
	/**
     * Removes itself from database.
     * After successsful finish object should be destroyed.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean delete() throws SQLException, ClassNotFoundException
	{
		DBTableName[] tableNames = {new DBTableName(TABLE_NAME)};
		DBCondition[] conditions = {new DBCondition(new DBColumnName(
				tableNames[0], "plugin_id"), DBCondition.REL_EQ, new Integer(
				_pluginID), DBCondition.NUMBERIC)};
		return (DBManager.getInstance().delete(conditions) > 0);
	}

} // end Description
