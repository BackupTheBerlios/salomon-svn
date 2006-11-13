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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import salomon.plugin.IPlatformUtil;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.gui.validation.IComponentFactory;
import salomon.util.gui.validation.IValidationModel;
import salomon.util.gui.validation.dataset.DataSet;
import salomon.util.gui.validation.dataset.DataSetValidator;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

/**
 * @author nico
 */
public class RandomDSSettingComponent implements ISettingComponent
{

    private static final Logger LOGGER = Logger.getLogger(RandomDSSettingComponent.class);

    private JPanel _addDescriptionPanel;

    private IDataEngine _dataEngine;

    private DataSet _dataSet;

    private DefaultListModel _descriptionListModel;

    private IPlatformUtil _platformUtil;

    private TableDescription _selectedDescription;

    private ITable _selectedTable;

    private JComponent _settingComponent;

    private DefaultListModel _tableListModel;

    private JTextField _txtDataSetName;

    private JTextField _txtRowCount;

    private JTextField _txtTableName;

    public RandomDSSettingComponent(IPlatformUtil platformUtil)
    {
        _platformUtil = platformUtil;
    }

    /**
     * 
     */
    public Component getComponent(ISettings settings, IDataEngine dataEngine)
    {
        if (_settingComponent == null) {
            _dataEngine = dataEngine;
            initComponents();
            init(dataEngine.getMetaData());
            _addDescriptionPanel = getDescriptionPanel();
            _settingComponent = createSettingComponent();
        }
        try {
            initSettingComponent(settings);
        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            // FIXME: where this error should be handled?
            // handle it by adding throws declaration to interface methods
        }
        return _settingComponent;
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
                _dataSet.getDataSetName());

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

        JList descriptionList = new JList(_descriptionListModel);
        descriptionList.addListSelectionListener(new ConditionSelectionListener());

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

        // building buttons panel
        JPanel buttonsPanel = ButtonBarFactory.buildAddRemoveBar(
                addConditionButton, removeConditionButton);

        JPanel rowsPanel = new JPanel();
        rowsPanel.setLayout(new BorderLayout());
        rowsPanel.add(createListComponent(descriptionList, "Random rows"),
                BorderLayout.CENTER);
        rowsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // building list form
        FormLayout layout = new FormLayout(
                "fill:100dlu:grow, 3dlu, fill:min:grow", "fill:200dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append(createListComponent(tableList, "Tables"));
        builder.append(rowsPanel);

        JPanel listPanel = builder.getPanel();

        // building dataset form
        layout = new FormLayout("left:pref, 3dlu, fill:100dlu:grow", "");
        builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append("Dataset name", _txtDataSetName);

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(builder.getPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel getDescriptionPanel()
    {
        if (_addDescriptionPanel == null) {
            FormLayout layout = new FormLayout(
                    "fill:100dlu:grow, 3dlu, fill:default:grow", "");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);

            _txtTableName.setEnabled(false);
            builder.append(_txtTableName);
            builder.append(_txtRowCount);

            _addDescriptionPanel = builder.getPanel();
        }
        return _addDescriptionPanel;
    }

    private void init(IMetaData metaData)
    {
        _tableListModel.clear();
        for (ITable table : metaData.getTables()) {
            _tableListModel.addElement(table);
        }
    }

    private void initComponents()
    {
        _tableListModel = new DefaultListModel();
        _descriptionListModel = new DefaultListModel();
        _txtTableName = new JTextField();
        _txtRowCount = new JTextField();

        // validation
        _dataSet = new DataSet();
        DataSetValidator dataSetValidator = new DataSetValidator(_dataSet,
                _dataEngine);
        IValidationModel validationModel = _platformUtil.getValidationModel(dataSetValidator);
        IComponentFactory componentFactory = validationModel.getComponentFactory();
        _txtDataSetName = componentFactory.createTextField(
                DataSet.PROPERTYNAME_DATASET_NAME, false);
    }

    private void initSettingComponent(ISettings settings)
            throws PlatformException
    {
        RandomDSSettings rSettings = (RandomDSSettings) settings;
        String dataSetName = rSettings.getDataSetName();

        TableDescription[] descriptions = rSettings.getTableDesctiptions();

        // setting gui values
        _dataSet.setDataSetName(dataSetName);
        _descriptionListModel.clear();
        for (TableDescription description : descriptions) {
            if (description != null) {
                _descriptionListModel.addElement(description);
            }
        }
    }

    private void removeTableDescription()
    {
        if (_selectedDescription != null) {
            _descriptionListModel.removeElement(_selectedDescription);
            _selectedDescription = null;
        }
    }

    private class ConditionSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            _selectedDescription = (TableDescription) ((JList) e.getSource()).getSelectedValue();
        }
    }

    private class TableSelectionListener implements ListSelectionListener
    {

        public void valueChanged(ListSelectionEvent e)
        {
            _selectedTable = (ITable) ((JList) e.getSource()).getSelectedValue();

        }
    }
}
