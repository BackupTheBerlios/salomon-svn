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

package salomon.util.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

public final class DBDataTable extends JTable
{

    private class DBDataTableModel extends DefaultTableModel
    {

        public DBDataTableModel(Object[][] data, Object[] columnNames)
        {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(DBDataTable.class);

    public DBDataTable(Object[][] data, Object[] columnNames)
    {
        setModel(new DBDataTableModel(data, columnNames));
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        // setModel(new TableSorter(new DBDataTableModel(data, columnNames),
        // getTableHeader()));

        // hiding the first column with the row ID
        //		getColumnModel().removeColumn(getColumnModel().getColumn(0));
    }

    public int getSelectedKey()
    {
        int selectedRow = getSelectedRow();
        int key = -1;
        if (selectedRow >= 0) {
            key = ((Integer) getModel().getValueAt(selectedRow, 0)).intValue();
        }
        return key;
    }
}
