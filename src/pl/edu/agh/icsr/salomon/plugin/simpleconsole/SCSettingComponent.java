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
		settings.query = getQueryEditor().getText();
		return settings;
	}

	public Component getComponent()
	{
		if (_settingsPanel == null) {
			_settingsPanel = new JPanel();
			_settingsPanel.setLayout(new BorderLayout());
			_settingsPanel.add(new JLabel("SQL Console"), BorderLayout.NORTH);
			_settingsPanel.add(new JScrollPane(getQueryEditor()),
					BorderLayout.CENTER);
			_settingsPanel.setSize(80, 70);
		}
		return _settingsPanel;
	}

	public Component getComponent(ISettings settings)
	{
		SCSettings scSettings = (SCSettings) settings;		
		_txtQueryEditor.setText(scSettings.query);
		return getComponent();
	}

	private JTextArea getQueryEditor()
	{
		if (_txtQueryEditor == null) {
			_txtQueryEditor = new JTextArea(5, 10);
		}
		return _txtQueryEditor;
	}
}