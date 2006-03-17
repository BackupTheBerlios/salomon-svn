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

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;
import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Class represents plugin description.
 */
public class PluginInfo implements IInfo, Serializable
{

    private static final String GEN_NAME = "gen_plugin_id";

    private static final Logger LOGGER = Logger.getLogger(PluginInfo.class);

    public static final String TABLE_NAME = "plugins";

    public static final String VIEW_NAME = "plugins_view";

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    private String _info;

    private String _input;

    private URL _location;

    private String _name;

    private String _output;

    private int _pluginID;

    private String _version;

    private Date _cDate;

    private Date _lmDate;

    public PluginInfo(DBManager manager)
    {
        _dbManager = manager;
    }

    /**
     * @param info
     * @param name
     */
    public PluginInfo(String name, String info)
    {
        _name = name;
        _info = info;
    }

    /**
     * Removes itself from database.
     * After successsful finish object should be destroyed.
     * @throws DBException 
     */
    public boolean delete() throws DBException
    {
        SQLDelete delete = new SQLDelete(TABLE_NAME);
        delete.addCondition("plugin_id =", _pluginID);
        try {
            return (_dbManager.delete(delete) > 0);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannor delete!", e);
        }
    }

    public Date getCreationDate() throws PlatformException
    {
        return _cDate;
    }

    public int getId()
    {
        return _pluginID;
    }

    /**
     * @return Returns the description.
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

    public Date getLastModificationDate() throws PlatformException
    {
        return _lmDate;
    }

    /**
     * @return Returns the location.
     */
    public URL getLocation()
    {
        return _location;
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
     * @return Returns the pluginID.
     */
    public int getPluginID()
    {
        return _pluginID;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion()
    {
        return _version;
    }

    /**
     * Initializes itself basing on given row from resultSet.
     * 
     * @param resultSet
     * @throws PlatformException 
     */
    public void load(ResultSet resultSet) throws PlatformException
    {
        try {
            _pluginID = resultSet.getInt("plugin_id");
            _name = resultSet.getString("plugin_name");
            _location = new URL(resultSet.getString("location"));
            _info = resultSet.getString("plugin_info");
            _cDate = resultSet.getDate("c_date");
            _lmDate = resultSet.getDate("lm_date");
        } catch (SQLException e) {
            LOGGER.fatal("Cannot load result!!", e);
            throw new DBException("Cannot load result!", e);
        } catch (MalformedURLException e) {
            LOGGER.fatal("Cannot load result!!", e);
            throw new PlatformException("Cannot load result!", e);
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
        update.addValue("plugin_name", _name);
        update.addValue("location", _location.toString());
        if (_info != null) {
            update.addValue("plugin_info", _info);
        }
        if (_cDate == null) {
            _cDate = new Date(System.currentTimeMillis());
            update.addValue("c_date", _cDate);
        }
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        try {
            _pluginID = _dbManager.insertOrUpdate(update, "plugin_id",
                    _pluginID, GEN_NAME);
        } catch (SQLException e) {
            _cDate = null;
            LOGGER.fatal("Cannot save! Exception was thrown!", e);
            throw new DBException("Cannot save!", e);
        }
        return _pluginID;
    }

    public void setInfo(String info) throws PlatformException
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
     * @param location The location to set.
     */
    public void setLocation(URL location)
    {
        _location = location;
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
     * @param pluginID The pluginID to set.
     */
    public void setPluginID(int pluginID)
    {
        _pluginID = pluginID;
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(String version)
    {
        _version = version;
    }

    @Override
    public String toString()
    {
        return "" + _pluginID + "," + _name + "," + _location + "," + _info;
    }
}
