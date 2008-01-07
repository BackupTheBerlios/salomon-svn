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

package salomon.engine.plugin;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Class represents plugin description.
 */
public class PluginInfo implements IPluginInfo {

	public static final String PRIMARY_KEY = "plugin_id";

	public static final String TABLE_NAME = "plugins";

	public static final String VIEW_NAME = "plugins_view";

	private static final String GEN_NAME = "gen_plugin_id";

	private static final Logger LOGGER = Logger.getLogger(PluginInfo.class);

	private Date _cDate;

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	private String _info;

	private String _input;

	private Date _lmDate;

	private String _location;

	private String _pluginName;

	private String _output;

	private long _pluginID;

	private PluginType _pluginType;

	private String _version;

	@Deprecated
	public PluginInfo(DBManager manager) {
		_dbManager = manager;
	}
	
	public PluginInfo() {
		// empty body
	}
	
	public PluginInfo(String pluginName) {
		_pluginName = pluginName;
	}

	/**
	 * @param info
	 * @param name
	 */
	public PluginInfo(String name, String info) {
		_pluginName = name;
		_info = info;
	}

	/**
	 * Removes itself from database. After successsful finish object should be
	 * destroyed.
	 * 
	 * @throws DBException
	 */
	public boolean delete() throws DBException {
		SQLDelete delete = new SQLDelete(TABLE_NAME);
		delete.addCondition(PRIMARY_KEY + " =", _pluginID);
		try {
			return (_dbManager.delete(delete) > 0);
		} catch (SQLException e) {
			LOGGER.fatal("Exception was thrown!", e);
			throw new DBException("Cannor delete!", e);
		}
	}

	public Date getCreationDate() throws PlatformException {
		return _cDate;
	}

	/**
	 * @return Returns the description.
	 */
	public String getInfo() {
		return _info;
	}

	/**
	 * @return Returns the input.
	 */
	public String getInput() {
		return _input;
	}
	
	public Date getLastModificationDate() throws PlatformException {
		return _lmDate;
	}

	/**
	 * @return Returns the location.
	 */
	public String getLocation() {
		return _location;
	}

	/**
	 * @return Returns the output.
	 */
	public String getOutput() {
		return _output;
	}

	public long getPluginId() {
		return _pluginID;
	}

	public String getPluginName() {
		return _pluginName;
	}

	/**
	 * Returns the pluginType.
	 * 
	 * @return The pluginType
	 */
	public final PluginType getPluginType() {
		return _pluginType;
	}

	/**
	 * @return Returns the version.
	 */
	public String getVersion() {
		return _version;
	}

	/**
	 * Initializes itself basing on given row from resultSet.
	 * 
	 * @param resultSet
	 * @throws PlatformException
	 */
	public void load(ResultSet resultSet) throws PlatformException {
		try {
			_pluginID = resultSet.getInt("plugin_id");
			_pluginName = resultSet.getString("plugin_name");
			_location = resultSet.getString("location");
			_info = resultSet.getString("plugin_info");
			String type = resultSet.getString("plugin_type");
			if (type.equals(PluginType.LOCAL.getDBType())) {
				_pluginType = PluginType.LOCAL;
			} else if (type.equals(PluginType.REMOTE.getDBType())) {
				_pluginType = PluginType.REMOTE;
			} else {
				throw new PlatformException("Invalid plugin type:" + type);
			}
			_cDate = resultSet.getDate("c_date");
			_lmDate = resultSet.getDate("lm_date");
		} catch (SQLException e) {
			LOGGER.fatal("Cannot load result!!", e);
			throw new DBException("Cannot load result!", e);
		}
	}

	/**
	 * Saves itself in data base. If already exists in database performs update
	 * otherwise inserts new record. Returns current id if update was executed
	 * or new id in case of insert.
	 * 
	 * @return unique id
	 * @throws DBException
	 */
	public int save() throws DBException {
//		SQLUpdate update = new SQLUpdate(TABLE_NAME);
//		update.addValue("plugin_name", _pluginName);
//		update.addValue("location", _location.toString());
//		update.addValue("plugin_type", _pluginType.getDBType());
//		if (_info != null) {
//			update.addValue("plugin_info", _info);
//		}
//		if (_cDate == null) {
//			_cDate = new Date(System.currentTimeMillis());
//			update.addValue("c_date", _cDate);
//		}
//		update.addValue("lm_date", new Date(System.currentTimeMillis()));
//		try {
//			_pluginID = _dbManager.insertOrUpdate(update, PRIMARY_KEY,
//					_pluginID, GEN_NAME);
//		} catch (SQLException e) {
//			_cDate = null;
//			LOGGER.fatal("Cannot save! Exception was thrown!", e);
//			throw new DBException("Cannot save!", e);
//		}
//		return _pluginID;
		throw new UnsupportedOperationException(
				"Method PluginInfo.save() is not implemented yet!");
	}

	public void setInfo(String info) throws PlatformException {
		_info = info;
	}

	/**
	 * @param input
	 *            The input to set.
	 */
	public void setInput(String input) {
		_input = input;
	}

	/**
	 * @param location
	 *            The location to set.
	 */
	public void setLocation(String location) {
		_location = location;
	}

	/**
	 * @param output
	 *            The output to set.
	 */
	public void setOutput(String output) {
		_output = output;
	}

	/**
	 * @param pluginID
	 *            The pluginID to set.
	 */
	public void setPluginID(int pluginID) {
		_pluginID = pluginID;
	}

	public void setPluginName(String pluginName) {
		_pluginName = pluginName;
	}

	/**
	 * Set the value of pluginType field.
	 * 
	 * @param pluginType
	 *            The pluginType to set
	 */
	public final void setPluginType(PluginType pluginType) {
		_pluginType = pluginType;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(String version) {
		_version = version;
	}

	@Override
	public String toString() {
		return "" + _pluginID + "," + _pluginName + "," + _location + ","
				+ _info;
	}

	// used by Hibernate only
	@SuppressWarnings("unused")
	private void setPluginId(long pluginId)
	{
		_pluginID = pluginId;
	}

	public static enum PluginType {
		LOCAL("L"), REMOTE("R");

		private String _type;

		PluginType(String type) {
			_type = type;
		}

		String getDBType() {
			return _type;
		}
	}
}
