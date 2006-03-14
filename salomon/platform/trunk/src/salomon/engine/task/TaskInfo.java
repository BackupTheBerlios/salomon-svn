/*
 * Copyright (C) 2005 Salomon Team
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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;
import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

public final class TaskInfo implements IInfo
{

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    private String _info;

    private String _name;

    private int _pluginID;

    private int _projectID;

    private String _result;

    private String _settings;

    private String _status;

    private int _taskID;

    private int _taskNr;

    private Date _cDate;

    private Date _lmDate;

    public TaskInfo(DBManager dbManager)
    {
        _dbManager = dbManager;
        _taskID = 0;
        _projectID = 0;
        _pluginID = 0;
    }

    /**
     * Removes itself from database. After successsful finish object should be
     * destroyed. This method deletes records only from 'tasks' table. It has
     * some constraints (some foreign keys), which should by taken into account
     * by TaskManager.
     * 
     * @return
     * @throws DBException
     */
    public boolean delete() throws DBException
    {
        SQLDelete delete = new SQLDelete(TABLE_NAME);
        delete.addCondition("task_id =", _taskID);
        try {
            return (_dbManager.delete(delete) > 0);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot delete task info!", e);
        }
    }

    public Date getCreationDate() throws PlatformException
    {
        return _cDate;
    }

    /**
     * @return Returns the taksId.
     */
    public int getId()
    {
        return _taskID;
    }

    public String getInfo() throws PlatformException
    {
        return _info;
    }

    public Date getLastModificationDate() throws PlatformException
    {
        return _lmDate;
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return _name;
    }

    public int getPluginID()
    {
        return _pluginID;
    }

    /**
     * @return Returns the projectID.
     */
    public int getProjectID()
    {
        return _projectID;
    }

    /**
     * @return the settings
     */
    public String getSettings()
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
     * Returns the taskNr.
     * 
     * @return The taskNr
     */
    public int getTaskNr()
    {
        return _taskNr;
    }

    /**
     * Initializes itself basing on given row from resultSet. Before calling
     * this method ISettings object must be set.
     * 
     * @param resultSet
     * @throws SQLException
     */
    public void load(ResultSet resultSet) throws DBException
    {
        try {
            // it is not neccessery to set plugin id
            // plugin is loaded from TaskManager
            _taskID = resultSet.getInt("task_id");
            _projectID = resultSet.getInt("project_id");
            _taskNr = resultSet.getInt("task_nr");
            _name = resultSet.getString("task_name");
            _info = resultSet.getString("task_info");
            // setting has to be set
            _settings = resultSet.getString("plugin_settings");
            // TODO: support result loading?
            // _result = resultSet.getString("plugin_result");
            _status = resultSet.getString("status");
            _cDate = resultSet.getDate("c_date");
            _lmDate = resultSet.getDate("lm_date");
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot load task info!", e);
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
    public int save() throws DBException
    {
        SQLUpdate update = new SQLUpdate(TABLE_NAME);
        update.addValue("project_id", _projectID);
        update.addValue("plugin_id", _pluginID);
        update.addValue("task_nr", _taskNr);
        update.addValue("task_name", _name);
        if (_info != null) {
            update.addValue("task_info", _info);
        }
        if (_settings != null) {
            update.addValue("plugin_settings", _settings);
        }
        if (_result != null) {
            update.addValue("plugin_result", _result);
        }
        update.addValue("status", _status);
        // updating start/stop time depanding on status
        if (_status == REALIZATION) {
            update.addValue("start_time", new Time(System.currentTimeMillis()));
        } else if (_status == FINISHED || _status == ERROR) {
            update.addValue("stop_time", new Time(System.currentTimeMillis()));
        }

        if (_cDate == null) {
            _cDate = new Date(System.currentTimeMillis());
            update.addValue("c_date", _cDate);
        }
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        try {
            _taskID = _dbManager.insertOrUpdate(update, "task_id", _taskID,
                    GEN_NAME);
        } catch (SQLException e) {
            _cDate = null;
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save task info!", e);
        }
        return _taskID;
    }

    public void setInfo(String info) throws PlatformException
    {
        _info = info;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        _name = name;
    }

    public void setPluginID(int pluginID)
    {
        _pluginID = pluginID;
    }

    /**
     * @param projectID The projectID to set.
     */
    public void setProjectID(int projectID)
    {
        _projectID = projectID;
    }

    public void setResult(String result)
    {
        _result = result;
    }

    public void setSettings(String settings)
    {
        _settings = settings;
    }

    /**
     * @param status The status to set.
     */
    public void setStatus(String status)
    {
        _status = status;
    }

    /**
     * @param taksId The taksId to set.
     */
    public void setTaskId(int taskId)
    {
        _taskID = taskId;
    }

    /**
     * Set the value of taskNr field.
     * 
     * @param taskNr The taskNr to set
     */
    public void setTaskNr(int taskNr)
    {
        _taskNr = taskNr;
    }

    public static final String ACTIVE = "AC";

    public static final String ERROR = "ER";

    public static final String EXCEPTION = "EX";

    public static final String FINISHED = "FI";

    public static final String REALIZATION = "RE";

    public static final String TABLE_NAME = "tasks";

    public static final String VIEW_NAME = "tasks_view";

    private static final String GEN_NAME = "gen_task_id";

    private static final Logger LOGGER = Logger.getLogger(TaskInfo.class);
}
