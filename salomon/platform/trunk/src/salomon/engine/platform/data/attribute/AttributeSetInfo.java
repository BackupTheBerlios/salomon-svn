/*
 * Copyright (C) 2006 Salomon Team
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

package salomon.engine.platform.data.attribute;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.database.queries.SQLUpdate;
import salomon.platform.IInfo;
import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AttributeSetInfo implements IInfo
{

    public static final String GEN_NAME = "gen_attributeset_id";

    public static final String ITEMS_TABLE_NAME = "attributeset_items";

    public static final String TABLE_NAME = "attributesets";

    private static final Logger LOGGER = Logger.getLogger(AttributeSetInfo.class);

    private AttributeManager _attributeManager;

    private int _attributeSetID;

    private Date _cDate;

    private DBManager _dbManager;

    private List<IAttributeDescription> _descriptions;

    private ExternalDBManager _externalDBManager;

    private String _info;

    private Date _lmDate;

    private String _name;

    private int _solutionID;

    AttributeSetInfo(AttributeManager attributeManager, DBManager dbManager,
            ExternalDBManager externalDBManager)
    {
        _attributeManager = attributeManager;
        _dbManager = dbManager;
        _externalDBManager = externalDBManager;
        _descriptions = new LinkedList<IAttributeDescription>();
    }

    public boolean delete() throws PlatformException, DBException
    {
        SQLDelete delete = new SQLDelete();
        // deleting data set
        delete.setTable(TABLE_NAME);
        delete.addCondition("attributeset_id =", _attributeSetID);
        int rows;
        try {
            rows = _dbManager.delete(delete);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException("Cannot delete!", e);
        }

        LOGGER.debug("rows deleted: " + rows);
        return (rows > 0);
    }

    /**
     * Returns the attributeSetID.
     * @return The attributeSetID
     */
    public final int getAttributeSetID()
    {
        return _attributeSetID;
    }

    public Date getCreationDate() throws PlatformException
    {
        return _cDate;
    }

    /**
     * Returns the descriptions.
     * @return The descriptions
     */
    public final IAttributeDescription[] getDescriptions()
    {
        return _descriptions.toArray(new IAttributeDescription[_descriptions.size()]);
    }

    public int getId()
    {
        return _attributeSetID;
    }

    /**
     * Returns the info.
     * @return The info
     */
    public final String getInfo()
    {
        return _info;
    }

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
     * @return Returns the solutionID.
     */
    public int getSolutionID()
    {
        return _solutionID;
    }

    public void load(ResultSet resultSet) throws DBException
    {
        // do not load solution_id - it is set while creating attribute set
        try {
            _attributeSetID = resultSet.getInt("attributeset_id");
            _name = resultSet.getString("attributeset_name");
            _info = resultSet.getString("attributeset_info");
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException("Cannot load attribute set!", e);
        }
    }

    public void loadDescriptions(ResultSet resultSet) throws SQLException,
            PlatformException
    {

        String type = resultSet.getString("attribute_type");
        String name = resultSet.getString("attribute_name");
        String tableName = resultSet.getString("table_name");
        String columnName = resultSet.getString("column_name");

        IAttributeDescription description = _attributeManager.createAttributeDescription(
                name, tableName, columnName, type);
        _descriptions.add(description);
    }

    public int save() throws PlatformException, DBException
    {
        // removing old items
        SQLDelete delete = new SQLDelete();
        delete.setTable(ITEMS_TABLE_NAME);
        delete.addCondition("attributeset_id = ", _attributeSetID);
        int rows;
        try {
            rows = _dbManager.delete(delete);
        } catch (SQLException e) {
            LOGGER.fatal("!", e);
            throw new DBException("Cannot save!", e);
        }
        LOGGER.debug("rows deleted: " + rows);

        // saving header
        SQLUpdate update = new SQLUpdate(TABLE_NAME);
        if (_name != null) {
            update.addValue("attributeset_name", _name);
        }
        if (_info != null) {
            update.addValue("attributeset_info", _info);
        }

        if (_attributeSetID == 0) {
            update.addValue("c_date", new Date(System.currentTimeMillis()));
        }

        update.addValue("solution_id", _solutionID);
        update.addValue("lm_date", new Date(System.currentTimeMillis()));

        try {
            _attributeSetID = _dbManager.insertOrUpdate(update,
                    "attributeset_id", _attributeSetID, GEN_NAME);
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save!", e);
        }

        try {
            // saving items
            for (IAttributeDescription description : _descriptions) {
                // FIXME: why it can be null??? 
                if (description != null) {
                    SQLInsert insert = new SQLInsert(ITEMS_TABLE_NAME);
                    insert.addValue("attributeset_id", _attributeSetID);
                    insert.addValue("attribute_name", description.getName());
                    insert.addValue("attribute_type",
                            description.getType().getDBString());
                    insert.addValue("table_name",
                            description.getColumn().getTable().getName());
                    insert.addValue("column_name",
                            description.getColumn().getName());
                    LOGGER.debug("insert: " + insert.getQuery());
                    // TODO: remove the paremeter -- the item doesn't have to be generated
                    _dbManager.insert(insert, "attributeset_item_id");
                }
            }
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot save!", e);
        }

        return _attributeSetID;
    }

    /**
     * Set the value of descriptions field.
     * @param descriptions The descriptions to set
     */
    public final void setDescriptions(IAttributeDescription[] descriptions)
    {
        if (descriptions != null) {
            _descriptions = Arrays.asList(descriptions);
        }
    }

    /**
     * Set the value of name field.
     * @param name The name to set
     */
    public final void setName(String name)
    {
        _name = name;
    }

    /**
     * Set the value of solutionID field.
     * @param solutionID The solutionID to set
     */
    public final void setSolutionID(int solutionID)
    {
        _solutionID = solutionID;
    }

    @Override
    public String toString()
    {
        String info = _attributeSetID + " " + _solutionID;
        info += (_name == null ? "" : _name);
        info += (_name == null ? "" : " " + _info);
        info += _descriptions;
        return info;
    }
}
