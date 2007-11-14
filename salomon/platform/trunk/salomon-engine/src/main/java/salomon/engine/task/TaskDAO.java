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

package salomon.engine.task;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 */
public final class TaskDAO extends HibernateDaoSupport implements ITaskDAO
{
    /**
     * @see salomon.engine.task.ITaskDAO#save(salomon.engine.task.Task)
     */
    public void save(Task task)
    {
        getHibernateTemplate().saveOrUpdate(task);
    }

    /**
     * @see salomon.engine.task.ITaskDAO#remove(salomon.engine.task.Task)
     */
    public void remove(Task task)
    {
        getHibernateTemplate().delete(task);
    }

    /**
     * @see salomon.engine.task.ITaskDAO#getTasks()
     */
    @SuppressWarnings("unchecked")
    public Task[] getTasks()
    {
        List list = getHibernateTemplate().find("from Task");
        return (Task[]) list.toArray(new Task[list.size()]);
    }

    /**
     * @see salomon.engine.task.ITaskDAO#getTask(java.lang.Long)
     */
    public Task getTask(Long id)
    {
        List list = getHibernateTemplate().find(
                "from Task t where t.taskId = ?", new Long[]{id});
        return (Task) (list.size() == 0 ? null : list.get(0));

    }

    /**
     * @see salomon.engine.task.ITaskDAO#getTask(java.lang.String)
     */
    public Task getTask(String taskName)
    {
        List list = getHibernateTemplate().find(
                "from Task t where t.taskName = ?", new String[]{taskName});
        return (Task) (list.size() == 0 ? null : list.get(0));
    }
}
