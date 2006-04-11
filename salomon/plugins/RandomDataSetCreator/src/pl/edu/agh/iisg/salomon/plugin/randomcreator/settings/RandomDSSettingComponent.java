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

package pl.edu.agh.iisg.salomon.plugin.randomcreator.settings;

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
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public class RandomDSSettingComponent implements ISettingComponent
{

    private JPanel _addDescriptionPanel;

    private DefaultListModel _descriptionListModel;

    private JComponent _settingComponent;

    private DefaultListModel _tableListModel;

    private JTextField _txtTableName;

    private JTextField _txtRowCount;

    private JTextField _txtDataSetName;

    private ITable _selectedTable;

    private TableDescription _selectedDescription;

    public RandomDSSettingComponent()
    {
        _tableListModel = new DefaultListModel();
        _descriptionListModel = new DefaultListModel();
    }

    /**
     * 
     */
    public Component getComponent(ISettings settings, IDataEngine dataEngine)
    {
        if (_settingComponent == null) {
            init(dataEngine.getMetaData());
            _addDescriptionPanel = getDescriptionPanel();
            _settingComponent = createSettingComponent();
        }
        try {
            initSettingComponent(settings, dataEngine.getMetaData());
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            // FIXME: where this error should be handled?
            // handle it by adding throws declaration to interface methods
        }
        return _settingComponent;
    }

    private void initSettingComponent(ISettings settings, IMetaData metaData)
            throws PlatformException
    {
        RandomDSSettings rSettings = (RandomDSSettings) settings;
        String dataSetName = rSettings.getDataSetName();

        TableDescription[] descriptions = rSettings.getTableDesctiptions();

        // setting gui values
        _txtDataSetName.setText(dataSetName);
        _descriptionListModel.clear();
        for (TableDescription description : descriptions) {
            if (description != null) {
                _descriptionListModel.addElement(description);
            }
        }
    }

    /**
     * 
     */
    public ISettings getDefaultSettings()
    {
        ISettings defaultSettings = new RandomDSSettings("");
        return defaultSettings;
    }

    /**
     * 
     */
    public ISettings getSettings()
    {
        RandomDSSettings settings = new RandomDSSettings(
                _txtDataSetName.getText());

        // setting table descriptions
        int size = _descriptionListModel.getSize();

        TableDescription[] descArray = new TableDescription[size];
        for (int i = 0; i < size; ++i) {
            descArray[i] = (TableDescription) _descriptionListModel.get(i);
            LOGGER.debug("desc: " + descArray[i]);
        }

        settings.setTableDesctiptions(descArray);
        return settings;
    }

    private void addTableDescription()
    {
        if (_selectedTable != null) {
            _txtTableName.setText(_selectedTable.getName());
            _txtRowCount.setText("");

            int result = JOptionPane.showConfirmDialog(_settingComponent,
                    getDescriptionPanel(), "Enter row number",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                // FIXME: add error support
                int value = Integer.parseInt(_txtRowCount.getText());
                if (!"".equals(value)) {
                    TableDescription description = new TableDescription(
                            _selectedTable.getName(), value);
                    // adding condition to the list
                    LOGGER.debug("adding description: " + description);
                    _descriptionListModel.addElement(description);
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

        JList conditionList = new JList(_descriptionListModel);
        conditionList.addListSelectionListener(new ConditionSelectionListener());

        JPanel rowsPanel = new JPanel();
        rowsPanel.setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        JButton addConditionButton = new JButton("Add");
        addConditionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                addTableDescription();
            }
        });

        JButton removeConditionButton = new JButton("Remove");
        removeConditionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                removeTableDescription();
            }
        });

        buttonsPanel.add(addConditionButton);
        buttonsPanel.add(removeConditionButton);

        rowsPanel.add(new JScrollPane(conditionList), BorderLayout.CENTER);
        rowsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        JPanel listPanel = new JPanel(new GridLayout(1, 3));
        listPanel.add(createListComponent(tableList, "Tables"));
        listPanel.add(createListComponent(rowsPanel, "Randomized rows"));

        _txtDataSetName = new JTextField();
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(20));
        box.add(new JLabel("Dataset name"));
        box.add(_txtDataSetName);
        box.add(Box.createHorizontalStrut(20));

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(box, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel getDescriptionPanel()
    {
        if (_addDescriptionPanel == null) {
            _addDescriptionPanel = new JPanel();
            _addDescriptionPanel.setLayout(new BoxLayout(_addDescriptionPanel,
                    BoxLayout.LINE_AXIS));
            _txtTableName = new JTextField();
            _txtRowCount = new JTextField();            
            _txtTableName.setEditable(false);

            _addDescriptionPanel.add(_txtTableName);
            _addDescriptionPanel.add(_txtRowCount);
        }
        return _addDescriptionPanel;
    }

    private void removeTableDescription()
    {
        if (_selectedDescription != null) {
            _descriptionListModel.removeElement(_selectedDescription);
            _selectedDescription = null;
        }
    }

    private void init(IMetaData metaData)
    {
        _tableListModel.clear();
        for (ITable table : metaData.getTables()) {
            _tableListModel.addElement(table);
        }
    }

    private class TableSelectionListener implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent e)
        {
            _selectedTable = (ITable) ((JList) e.getSource()).getSelectedValue();

        }
    }

    private class ConditionSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            _selectedDescription = (TableDescription) ((JList) e.getSource()).getSelectedValue();
        }
    }

    private static final Logger LOGGER = Logger.getLogger(RandomDSSettingComponent.class);
}
