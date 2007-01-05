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

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;

/**
 * 
 */
public class AgentConfigManager implements IAgentConfigManager
{
    private static final Logger LOGGER = Logger.getLogger(AgentConfigManager.class);

    private DBManager _dbManager;

    public AgentConfigManager(DBManager dbManager)
    {
        _dbManager = dbManager;
    }

    /**
     * @see salomon.engine.agentconfig.IAgentConfigManager#addAgentConfig(salomon.engine.agentconfig.IAgentConfig)
     */
    public void addAgentConfig(IAgentConfig config)
    {
        try {
            config.getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
    }

    public IAgentConfig createAgentConfig()
    {
        return new AgentConfig(_dbManager);
    }

    /**
     * @see salomon.engine.agentconfig.IAgentConfigManager#getAgentConfigs(int)
     */
    public IAgentConfig[] getAgentConfigs(int projectId)
    {
        return getAgentConfigs(projectId, -1);
    }

    private IAgentConfig[] getAgentConfigs(int projectId, int configId)
    {
        SQLSelect select = new SQLSelect();
        select.addTable(AgentConfigInfo.TABLE_NAME);
        if (projectId > 0) {
            select.addCondition("project_id =", projectId);
        }
        if (configId > 0) {
            select.addCondition(AgentConfigInfo.PRIMARY_KEY + " =", configId);
        }

        ResultSet resultSet = null;
        ArrayList<IAgentConfig> agentConfigArrayList = new ArrayList<IAgentConfig>();

        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                AgentConfig agentConfig = (AgentConfig) this.createAgentConfig();
                AgentConfigInfo agentConfigInfo = (AgentConfigInfo) agentConfig.getInfo();
                agentConfigInfo.load(resultSet);

                // parsing agent configuration
                ByteArrayInputStream bis = new ByteArrayInputStream(
                        agentConfigInfo.getConfiguration().getBytes());
                IObject config = XMLSerializer.deserialize(bis);
                agentConfig.setConfig(config);

                agentConfigArrayList.add(agentConfig);
            }
            _dbManager.closeResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        IAgentConfig[] agentConfigArray = new IAgentConfig[agentConfigArrayList.size()];
        agentConfigArray = agentConfigArrayList.toArray(agentConfigArray);

        return agentConfigArray;
    }

    /**
     * @see salomon.engine.agentconfig.IAgentConfigManager#removeAgentConfig(salomon.engine.agentconfig.IAgentConfig)
     */
    public boolean removeAgentConfig(IAgentConfig config)
    {
        SQLDelete delete = new SQLDelete(AgentConfigInfo.TABLE_NAME);
        delete.addCondition(AgentConfigInfo.PRIMARY_KEY + " =",
                config.getInfo().getId());
        boolean success = false;
        try {
            _dbManager.delete(delete);
            _dbManager.commit();
            success = true;
        } catch (SQLException e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return success;
    }

    public IAgentConfig getAgentConfig(int configId)
    {
        IAgentConfig[] configs = getAgentConfigs(-1, configId);
        IAgentConfig config = configs.length > 0 ? configs[0] : null;
        return config;
    }

}
