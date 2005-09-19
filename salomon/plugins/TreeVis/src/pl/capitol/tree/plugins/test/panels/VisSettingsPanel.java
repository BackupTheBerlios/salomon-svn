package pl.capitol.tree.plugins.test.panels;

import javax.swing.JPanel;

import salomon.platform.IDataEngine;

import salomon.plugin.ISettings;
//NK
/**
 * @author mnowakowski
 *
 */
public class VisSettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ISettings settings;
	private IDataEngine dataEngine;
	
	/**
	 * This is the default constructor
	 */
	public VisSettingsPanel(ISettings settings, IDataEngine dataEngine) {
		super();
		this.dataEngine = dataEngine;
		this.settings = settings;
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private  void initialize() {
	
	}
	
	public ISettings getSettings() {
		//TODO umiescic z pol settingsy dla doJob()
		return settings;
	}
	public void setSettings(ISettings settings) {
		this.settings = settings;
	}
	public IDataEngine getDataEngine() {
		return dataEngine;
	}
	public void setDataEngine(IDataEngine dataEngine) {
		this.dataEngine = dataEngine;
	}
	
	
 }  //  @jve:decl-index=0:visual-constraint="10,10"  