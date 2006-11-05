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

package salomon.engine.controller.gui.viewer.spread;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.Messages;
import salomon.engine.database.DBManager;
import salomon.util.gui.Utils;

/**
 * Class represents an universal component, which can show table. It allows to
 * define columns to display values from DB and filters, to simplify searching.
 */
public abstract class AbstractSearchSpread extends AbstractSpread
{
    //    private static final SoftResourceBundle BUNDLE = BundleManager.getResourceBundle("farm.gui.common");

    private static final Logger LOGGER = Logger.getLogger(AbstractSearchSpread.class);

    protected JButton _btnSearch;

    protected Map<String, String> _columnNamesMap;

    protected JPanel _filterPanel;

    protected LinkedList<FilterField> _filters;

    protected Collection<String> _searchConditions;

    public AbstractSearchSpread(DBManager dbManager)
    {
        super(dbManager);
        _filters = new LinkedList<FilterField>();
        _searchConditions = new LinkedList<String>();
        _columnNamesMap = new HashMap<String, String>();

        _btnSearch = new JButton(Messages.getString("BTN_SEARCH"));
        _btnSearch.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                search();
            }
        });
        _filterPanel = new JPanel();
        _filterPanel.setLayout(new BoxLayout(_filterPanel, BoxLayout.LINE_AXIS));

        this.add(_filterPanel, BorderLayout.NORTH);
        this.initFilters();
        _filterPanel.add(Box.createHorizontalGlue());
        _filterPanel.add(_btnSearch);

        initColumnMapping();
    }

    public abstract void initFilters();

    /**
     * Set the value of columnNamesMap field.
     * @param columnNamesMap The columnNamesMap to set
     */
    public void setColumnNamesMap(Map<String, String> columnNamesMap)
    {
        _columnNamesMap = columnNamesMap;
    }

    protected void addFilteredField(String title, String columnName)
    {
        FilterField filter = new FilterField(title, columnName);
        _filters.add(filter);
        _filterPanel.add(filter);
        _filterPanel.add(Box.createHorizontalStrut(5));
    }

    protected void addSearchCondition(String condition)
    {
        _searchConditions.add(condition);
    }

    protected void clearSearchConditions()
    {
        _searchConditions.clear();
    }

    protected void initColumnMapping()
    {
        // empty by default
    }

    protected void search()
    {
        _select.clearConditions();
        for (FilterField filter : _filters) {
            if (!filter.isEmpty()) {
                _select.addCondition(filter.getCondition());
            }
        }
        for (String condition : _searchConditions) {
            _select.addCondition(condition);
        }
        ResultSet resultSet = null;
        try {
            resultSet = _dbManager.select(_select);
            _table = Utils.createResultTable(resultSet);
            // remap columns
            if (_columnNamesMap != null) {
                _table.remapColumnNames(_columnNamesMap);
            }
            _scrTablePane.setViewportView(_table);
        } catch (SQLException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage("ERR_QUERY_EXEC_ERROR");
        }
    }

    private class FilterField extends JComponent
    {

        private String _columnName;

        private JLabel _lblTitle;

        private JTextField _txtColumn;

        public FilterField(String title, String columnName)
        {
            _columnName = columnName;
            _lblTitle = new JLabel(title);
            _txtColumn = new JTextField();

            Dimension dim = new Dimension(50, 20);
            // _lblTitle.setMinimumSize(dim);
            // _lblTitle.setMaximumSize(dim);
            _lblTitle.setPreferredSize(dim);

            // _txtColumn.setMinimumSize(dim);
            // _txtColumn.setMaximumSize(dim);
            _txtColumn.setPreferredSize(dim);

            this.setLayout(new GridLayout(2, 1));
            this.add(_lblTitle);
            this.add(_txtColumn);
        }

        public String getColumnName()
        {
            return _columnName;
        }

        public String getCondition()
        {
            String condition = _txtColumn.getText();
            String operator = null;
            // if there is '%' in condition, it means that LIKE word shoud be
            // used
            if (condition.indexOf('%') >= 0) {
                operator = " LIKE ";
            } else {
                operator = " = ";
            }
            return _columnName + operator + "'" + condition + "'";
        }

        public boolean isEmpty()
        {
            return "".equals(_txtColumn.getText().trim());
        }
    }
}
