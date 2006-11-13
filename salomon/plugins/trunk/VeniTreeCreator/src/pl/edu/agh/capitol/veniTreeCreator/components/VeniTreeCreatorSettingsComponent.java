package pl.edu.agh.capitol.veniTreeCreator.components;

import java.awt.Component;

import pl.edu.agh.capitol.veniTreeCreator.panels.VeniTreeCreatorSettingsPanel;
import pl.edu.agh.capitol.veniTreeCreator.util.VeniTreeCreatorSettings;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

public class VeniTreeCreatorSettingsComponent implements ISettingComponent {
	
	private VeniTreeCreatorSettingsPanel panel;
	
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
		return new VeniTreeCreatorSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new VeniTreeCreatorSettingsPanel(settings,dataEngine);
		return panel;
	}

}
