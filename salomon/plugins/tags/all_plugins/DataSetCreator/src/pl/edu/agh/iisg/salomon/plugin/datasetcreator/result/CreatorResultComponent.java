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

package pl.edu.agh.iisg.salomon.plugin.datasetcreator.result;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;

/**
 * @author nico
 */
public class CreatorResultComponent implements IResultComponent
{
    private JComponent _resultComponent;

    private JTextField _txtDataSetName;

    public CreatorResultComponent()
    {
        _txtDataSetName = new JTextField();
    }

    /**
     * 
     */
    public Component getComponent(IResult result, IDataEngine dataEngine)
    {
        if (_resultComponent == null) {
            _resultComponent = createResultComponent();
        }
        CreatorResult cResult = (CreatorResult) result;
        _txtDataSetName.setText(cResult.getDataSetName());
        return _resultComponent;
    }

    private JComponent createResultComponent()
    {
        JComponent panel = new JPanel();
        panel.add(new JLabel("Data set name"));
        _txtDataSetName.setPreferredSize(new Dimension(100, 20));
        _txtDataSetName.setEditable(false);
        panel.add(_txtDataSetName);

        return panel;
    }

    /**
     * 
     */
    public IResult getDefaultResult()
    {
        return new CreatorResult();
    }

}
