package pl.capitol.tree.plugins.dataloader.components;

import java.awt.Component;

//import pl.capitol.tree.plugins.test.panels.TestSettingsPanel;
import pl.capitol.tree.plugins.dataloader.panels.TreeDataLoaderSettingsPanel;
//import pl.capitol.tree.plugins.util.TreeSettings;
import pl.capitol.tree.plugins.util.TreeDataLoaderSettings;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class TreeDataLoaderSettingComponent implements ISettingComponent {
	
	private TreeDataLoaderSettingsPanel panel;
	
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
		return new TreeDataLoaderSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new TreeDataLoaderSettingsPanel(settings,dataEngine);
		return panel;
	}

}
