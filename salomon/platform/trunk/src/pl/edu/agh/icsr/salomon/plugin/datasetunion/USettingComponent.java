
package pl.edu.agh.icsr.salomon.plugin.datasetunion;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.platform.data.dataset.IDataSet;
import salomon.platform.data.dataset.IDataSetManager;
import salomon.platform.exception.PlatformException;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 * TODO: add comment.
 * 
 * @author krzychu
 * 
 */
public final class USettingComponent implements ISettingComponent
{
	private JComboBox _cmbFirstDataSet;

	private JComboBox _cmbSecondDataSet;

	private JTextField _txtResultDataSet;

	private JPanel _settingPanel;

	/**
	 * 
	 */
	public USettingComponent()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettingComponent#getSettings()
	 */
	public ISettings getSettings()
	{
		USettings uSettings = new USettings();
		uSettings.setFirstDataSet((String) _cmbFirstDataSet.getSelectedItem());
		uSettings.setSecondDataSet((String) _cmbSecondDataSet.getSelectedItem());
		uSettings.setResultDataSet(_txtResultDataSet.getText());
		return uSettings;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettingComponent#getDefaultSettings()
	 */
	public ISettings getDefaultSettings()
	{
		return new USettings();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings)
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine)
	{
		if (_settingPanel == null) {
			_settingPanel = new JPanel();
			_cmbFirstDataSet = new JComboBox();
			_cmbSecondDataSet = new JComboBox();
			_txtResultDataSet = new JTextField();
			_settingPanel.setLayout(new GridLayout(0, 2));
			_settingPanel.add(new JLabel("First data set"));
			_settingPanel.add(_cmbFirstDataSet);
			_settingPanel.add(new JLabel("Second data set"));
			_settingPanel.add(_cmbSecondDataSet);
			_settingPanel.add(new JLabel("Result data set"));
			_settingPanel.add(_txtResultDataSet);
		}
		IDataSetManager dataSetManager = dataEngine.getDataSetManager();
		IDataSet[] dataSets = null;
        
		try {
			dataSetManager.getDataSets();
		} catch (PlatformException e) {
			// TODO:
		}

		_cmbFirstDataSet.removeAllItems();
		_cmbSecondDataSet.removeAllItems();
		for (int i = 0; i < dataSets.length; i++) {
			_cmbFirstDataSet.addItem(dataSets[i].getName());
			_cmbSecondDataSet.addItem(dataSets[i].getName());
		}

		USettings uSettings = (USettings) settings;
		_cmbFirstDataSet.setSelectedItem(uSettings.getFirstDataSet());
		_cmbSecondDataSet.setSelectedItem(uSettings.getSecondDataSet());
		_txtResultDataSet.setText(uSettings.getResultDataSet());

		return _settingPanel;
	}
}
