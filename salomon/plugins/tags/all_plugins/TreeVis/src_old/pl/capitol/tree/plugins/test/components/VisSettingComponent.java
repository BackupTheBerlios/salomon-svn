package pl.capitol.tree.plugins.test.components;

import java.awt.Component;

import pl.capitol.tree.plugins.test.panels.VisSettingsPanel;
import pl.capitol.tree.plugins.util.TreeVisSettings;

import salomon.platform.IDataEngine;

import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class VisSettingComponent implements ISettingComponent {
	
	private VisSettingsPanel panel;
	
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
		return new TreeVisSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new VisSettingsPanel(settings,dataEngine);
		return panel;
	}
}
