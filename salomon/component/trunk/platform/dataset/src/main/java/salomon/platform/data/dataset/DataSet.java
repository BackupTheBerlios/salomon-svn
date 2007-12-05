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
public class DataSet implements IDataSet
{

    /**
     * @see salomon.platform.data.dataset.IDataSet#createSubset(salomon.platform.data.dataset.ICondition[])
     */
    public IDataSet createSubset(ICondition[] arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.createSubset() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#getConditions()
     */
    public ICondition[] getConditions()
    {
        throw new UnsupportedOperationException(
                "Method DataSet.getConditions() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#getName()
     */
    public String getName() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.getName() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#intersection(salomon.platform.data.dataset.IDataSet)
     */
    public IDataSet intersection(IDataSet arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.intersection() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#intersection(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet intersection(IDataSet arg0, int arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.intersection() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#minus(salomon.platform.data.dataset.IDataSet)
     */
    public IDataSet minus(IDataSet arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.minus() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#minus(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet minus(IDataSet arg0, int arg1) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.minus() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#selectData(salomon.platform.data.IColumn[], salomon.platform.data.dataset.ICondition[])
     */
    public IData selectData(IColumn[] arg0, ICondition[] arg1)
            throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.selectData() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#setName(java.lang.String)
     */
    public void setName(String arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.setName() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#union(salomon.platform.data.dataset.IDataSet)
     */
    public IDataSet union(IDataSet arg0) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.union() not implemented yet!");
    }

    /**
     * @see salomon.platform.data.dataset.IDataSet#union(salomon.platform.data.dataset.IDataSet, int)
     */
    public IDataSet union(IDataSet arg0, int arg1) throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method DataSet.union() not implemented yet!");
    }

}
