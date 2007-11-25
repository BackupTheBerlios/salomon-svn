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

public final class DomainManager implements IDomainManager
{

    private static final Logger LOGGER = Logger.getLogger(DomainManager.class);

    /**
     * 
     * @uml.property name="_currentSolution"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private IDomain _currentDomain;

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

    public DomainManager(IManagerEngine managerEngine, DBManager manager)
    {
        _managerEngine = managerEngine;
        _dbManager = manager;
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.domain.IDomainManager#addDomain(salomon.platform.domain.IDomain)
     */
    public void addDomain(IDomain domain) throws PlatformException
    {
        try {
            //FIXME:
//            DomainInfo domainInfo = (DomainInfo) domain.getInfo();
//
//            // saving domain
//            domainInfo.save();
//            _dbManager.commit();

        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new PlatformException(e.getLocalizedMessage());
        }
        _currentDomain = domain;
    }

    /**
     * @see salomon.engine.domain.IDomainManager#createDomain()
     */
    public IDomain createDomain() throws PlatformException
    {
//        IDomain result = new Domain((ManagerEngine) _managerEngine,
//                _dbManager);
//        _currentDomain = result;
//        return result;
        //FIXME;
        throw new UnsupportedOperationException(
                "Method DomainManager.createDomain() not implemented yet!");
    }

    public boolean exists(String domainName)
    {
        boolean exists = false;
        SQLSelect select = new SQLSelect();
//FIXME:        select.addTable(DomainInfo.TABLE_NAME);
        select.addCondition("domain_name =", domainName);
        try {
            exists = _dbManager.existsSelect(select);
        } catch (SQLException e) {
            throw new PlatformException(e.getLocalizedMessage());
        }
        return exists;
    }

    public IDomain getCurrentDomain() throws PlatformException
    {
        return _currentDomain;
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
     * @see salomon.engine.domain.IDomainManager#getDomain(java.lang.String)
     */
    public IDomain getDomain(int id) throws PlatformException
    {
        IDomain domain = this.createDomain();
        // loading plugin information
        // building query
        SQLSelect select = new SQLSelect();
//FIXME:        select.addTable(DomainInfo.TABLE_NAME);
        select.addCondition("domain_id =", id);
        ResultSet resultSet = null;
        try {
            resultSet = _dbManager.select(select);
            resultSet.next();
            // loading domain
            //FIXME:((Domain) domain).getInfo().load(resultSet);

            // one row should be found, if found more, the first is got
            if (resultSet.next()) {
                LOGGER.warn("TOO MANY ROWS");
            }
            _dbManager.closeResultSet(resultSet);
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        LOGGER.debug("domain: " + domain);
        LOGGER.info("Domain successfully loaded.");

        // setting current domain
        _currentDomain = domain;
        return _currentDomain;
    }

    /**
     * @throws PlatformException 
     * @see salomon.engine.domain.IDomainManager#getDomains()
     */
    public IDomain[] getDomains() throws PlatformException
    {

        SQLSelect select = new SQLSelect();
        //FIXME: select.addTable(DomainInfo.TABLE_NAME);
        // executing query
        ResultSet resultSet = null;

        ArrayList<IDomain> domainsArrayList = new ArrayList<IDomain>();

        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                IDomain domain = this.createDomain();
                //FIXME: domain.getInfo().load(resultSet);
                domainsArrayList.add(domain);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        Domain[] domainsArray = new Domain[domainsArrayList.size()];

        domainsArray = domainsArrayList.toArray(domainsArray);
        return domainsArray;
    }

}
