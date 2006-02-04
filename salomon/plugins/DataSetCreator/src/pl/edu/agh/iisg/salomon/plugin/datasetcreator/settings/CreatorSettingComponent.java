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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;
import salomon.platform.serialization.IObject;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleArray;
import salomon.util.serialization.SimpleString;

/**
 * @author nico
 */
public class CreatorSettingComponent implements ISettingComponent
{

	private JPanel _addConditionPanel;

	private DefaultListModel _columnListModel;

	private DefaultListModel _conditionListModel;

	private JComboBox _operations;

	private IColumn _selectedColumn;

	private JComponent _settingComponent;

	private DefaultListModel _tableListModel;

	private JTextField _txtColumnName;

	private JTextField _txtColumnValue;

	private JTextField _txtDataSetName;

	private IDataEngine _dataEngine;

	private ICondition _selectedCondition;

	private ISettings _defaultSettings;

	public CreatorSettingComponent()
	{
		_tableListModel = new DefaultListModel();
		_columnListModel = new DefaultListModel();
		_conditionListModel = new DefaultListModel();

		_txtColumnName = new JTextField();
		_txtColumnValue = new JTextField();
		_txtDataSetName = new JTextField();
		_operations = new JComboBox(new String[]{"=", "<", ">"});
	}

	/**
	 * 
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine)
	{
		if (_settingComponent == null) {
			_dataEngine = dataEngine;
			init(dataEngine.getMetaData());
			_settingComponent = createSettingComponent();
			try {
				initSettingComponent(settings, dataEngine.getMetaData());
			} catch (PlatformException e) {
				LOGGER.fatal("", e);
				// FIXME: where this error should be handled?
				// handle it by adding throws declaration to interface methods
			}
		}
		return _settingComponent;
	}

	private void initSettingComponent(ISettings settings, IMetaData metaData)
			throws PlatformException
	{
		CreatorSettings cSettings = (CreatorSettings) settings;
		SimpleString dataSetName = (SimpleString) cSettings.getField(CreatorSettings.DATA_SET_NAME);
		SimpleArray condArray = (SimpleArray) cSettings.getField(CreatorSettings.CONDITIONS);

		ICondition[] conditions = new ICondition[condArray.size()];

		// creating conditions
		int i = 0;
		IObject[] values = condArray.getValue();
		if (values != null) {
			for (IObject object : values) {
				String value = ((SimpleString) object).getValue();
				LOGGER.debug("value = " + value);
				conditions[i] = _dataEngine.getDataSetManager().createCondition(
						value);
				++i;
			}
		}

		// setting gui values
		_txtDataSetName.setText(dataSetName.getValue());
		for (ICondition condition : conditions) {
			_conditionListModel.addElement(condition);
		}
	}

	/**
	 * 
	 */
	public ISettings getDefaultSettings()
	{
		if (_defaultSettings == null) {
			_defaultSettings = new CreatorSettings();
			_defaultSettings.setField(CreatorSettings.DATA_SET_NAME,
					new SimpleString(""));
			_defaultSettings.setField(CreatorSettings.CONDITIONS,
					new SimpleArray());
		}
		return _defaultSettings;
	}

	/**
	 * 
	 */
	public ISettings getSettings()
	{
		ISettings settings = new CreatorSettings();
		// setting data seta name
		SimpleString dataSetName = new SimpleString(_txtDataSetName.getText());

		// setting conditions
		int size = _conditionListModel.getSize();
		SimpleString[] conditions = new SimpleString[size];
		for (int i = 0; i < size; ++i) {
			conditions[i] = new SimpleString(
					_conditionListModel.get(i).toString());
		}
		SimpleArray array = new SimpleArray(conditions);
		settings.setField(CreatorSettings.DATA_SET_NAME, dataSetName);
//		settings.setField(CreatorSettings.CONDITIONS, array);

		return settings;
	}

	private void addCondition()
	{
		LOGGER.info("CreatorSettingComponent.addCondition()");
		if (_selectedColumn != null) {
			StringBuilder colName = new StringBuilder();
			colName.append(_selectedColumn.getTable().getName());
			colName.append(".");
			colName.append(_selectedColumn.getName());
			_txtColumnName.setText(colName.toString());
			_txtColumnValue.setText("");
			_operations.setSelectedIndex(0);

			int result = JOptionPane.showConfirmDialog(_settingComponent,
					getAddConditionPanel(), "Choose condition",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				String value = _txtColumnValue.getText();
				if (!"".equals(value)) {
					try {
						IDataSetManager dataSetManager = _dataEngine.getDataSetManager();
						String operation = (String) _operations.getSelectedItem();
						ICondition condition = null;
						if (operation.equals("=")) {
							condition = dataSetManager.createEqualsCondition(
									_selectedColumn, value);
						} else if (operation.equals("<")) {
							condition = dataSetManager.createLowerCondition(
									_selectedColumn, value);
						} else if (operation.equals(">")) {
							condition = dataSetManager.createGreaterCondition(
									_selectedColumn, value);
						}

						// adding condition to the list
						LOGGER.debug("adding condition: " + condition);
						_conditionListModel.addElement(condition);

						// _conditionListModel.addElement(colName + "=" +
						// value);
					} catch (PlatformException e) {
						LOGGER.fatal("", e);
					}
				}
			}
		}
	}

	private JComponent createListComponent(JComponent list, String title)
	{
		JScrollPane panel = new JScrollPane(list);
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.setBorder(BorderFactory.createTitledBorder(title));
		panel.setPreferredSize(new Dimension(150, 300));
		return panel;
	}

	private JComponent createSettingComponent()
	{
		JPanel mainPanel = new JPanel(new BorderLayout());

		JList tableList = new JList(_tableListModel);
		tableList.addListSelectionListener(new TableSelectionListener());

		JList columnList = new JList(_columnListModel);
		columnList.addListSelectionListener(new ColumnSelectionListener());

		JList conditionList = new JList(_conditionListModel);
		conditionList.addListSelectionListener(new ConditionSelectionListener());

		JPanel conditionsPanel = new JPanel();
		conditionsPanel.setLayout(new BorderLayout());

		JPanel buttonsPanel = new JPanel();
		JButton addConditionButton = new JButton("Add");
		addConditionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				addCondition();
			}
		});

		JButton removeConditionButton = new JButton("Remove");
		removeConditionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				removeCondition();
			}
		});

		buttonsPanel.add(addConditionButton);
		buttonsPanel.add(removeConditionButton);

		conditionsPanel.add(new JScrollPane(conditionList), BorderLayout.CENTER);
		conditionsPanel.add(buttonsPanel, BorderLayout.SOUTH);

		JPanel listPanel = new JPanel(new GridLayout(1, 3));
		listPanel.add(createListComponent(tableList, "Tables"));
		listPanel.add(createListComponent(columnList, "Columns"));
		listPanel.add(createListComponent(conditionsPanel, "Conditions"));

		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalStrut(20));
		box.add(new JLabel("Dataset name"));
		box.add(_txtDataSetName);
		box.add(Box.createHorizontalStrut(20));

		mainPanel.add(listPanel, BorderLayout.CENTER);
		mainPanel.add(box, BorderLayout.SOUTH);

		return mainPanel;
	}

	private JPanel getAddConditionPanel()
	{
		if (_addConditionPanel == null) {
			_addConditionPanel = new JPanel();
			_addConditionPanel.setLayout(new BoxLayout(_addConditionPanel,
					BoxLayout.LINE_AXIS));
			_txtColumnName.setEditable(false);

			_addConditionPanel.add(_txtColumnName);
			_addConditionPanel.add(_operations);
			_addConditionPanel.add(_txtColumnValue);
		}
		return _addConditionPanel;
	}

	private void removeCondition()
	{
		if (_selectedCondition != null) {
			_conditionListModel.removeElement(_selectedCondition);
			_selectedCondition = null;
		}
	}

	private void init(IMetaData metaData)
	{
		for (ITable table : metaData.getTables()) {
			_tableListModel.addElement(table);
		}
	}

	private class ColumnSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			_selectedColumn = (IColumn) ((JList) e.getSource()).getSelectedValue();
		}
	}

	private class TableSelectionListener implements ListSelectionListener
	{

		public void valueChanged(ListSelectionEvent e)
		{
			ITable table = (ITable) ((JList) e.getSource()).getSelectedValue();
			_columnListModel.clear();
			for (IColumn column : table.getColumns()) {
				_columnListModel.addElement(column);
			}
		}
	}

	private class ConditionSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			_selectedCondition = (ICondition) ((JList) e.getSource()).getSelectedValue();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(CreatorSettingComponent.class);
}
