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

package salomon.engine.project;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLUpdate;

import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

public final class ProjectInfo implements IInfo
{

    /**
     * 
     * @uml.property name="_dbManager"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private DBManager _dbManager;

    private String _environment;

    private String _info;

    private String _name;

    private int _projectID = 0;

    private int _solutionID = 0;

    public ProjectInfo(DBManager dbManager)
    {
        _dbManager = dbManager;
    }

    /**
     * Removes itself from database. After successsful finish object should be
     * destroyed.
     * 
     * @return
     * @throws DBException 
     */
    public boolean delete() throws DBException
    {
        SQLDelete delete = new SQLDelete(TABLE_NAME);
        delete.addCondition("project_id =", _projectID);
        try {
            return (_dbManager.delete(delete) > 0);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot delete project info!", e);
        }
    }

    public Date getCreationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getCreationDate() not implemented yet!");
    }

    /**
     * Returns the environment.
     * @return The environment
     */
    public String getEnvironment()
    {
        return _environment;
    }

    /**
     * @return Returns the projectID.
     */
    public int getId()
    {
        return _projectID;
    }

    /**
     * @return Returns the info.
     */
    public String getInfo()
    {
        return _info;
    }

    public Date getLastModificationDate() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method getLastModificationDate() not implemented yet!");
    }

    /**
     * @return Returns the name.
     */
    public String getName()
    {
        return _name;
    }

    public int getSolutionID()
    {
        return _solutionID;
    }

    /**
     * Initializes itself basing on given row from resultSet.
     * 
     * @param resultSet
     * @throws DBException
     */
    public void load(ResultSet resultSet) throws DBException
    {
        try {
            _projectID = resultSet.getInt("project_id");
            _solutionID = resultSet.getInt("solution_id");
            _name = resultSet.getString("project_name");
            _info = resultSet.getString("project_info");
            _environment = resultSet.getString("env");
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot load project info!", e);
        }
    }

    /**
     * Saves itself in data base. If already exists in database performs update
     * otherwise inserts new record. Returns current id if update was executed
     * or new id in case of insert.
     * 
     * @return unique id
     * @throws DBException 
     */
    public int save() throws DBException
    {
        SQLUpdate update = new SQLUpdate(TABLE_NAME);
        update.addValue("solution_id", _solutionID);
        if (_name != null) {
            update.addValue("project_name", _name);
        }
        if (_info != null) {
            update.addValue("project_info", _info);
        }
        if (_environment != null) {
            update.addValue("env", _environment);
        }
        update.addValue("lm_date", new Date(System.currentTimeMillis()));
        try {
            _projectID = _dbManager.insertOrUpdate(update, "project_id",
                    _projectID, GEN_NAME);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save project info!", e);
        }

        return _projectID;
    }

    /**
     * Set the value of environemnt field.
     * @param environment The environment to set
     */
    public void setEnvironment(String environment)
    {
        _environment = environment;
    }

    /**
     * @param info The info to set.
     */
    public void setInfo(String info)
    {
        _info = info;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * @param projectId The projectID to set.
     */
    public void setProjectID(int projectId)
    {
        _projectID = projectId;
    }

    public void setSolutionID(int solutionID)
    {
        _solutionID = solutionID;
    }

    public String toString()
    {
        return "[" + _projectID + ", " + _name + ", " + _info + "]";
    }

    public static final String TABLE_NAME = "projects";
    
    public static final String VIEW_NAME = "projects_view";

    private static final String GEN_NAME = "gen_project_id";

    private static final Logger LOGGER = Logger.getLogger(ProjectInfo.class);
}
