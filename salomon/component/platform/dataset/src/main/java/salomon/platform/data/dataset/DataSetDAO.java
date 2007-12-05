/*
 * Copyright (C) 2007 dataset Team
 *
 * This file is part of dataset.
 *
 * dataset is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * dataset is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with dataset; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package salomon.platform.data.dataset;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 */
public class DataSetDAO extends HibernateDaoSupport implements IDataSetDAO
{
    /**
     * @see salomon.platform.data.dataset.IDataSetDAO#getDataSet(java.lang.Long)
     */
    public IDataSet getDataSet(Long id)
    {
        throw new UnsupportedOperationException(
                "Method DataSetDAO.getDataSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetDAO#getDataSet(java.lang.String)
     */
    public IDataSet getDataSet(String dataSetName)
    {
        throw new UnsupportedOperationException(
                "Method DataSetDAO.getDataSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetDAO#getDataSets()
     */
    public IDataSet[] getDataSets()
    {
        throw new UnsupportedOperationException(
                "Method DataSetDAO.getDataSets() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetDAO#remove(salomon.platform.data.dataset.IDataSet)
     */
    public void remove(IDataSet dataSet)
    {
        throw new UnsupportedOperationException(
                "Method DataSetDAO.remove() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetDAO#save(salomon.platform.data.dataset.IDataSet)
     */
    public void save(IDataSet dataSet)
    {
        throw new UnsupportedOperationException(
                "Method DataSetDAO.save() not implemented yet!");
    }

}
