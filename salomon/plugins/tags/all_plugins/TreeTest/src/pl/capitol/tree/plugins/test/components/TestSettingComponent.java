package pl.capitol.tree.plugins.test.components;

import java.awt.Component;

import pl.capitol.tree.plugins.test.panels.TestSettingsPanel;
import pl.capitol.tree.plugins.util.TreeSettings;
import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TestSettingComponent implements ISettingComponent {
	
	private TestSettingsPanel panel;
	
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
		return new TreeSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new TestSettingsPanel(settings,dataEngine);
		return panel;
	}

}
