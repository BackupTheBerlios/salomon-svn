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

import org.apache.log4j.Logger;

import salomon.engine.DAOContext;
import salomon.engine.platform.IManagerEngine;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlatformUtil;

public final class DomainManager implements IDomainManager {

	private static final Logger LOGGER = Logger.getLogger(DomainManager.class);

	private IDomainDAO _domainDAO = (IDomainDAO) DAOContext
			.getBean("domainDAO");
	/**
	 * 
	 * @uml.property name="_managerEngine"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private IManagerEngine _managerEngine;

	public DomainManager() {
		// empty body
	}

	@Deprecated
	public DomainManager(IManagerEngine managerEngine) {
		_managerEngine = managerEngine;
	}

	/**
	 * @throws PlatformException
	 * @see salomon.engine.domain.IDomainManager#addDomain(salomon.platform.domain.IDomain)
	 */
	public void addDomain(IDomain domain) throws PlatformException {
		_domainDAO.save(domain);
	}

	/**
	 * @see salomon.engine.domain.IDomainManager#createDomain()
	 */
	public IDomain createDomain() throws PlatformException {
		// IDomain result = new Domain((ManagerEngine) _managerEngine,
		// _dbManager);
		// _currentDomain = result;
		// return result;
		// FIXME;
		return new Domain();
	}

	public boolean exists(String domainName) {
		// FIXME: implement using Hibernate
		boolean exists = false;
		// SQLSelect select = new SQLSelect();
		// //FIXME: select.addTable(DomainInfo.TABLE_NAME);
		// select.addCondition("domain_name =", domainName);
		// try {
		// exists = _dbManager.existsSelect(select);
		// } catch (SQLException e) {
		// throw new PlatformException(e.getLocalizedMessage());
		// }
		return exists;
	}

	public IDomain getCurrentDomain() throws PlatformException {
		throw new UnsupportedOperationException(
				"Method DomainManager.getCurrentDomain() is not implemented yet!");
	}

	public IPlatformUtil getPlaftormUtil() {
		return _managerEngine.getPlatformUtil();
	}

	/**
	 * @throws PlatformException
	 * @see salomon.engine.domain.IDomainManager#getDomain(java.lang.String)
	 */
	public IDomain getDomain(long id) throws PlatformException {
		IDomain domain = _domainDAO.getDomain(id);

		LOGGER.debug("domain: " + domain);
		LOGGER.info("Domain successfully loaded.");

		return domain;
	}

	/**
	 * @throws PlatformException
	 * @see salomon.engine.domain.IDomainManager#getDomains()
	 */
	public IDomain[] getDomains() throws PlatformException {
		return _domainDAO.getDomains();
	}

	public IDomain getDomain(String domainName) throws PlatformException {
		IDomain domain = _domainDAO.getDomain(domainName);

		LOGGER.debug("domain: " + domain);
		LOGGER.info("Domain successfully loaded.");

		return domain;
	}

	public void remove(IDomain domain) {
		_domainDAO.remove(domain);
	}

}
