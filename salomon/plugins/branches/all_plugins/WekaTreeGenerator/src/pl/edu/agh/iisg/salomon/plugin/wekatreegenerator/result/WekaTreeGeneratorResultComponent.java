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

package pl.edu.agh.iisg.salomon.plugin.wekatreegenerator.result;

import salomon.platform.IDataEngine;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

import javax.swing.*;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.*;

/**
 * 
 */
public final class WekaTreeGeneratorResultComponent implements IResultComponent
{
    private JComponent _resultComponent;

    private JTextArea _txtOutput;

    /**
     * @see salomon.plugin.IResultComponent#getComponent(salomon.plugin.IResult,salomon.platform.IDataEngine)
     */
    public Component getComponent(IResult result, IDataEngine dataEngine)
    {
        if (_resultComponent == null) {
            _resultComponent = createResultComponent();
        }
        WekaTreeGeneratorResult treeResult = (WekaTreeGeneratorResult) result;
        _txtOutput.setText(treeResult.getOutput());

        return _resultComponent;
    }

    /**
     * @see salomon.plugin.IResultComponent#getDefaultResult()
     */
    public IResult getDefaultResult()
    {
        return new WekaTreeGeneratorResult();
    }

    private JComponent createResultComponent()
    {
        JComponent panel = new JPanel();
        panel.add(new JLabel("Output"));
        _txtOutput = new JTextArea(20, 20);
        JScrollPane scrollPane = new JScrollPane(_txtOutput);
        _txtOutput.setPreferredSize(new Dimension(100, 20));
        _txtOutput.setEditable(false);
        
        FormLayout formLayout = new FormLayout("pref:GROW", // columns
        "pref, 2dlu, pref"); // rows)

        CellConstraints cellConstraints = new CellConstraints();
        PanelBuilder panelBuilder = new PanelBuilder(formLayout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.addSeparator("Algorithm output", cellConstraints.xy(1, 1));
        panelBuilder.add(scrollPane, cellConstraints.xy(1, 3));
        
        return panelBuilder.getPanel();
    }


}
