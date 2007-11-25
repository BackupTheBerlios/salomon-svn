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

package salomon.engine.project;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 */
public final class ProjectDAO extends HibernateDaoSupport
        implements IProjectDAO
{

    /**
     * @see salomon.engine.project.IProjectDAO#getProject(java.lang.Long)
     */
    public IProject getProject(Long id)
    {
        List list = getHibernateTemplate().find(
                "from Project p where p.projectId = ?", new Long[]{id});
        return (IProject) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.project.IProjectDAO#getProject(java.lang.String)
     */
    public IProject getProject(String projectName)
    {
        List list = getHibernateTemplate().find(
                "from Project p where p.projectName = ?", new String[]{projectName});
        return (IProject) (list.size() == 0 ? null : list.get(0));
    }

    /**
     * @see salomon.engine.project.IProjectDAO#getProjects()
     */
    @SuppressWarnings("unchecked")
    public IProject[] getProjects()
    {
        List list = getHibernateTemplate().find("from Project");
        return (Project[]) list.toArray(new Project[list.size()]);
    }

    /**
     * @see salomon.engine.project.IProjectDAO#remove(salomon.engine.project.IProject)
     */
    public void remove(IProject project)
    {
        getHibernateTemplate().delete(project);
    }

    /**
     * @see salomon.engine.project.IProjectDAO#save(salomon.engine.project.IProject)
     */
    public void save(IProject project)
    {
        getHibernateTemplate().saveOrUpdate(project);
    }

}
