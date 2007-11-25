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

import java.util.HashSet;
import java.util.Set;

import salomon.engine.project.IProject;
import salomon.engine.project.Project;
import salomon.platform.IDataEngine;
import salomon.platform.exception.PlatformException;

public class Domain implements IDomain
{
    private Long _domainId;

    private String _domainName;

    private Set<Project> _projectSet;

    public Domain()
    {
        _projectSet = new HashSet<Project>();
    }

    public void addProject(IProject project)
    {
        ((Project) project).setDomain(this);
        _projectSet.add((Project) project);
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
        // TODO: optimize the search
        IProject project = null;
        for (Project p : _projectSet) {
            if (projectId == p.getProjectId()) {
                project = p;
                break;
            }
        }
        return project;
    }

    public IProject getProject(String projectName)
    {
        // TODO: optimize the search
        IProject project = null;
        for (Project p : _projectSet) {
            if (projectName.equals(p.getProjectName())) {
                project = p;
                break;
            }
        }
        return project;
    }

    public IProject[] getProjects()
    {
        return _projectSet.toArray(new Project[_projectSet.size()]);
    }

    @Override
    public int hashCode()
    {
        return _domainId.hashCode();
    }

    /**
     * @see salomon.engine.domain.IDomain#removeProject(salomon.engine.project.IProject)
     */
    public void removeProject(IProject project)
    {
        _projectSet.remove(project);
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
     * TODO: add comment.
     * @return
     */
    // used by Hibernate only
    @SuppressWarnings("unused")
    private Set<Project> getProjectSet()
    {
        return _projectSet;
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

    /**
     * Set the value of projects field.
     * @param projects The projects to set
     */
    // used by Hibernate only
    @SuppressWarnings("unused")
    private void setProjectSet(Set<Project> projects)
    {
        _projectSet = projects;
    }
}
