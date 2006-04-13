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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.dataset.ICondition;
import salomon.platform.data.dataset.IDataSetManager;
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
public class CreatorSettingComponent implements ISettingComponent
{

    private static final Logger LOGGER = Logger.getLogger(CreatorSettingComponent.class);

    private JPanel _addConditionPanel;

    private DefaultListModel _columnListModel;

    private DefaultListModel _conditionListModel;

    private IDataEngine _dataEngine;

    private DataSet _dataSet;

    private JComboBox _operations;

    private IColumn _selectedColumn;

    private ICondition _selectedCondition;

    private JComponent _settingComponent;

    private DefaultListModel _tableListModel;

    private JTextField _txtColumnName;

    private JTextField _txtColumnValue;

    private JTextField _txtDataSetName;

    private IPlatformUtil _platformUtil;

    public CreatorSettingComponent(IPlatformUtil platformUtil)
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
            _addConditionPanel = getAddConditionPanel();
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
        ISettings defaultSettings = new CreatorSettings("");
        return defaultSettings;
    }

    /**
     * 
     */
    public ISettings getSettings()
    {
        CreatorSettings settings = new CreatorSettings(
                _dataSet.getDataSetName());

        // setting conditions
        int size = _conditionListModel.getSize();

        String[] condArray = new String[size];
        for (int i = 0; i < size; ++i) {
            condArray[i] = _conditionListModel.get(i).toString();
            LOGGER.debug("cond: " + condArray[i]);
        }

        settings.setConditions(condArray);
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

        // building buttons panel
        JPanel buttonsPanel = ButtonBarFactory.buildAddRemoveBar(
                addConditionButton, removeConditionButton);

        JPanel conditionsPanel = new JPanel();
        conditionsPanel.setLayout(new BorderLayout());
        conditionsPanel.add(createListComponent(conditionList, "Conditions"),
                BorderLayout.CENTER);
        conditionsPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // building list form
        FormLayout layout = new FormLayout(
                "fill:100dlu:grow, 3dlu, fill:100dlu:grow, 3dlu, fill:min:grow",
                "fill:200dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append(createListComponent(tableList, "Tables"));
        builder.append(createListComponent(columnList, "Columns"));
        builder.append(conditionsPanel);

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

    private JPanel getAddConditionPanel()
    {
        if (_addConditionPanel == null) {
            FormLayout layout = new FormLayout(
                    "fill:100dlu:grow, 3dlu, fill:default:grow, 3dlu, fill:100dlu:grow",
                    "");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);

            _txtColumnName.setEditable(false);

            builder.append(_txtColumnName);
            builder.append(_operations);
            builder.append(_txtColumnValue);

            _addConditionPanel = builder.getPanel();
        }
        return _addConditionPanel;
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
        _columnListModel = new DefaultListModel();
        _conditionListModel = new DefaultListModel();
        _operations = new JComboBox(new String[]{"=", "<", ">"});
        _txtColumnName = new JTextField();
        _txtColumnValue = new JTextField();
        
        
        // validation
        _dataSet = new DataSet();
        DataSetValidator dataSetValidator = new DataSetValidator(_dataSet,
                _dataEngine);        
        IValidationModel validationModel = _platformUtil.getValidationModel(dataSetValidator);
        IComponentFactory componentFactory = validationModel.getComponentFactory();        
        _txtDataSetName = componentFactory.createTextField(DataSet.PROPERTYNAME_DATASET_NAME, false);
    }

    private void initSettingComponent(ISettings settings)
            throws PlatformException
    {
        CreatorSettings cSettings = (CreatorSettings) settings;

        // creating conditions
        String[] strConds = cSettings.getConditions();
        ICondition[] conditions = new ICondition[strConds.length];
        int i = 0;
        if (strConds != null) {
            for (String cond : strConds) {
                conditions[i] = _dataEngine.getDataSetManager().createCondition(
                        cond);
                ++i;
            }
        }

        // setting gui values
        _dataSet.setDataSetName(cSettings.getDataSetName());
        _conditionListModel.clear();
        for (ICondition condition : conditions) {
            if (condition != null) {
                _conditionListModel.addElement(condition);
            }
        }
    }

    private void removeCondition()
    {
        if (_selectedCondition != null) {
            _conditionListModel.removeElement(_selectedCondition);
            _selectedCondition = null;
        }
    }

    private class ColumnSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            _selectedColumn = (IColumn) ((JList) e.getSource()).getSelectedValue();
        }
    }

    private class ConditionSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            _selectedCondition = (ICondition) ((JList) e.getSource()).getSelectedValue();
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
}
