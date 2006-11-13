package pl.edu.agh.capitol.c45TreeCreator.panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettings;
import salomon.util.serialization.SimpleString;

/**
 * 
 * Settingsy (poziom ufnoœci algorytmu)
 * 
 * @author Lukasz
 *
 */
public class C45TreeCreatorSettingsPanel extends JPanel {

	/**
	 * 
	 */

	private ISettings settings;

	private IDataEngine dataEngine;

	private JTextField jTextFieldConfidenceLevel = null;

	private JLabel jLabelTable = null;

	private static final long serialVersionUID = 1L;

	public ISettings getSettings() {
		settings.setField("confidenceLevel", new SimpleString(
				jTextFieldConfidenceLevel.getText()));
		return settings;
	}

	/**
	 * This is the default constructor
	 */
	public C45TreeCreatorSettingsPanel(ISettings settings,
			IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);

		jLabelTable = new JLabel();
		jLabelTable.setBounds(new java.awt.Rectangle(5, 5, 100, 20));
		jLabelTable.setText("Confidence Level");

		this.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		this.setPreferredSize(new java.awt.Dimension(100, 55));
		this.setSize(100, 55);
		this.setName("settingsPanel");
		this.add(jLabelTable);
		this.add(getJTextFieldConfidenceLevel(), null);
	}

	private JTextField getJTextFieldConfidenceLevel() {
		if (jTextFieldConfidenceLevel == null) {
			jTextFieldConfidenceLevel = new JTextField();
			jTextFieldConfidenceLevel.setText("1.0");
			jTextFieldConfidenceLevel.setMinimumSize(new Dimension(30, 30));
			jTextFieldConfidenceLevel.setBounds(new java.awt.Rectangle(10, 25,
					75, 25));
		}
		return jTextFieldConfidenceLevel;
	}
}
