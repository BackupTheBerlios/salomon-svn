/*
 * Copyright (C) 2007 salomon-engine Team
 *
 * This file is part of salomon-engine.
 *
 * salomon-engine is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * salomon-engine is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with salomon-engine; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.engine.domain;

import salomon.engine.DAOContext;
import junit.framework.TestCase;

/**
 * 
 */
public class DomainDAOTest extends TestCase
{
    private IDomainDAO domainDAO = (IDomainDAO) DAOContext.getBean("domainDAO");

    /**
     * Test method for {@link salomon.engine.domain.DomainDAO#getDomain(java.lang.String)}.
     */
    public void testGetDomainString()
    {
        Domain domain = createDomain();
        domainDAO.save(domain);

        IDomain inserted = domainDAO.getDomain(domain.getDomainName());
        assertNotNull(inserted);
        assertEquals(domain.getDomainName(), inserted.getDomainName());
    }

    /**
     * Test method for {@link salomon.engine.domain.DomainDAO#getDomains()}.
     */
    public void testGetDomains()
    {
        Domain domain = createDomain();
        domainDAO.save(domain);

        IDomain[] domains = domainDAO.getDomains();
        assertNotNull(domains);
        assertTrue(domains.length >= 1);
    }

    /**
     * Test method for {@link salomon.engine.domain.DomainDAO#remove(salomon.engine.domain.IDomain)}.
     */
    public void testRemove()
    {
        Domain domain = createDomain();
        domain.setDomainName("domain-to-remove");
        domainDAO.save(domain);

        IDomain removed = domainDAO.getDomain(domain.getDomainId());
        assertNotNull(removed);

        domainDAO.remove(removed);
        removed = domainDAO.getDomain(domain.getDomainId());
        assertNull(removed);
    }

    /**
     * Test method for {@link salomon.engine.domain.DomainDAO#save(salomon.engine.domain.IDomain)}.
     */
    public void testSave()
    {
        Domain domain = createDomain();
        domainDAO.save(domain);

        IDomain inserted = domainDAO.getDomain(domain.getDomainId());
        assertNotNull(inserted);
        assertEquals(domain.getDomainName(), inserted.getDomainName());
    }

    private Domain createDomain()
    {
        Domain domain = new Domain();
        domain.setDomainName("test-domain");
        return domain;
    }

}
