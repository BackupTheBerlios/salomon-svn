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

package salomon.engine.controller.gui.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import salomon.engine.database.DBManager;
import salomon.engine.database.queries.SQLSelect;

import salomon.util.gui.Utils;

/**
 * Class represents universal component, which can show table.
 */
abstract class ObjectViewer extends JPanel
{

	/**
	 * 
	 * @uml.property name="_select"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	protected SQLSelect _select;

	private JButton _btnSearch;

	/**
	 * 
	 * @uml.property name="_dbManager"
	 * @uml.associationEnd multiplicity="(0 1)"
	 */
	private DBManager _dbManager;

	private JPanel _filterPanel;

	private LinkedList<FilterField> _filters;

	private JTable _table;

	private JScrollPane _scrTablePane;

	protected ObjectViewer(DBManager dbManager)
	{
		_dbManager = dbManager;
		_filters = new LinkedList<FilterField>();
		_select = new SQLSelect();

		_btnSearch = new JButton("Search");
		_btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				search();
			}
		});
		_filterPanel = new JPanel();
		_filterPanel.setLayout(new BoxLayout(_filterPanel, BoxLayout.LINE_AXIS));
		_scrTablePane = new JScrollPane();

		this.setLayout(new BorderLayout());
		this.add(_filterPanel, BorderLayout.NORTH);
		this.add(_scrTablePane, BorderLayout.CENTER);

		this.initList();
		_filterPanel.add(Box.createHorizontalGlue());
		_filterPanel.add(_btnSearch);
		this.setPreferredSize(new Dimension(400, 400));
	}

	/**
	 * In this method select object should be filled 
	 * and apropriate column and filters should be added.
	 * 
	 */
	public abstract void initList();

	protected void addColumn(String title, String columnName)
	{
		_select.addColumn(columnName + " " + title);
	}

	protected void addFilteredField(String title, String columnName)
	{
		FilterField filter = new FilterField(title, columnName);
		_filters.add(filter);
		_filterPanel.add(filter);
	}

	private void search()
	{
		_select.clearConditions();
		for (FilterField filter : _filters) {
			if (!filter.isEmpty()) {
				_select.addCondition(filter.getCondition());
			}
		}
		ResultSet resultSet = null;
		try {
			resultSet = _dbManager.select(_select);
			_table = Utils.createResultTable(resultSet);
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

		private String _title;

		private JTextField _txtColumn;

		public FilterField(String title, String columnName)
		{
			_title = title;
			_columnName = columnName;
			_lblTitle = new JLabel(title);
			_txtColumn = new JTextField();

			Dimension dim = new Dimension(100, 20);
			//			_lblTitle.setMinimumSize(dim);
			//			_lblTitle.setMaximumSize(dim);
			_lblTitle.setPreferredSize(dim);

			//			_txtColumn.setMinimumSize(dim);
			//			_txtColumn.setMaximumSize(dim);
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
			// if there is '%' in condition, it means that LIKE word shoud be used
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

	private static final Logger LOGGER = Logger.getLogger(ObjectViewer.class);
}
