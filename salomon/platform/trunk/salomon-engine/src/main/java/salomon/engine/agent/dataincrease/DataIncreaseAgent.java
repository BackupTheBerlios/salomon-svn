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

package salomon.engine.agent.dataincrease;

import java.io.ByteArrayInputStream;

import org.apache.log4j.Logger;

import salomon.agent.IAgentDecisionComponent;
import salomon.agent.IAgentProcessingComponent;
import salomon.agent.IConfigComponent;
import salomon.engine.agent.AbstractAgent;
import salomon.engine.agent.AgentInfo;
import salomon.engine.platform.data.DBMetaData;
import salomon.engine.platform.serialization.XMLSerializer;
import salomon.engine.project.IProject;
import salomon.platform.IDataEngine;
import salomon.platform.data.ITable;
import salomon.platform.serialization.IStruct;

/**
 * 
 */
public final class DataIncreaseAgent extends AbstractAgent
{
    static final String TRESHOLD = "treshold";

    private static final Logger LOGGER = Logger.getLogger(DataIncreaseAgent.class);

    private static final int SLEEPTING_TIME = 5000;

    private ConfigComponent _configComponent;

    private boolean _stopped;

    public DataIncreaseAgent(AgentInfo agentInfo)
    {
        super(agentInfo);
        _configComponent = new ConfigComponent();
        _stopped = false;
    }

    public IConfigComponent getConfigComponent()
    {
        updateConfigComponent();
        return _configComponent;
    }

    /**
     * @see salomon.agent.IAgent#start(IProject)
     */
    public void start(IProject project)
    {
//FIXME:        
//        LOGGER.info("DataIncreaseAgent.start()");
//        updateConfigComponent();
//        int treshold = ((IInteger) ((IStruct) _configComponent.getConfig()).getField(TRESHOLD)).getValue();
//        IDataEngine dataEngine = project.getProjectManager().getDomain().getDataEngine();
//
//        int initialRowNo = getAllRowsCount(dataEngine);
//        LOGGER.debug("Initial row no: " + initialRowNo);
//
//        _stopped = false;
//        while (!_stopped) {
//            int currentRowNo = getAllRowsCount(dataEngine);
//            LOGGER.debug("Current row no: " + currentRowNo);
//            if ((Math.abs(currentRowNo - initialRowNo)) > (treshold / 100 * initialRowNo)) {
//                LOGGER.info("Restarting project...");
//                project.start();
//                initialRowNo = currentRowNo;
//            }
//            try {
//                Thread.sleep(SLEEPTING_TIME);
//            } catch (InterruptedException e) {
//                LOGGER.info("Thread interrupted");
//            }
//        }
    }

    /**
     * @see salomon.agent.IAgent#stop()
     */
    public void stop()
    {
        LOGGER.info("DataIncreaseAgent.stop()");
        _stopped = true;
    }

    private int getAllRowsCount(IDataEngine dataEngine)
    {
        LOGGER.info("DataIncreaseAgent.getAllRowsCount()");
        int count = 0;
        DBMetaData metaData = (DBMetaData) dataEngine.getMetaData();
        ITable[] tables = metaData.getTables();
        for (ITable table : tables) {
            count += metaData.getCount(table);
        }
        return count;
    }

    // FIXME: redesign the way of confiugring agents
    private void updateConfigComponent()
    {
//FIXME:        
//        String strConfig = ((AgentConfigInfo) _agentConfig.getInfo()).getConfiguration();
//        if (strConfig != null && strConfig.trim().length() > 0) {
//            ByteArrayInputStream bis = new ByteArrayInputStream(
//                    strConfig.getBytes());
//            IStruct struct = XMLSerializer.deserialize(bis);
//            _configComponent.update(struct);
//        }
    }

    public IAgentDecisionComponent getAgentDecisionComponent()
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.getAgentDecisionComponent() not implemented yet!");
    }

    public IAgentProcessingComponent getAgentProcessingComponent()
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.getAgentProcessingComponent() not implemented yet!");
    }

    public void setAgentDecisionComponent(
            IAgentDecisionComponent agentDecisionComponent)
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.setAgentDecisionComponent() not implemented yet!");
    }

    public void setAgentProcessingComponent(
            IAgentProcessingComponent agentProcessingComponent)
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.setAgentProcessingComponent() not implemented yet!");
    }

    public void start()
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.start() not implemented yet!");
    }

    public String getAgentName()
    {
        throw new UnsupportedOperationException("Method DataIncreaseAgent.getName() not implemented yet!");
    }
}
