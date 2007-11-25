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

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import salomon.engine.project.Project;

/**
 * 
 */
public class DomainDAO extends HibernateDaoSupport implements IDomainDAO
{

    /**
     * @see salomon.engine.domain.IDomainDAO#getDomain(java.lang.Long)
     */
    public IDomain getDomain(Long id)
    {
        List list = getHibernateTemplate().find(
                "from Domain d where d.domainId = ?", new Long[]{id});
        return (IDomain) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.domain.IDomainDAO#getDomain(java.lang.String)
     */
    public IDomain getDomain(String domainName)
    {
        List list = getHibernateTemplate().find(
                "from Domain d where d.domainName = ?",
                new String[]{domainName});
        return (IDomain) (list.size() == 0 ? null : list.get(0));

    }

    /**
     * @see salomon.engine.domain.IDomainDAO#getDomains()
     */
    @SuppressWarnings("unchecked")
    public IDomain[] getDomains()
    {
        List list = getHibernateTemplate().find("from Domain");
        return (Domain[]) list.toArray(new Domain[list.size()]);
    }

    /**
     * @see salomon.engine.domain.IDomainDAO#remove(salomon.engine.domain.IDomain)
     */
    public void remove(IDomain domain)
    {
        getHibernateTemplate().delete(domain);
    }

    /**
     * @see salomon.engine.domain.IDomainDAO#save(salomon.engine.domain.IDomain)
     */
    public void save(IDomain domain)
    {
        getHibernateTemplate().saveOrUpdate(domain);
    }

}
