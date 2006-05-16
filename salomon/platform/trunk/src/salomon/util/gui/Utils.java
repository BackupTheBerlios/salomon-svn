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

package salomon.util.gui;

import java.awt.Component;
import java.awt.Point;
import java.awt.Window;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import org.apache.log4j.Logger;

import salomon.engine.Messages;

/**
 * Class supply some useful methods used in GUI. All public methods are static
 * to simplyfy usage.
 */
public final class Utils
{
    private Component _parent;

    private Point getCenterLocationImpl(Window window)
    {
        Point location = new Point();
        location.x = _parent.getLocation().x
                + (_parent.getSize().width - window.getWidth()) / 2;
        location.y = _parent.getLocation().y
                + (_parent.getSize().height - window.getHeight()) / 2;
        return location;
    }

    private void setParentImpl(Component parent)
    {
        _parent = parent;
    }

    private void showErrorMessageImpl(String msg)
    {
        JOptionPane.showMessageDialog(_parent, msg,
                Messages.getString("TIT_ERROR"), JOptionPane.ERROR_MESSAGE);
    }

    private void showInfoMessageImpl(String msg)
    {
        JOptionPane.showMessageDialog(_parent, msg,
                Messages.getString("TIT_INFO"), JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean showQuestionMessageImpl(String title, String msg)
    {
        int retVal = JOptionPane.showConfirmDialog(_parent, msg, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        return (retVal == JOptionPane.YES_OPTION);
    }

    private boolean showWarningMessageImpl(String msg)
    {
        int retVal = JOptionPane.showConfirmDialog(_parent, msg,
                Messages.getString("TIT_WARN"), JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        return (retVal == JOptionPane.YES_OPTION);
    }

    /**
     * Creates JTable basing on given collection of data.
     * 
     * @param allData collection of data
     * @return JTable representing given data
     */
    public static JTable createResultTable(Collection<Object[]> allData)
    {
        String[] columnNames = null;
        Object[][] data = null;

        int rowCount = 0;
        for (Object[] row : allData) {
            if (rowCount == 0) {
                columnNames = Arrays.asList(row).toArray(new String[row.length]);
                // creating matrix for data
                data = new Object[allData.size() - 1][columnNames.length];
            } else {
                data[rowCount - 1] = row;
            }
            rowCount++;
        }

        LOGGER.info("rowCount " + rowCount);

        DBDataTable table = new DBDataTable(data, columnNames);
        return table;
    }

    /**
     * Method creates JTable representing given result set.
     * 
     * @param resultSet result of SQL query
     * @return table representing given result set.
     * @throws SQLException
     */
    public static JTable createResultTable(ResultSet resultSet)
            throws SQLException
    {
        JTable table = createResultTable(getDataFromResultSet(resultSet));
        return table;
    }

    public static Point getCenterLocation(Window window)
    {
        return getInstance().getCenterLocationImpl(window);
    }

    /**
     * Processes given result set. Returns Collection, which first element is an
     * array of column names and a selected rows as next elements
     * 
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static Collection getDataFromResultSet(ResultSet resultSet)
            throws SQLException
    {
        LinkedList<Object[]> allData = new LinkedList<Object[]>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        // getting column names
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = metaData.getColumnLabel(i + 1);
        }
        allData.add(columnNames);
        // getting data
        int size = 0;
        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            int i = 0;
            for (; i < columnCount; i++) {
                row[i] = resultSet.getObject(i + 1);
            }
            allData.add(row);
            size++;
        }

        // printing result
        StringBuilder buffer = new StringBuilder(512);
        int rowCount = 0;
        buffer.append("\n");
        for (Object[] row : allData) {
            for (int i = 0; i < row.length; i++) {
                buffer.append(row[i] + "|"); //$NON-NLS-1$
            }
            if (rowCount == 0) {
                buffer.append("\n=============================================\n"); //$NON-NLS-1$
            } else {
                buffer.append("\n"); //$NON-NLS-1$   
            }
            rowCount++;
        }
        LOGGER.info(buffer);

        return allData;
    }

    /**
     * Sets parent component. It is used to display messages relativly to it
     * 
     * @param parent parent component
     */
    public static void setParent(Component parent)
    {
        getInstance().setParentImpl(parent);
    }

    /**
     * Shows an error message
     * 
     * @param msg text of the message
     */
    public static void showErrorMessage(String msg)
    {
        getInstance().showErrorMessageImpl(msg);
    }

    /**
     * Shows an information message
     * 
     * @param msg text of the message
     */
    public static void showInfoMessage(String msg)
    {
        getInstance().showInfoMessageImpl(msg);
    }

    /**
     * Shows a question message
     * 
     * @param title title of message
     * @param msg text of the message
     */
    public static boolean showQuestionMessage(String title, String msg)
    {
        return getInstance().showQuestionMessageImpl(title, msg);
    }

    /**
     * Shows a warning message
     * 
     * @param msg text of the message
     */
    public static boolean showWarningMessage(String msg)
    {
        return getInstance().showWarningMessageImpl(msg);
    }

    private static Utils getInstance()
    {
        if (_instance == null) {
            _instance = new Utils();
        }
        return _instance;
    }

    /**
     * 
     * @uml.property name="_instance"
     * @uml.associationEnd multiplicity="(0 1)"
     */
    private static Utils _instance;

    private static final Logger LOGGER = Logger.getLogger(Utils.class);
}