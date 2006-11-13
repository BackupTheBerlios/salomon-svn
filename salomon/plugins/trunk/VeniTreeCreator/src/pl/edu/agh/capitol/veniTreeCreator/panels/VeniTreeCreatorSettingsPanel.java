package pl.edu.agh.capitol.veniTreeCreator.panels;

import javax.swing.JPanel;
import javax.swing.JTextField;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettings;

public class VeniTreeCreatorSettingsPanel extends JPanel {

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
	public VeniTreeCreatorSettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
	}
}
