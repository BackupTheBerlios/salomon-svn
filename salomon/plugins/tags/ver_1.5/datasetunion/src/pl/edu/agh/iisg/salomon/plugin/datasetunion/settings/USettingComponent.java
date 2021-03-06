/*
 * Copyright (C) 2004 Salomon Team
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

package pl.edu.agh.iisg.salomon.plugin.datasetunion.settings;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

/**
 * 
 */
public final class USettingComponent implements ISettingComponent
{
	private JTextField _cmbFirstDataSet;

	private JTextField _cmbSecondDataSet;

	private JPanel _settingPanel;

	private JTextField _txtResultDataSet;

	/**
	 * 
	 */
	public USettingComponent()
	{
		// empty body
	}

	/**
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings,
	 *      salomon.platform.IDataEngine)
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine)
	{
		if (_settingPanel == null) {
			_settingPanel = new JPanel();
			_cmbFirstDataSet = new JTextField();
			_cmbSecondDataSet = new JTextField();
			_txtResultDataSet = new JTextField();
			_settingPanel.setLayout(new GridLayout(0, 2));
			_settingPanel.add(new JLabel("First data set"));
			_settingPanel.add(_cmbFirstDataSet);
			_settingPanel.add(new JLabel("Second data set"));
			_settingPanel.add(_cmbSecondDataSet);
			_settingPanel.add(new JLabel("Result data set"));
			_settingPanel.add(_txtResultDataSet);
		}
		// FIXME
		/*
		 * IDataSetManager dataSetManager = dataEngine.getDataSetManager();
		 * IDataSet[] dataSets = null;
		 * 
		 * try { dataSetManager.getDataSets(); } catch (PlatformException e) { //
		 * TODO: }
		 */
        /*
		String[] strDataSets = {"first", "second", "third"};
		_cmbFirstDataSet.removeAllItems();
		_cmbSecondDataSet.removeAllItems();

		// for (int i = 0; i < dataSets.length; i++) {
		// _cmbFirstDataSet.addItem(dataSets[i].getName());
		// _cmbSecondDataSet.addItem(dataSets[i].getName());
		// }
		for (String name : strDataSets) {
			_cmbFirstDataSet.addItem(name);
			_cmbSecondDataSet.addItem(name);
		}
*/
		USettings uSettings = (USettings) settings;

		_cmbFirstDataSet.setText(((SimpleString) uSettings
				.getField(USettings.FIRST_DATA_SET)).getValue());
		_cmbSecondDataSet.setText(((SimpleString) uSettings
				.getField(USettings.SECOND_DATA_SET)).getValue());
		_txtResultDataSet.setText(((SimpleString) uSettings
				.getField(USettings.RESULT_DATA_SET)).getValue());
        
		return _settingPanel;
	}

	/**
	 * @see salomon.plugin.ISettingComponent#getDefaultSettings()
	 */
	public ISettings getDefaultSettings()
	{
		USettings result = new USettings();
		result.setField(USettings.FIRST_DATA_SET, new SimpleString("first"));
		result.setField(USettings.SECOND_DATA_SET, new SimpleString("second"));
		result.setField(USettings.RESULT_DATA_SET, new SimpleString("result"));
		return result;
	}

	/**
	 * @see salomon.plugin.ISettingComponent#getSettings()
	 */
	public ISettings getSettings()
	{

		USettings uSettings = new USettings();
		uSettings.setField(USettings.FIRST_DATA_SET, new SimpleString(
				_cmbFirstDataSet.getText()));
		// uSettings.setF
		uSettings.setField(USettings.SECOND_DATA_SET, new SimpleString(
				_cmbSecondDataSet.getText()));
		uSettings.setField(USettings.RESULT_DATA_SET, new SimpleString(
				_txtResultDataSet.getText()));
		return uSettings;
	}
}
