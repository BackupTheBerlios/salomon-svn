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

import salomon.engine.project.IProject;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

public class Domain implements IDomain
{
    private Long _domainId;

    private String _domainName;

    public void addProject(IProject agent)
    {
        throw new UnsupportedOperationException(
                "Method Domain.addProject() not implemented yet!");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Domain) {
            Domain domain = (Domain) obj;
            return _domainId.equals(domain.getDomainId());
        }
        return false;
    }

    public IDataEngine getDataEngine() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method Domain.getDataEngine() not implemented yet!");
    }

    /**
     * Returns the domainId.
     * @return The domainId
     */
    public Long getDomainId()
    {
        return _domainId;
    }

    public String getDomainName()
    {
        return _domainName;
    }

    public IProject getProject(long projectId)
    {
        throw new UnsupportedOperationException(
                "Method Domain.getProject() not implemented yet!");
    }

    public IProject getProject(String projectName)
    {
        throw new UnsupportedOperationException(
                "Method Domain.getProject() not implemented yet!");
    }

    public IProject[] getProjects()
    {
        throw new UnsupportedOperationException(
                "Method Domain.getProjects() not implemented yet!");
    }

    @Override
    public int hashCode()
    {
        return _domainId.hashCode();
    }

    public void removeProject(IProject project)
    {
        throw new UnsupportedOperationException(
                "Method Domain.removeProject() not implemented yet!");
    }

    /**
     * Set the value of domainName field.
     * @param domainName The domainName to set
     */
    public void setDomainName(String domainName)
    {
        _domainName = domainName;
    }

    /**
     * Set the value of domainId field.
     * @param domainId The domainId to set
     */
    // used by Hibernate to generate id
    @SuppressWarnings("unused")
    private void setDomainId(Long domainId)
    {
        _domainId = domainId;
    }
}
