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

import salomon.platform.data.IColumn;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public class DataSetManager implements IDataSetManager
{

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#add(salomon.platform.data.dataset.IDataSet)
     */
    public void add(IDataSet arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.add() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createAndCondition(salomon.platform.data.dataset.ICondition, salomon.platform.data.dataset.ICondition[])
     */
    public ICondition createAndCondition(ICondition arg0, ICondition... arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createAndCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createCondition(java.lang.String)
     */
    public ICondition createCondition(String arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createEqualsCondition(salomon.platform.data.IColumn, java.lang.Object)
     */
    public ICondition createEqualsCondition(IColumn arg0, Object arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createEqualsCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createGreaterCondition(salomon.platform.data.IColumn, java.lang.Object)
     */
    public ICondition createGreaterCondition(IColumn arg0, Object arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createGreaterCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createLowerCondition(salomon.platform.data.IColumn, java.lang.Object)
     */
    public ICondition createLowerCondition(IColumn arg0, Object arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createLowerCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#createOrCondition(salomon.platform.data.dataset.ICondition, salomon.platform.data.dataset.ICondition[])
     */
    public ICondition createOrCondition(ICondition arg0, ICondition... arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.createOrCondition() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#getAll()
     */
    public IDataSet[] getAll() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.getAll() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#getDataSet(int)
     */
    public IDataSet getDataSet(int arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.getDataSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#getDataSet(java.lang.String)
     */
    public IDataSet getDataSet(String arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.getDataSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#getMainDataSet()
     */
    public IDataSet getMainDataSet() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.getMainDataSet() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSetManager#remove(salomon.platform.data.dataset.IDataSet)
     */
    public void remove(IDataSet arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSetManager.remove() not implemented yet!");
    }

}
