/*
 * Copyright (C) 2006 WekaTreeGenerator Team
 *
 * This file is part of WekaTreeGenerator.
 *
 * WekaTreeGenerator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * WekaTreeGenerator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WekaTreeGenerator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.settings;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.builder.PanelBuilder;
import salomon.platform.IDataEngine;
import salomon.platform.data.attribute.IAttributeSet;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

import javax.swing.*;
import java.awt.*;

/**
 * 
 */
public final class WekaTreeGeneratorSettingsComponent implements ISettingComponent
{
    private JComponent _settingsComponent;
    private WekaTreeGeneratorSettings _settings;
    private JTextField _resultTreeName;
    private JComboBox _dataSetName;
    private JComboBox _attributeSetName;
    private JComboBox _algorithmName;

    public Component getComponent(ISettings settings, IDataEngine dataEngine) throws PlatformException
    {
        if (_settingsComponent == null) {
            _settingsComponent = createSettingsComponent(dataEngine);
        }
        _settings = (WekaTreeGeneratorSettings) settings;

        //TODO check if intern strings is mendatory
        _algorithmName.setSelectedItem(_settings.getAlgorithmName());
        _attributeSetName.setSelectedItem(_settings.getAttributeSetName());
        _dataSetName.setSelectedItem(_settings.getDataSetName());
        _resultTreeName.setText(_settings.getTreeName());


        return _settingsComponent;
    }

    private JComponent createSettingsComponent(IDataEngine dataEngine) throws PlatformException
    {

        String[] algorithms = new String[]{"J48"};
        _algorithmName = new JComboBox(algorithms);

        IAttributeSet[] attributeSets = dataEngine.getAttributeManager().getAll();
        String[] attributeSetNames = new String[attributeSets.length];
        for (int i = 0; i < attributeSets.length; i++) {
            IAttributeSet attributeSet = attributeSets[i];
            attributeSetNames[i] = attributeSet.getName();

        }
        _attributeSetName = new JComboBox(attributeSetNames);

        IDataSet[] dataSets = dataEngine.getDataSetManager().getAll();
        String[] dataSetsNames = new String[dataSets.length];
        for (int i = 0; i < dataSets.length; i++) {
            IDataSet dataSet = dataSets[i];
            dataSetsNames[i] = dataSet.getName();
        }

        _dataSetName = new JComboBox(dataSetsNames);
        _resultTreeName = new JTextField();
        // TODO add support options

        FormLayout formLayout = new FormLayout("pref, 4dlu, pref:GROW, 4dlu, min", // columns
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); // rows)
        formLayout.setRowGroups(new int[][]{{3, 5, 7, 11}});

        CellConstraints cellConstraints = new CellConstraints();
        PanelBuilder panelBuilder = new PanelBuilder(formLayout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.addSeparator("Input", cellConstraints.xyw(1, 1, 5));
        panelBuilder.add(new JLabel("Algorithm:"), cellConstraints.xy(1, 3));
        panelBuilder.add(_algorithmName, cellConstraints.xy(3, 3));

        panelBuilder.add(new JLabel("Data set:"), cellConstraints.xy(1, 5));
        panelBuilder.add(_dataSetName, cellConstraints.xy(3, 5));

        panelBuilder.add(new JLabel("Attribute set:"), cellConstraints.xy(1, 7));
        panelBuilder.add(_attributeSetName, cellConstraints.xy(3, 7));

        panelBuilder.addSeparator("Output", cellConstraints.xyw(1, 9, 5));
        panelBuilder.add(new JLabel("Result tree:"), cellConstraints.xy(1, 11));
        panelBuilder.add(_resultTreeName, cellConstraints.xy(3, 11));

        return panelBuilder.getPanel();
    }

    public ISettings getDefaultSettings()
    {
        WekaTreeGeneratorSettings defaultSettings =  new WekaTreeGeneratorSettings();
        defaultSettings.setAlgorithmName("J48");
        
        return defaultSettings;
    }

    public ISettings getSettings()
    {
        _settings.setAlgorithmName((String) _algorithmName.getSelectedItem());
        _settings.setAttributeSetName((String) _attributeSetName.getSelectedItem());
        _settings.setDataSetName((String) _dataSetName.getSelectedItem());
        _settings.setTreeName(_resultTreeName.getText());

        return _settings;
    }
}
