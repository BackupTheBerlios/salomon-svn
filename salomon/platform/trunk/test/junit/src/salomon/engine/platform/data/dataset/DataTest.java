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

package salomon.engine.platform.data.dataset;

import java.sql.ResultSet;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import salomon.TestObjectFactory;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.SolutionInfo;
import salomon.platform.data.dataset.IData;

public class DataTest extends TestCase
{

    private DBManager _manager;

    public DataTest() throws Exception
    {
        _manager = TestObjectFactory.getDbManager();
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.Data.getData()'
     */
    public void testGetData() throws Exception
    {
        LOGGER.info("DataTest.testGetData()");
        SQLSelect select = new SQLSelect();
        select.addTable(SolutionInfo.TABLE_NAME);

        ResultSet resultSet = _manager.select(select);

        IData data = new Data(resultSet);

        while (data.next()) {
            Object[] dataArray = data.getData();
            StringBuilder dataString = new StringBuilder();
            for (Object d : dataArray) {
                dataString.append("|");
                dataString.append(d);
            }
            LOGGER.debug(dataString);
        }
        data.close();
    }

    /*
     * Test method for 'salomon.engine.platform.data.dataset.Data.getData(IColumn)'
     */
    public void testGetDataIColumn()
    {

    }

    private static final Logger LOGGER = Logger.getLogger(DataTest.class);

}
