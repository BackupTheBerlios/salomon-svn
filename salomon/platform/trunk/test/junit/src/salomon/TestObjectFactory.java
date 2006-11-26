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

import java.sql.ResultSet;

import org.apache.log4j.PropertyConfigurator;

import salomon.engine.Config;
import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.platform.ManagerEngine;
import salomon.engine.platform.data.DBMetaData;
import salomon.engine.plugin.PluginInfo;
import salomon.engine.project.IProject;
import salomon.engine.project.ProjectInfo;
import salomon.engine.solution.ISolution;
import salomon.engine.solution.ISolutionManager;
import salomon.engine.solution.SolutionInfo;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IPlugin;

/**
 * 
 */
public final class TestObjectFactory
{
    private static DBManager DB_MANGER;

    private static ManagerEngine MANAGER_ENGINE;

    private static DBMetaData META_DATA;

    private static ISolutionManager SOLUTION_MANAGER;

    /**
     * TODO: add comment.
     * @return
     * @throws PlatformException
     */
    public static DBManager getDbManager() throws PlatformException
    {
        if (DB_MANGER == null) {
            ManagerEngine engine = getManagerEngine();
            DB_MANGER = engine.getDbManager();
        }

        return DB_MANGER;
    }

    public static DBMetaData getMetaData() throws Exception
    {
        if (META_DATA == null) {
            DBManager dbManager = getDbManager();
            META_DATA = dbManager.getMetaData();
        }

        return META_DATA;
    }

    /**
     * Returns plugin by given name.
     * If plugin was not found, null is returend
     * 
     * @param name
     * @return plugin object with given name or null if not found in DB
     * @throws PlatformException
     */

    public static IPlugin getPlugin(String name) throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(PluginInfo.TABLE_NAME);
        select.addColumn(PluginInfo.PRIMARY_KEY);
        select.addCondition("plugin_name =", name);

        int pluginID = 0;
        IPlugin plugin = null;
        ResultSet resultSet = null;
        try {
            resultSet = getDbManager().select(select);
            if (resultSet.next()) {
                pluginID = resultSet.getInt(PluginInfo.PRIMARY_KEY);
                getDbManager().closeResultSet(resultSet);
                plugin = getManagerEngine().getPluginManager().getPlugin(
                        pluginID);
            }

        } catch (Exception e) {
            throw new PlatformException(e);
        }

        return plugin;
    }

    /**
     * Returns project by given name.
     * If project was not found, null is returend
     * 
     * @param name
     * @return project object with given name or null if not found in DB
     * @throws PlatformException
     */

    public static IProject getProject(String name) throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(ProjectInfo.TABLE_NAME);
        select.addColumn(ProjectInfo.PRIMARY_KEY);
        select.addCondition("project_name =", name);

        int projectID = 0;
        IProject project = null;
        ResultSet resultSet = null;
        try {
            resultSet = getDbManager().select(select);
            if (resultSet.next()) {
                projectID = resultSet.getInt(ProjectInfo.PRIMARY_KEY);
                getDbManager().closeResultSet(resultSet);
                project = getSolutionManager().getCurrentSolution().getProjectManager().getProject(
                        projectID);
            }

        } catch (Exception e) {
            throw new PlatformException(e);
        }
        return project;
    }

    /**
     * Returns solution by given name.
     * If solution was not found, null is returend
     * 
     * @param name
     * @return solution object with given name or null if not found in DB
     * @throws PlatformException
     */
    public static ISolution getSolution(String name) throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(SolutionInfo.TABLE_NAME);
        select.addColumn(SolutionInfo.PRIMARY_KEY);
        select.addCondition("solution_name =", name);

        int solutionID = 0;
        ISolution solution = null;
        ResultSet resultSet = null;
        try {
            resultSet = getDbManager().select(select);
            if (resultSet.next()) {
                solutionID = resultSet.getInt(SolutionInfo.PRIMARY_KEY);
                getDbManager().closeResultSet(resultSet);
                solution = getSolutionManager().getSolution(solutionID);
            }
        } catch (Exception e) {
            throw new PlatformException(e);
        }
        return solution;
    }

    public static ISolutionManager getSolutionManager()
            throws PlatformException
    {
        if (SOLUTION_MANAGER == null) {
            SOLUTION_MANAGER = getManagerEngine().getSolutionManager();
        }
        return SOLUTION_MANAGER;
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
            Config.readConfiguration();
            MANAGER_ENGINE = new ManagerEngine();
        }

        return MANAGER_ENGINE;
    }
}
