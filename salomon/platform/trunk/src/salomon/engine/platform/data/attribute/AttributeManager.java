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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLDelete;
import salomon.engine.database.queries.SQLInsert;
import salomon.engine.database.queries.SQLSelect;
import salomon.engine.solution.ShortSolutionInfo;

import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.data.attribute.description.IDateAttributeDescription;
import salomon.platform.data.attribute.description.IEnumAttributeDescription;
import salomon.platform.data.attribute.description.IIntegerAttributeDescription;
import salomon.platform.data.attribute.description.IRealAttributeDescription;
import salomon.platform.data.attribute.description.IStringAttributeDescription;
import salomon.platform.exception.DBException;
import salomon.platform.exception.PlatformException;

/**
 * Not used yet.
 */
public final class AttributeManager implements IAttributeManager
{

    private DBManager _dbManager;

    private Hashtable<IEnumAttributeDescription, Integer> _invokeLaterEnumTypes = new Hashtable<IEnumAttributeDescription, Integer>();

    private boolean _restrictiveTypeCheck = true;

    public AttributeManager(DBManager dbManager, ShortSolutionInfo solutionInfo)
    {
        _dbManager = dbManager;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#add(salomon.platform.data.attribute.IAttributeSet)
     */
    public void add(IAttributeSet attributeSet) throws PlatformException
    {
        // first we insert the attribute set
        SQLInsert insertAttrSet = new SQLInsert();
        insertAttrSet.setTable(AttributeManager.ATTRIBUTE_SETS_TABLE_NAME);
        insertAttrSet.addValue("ATTRIBUTESET_NAME", attributeSet.getName());
        insertAttrSet.addValue("ATTRIBUTESET_INFO", attributeSet.getInfo());
        insertAttrSet.addValue("ATTRIBUTESET_CD", new Date(
                System.currentTimeMillis()));
        int attrSetPrimaryKey = -1;
        try {
            attrSetPrimaryKey = _dbManager.insert(insertAttrSet,
                    "ATTRIBUTESET_ID");
        } catch (Exception e) {
            LOGGER.fatal("", e);
            _dbManager.rollback();
            throw new PlatformException(e);
        }
        // second we insert attribute descriptions
        for (IAttributeDescription iD : attributeSet.getDesciptions()) {
            SQLInsert insert = new SQLInsert();
            insert.setTable(AttributeManager.ATTRIBUTES_TABLE_NAME);
            insert.addValue("ATTRIBUTESET_ID", attrSetPrimaryKey);
            insert.addValue("COLUMN_NAME", iD.getName());
            if (iD instanceof RealAttributeDescription)
                insert.addValue("COLUMN_TYPE",
                        AttributeManager.ATTRIBUTE_TYPE_REAL);
            else if (iD instanceof StringAttributeDescription)
                insert.addValue("COLUMN_TYPE",
                        AttributeManager.ATTRIBUTE_TYPE_STRING);
            else if (iD instanceof IntegerAttributeDescription)
                insert.addValue("COLUMN_TYPE",
                        AttributeManager.ATTRIBUTE_TYPE_INTEGER);
            else if (iD instanceof DateAttributeDescription)
                insert.addValue("COLUMN_TYPE",
                        AttributeManager.ATTRIBUTE_TYPE_DATE);
            else if (iD instanceof EnumAttributeDescription) {
                insert.addValue("COLUMN_TYPE",
                        AttributeManager.ATTRIBUTE_TYPE_ENUM);
            } else {
                _dbManager.rollback();
                throw new PlatformException(
                        "Unsupported object type. Storing impossible");
            }
            try {
                int attrDescPrmKey = _dbManager.insert(insert,
                        "ATTRIBUTESET_ITEM_ID");
                if (iD instanceof IEnumAttributeDescription) {
                    // if we have Enum type we also have to store its allowable
                    // values
                    IEnumAttributeDescription iE = (IEnumAttributeDescription) iD;
                    for (Object ob : iE.getPossibleValues()) {
                        SQLInsert insertAllowedValues = new SQLInsert();
                        insertAllowedValues.setTable(AttributeManager.ATTRIBUTE_SET_ITEM_ENUM_ALLOWED_VALUES);
                        insertAllowedValues.addValue("ATTRIBUTESET_ITEM_ID",
                                attrDescPrmKey);
                        insertAllowedValues.addValue("ALLOWED_VALUE_TO_STRING",
                                ob.toString());
                        // the above is a possisble weekness as the objects are
                        // transformed onto Strings but for now it seems fine :)
                        _dbManager.insert(insertAllowedValues,
                                "ALLOWED_TYPE_ID");
                    }
                }
            } catch (Exception e) {
                LOGGER.fatal("", e);
                _dbManager.rollback();
                throw new PlatformException(e);
            }

        }
        // finally commit all
        _dbManager.commit();

    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createAttributeSet(salomon.platform.data.attribute.description.IAttributeDescription[])
     */
    public IAttributeSet createAttributeSet(IAttributeDescription[] descriptions)
    {
        return new AttributeSet(descriptions, true);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createDateAttributeDescription(java.lang.String)
     */
    public IDateAttributeDescription createDateAttributeDescription(String name)
    {
        return new DateAttributeDescription(name);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createEnumAttributeDescription(java.lang.String, java.lang.Object[])
     */
    public IEnumAttributeDescription createEnumAttributeDescription(
            String name, Object[] possibleValues)
    {
        return new EnumAttributeDescription(name, possibleValues);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createIntegerAttributeDescription(java.lang.String)
     */
    public IIntegerAttributeDescription createIntegerAttributeDescription(
            String name)
    {
        return new IntegerAttributeDescription(name);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createRealAttributeDescription(java.lang.String)
     */
    public IRealAttributeDescription createRealAttributeDescription(String name)
    {
        return new RealAttributeDescription(name);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#createStringAttributeDescription(java.lang.String)
     */
    public IStringAttributeDescription createStringAttributeDescription(
            String name)
    {
        return new StringAttributeDescription(name);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#getAll()
     */
    public IAttributeSet[] getAll() throws PlatformException
    {
        Vector<IAttributeSet> attributeSets = new Vector<IAttributeSet>();

        SQLSelect select = new SQLSelect();
        select.addTable(AttributeManager.ATTRIBUTE_SETS_TABLE_NAME + " d");
        select.addTable(AttributeManager.ATTRIBUTES_TABLE_NAME + " di");
        select.addColumn("d.ATTRIBUTESET_ID");
        select.addColumn("d.ATTRIBUTESET_NAME");
        select.addColumn("d.ATTRIBUTESET_INFO");
        select.addColumn("di.ATTRIBUTESET_ITEM_ID");
        select.addColumn("di.COLUMN_NAME");
        select.addColumn("di.COLUMN_TYPE");
        select.addCondition("d.ATTRIBUTESET_ID = di.ATTRIBUTESET_ID");
        select.addOrderBy("d.ATTRIBUTESET_ID");

        ResultSet resultSet;

        int attrSetId = 1;

        try {
            resultSet = _dbManager.select(select);

            Vector<IAttributeDescription> tmpDescriptions = new Vector<IAttributeDescription>();
            int attrSetTmpId;
            while (resultSet.next()) {
                attrSetTmpId = resultSet.getInt("ATTRIBUTESET_ID");
                if (attrSetId != attrSetTmpId) {
                    // new attrSet begin in query
                    // finalize with previous
                    IAttributeDescription[] iTable = new IAttributeDescription[tmpDescriptions.size()];
                    for (int i = 0; i < tmpDescriptions.size(); i++) {
                        iTable[i] = tmpDescriptions.elementAt(i);
                    }
                    IAttributeSet iAttributeSet = this.createAttributeSet(iTable);
                    iAttributeSet.setName(resultSet.getString("ATTRIBUTESET_NAME"));
                    iAttributeSet.setInfo(resultSet.getString("ATTRIBUTESET_INFO"));
                    attributeSets.add(iAttributeSet);
                    // go with a new one
                    attrSetId = attrSetTmpId;
                    tmpDescriptions = new Vector<IAttributeDescription>();
                }
                tmpDescriptions.add(retrieveAttrDescFromDb(resultSet));
            }
            // finish with the last one
            if (tmpDescriptions.size() > 0) {
                IAttributeDescription[] iTable = new IAttributeDescription[tmpDescriptions.size()];
                for (int i = 0; i < tmpDescriptions.size(); i++) {
                    iTable[i] = tmpDescriptions.elementAt(i);
                }
                IAttributeSet iAttributeSet = this.createAttributeSet(iTable);
                iAttributeSet.setName(resultSet.getString("ATTRIBUTESET_NAME"));
                iAttributeSet.setInfo(resultSet.getString("ATTRIBUTESET_INFO"));
                attributeSets.add(iAttributeSet);
            }
            resultSet.close();
            // now late queries (query possible enum values)
            proceedWithLateQueries();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        }
        LOGGER.info("AttributeSet list successfully loaded.");
        IAttributeSet[] iArray = new IAttributeSet[attributeSets.size()];
        for (int i = 0; i < attributeSets.size(); i++) {
            iArray[i] = attributeSets.elementAt(i);
        }
        return iArray;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#getAttributeSet(salomon.platform.IUniqueId)
     */
    public IAttributeSet getAttributeSet(int id) throws PlatformException
    {
        SQLSelect select = new SQLSelect();
        select.addTable(AttributeManager.ATTRIBUTE_SETS_TABLE_NAME + " d");
        select.addTable(AttributeManager.ATTRIBUTES_TABLE_NAME + " di");
        select.addColumn("d.ATTRIBUTESET_ID");
        select.addColumn("d.ATTRIBUTESET_NAME");
        select.addColumn("d.ATTRIBUTESET_INFO");
        select.addColumn("d.INFO");
        select.addColumn("di.ATTRIBUTESET_ITEM_ID");
        select.addColumn("di.COLUMN_NAME");
        select.addColumn("di.COLUMN_TYPE");
        select.addCondition("d.ATTRIBUTESET_ID = di.ATTRIBUTESET_ID");
        select.addCondition("d.ATTRIBUTESET_ID = " + id);

        ResultSet resultSet;

        int attrSetId = -1;

        try {
            resultSet = _dbManager.select(select);

            Vector<IAttributeDescription> tmpDescriptions = new Vector<IAttributeDescription>();
            int attrSetTmpId;
            if (resultSet.next()) {
                attrSetTmpId = resultSet.getInt("ATTRIBUTESET_ID");
                if (attrSetId != attrSetTmpId) {
                    // new attrSet begin in query
                    // error situation - should NOT happen
                    throw new PlatformException("Query returned too many rows");
                }
                tmpDescriptions.add(retrieveAttrDescFromDb(resultSet));
            }
            // finish with the last one
            IAttributeSet iAttributeSet = null;
            if (tmpDescriptions.size() > 0) {
                iAttributeSet = this.createAttributeSet((IAttributeDescription[]) tmpDescriptions.toArray());
                iAttributeSet.setName(resultSet.getString("ATTRIBUTESET_NAME"));
                iAttributeSet.setInfo(resultSet.getString("ATTRIBUTESET_INFO"));

            }
            resultSet.close();
            // now late queries (query possible enum values)
            proceedWithLateQueries();
            LOGGER.info("AttributeSet successfully loaded.");
            return iAttributeSet;
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        }
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#getRestrictiveTypeCheck()
     */
    public boolean getRestrictiveTypeCheck()
    {
        return _restrictiveTypeCheck;
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#remove(salomon.platform.data.attribute.IAttributeSet)
     */
    public void remove(IAttributeSet attributeSet) throws PlatformException
    {
        // deleting data set
        // others should be deleted cascadly
        SQLDelete delete = new SQLDelete();
        delete.setTable(AttributeManager.ATTRIBUTE_SETS_TABLE_NAME);
        delete.addCondition("ATTRIBUTESET_ID =",
                attributeSet.getAttributeSetId());
        int rows;
        try {
            rows = _dbManager.delete(delete);
            _dbManager.commit();
        } catch (SQLException e) {
            LOGGER.fatal("Exception was thrown!", e);
            throw new DBException("Cannot delete!", e);
        }
        LOGGER.debug("rows deleted: " + rows);
    }

    /* (non-Javadoc)
     * @see salomon.platform.data.attribute.IAttributeManager#setRestrictiveTypeCheck(boolean)
     */
    public void setRestrictiveTypeCheck(boolean restrictiveTypeCheck)
    {
        _restrictiveTypeCheck = restrictiveTypeCheck;

    }

    private Object[] getAllowedValuesForAttrSetItem(int id)
            throws PlatformException
    {
        Vector<String> result = new Vector<String>();
        SQLSelect select = new SQLSelect();
        select.addTable(AttributeManager.ATTRIBUTE_SET_ITEM_ENUM_ALLOWED_VALUES
                + " d");
        select.addCondition("d.ALLOWED_TYPE_ID = " + id);
        select.addColumn("d.ALLOWED_VALUE_TO_STRING");
        ResultSet resultSet;
        try {
            resultSet = _dbManager.select(select);
            while (resultSet.next()) {
                result.add(resultSet.getString("ALLOWED_VALUE_TO_STRING"));
            }
            /* resultSet.close(); */
            return result.toArray();
        } catch (Exception e) {
            LOGGER.fatal("", e);
            throw new PlatformException(e);
        }
    }

    private void proceedWithLateQueries() throws PlatformException
    {
        Enumeration en = _invokeLaterEnumTypes.keys();
        while (en.hasMoreElements()) {
            IEnumAttributeDescription ie = (IEnumAttributeDescription) en.nextElement();
            for (Object ob : getAllowedValuesForAttrSetItem(_invokeLaterEnumTypes.get(ie))) {
                ie.addPossibleValue(ob);
            }
        }
    }

    private IAttributeDescription retrieveAttrDescFromDb(ResultSet resultSet)
            throws SQLException, PlatformException
    {
        int type = resultSet.getInt("COLUMN_TYPE");
        switch (type) {
            case ATTRIBUTE_TYPE_INTEGER :
                return this.createIntegerAttributeDescription(resultSet.getString("COLUMN_NAME"));
            case ATTRIBUTE_TYPE_REAL :
                return this.createRealAttributeDescription(resultSet.getString("COLUMN_NAME"));
            case ATTRIBUTE_TYPE_ENUM :
                IEnumAttributeDescription ie = this.createEnumAttributeDescription(
                        resultSet.getString("COLUMN_NAME"), null);
                _invokeLaterEnumTypes.put(ie,
                        resultSet.getInt("ATTRIBUTESET_ITEM_ID"));
                return ie;
            case ATTRIBUTE_TYPE_STRING :
                return this.createStringAttributeDescription(resultSet.getString("COLUMN_NAME"));
            case ATTRIBUTE_TYPE_DATE :
                return this.createDateAttributeDescription(resultSet.getString("COLUMN_NAME"));
            default :
                throw new PlatformException("Unsupported column type (" + type
                        + ")");
        }
    }

    public static final String ATTRIBUTE_SET_ITEM_ENUM_ALLOWED_VALUES = "ATTRIBUTESET_ENUM_ALLOWED_TYPE";

    public static final String ATTRIBUTE_SETS_TABLE_NAME = "ATTRIBUTESETS";

    public static final int ATTRIBUTE_TYPE_DATE = 4;

    public static final int ATTRIBUTE_TYPE_ENUM = 3;

    public static final int ATTRIBUTE_TYPE_INTEGER = 1;

    public static final int ATTRIBUTE_TYPE_REAL = 2;

    public static final int ATTRIBUTE_TYPE_STRING = 0;

    public static final String ATTRIBUTES_TABLE_NAME = "ATTRIBUTESET_ITEMS";

    private static final Logger LOGGER = Logger.getLogger(AttributeManager.class);

} // end AttributeManager
