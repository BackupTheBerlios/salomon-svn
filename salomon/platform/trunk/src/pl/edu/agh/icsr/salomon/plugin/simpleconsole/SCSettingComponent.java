/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.simpleconsole;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import salomon.core.data.DataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 *  
 */
public class SCSettingComponent implements ISettingComponent
{
	private JPanel _settingsPanel = null;

	private JTextArea _txtQueryEditor = null;

	public ISettings getSettings()
	{
		SCSettings settings = new SCSettings();
		settings.setQuery(getQueryEditor().getText());
		return settings;
	}

	public Component getComponent(ISettings settings)
	{
		SCSettings scSettings = (SCSettings) settings;
		if (_settingsPanel == null) {
			_settingsPanel = new JPanel();
			_settingsPanel.setLayout(new BorderLayout());
			_settingsPanel.add(new JLabel("SQL Console"), BorderLayout.NORTH);
			_settingsPanel.add(new JScrollPane(getQueryEditor()),
					BorderLayout.CENTER);
			_settingsPanel.setSize(80, 70);
		}
		_txtQueryEditor.setText(scSettings.getQuery());
		return _settingsPanel;
	}

	private JTextArea getQueryEditor()
	{
		if (_txtQueryEditor == null) {
			_txtQueryEditor = new JTextArea(5, 10);
		}
		return _txtQueryEditor;
	}

	public ISettings getDefaultSettings()
	{
		return new SCSettings();
	}

	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings, salomon.core.data.DataEngine)
	 */
	public Component getComponent(ISettings settings, DataEngine dataEngine)
	{
		// TODO Auto-generated method stub
		return null;
	}
}