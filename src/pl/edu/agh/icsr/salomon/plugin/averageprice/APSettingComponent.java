/*
 * Created on 2004-05-22
 *
 */

package pl.edu.agh.icsr.salomon.plugin.averageprice;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * @author nico
 *  
 */
public class APSettingComponent implements ISettingComponent
{
	private JPanel _pnlAttributes = null;

	private JPanel _settingsPanel = null;

	private JTextField _txtEmail = null;

	private JTextField _txtName = null;

	private JTextField _txtNick = null;

	private JTextField _txtSurname = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings)
	 */
	public Component getComponent(ISettings settings)
	{
		APSettings apSettings = (APSettings) settings;
		if (_settingsPanel == null) {
			_settingsPanel = new JPanel();
			_settingsPanel.setLayout(new BorderLayout());
			_settingsPanel.add(new JLabel("Age Counter Settings"),
					BorderLayout.NORTH);
			_settingsPanel.add(getPnlAttributes(), BorderLayout.CENTER);
			_settingsPanel.setSize(80, 70);
		}
		_txtName.setText(apSettings.getName());
		_txtSurname.setText(apSettings.getSurname());
		_txtNick.setText(apSettings.getNick());
		_txtEmail.setText(apSettings.getEmail());
		return _settingsPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see salomon.plugin.ISettingComponent#getSettings()
	 */
	public ISettings getSettings()
	{
		APSettings settings = new APSettings();
		settings.setName(getValue(getTxtName()));
		settings.setSurname(getValue(getTxtSurname()));
		settings.setNick(getValue(getTxtNick()));
		settings.setEmail(getValue(getTxtEmail()));
		return settings;
	}

	/**
	 * Method return value from specified text field. If no value is entered,
	 * null is returned
	 */
	private String getValue(JTextField txtField)
	{
		String value = txtField.getText().trim();
		return value;
	}

	private JPanel getPnlAttributes()
	{
		if (_pnlAttributes == null) {
			_pnlAttributes = new JPanel(new GridLayout(0, 2));
			_pnlAttributes.add(new JLabel("Name"));
			_pnlAttributes.add(getTxtName());
			_pnlAttributes.add(new JLabel("Surname"));
			_pnlAttributes.add(getTxtSurname());
			_pnlAttributes.add(new JLabel("Nick"));
			_pnlAttributes.add(getTxtNick());
			_pnlAttributes.add(new JLabel("Email"));
			_pnlAttributes.add(getTxtEmail());
		}
		return _pnlAttributes;
	}

	private JTextField getTxtEmail()
	{
		if (_txtEmail == null) {
			_txtEmail = new JTextField();
		}
		return _txtEmail;
	}

	private JTextField getTxtName()
	{
		if (_txtName == null) {
			_txtName = new JTextField();
		}
		return _txtName;
	}

	private JTextField getTxtNick()
	{
		if (_txtNick == null) {
			_txtNick = new JTextField();
		}
		return _txtNick;
	}

	private JTextField getTxtSurname()
	{
		if (_txtSurname == null) {
			_txtSurname = new JTextField();
		}
		return _txtSurname;
	}

	public ISettings getDefaultSettings()
	{
		APSettings apSettings = new APSettings();
		apSettings.setName("");
		apSettings.setSurname("");
		apSettings.setNick("");
		apSettings.setEmail("");
		return apSettings;
	}
}