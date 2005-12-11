package pl.capitol.tree.plugins.treeConclude.components;

import java.awt.Component;

import pl.capitol.tree.plugins.treeConclude.panels.SettingsPanel;
import pl.capitol.tree.plugins.treeConclude.util.Settings;
import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class SettingComponent implements ISettingComponent {
	
	private SettingsPanel panel;
	
	/**
	 * Ta metoda zwraca settingsy i przekazuje je do doJob() w pluginie.
	 */
	public ISettings getSettings() {
		return panel.getSettings();
	}

	/**
	 * Ta metoda zwraca obiek przekazywany pozniej do getComponent() podczas pokazywania okna z settingsami
	 */
	public ISettings getDefaultSettings() {
		return new Settings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new SettingsPanel(settings,dataEngine);
		return panel;
	}

}
