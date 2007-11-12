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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.IManagerEngine;
import salomon.engine.platform.ManagerEngine;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

public final class SolutionManager implements IDomainManager
{

    private static final Logger LOGGER = Logger.getLogger(SolutionManager.class);

    /**
     * 
     * @uml.property name="_currentSolution"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IDomain _currentSolution;

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
    private IManagerEngine _managerEngine;

    public SolutionManager(IManagerEngine managerEngine, DBManager manager)
    {
        _managerEngine = managerEngine;
        _dbManager = manager;
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.domain.IDomainManager#addSolution(salomon.platform.solution.IDomain)
     */
    public void addSolution(IDomain solution) throws PlatformException
    {
        try {
            SolutionInfo solutionInfo = (SolutionInfo) solution.getInfo();

            // saving solution
            solutionInfo.save();
            _dbManager.commit();

        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        _currentSolution = solution;
    }

    /**
     * @see salomon.engine.domain.IDomainManager#createSolution()
     */
    public IDomain createSolution() throws PlatformException
    {
        IDomain result = new Solution((ManagerEngine) _managerEngine,
                _dbManager);
        _currentSolution = result;
        return result;
    }

    public boolean exists(String solutionName)
    {
        boolean exists = false;
        SQLSelect select = new SQLSelect();
        select.addTable(SolutionInfo.TABLE_NAME);
        select.addCondition("solution_name =", solutionName);
        try {
            exists = _dbManager.existsSelect(select);
        } catch (SQLException e) {
            throw new PlatformException(e.getLocalizedMessage());
        }
        return exists;
    }

    public IDomain getCurrentSolution() throws PlatformException
    {
        return _currentSolution;
    }

    public DBManager getDBManager()
    {
        return _dbManager;
    }

    public IPlatformUtil getPlaftormUtil()
    {
        return _managerEngine.getPlatformUtil();
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.domain.IDomainManager#getSolution(java.lang.String)
     */
    public IDomain getSolution(int id) throws PlatformException
    {
        IDomain solution = this.createSolution();
        // loading plugin information
        // building query
        SQLSelect select = new SQLSelect();
        select.addTable(SolutionInfo.TABLE_NAME);
        select.addCondition("solution_id =", id);
        ResultSet resultSet = null;
        try {
            resultSet = _dbManager.select(select);
            resultSet.next();
            // loading solution
            ((Solution) solution).getInfo().load(resultSet);

            // one row should be found, if found more, the first is got
            if (resultSet.next()) {
                LOGGER.warn("TOO MANY ROWS");
            }
            _dbManager.closeResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        LOGGER.debug("solution: " + solution);
        LOGGER.info("Solution successfully loaded.");

        // setting current solution
        _currentSolution = solution;
        return _currentSolution;
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.domain.IDomainManager#getSolutions()
     */
    public IDomain[] getSolutions() throws PlatformException
    {

        SQLSelect select = new SQLSelect();
        select.addTable(SolutionInfo.TABLE_NAME);
        // executing query
        ResultSet resultSet = null;

        ArrayList<IDomain> solutionsArrayList = new ArrayList<IDomain>();

        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                IDomain solution = this.createSolution();
                solution.getInfo().load(resultSet);
                solutionsArrayList.add(solution);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        Solution[] solutionsArray = new Solution[solutionsArrayList.size()];

        solutionsArray = solutionsArrayList.toArray(solutionsArray);
        return solutionsArray;
    }

}