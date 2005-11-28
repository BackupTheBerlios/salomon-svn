package pl.edu.agh.capitol.c45TreeCreator.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettings;

public class C45TreeCreatorSettingsPanel extends JPanel {

	/**
	 * 
	 */
	
	private ISettings settings;
	private IDataEngine dataEngine;
	
	
	private static final long serialVersionUID = 1L;
	
	public ISettings getSettings() {
		//TODO umiescic z pol settingsy dla doJob()
		return settings;
	}
	
	/**
	 * This is the default constructor
	 */
	public C45TreeCreatorSettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
	}
}
