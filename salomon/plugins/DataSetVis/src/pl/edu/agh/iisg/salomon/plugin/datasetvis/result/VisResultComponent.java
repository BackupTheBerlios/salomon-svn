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

package pl.edu.agh.iisg.salomon.plugin.datasetvis.result;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.util.gui.Utils;
import salomon.util.serialization.SimpleString;
import sun.reflect.generics.scope.Scope;

/**
 * @author nico
 */
public final class VisResultComponent implements IResultComponent
{

    private IDataEngine _dataEngine;

    private DefaultTableModel _dataModel;

    private JComponent _resultComponent;

    private JTextField _txtDataSetName;

    //FIXME: !!!
    private JTable _table;

    public VisResultComponent()
    {
        _dataModel = new DefaultTableModel();
        _txtDataSetName = new JTextField();
    }

    /**
     * 
     */
    public Component getComponent(IResult result, IDataEngine dataEngine)
    {
        _dataEngine = dataEngine;
        //FIXME: !!!
        if (_resultComponent == null) {
            initComponents();
            _resultComponent = createResultComponent();
        }
        initResultComponent(result);
        return _resultComponent;
    }

    /**
     * 
     */
    public IResult getDefaultResult()
    {
        return new VisResult();
    }

    private void initComponents()
    {
        _table = new JTable(_dataModel);
        _table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        _txtDataSetName.setEditable(false);
    }

    private JComponent createResultComponent()
    {
        JScrollPane scrollPanel = new JScrollPane(_table);
        scrollPanel.setBorder(BorderFactory.createLoweredBevelBorder());

        JButton showButton = new JButton("Show");
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                showDataSet();
            }
        });

        // building form
        FormLayout layout = new FormLayout(
                "fill:default, 3dlu, fill:100dlu:grow, 3dlu, fill:min",
                "top:default, default, fill:200dlu:grow");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();
        builder.append("Dataset name", _txtDataSetName);
        builder.append(showButton);
        builder.appendSeparator("Dataset data");
        builder.append(scrollPanel, 5);

        return builder.getPanel();
    }

    private void initResultComponent(IResult result)
    {
        LOGGER.debug("VisResultComponent.initResultComponent()");
        VisResult vResult = (VisResult) result;
        String dataSetName = vResult.getDataSetName();
        LOGGER.debug("dataSetName: " + dataSetName);
        _txtDataSetName.setText(dataSetName);
    }

    private void showDataSet()
    {
        try {
            IDataSet dataSet = _dataEngine.getDataSetManager().getDataSet(
                    _txtDataSetName.getText());
            //FIXME: !!!
            _dataModel = new DefaultTableModel();
            _table.setModel(_dataModel);
            // selects all data
            IData data = dataSet.selectData(null, null);
            String[] header = data.getHeader();
            for (int i = 0; i < header.length; ++i) {
                _dataModel.addColumn(header[i]);
            }

            while (data.next()) {
                _dataModel.addRow(data.getData());
            }
            data.close();

        } catch (PlatformException e) {
            LOGGER.fatal("", e);
            Utils.showErrorMessage("Cannot show data set");
        }
    }

    private static final Logger LOGGER = Logger.getLogger(VisResultComponent.class);

}
