/*
 * Copyright (C) 2007 Salomon Team
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

package salomon.engine.agent;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import salomon.platform.IInfo;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AgentInfo implements IInfo
{
    public static final String PRIMARY_KEY = "agent_id";

    public static final String TABLE_NAME = "agents";

    private static final Logger LOGGER = Logger.getLogger(AgentInfo.class);

    private String _agentClass;

    private int _agentID;

    private Date _cDate;

    private String _info;

    private Date _lmDate;

    private String _name;

    /**
     * @see salomon.platform.IInfo#delete()
     */
    public boolean delete() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method AgentInfo.delete() cannot be invoked!");
    }

    /**
     * Returns the agentClass.
     * @return The agentClass
     */
    public final String getAgentClass()
    {
        return _agentClass;
    }

    /**
     * @see salomon.platform.IInfo#getCreationDate()
     */
    public Date getCreationDate() throws PlatformException
    {
        return _cDate;
    }

    /**
     * Returns the agentID.
     * @return The agentID
     */
    public final int getId()
    {
        return _agentID;
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final String getInfo()
    {
        return _info;
    }

    /**
     * @see salomon.platform.IInfo#getLastModificationDate()
     */
    public Date getLastModificationDate() throws PlatformException
    {
        return _lmDate;
    }

    /**
     * Returns the name.
     * @return The name
     */
    public final String getName()
    {
        return _name;
    }

    /**
     * @see salomon.platform.IInfo#load(java.sql.ResultSet)
     */
    public void load(ResultSet resultSet) throws PlatformException
    {
        try {
            _agentID = resultSet.getInt("agent_id");
            _name = resultSet.getString("agent_name");
            _info = resultSet.getString("agent_info");
            _agentClass = resultSet.getString("agent_class");
            _cDate = resultSet.getDate("c_date");
            _lmDate = resultSet.getDate("lm_date");
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot load project info!", e);
        }
    }

    /**
     * @see salomon.platform.IInfo#save()
     */
    public int save() throws PlatformException
    {
        throw new UnsupportedOperationException(
                "Method AgentInfo.save() cannot be invoked!");
    }

    /**
     * Set the value of agentClass field.
     * @param agentClass The agentClass to set
     */
    public final void setAgentClass(String agentClass)
    {
        _agentClass = agentClass;
    }

    /**
     * Set the value of agentID field.
     * @param agentID The agentID to set
     */
    public final void setAgentID(int agentID)
    {
        _agentID = agentID;
    }

    /**
     * Set the value of cDate field.
     * @param date The cDate to set
     */
    public final void setCDate(Date date)
    {
        _cDate = date;
    }

    /**
     * Set the value of info field.
     * @param info The info to set
     */
    public final void setInfo(String info)
    {
        _info = info;
    }

    /**
     * Set the value of lmDate field.
     * @param lmDate The lmDate to set
     */
    public final void setLmDate(Date lmDate)
    {
        _lmDate = lmDate;
    }

    /**
     * Set the value of name field.
     * @param name The name to set
     */
    public final void setName(String name)
    {
        _name = name;
    }

    @Override
    public String toString()
    {
        return "" + _agentID + ": " + _name + "(" + _agentClass + ")";
    }

}
