/*
 * Copyright (C) 2005 Salomon Team
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

package salomon;

import org.apache.log4j.PropertyConfigurator;

import salomon.engine.database.DBManager;

import salomon.platform.exception.PlatformException;

import salomon.engine.platform.ManagerEngine;
import salomon.engine.platform.data.DBMetaData;

/**
 * 
 */
public final class TestObjectFactory
{
	public static DBMetaData getMetaData() throws Exception
	{
		if (META_DATA == null) {
			DBManager dbManager = getDbManager();
			META_DATA = dbManager.getMetaData();
		}

		return META_DATA;
	}

	/**
	 * TODO: add comment.
	 * @return
	 * @throws PlatformException
	 */
	private static DBManager getDbManager() throws PlatformException
	{
		if (DB_MANGER == null) {
			ManagerEngine engine = getManagerEngine();
			DB_MANGER = engine.getDbManager();
		}

		return DB_MANGER;
	}

	/**
	 * TODO: add comment.
	 * @return
	 * @throws PlatformException
	 */
	private static ManagerEngine getManagerEngine() throws PlatformException
	{
		if (MANAGER_ENGINE == null) {
			PropertyConfigurator.configure("log.conf"); //$NON-NLS-1$
			MANAGER_ENGINE = new ManagerEngine();
		}

		return MANAGER_ENGINE;
	}

	private static DBManager DB_MANGER;

	private static ManagerEngine MANAGER_ENGINE;

	private static DBMetaData META_DATA;
}
