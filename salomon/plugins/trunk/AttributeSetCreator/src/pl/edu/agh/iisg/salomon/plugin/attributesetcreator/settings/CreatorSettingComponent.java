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

package pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import salomon.util.gui.validation.IComponentFactory;
import salomon.util.gui.validation.IValidationModel;
import salomon.util.gui.validation.attributeset.AttributeSet;
import salomon.util.gui.validation.attributeset.AttributeSetValidator;

import salomon.platform.IDataEngine;
import salomon.platform.data.IColumn;
import salomon.platform.data.IMetaData;
import salomon.platform.data.ITable;
import salomon.platform.data.attribute.IAttributeManager;
import salomon.platform.data.attribute.description.AttributeType;
import salomon.platform.data.attribute.description.IAttributeDescription;
import salomon.platform.exception.PlatformException;

import pl.edu.agh.iisg.salomon.plugin.attributesetcreator.settings.CreatorSettings.Description;
import salomon.plugin.IPlatformUtil;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 */
public class CreatorSettingComponent implements ISettingComponent
{

    private static final Logger LOGGER = Logger.getLogger(CreatorSettingComponent.class);

    private JPanel _addAttributePanel;

    private DefaultListModel _columnListModel;

    private DefaultListModel _attributeListModel;

    private IDataEngine _dataEngine;

    private AttributeSet _attributeSet;

    private JComboBox _attributeTypes;

    private IColumn _selectedColumn;

    private IAttributeDescription _selectedCondition;

    private JComponent _settingComponent;

    private DefaultListModel _tableListModel;

    private JTextField _txtColumnName;

    private JTextField _txtAttributeName;

    private JTextField _txtAttributeSetName;

    private IPlatformUtil _platformUtil;

    private JCheckBox _chkIsOutput;

    public CreatorSettingComponent(IPlatformUtil platformUtil)
    {
        _platformUtil = platformUtil;
    }

    /**
     * @throws PlatformException 
     * 
     */
    public Component getComponent(ISettings settings, IDataEngine dataEngine)
            throws PlatformException
    {
        if (_settingComponent == null) {
            _dataEngine = dataEngine;
            initComponents();
            init(dataEngine.getMetaData());
            _addAttributePanel = getAddAttributePanel();
            _settingComponent = createSettingComponent();
        }
        initSettingComponent(settings);
        return _settingComponent;
    }

    /**
     * 
     */
    public ISettings getDefaultSettings()
    {
        return new CreatorSettings("");
    }

    /**
     * 
     */
    public ISettings getSettings()
    {
        CreatorSettings settings = new CreatorSettings(
                _attributeSet.getAttributeSetName());

        // setting attribute set name
        settings.setAttributeSetName(_txtAttributeSetName.getText());

        // setting conditions
        int size = _attributeListModel.getSize();

        Description[] descArray = new Description[size];
        for (int i = 0; i < size; ++i) {
            IAttributeDescription attrDescription = (IAttributeDescription) _attributeListModel.get(i);
            Description description = settings.new Description();
            description.setAttributeName(attrDescription.getName());
            description.setTableName(attrDescription.getColumn().getTable().getName());
            description.setColumnName(attrDescription.getColumn().getName());
            description.setType(attrDescription.getType().getDBString());
            description.setIsOutput(attrDescription.isOutput() ? "Y" : "N");
            descArray[i] = description;
        }

        settings.setDescriptions(descArray);
        return settings;
    }

    private void addAttribute()
    {
        LOGGER.info("CreatorSettingComponent.addAttribute()");
        if (_selectedColumn != null) {
            StringBuilder colName = new StringBuilder();
            colName.append(_selectedColumn.getTable().getName());
            colName.append(".");
            colName.append(_selectedColumn.getName());
            _txtColumnName.setText(colName.toString());
            _txtAttributeName.setText("");
            _attributeTypes.setSelectedIndex(0);

            int result = JOptionPane.showConfirmDialog(_settingComponent,
                    getAddAttributePanel(), "Choose condition",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String attrName = _txtAttributeName.getText();
                if (!"".equals(attrName)) {
                    try {
                        IAttributeManager attributeManager = _dataEngine.getAttributeManager();
                        String type = (String) _attributeTypes.getSelectedItem();
                        IAttributeDescription description = attributeManager.createAttributeDescription(
                                attrName, _selectedColumn.getTable().getName(),
                                _selectedColumn.getName(), type,
                                _chkIsOutput.isSelected());
                        
                        // setting isOutput default value
                        _chkIsOutput.setSelected(false);
                        
                        // adding description to the list
                        LOGGER.debug("adding description: " + description);
                        _attributeListModel.addElement(description);
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

        JList conditionList = new JList(_attributeListModel);
        conditionList.addListSelectionListener(new AttributeSelectionListener());

        JButton addConditionButton = new JButton("Add");
        addConditionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                addAttribute();
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
        conditionsPanel.add(createListComponent(conditionList, "Attributes"),
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

        builder.append("AttributeSet name", _txtAttributeSetName);

        mainPanel.add(listPanel, BorderLayout.CENTER);
        mainPanel.add(builder.getPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel getAddAttributePanel()
    {
        if (_addAttributePanel == null) {
            FormLayout layout = new FormLayout(
                    "fill:100dlu:grow, 3dlu, fill:default:grow, 3dlu, fill:100dlu:grow, 3dlu, fill:default:grow",
                    "");
            DefaultFormBuilder builder = new DefaultFormBuilder(layout);

            _txtColumnName.setEditable(false);

            builder.append(_txtColumnName);
            builder.append(_attributeTypes);
            builder.append(_txtAttributeName);
            builder.append(_chkIsOutput);

            _addAttributePanel = builder.getPanel();
        }
        return _addAttributePanel;
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
        _attributeListModel = new DefaultListModel();
        _attributeTypes = new JComboBox(new String[]{
                AttributeType.INTEGER.getDBString(),
                AttributeType.STRING.getDBString(),
                AttributeType.REAL.getDBString(),
                AttributeType.ENUM.getDBString()});
        _txtColumnName = new JTextField();
        _txtAttributeName = new JTextField();
        _chkIsOutput = new JCheckBox("Is output?");
        _chkIsOutput.setSelected(false);

        // validation
        _attributeSet = new AttributeSet();
        AttributeSetValidator attributeSetValidator = new AttributeSetValidator(
                _attributeSet, _dataEngine);
        IValidationModel validationModel = _platformUtil.getValidationModel(attributeSetValidator);
        IComponentFactory componentFactory = validationModel.getComponentFactory();
        _txtAttributeSetName = componentFactory.createTextField(
                AttributeSet.PROPERTYNAME_ATTRIBUTESET_NAME, false);
    }

    private void initSettingComponent(ISettings settings)
            throws PlatformException
    {
        CreatorSettings cSettings = (CreatorSettings) settings;

        // creating conditions
        Description[] strDesc = cSettings.getDescriptions();
        IAttributeDescription[] descriptions = new IAttributeDescription[strDesc.length];
        int i = 0;
        if (strDesc != null) {
            IAttributeManager attributeManager = _dataEngine.getAttributeManager();
            for (Description desc : strDesc) {
                descriptions[i] = attributeManager.createAttributeDescription(
                        desc.getAttributeName(), desc.getTableName(),
                        desc.getColumnName(), desc.getType(),
                    "Y".equals(desc.getIsOutput()));
                ++i;
            }
        }

        // setting gui values
        _attributeSet.setAttributeSetName(cSettings.getAttributeSetName());
        _attributeListModel.clear();
        for (IAttributeDescription desc : descriptions) {
            if (desc != null) {
                _attributeListModel.addElement(desc);
            }
        }
    }

    private void removeCondition()
    {
        if (_selectedCondition != null) {
            _attributeListModel.removeElement(_selectedCondition);
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

    private class AttributeSelectionListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            _selectedCondition = (IAttributeDescription) ((JList) e.getSource()).getSelectedValue();
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
