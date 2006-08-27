/*
 * Copyright (C) 2006 TreeVis Team
 *
 * This file is part of TreeVis.
 *
 * TreeVis is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * TreeVis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with TreeVis; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 */

package pl.edu.agh.iisg.salomon.plugin.treevis.settings;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import salomon.platform.IDataEngine;
import salomon.platform.data.tree.ITree;
import salomon.platform.exception.PlatformException;

import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public final class TreeVisSettingsComponent implements ISettingComponent
{
    private JComboBox _treeName;
    private JComponent _settingsComponent;
    private TreeVisSettings _settings;
    
    public Component getComponent(ISettings settings, IDataEngine dataEngine)
            throws PlatformException
    {
        if (_settingsComponent == null) {
            _settingsComponent = createSettingsComponent(dataEngine);
        }
        
        _settings = (TreeVisSettings) settings;
        _treeName.setSelectedItem(_settings.getTreeName());


        return _settingsComponent;
    }

    private JComponent createSettingsComponent(IDataEngine dataEngine) throws PlatformException
    {

        ITree[] attributeSets = dataEngine.getTreeManager().getAll();
        String[] attributeSetNames = new String[attributeSets.length];
        for (int i = 0; i < attributeSets.length; i++) {
            ITree attributeSet = attributeSets[i];
            attributeSetNames[i] = attributeSet.getName();

        }
        _treeName = new JComboBox(attributeSetNames);


        FormLayout formLayout = new FormLayout("pref, 4dlu, pref:GROW, 4dlu, min", // columns
                "pref"); // rows)
        formLayout.setRowGroups(new int[][]{{3, 5, 7, 11}});

        CellConstraints cellConstraints = new CellConstraints();
        PanelBuilder panelBuilder = new PanelBuilder(formLayout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.addSeparator("Tree", cellConstraints.xyw(1, 1, 5));
        panelBuilder.add(new JLabel("Name:"), cellConstraints.xy(1, 3));
        panelBuilder.add(_treeName, cellConstraints.xy(3, 3));

        return panelBuilder.getPanel();    }

    public ISettings getDefaultSettings()
    {
        return new TreeVisSettings();
    }

    public ISettings getSettings()
    {
        _settings.setTreeName((String) _treeName.getSelectedItem());
        
        return _settings;
    }

}
