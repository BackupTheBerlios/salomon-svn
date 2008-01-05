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

import salomon.platform.exception.PlatformException;

/**
 * Manager of domains.
 * 
 * @see salomon.engine.domain.IDomain
 */
public interface IDomainManager {
	/**
	 * Stores the domain in the database.
	 * 
	 * @see #createDomain()
	 * 
	 * @param domain
	 *            The domain
	 * @throws PlatformException
	 */
	void addDomain(IDomain domain) throws PlatformException;

	/**
	 * Creates an empty domain, but doesn't add it to database. Use
	 * <code>addDomain</code> method to store this domain in the database.
	 * 
	 * @see #addDomain(IDomain)
	 * @return The empty domain
	 * @throws PlatformException
	 */
	IDomain createDomain();

	/**
	 * Returns current domain.
	 * 
	 * @return
	 * @throws PlatformException
	 */
	@Deprecated
	IDomain getCurrentDomain();

	/**
	 * Returns domain with given identifier.
	 * 
	 * @param id
	 *            identifier of domain
	 * @return The domain
	 * @throws PlatformException
	 */
	IDomain getDomain(long id) throws PlatformException;

	/**
	 * Returns domain with given name.
	 * 
	 * @param domainName
	 *            name of domain
	 * @return The domain
	 * @throws PlatformException
	 */
	IDomain getDomain(String domainName) throws PlatformException;

	/**
	 * Returns all domains.
	 * 
	 * @return The array of all domains
	 * @throws PlatformException
	 */
	IDomain[] getDomains() throws PlatformException;
	
    void remove(IDomain domain);
}
