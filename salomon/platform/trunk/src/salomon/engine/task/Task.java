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

package salomon.engine.task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import salomon.engine.database.DBManager;
import salomon.engine.database.IDBSupporting;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.plugin.IPlugin;
import salomon.plugin.IResult;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleStruct;

import salomon.platform.exception.PlatformException;

/**
 * Represents task which may be executed. It is an implementation of ITask
 * interface.
 */
public final class Task implements ITask, IDBSupporting
{

	private String _info;

	private String _name;

	private IPlugin _plugin;

	private int _projectID;

	private IResult _result;

	private ISettings _settings;

	private String _status;

	private int _taskID;

	public Task()
	{
		_taskID = 0;
		_projectID = 0;
		_status = ACTIVE;
	}

	/**
	 * Removes itself from database. After successsful finish object should be
	 * destroyed. This method deletes records only from 'tasks' table. It has
	 * some constraints (some foreign keys), which should by taken into account
	 * by TaskManager.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean delete() throws SQLException, ClassNotFoundException
	{
		SQLDelete delete = new SQLDelete(TABLE_NAME);
		delete.addCondition("task_id =", _taskID);
		return (DBManager.getInstance().delete(delete) > 0);
	}

	/**
	 * @return Returns the name.
	 */
	public String getName()
	{
		return _name;
	}

	/**
	 * @return Returns the _plugin.
	 */
	public IPlugin getPlugin()
	{
		return _plugin;
	}

	/**
	 * @return Returns the projectID.
	 */
	public int getProjectID()
	{
		return _projectID;
	}

	/**
	 * @return Returns the _result.
	 */
	public IResult getResult()
	{
		IResult result = _result;
		if (_result == null) {
			result = _plugin.getResultComponent().getDefaultResult();
		}
		return result;
	}

	/**
	 * @return Returns the _settings.
	 */
	public ISettings getSettings()
	{
		return _settings;
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus()
	{
		return _status;
	}

	/**
	 * @return Returns the taksId.
	 */
	public int getTaskId()
	{
		return _taskID;
	}

	/**
	 * Initializes itself basing on given row from resultSet. Before calling
	 * this method ISettings object must be set.
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public void load(ResultSet resultSet) throws SQLException
	{
		_taskID = resultSet.getInt("task_id");
		_projectID = resultSet.getInt("project_id");
		// it is not neccessery to set plugin id
		_name = resultSet.getString("task_name");
		_info = resultSet.getString("task_info");
		// setting has to be set
		String settings = resultSet.getString("plugin_settings");
		ByteArrayInputStream bis = new ByteArrayInputStream(settings.getBytes());
		_settings = (ISettings) XMLSerializer.deserialize(bis);
		// TODO: support result loading?
		_status = resultSet.getString("status");
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
		SQLUpdate update = new SQLUpdate(TABLE_NAME);
		update.addValue("project_id", _projectID);
		update.addValue("plugin_id", _plugin.getDescription().getPluginID());
		update.addValue("task_name", _name);
		if (_info != null) {
			update.addValue("task_info", _info);
		}
		// let it happen?
		if (_settings != null) {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			XMLSerializer.serialize((SimpleStruct) _settings, bos);
			update.addValue("plugin_settings", bos.toString());
		}
		if (_result != null) {
			update.addValue("plugin_result", _result.resultToString());
		}
		update.addValue("status", _status);
		// updating start/stop time depanding on status
		if (_status == REALIZATION) {
			update.addValue("start_time", new Time(System.currentTimeMillis()));
		} else if (_status == FINISHED || _status == ERROR) {
			update.addValue("stop_time", new Time(System.currentTimeMillis()));
		}
		update.addValue("lm_date", new Date(System.currentTimeMillis()));
		_taskID = DBManager.getInstance().insertOrUpdate(update, "task_id",
				_taskID, GEN_NAME);
		return _taskID;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this._name = name;
	}

	/**
	 * @param plugin The plugin to set.
	 */
	public void setPlugin(IPlugin plugin)
	{
		_plugin = plugin;
	}

	/**
	 * @param projectID The projectID to set.
	 */
	public void setProjectID(int projectID)
	{
		_projectID = projectID;
	}

	/**
	 * @param result The result to set.
	 */
	public void setResult(IResult result)
	{
		_result = result;
		if (_result.isSuccessful()) {
			_status = Task.FINISHED;
		} else {
			_status = Task.ERROR;
		}
	}

	/**
	 * @param settings The settings to set.
	 */
	public void setSettings(ISettings settings)
	{
		_settings = settings;
	}
	
	

	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status)
	{
		this._status = status;
	}

	/**
	 * @param taksId The taksId to set.
	 */
	public void setTaskId(int taskId)
	{
		_taskID = taskId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return _name + " [" + _plugin + "," + _settings + "," + _result + "]";
	}

	public static final String ACTIVE = "AC";

	public static final String ERROR = "ER";

	public static final String EXCEPTION = "EX";

	public static final String FINISHED = "FI";

	public static final String REALIZATION = "RE";

	public static final String TABLE_NAME = "tasks";

	private static final String GEN_NAME = "gen_task_id";

	/**
	 * @see salomon.engine.task.ITask#setSettings(java.lang.String)
	 */
	public void setSettings(String settings) throws PlatformException
	{
		InputStream stream = new StringBufferInputStream(settings);
		_settings = (ISettings) XMLSerializer.deserialize(stream);
	}
} // end Task
