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

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IData;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.exception.PlatformException;
import salomon.plugin.IResult;
import salomon.plugin.IResultComponent;
import salomon.util.gui.Utils;
import salomon.util.serialization.SimpleString;

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

	private JComponent createResultComponent()
	{
		JPanel panel = new JPanel(new BorderLayout());
		_table = new JTable(_dataModel);
		_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPanel = new JScrollPane(_table);

		JButton showButton = new JButton("Show");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				showDataSet();
			}
		});

		_txtDataSetName.setPreferredSize(new Dimension(75, 25));
		_txtDataSetName.setEditable(false);

		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
		headerPanel.add(new JLabel("Dataset name"));
		headerPanel.add(Box.createHorizontalStrut(10));
		headerPanel.add(_txtDataSetName);
		headerPanel.add(Box.createHorizontalStrut(10));
		headerPanel.add(Box.createHorizontalGlue());
		headerPanel.add(showButton);

		panel.add(headerPanel, BorderLayout.NORTH);
		panel.add(scrollPanel);

		return panel;
	}

	private void initResultComponent(IResult result)
	{
		LOGGER.info("VisResultComponent.initResultComponent()");
		VisResult vResult = (VisResult) result;
		SimpleString dataSetName = (SimpleString) vResult.getField(VisResult.DATA_SET_NAME);
		String name = (dataSetName == null ? "" : dataSetName.getValue());
		LOGGER.info("name: " + name);
		_txtDataSetName.setText(name);
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
