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

package salomon.engine.platform.data.attribute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.ExternalDBManager;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.ShortSolutionInfo;
import salomon.platform.data.IColumn;
import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * 
 */
public final class AttributeManager implements IAttributeManager
{

    private static final Logger LOGGER = Logger.getLogger(AttributeManager.class);

    private DBManager _dbManager;

    private ExternalDBManager _externalDBManager;

    private ShortSolutionInfo _solutionInfo;

    public AttributeManager(DBManager dbManager,
            ShortSolutionInfo solutionInfo, ExternalDBManager externalDBManager)
    {
        _dbManager = dbManager;
        _solutionInfo = solutionInfo;
        _externalDBManager = externalDBManager;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#add(salomon.platform.data.attribute.IAttributeSet)
     */
    public void add(IAttributeSet attributeSet) throws PlatformException
    {
        try {
            ((AttributeSet) attributeSet).getInfo().save();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
            throw new DBException(e);
        }
    }

    /**
     * @see salomon.platform.data.attribute.IAttributeManager#createAttributeDescription(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public IAttributeDescription createAttributeDescription(
            String attributeName, String tableName, String columnName,
            String type) throws PlatformException
    {
        IAttributeDescription description = null;

        IColumn column = null;
        try {
            column = _externalDBManager.getMetaData().getTable(tableName).getColumn(
                    columnName);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        }

        if (type != null) {
            if (AttributeType.STRING.getDBString().equals(type)) {
                description = this.createStringAttributeDescription(
                        attributeName, column);
            } else if (AttributeType.INTEGER.getDBString().equals(type)) {
                description = this.createIntegerAttributeDescription(
                        attributeName, column);
            } else if (AttributeType.ENUM.getDBString().equals(type)) {
                // FIXME: add support for enum
                //description = _attributeManager.createEnumAttributeDescription(name, column, possibleValues)
                throw new UnsupportedOperationException(
                        "Method AttributeSetInfo.loadDescriptions() for ENUM not implemented yet!");
            } else if (AttributeType.REAL.getDBString().equals(type)) {
                description = this.createRealAttributeDescription(
                        attributeName, column);
            } else if (AttributeType.DATE.getDBString().equals(type)) {
                description = this.createDateAttributeDescription(
                        attributeName, column);
            } else {
                throw new PlatformException("Invalid attribute type: " + type);
            }
        }
        return description;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createAttributeSet(salomon.platform.data.attribute.description.IAttributeDescription[])
     */
    public IAttributeSet createAttributeSet(IAttributeDescription[] descriptions)
    {
        AttributeSet attributeSet = new AttributeSet(this, descriptions,
                _dbManager, _externalDBManager);
        attributeSet.getInfo().setSolutionID(_solutionInfo.getId());
        return attributeSet;
    }

    public IAttributeDescription createDateAttributeDescription(String name,
            IColumn column)
    {
        return new DateAttributeDescription(name, column);
    }

    public IAttributeDescription createEnumAttributeDescription(String name,
            IColumn column, Object[] possibleValues)
    {
        return new EnumAttributeDescription(name, column, possibleValues);
    }

    public IAttributeDescription createIntegerAttributeDescription(String name,
            IColumn column)
    {
        return new IntegerAttributeDescription(name, column);
    }

    public IAttributeDescription createRealAttributeDescription(String name,
            IColumn column)
    {
        return new RealAttributeDescription(name, column);
    }

    public IAttributeDescription createStringAttributeDescription(String name,
            IColumn column)
    {
        return new StringAttributeDescription(name, column);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#getAll()
     */
    public IAttributeSet[] getAll() throws PlatformException
    {
        // enforcing getting all attribute sets
        return getAttributeSets(-1);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#getAttributeSet(salomon.platform.IUniqueId)
     */
    public IAttributeSet getAttributeSet(int id) throws PlatformException
    {
        IAttributeSet[] attributeSets = getAttributeSets(id);
        IAttributeSet attributeSet = attributeSets.length > 0
                ? attributeSets[0]
                : null;
        return attributeSet;
    }

    public IAttributeSet getAttributeSet(String name) throws PlatformException
    {

        SQLSelect attributeSetSelect = new SQLSelect();
        attributeSetSelect.addTable(AttributeSetInfo.TABLE_NAME);
        attributeSetSelect.addCondition("attributeset_name =", name);
        // to ensure solution_id consistency
        attributeSetSelect.addCondition("solution_id =", _solutionInfo.getId());

        ResultSet resultSet = null;
        try {
            resultSet = _dbManager.select(attributeSetSelect);

            // WARNING!
            // cannot be < 0, cause then attributeManager returns all attributesets
            int attributeSetID = 0;
            IAttributeSet attributeSet = null;
            if (resultSet.next()) {
                attributeSetID = resultSet.getInt("attributeset_id");
                // assuming it exist, cause it was return by the query above (no multiuser access supported)
                attributeSet = getAttributeSets(attributeSetID)[0];
            }

            return attributeSet;

        } catch (SQLException e) {
            LOGGER.fatal("", e);
            throw new DBException(e.getLocalizedMessage());
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.fatal("", e);
                throw new DBException(e.getLocalizedMessage());
            }
        }

    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#remove(salomon.platform.data.attribute.IAttributeSet)
     */
    public void remove(IAttributeSet attributeSet) throws PlatformException
    {
        // deleting attribute set
        // others should be deleted cascadly
        try {
            ((AttributeSet) attributeSet).getInfo().delete();
            _dbManager.commit();
        } catch (Exception e) {
            _dbManager.rollback();
            LOGGER.fatal("", e);
        }
    }

    private IAttributeSet[] getAttributeSets(int attributeSetID)
            throws PlatformException
    {
        List<IAttributeSet> attributeSets = new LinkedList<IAttributeSet>();

        SQLSelect select = new SQLSelect();
        select.addTable(AttributeSetInfo.TABLE_NAME + " a");
        select.addTable(AttributeSetInfo.ITEMS_TABLE_NAME + " ai");
        select.addColumn("a.attributeset_id");
        select.addColumn("a.attributeset_name");
        select.addColumn("a.attributeset_info");
        select.addColumn("ai.attribute_name");
        select.addColumn("ai.attribute_type");
        select.addColumn("ai.table_name");
        select.addColumn("ai.column_name");
        select.addCondition("a.attributeset_id = ai.attributeset_id");
        select.addCondition("a.solution_id = ", _solutionInfo.getId());
        // if attributeSetID >= 0 it means, that certain attribute set should be selected
        if (attributeSetID >= 0) {
            select.addCondition("a.attributeset_id = ", attributeSetID);
        }
        select.addOrderBy("a.attributeset_id");

        ResultSet resultSet = null;
        // always set default
        int firstAttributeSetID = -1;
        try {
            resultSet = _dbManager.select(select);
            AttributeSet attributeSet = null;
            while (resultSet.next()) {
                int tmpAttributeSetID = resultSet.getInt("attributeset_id");
                // when dataset_id changes, creating new DataSet object
                if (tmpAttributeSetID != firstAttributeSetID) {
                    firstAttributeSetID = tmpAttributeSetID;
                    attributeSet = (AttributeSet) this.createAttributeSet(null);
                    attributeSet.getInfo().load(resultSet);
                    attributeSets.add(attributeSet);
                }
                // loading items
                ((AttributeSetInfo) attributeSet.getInfo()).loadDescriptions(resultSet);
            }
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    LOGGER.fatal("", e);
                }
            }
        }
        LOGGER.info("AttributeSet list successfully loaded.");
        IAttributeSet[] attributeSetsArray = new IAttributeSet[attributeSets.size()];
        return attributeSets.toArray(attributeSetsArray);
    }
} // end AttributeManager
