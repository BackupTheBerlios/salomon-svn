/*
 * Copyright (C) 2006 TreeEvaluator Team
 *
 * This file is part of TreeEvaluator.
 *
 * TreeEvaluator is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeEvaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeEvaluator; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treeevaluator.result;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;


import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;

import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class TreeEvaluatorSettingsComponent implements ISettingComponent
{
    private TreeEvaluatorSettings _settings;

    private JComponent _settingsComponent;

    private JComboBox _treeName;
    
    private JComboBox _dataSetName;

    public Component getComponent(ISettings settings, IDataEngine dataEngine)
            throws PlatformException
    {
        if (_settingsComponent == null) {
            _settingsComponent = createSettingsComponent(dataEngine);
        }

        _settings = (TreeEvaluatorSettings) settings;
        _treeName.setSelectedItem(_settings.getTreeName());
        _dataSetName.setSelectedItem(_settings.getDataSetName());

        return _settingsComponent;
    }

    public ISettings getDefaultSettings()
    {
        return new TreeEvaluatorSettings();
    }

    public ISettings getSettings()
    {
        _settings.setTreeName((String) _treeName.getSelectedItem());
        _settings.setDataSetName((String) _dataSetName.getSelectedItem());
        

        return _settings;
    }

    private JComponent createSettingsComponent(IDataEngine dataEngine)
            throws PlatformException
    {

        ITree[] trees = dataEngine.getTreeManager().getAll();
        String[] treesNames = new String[trees.length];
        for (int i = 0; i < trees.length; i++) {
            ITree tree = trees[i];
            treesNames[i] = tree.getName();

        }
        _treeName = new JComboBox(treesNames);

        
        IDataSet[] dataSets = dataEngine.getDataSetManager().getAll();
        String[] dataSetNames = new String[dataSets.length];
        for (int i = 0; i < dataSets.length; i++) {
            IDataSet dataSet = dataSets[i];
            dataSetNames[i] = dataSet.getName();

        }
        _dataSetName = new JComboBox(dataSetNames);

        
        FormLayout formLayout = new FormLayout(
                "pref, 4dlu, pref:GROW, 4dlu, min", // columns
                "pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"); // rows)
        formLayout.setRowGroups(new int[][]{{3, 5}});

        CellConstraints cellConstraints = new CellConstraints();
        PanelBuilder panelBuilder = new PanelBuilder(formLayout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.addSeparator("Tree", cellConstraints.xyw(1, 1, 5));
        panelBuilder.add(new JLabel("Name:"), cellConstraints.xy(1, 3));
        panelBuilder.add(_treeName, cellConstraints.xy(3, 3));

        panelBuilder.addSeparator("Data set", cellConstraints.xyw(1, 5, 5));
        panelBuilder.add(new JLabel("Name:"), cellConstraints.xy(1, 7));
        panelBuilder.add(_dataSetName, cellConstraints.xy(3, 7));

        return panelBuilder.getPanel();
    }

}
