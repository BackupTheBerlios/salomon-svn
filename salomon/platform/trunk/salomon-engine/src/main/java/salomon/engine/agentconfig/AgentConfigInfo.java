/*
 * Copyright (C) 2007 Salomon Team
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

package salomon.engine.agentconfig;

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
 * 
 */
public class AgentConfigInfo implements IInfo
{
    public static final String PRIMARY_KEY = "config_id";

    public static final String TABLE_NAME = "agent_config";

    private static final String GEN_NAME = "gen_agent_config_id";

    private static final Logger LOGGER = Logger.getLogger(AgentConfigInfo.class);

    private int _agentId;

    private Date _cDate;

    private int _configId;

    private String _configuration;

    private DBManager _dbManager;

    private Date _lmDate;

    private int _projectId;

    /**
     * @param dbManager
     */
    public AgentConfigInfo(DBManager dbManager)
    {
        _dbManager = dbManager;
    }

    /**
     * @see salomon.platform.IInfo#delete()
     */
    public boolean delete() throws PlatformException
    {
        SQLDelete delete = new SQLDelete(TABLE_NAME);
        delete.addCondition(PRIMARY_KEY + " =", _configId);
        try {
            return (_dbManager.delete(delete) > 0);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot delete agent config info!", e);
        }
    }

    /**
     * Returns the agentId.
     * @return The agentId
     */
    public final int getAgentId()
    {
        return _agentId;
    }

    /**
     * Returns the configuration.
     * @return The configuration
     */
    public final String getConfiguration()
    {
        return _configuration;
    }

    /**
     * @see salomon.platform.IInfo#getCreationDate()
     */
    public Date getCreationDate() throws PlatformException
    {
        return _cDate;
    }

    /**
     * @see salomon.platform.IInfo#getId()
     */
    public int getId()
    {
        return _configId;
    }

    /**
     * @see salomon.platform.IInfo#getLastModificationDate()
     */
    public Date getLastModificationDate() throws PlatformException
    {
        return _lmDate;
    }

    /**
     * Returns the projectId.
     * @return The projectId
     */
    public final int getProjectId()
    {
        return _projectId;
    }

    /**
     * @see salomon.platform.IInfo#load(java.sql.ResultSet)
     */
    public void load(ResultSet resultSet) throws PlatformException
    {
        try {
            _configId = resultSet.getInt("config_id");
            _agentId = resultSet.getInt("agent_id");
            _projectId = resultSet.getInt("project_id");
            _configuration = resultSet.getString("configuration");
            _cDate = resultSet.getDate("c_date");
            _lmDate = resultSet.getDate("lm_date");
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot load agent config info!", e);
        }
    }

    /**
     * @see salomon.platform.IInfo#save()
     */
    public int save() throws PlatformException
    {
        SQLUpdate update = new SQLUpdate(TABLE_NAME);
        update.addValue("agent_id", _agentId);
        update.addValue("project_id", _projectId);
        update.addValue("configuration", _configuration);

        // if projectID == -1, the new project should be created
        // so cDate must be changed
        if (_cDate == null || _configId == 0) {
            _cDate = new Date(System.currentTimeMillis());
            update.addValue("c_date", _cDate);
        }
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        try {
            _configId = _dbManager.insertOrUpdate(update, PRIMARY_KEY,
                    _configId, GEN_NAME);
        } catch (SQLException e) {
            _cDate = null;
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save agent config info!", e);
        }

        return _configId;
    }

    /**
     * Set the value of agentId field.
     * @param agentId The agentId to set
     */
    public final void setAgentId(int agentId)
    {
        _agentId = agentId;
    }

    /**
     * Set the value of configId field.
     * @param configId The configId to set
     */
    public final void setConfigId(int configId)
    {
        _configId = configId;
    }

    /**
     * Set the value of configuration field.
     * @param configuration The configuration to set
     */
    public final void setConfiguration(String configuration)
    {
        _configuration = configuration;
    }

    /**
     * Set the value of projectId field.
     * @param projectId The projectId to set
     */
    public final void setProjectId(int projectId)
    {
        _projectId = projectId;
    }

    @Override
    public String toString()
    {
        return "" + _configId + ", " + _agentId + ", " + _projectId + ", "
                + _configuration;
    }

}
