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

package salomon.engine.domain;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.platform.DataEngine;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.project.IProjectManager;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

//FIXME: merge with Domain
public final class Solution 
{

    /**
     * 
     * @uml.property name="_dataEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IDataEngine _dataEngine;

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    /**
     * 
     * @uml.property name="_managerEngine"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private ManagerEngine _managerEngine;

    /**
     * 
     * @uml.property name="_solutionInfo"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private SolutionInfo _solutionInfo;

    /**
     * Constructor should be used only by createSolution() in DomainManager.
     *  
     * @param managerEngine
     * @throws PlatformException 
     */
    protected Solution(ManagerEngine managerEngine, DBManager manager)
    {
        _managerEngine = managerEngine;
        _dbManager = manager;
        _solutionInfo = new SolutionInfo(_dbManager);
    }

    /**
     * @see salomon.engine.domain.IDomain#getDataEngine()
     */
    public IDataEngine getDataEngine() throws PlatformException
    {
        if (_dataEngine == null) {
            // creating data engine
            // with connection to external data base
            _dataEngine = this.createDataEngine(_solutionInfo);
        }
        return _dataEngine;
    }

    public SolutionInfo getInfo()
    {
        return _solutionInfo;
    }

    /**
     * @see salomon.engine.domain.IDomain#getProjectManager()
     */
    public IProjectManager getProjectManager()
    {
        return _managerEngine.getProjectManager();
    }

    @Override
    public String toString()
    {
        return (_solutionInfo == null) ? "" : _solutionInfo.getName();
    }

    private IDataEngine createDataEngine(SolutionInfo solutionInfo)
            throws PlatformException
    {
        ExternalDBManager externalDBManager = new ExternalDBManager();
        String host = solutionInfo.getHost();
        String dataBasePath = solutionInfo.getPath();
        String user = solutionInfo.getUser();
        String passwd = solutionInfo.getPasswd();

        try {
            externalDBManager.connect(host, dataBasePath, user, passwd);
        } catch (SQLException e) {
            LOGGER.fatal("Cannot connect to external data base", e);
            throw new PlatformException(e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        IDataEngine dataEngine = null;
        try {
            dataEngine = new DataEngine(_dbManager, externalDBManager,
                    new ShortSolutionInfo(_solutionInfo.getId()));
        } catch (SQLException e) {
            LOGGER.fatal("Cannot get MetaData", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        return dataEngine;
    }

    private static final Logger LOGGER = Logger.getLogger(Solution.class);

}
