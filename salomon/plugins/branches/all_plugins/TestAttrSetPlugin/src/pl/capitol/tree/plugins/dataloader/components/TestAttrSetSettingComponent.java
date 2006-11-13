package pl.capitol.tree.plugins.dataloader.components;

import java.awt.Component;

//import pl.capitol.tree.plugins.test.panels.TestSettingsPanel;
import pl.capitol.tree.plugins.dataloader.panels.TestAttrSetSettingsPanel;
//import pl.capitol.tree.plugins.util.TreeSettings;
import pl.capitol.tree.plugins.util.TestAttrSetSettings;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TestAttrSetSettingComponent implements ISettingComponent {
	
	private TestAttrSetSettingsPanel panel;
	
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
		return new TestAttrSetSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new TestAttrSetSettingsPanel(settings,dataEngine);
		return panel;
	}

}
