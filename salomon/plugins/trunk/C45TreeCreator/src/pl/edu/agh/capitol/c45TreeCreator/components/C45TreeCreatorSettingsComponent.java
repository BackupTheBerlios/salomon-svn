package pl.edu.agh.capitol.c45TreeCreator.components;

import java.awt.Component;

import pl.edu.agh.capitol.c45TreeCreator.panels.C45TreeCreatorSettingsPanel;
import pl.edu.agh.capitol.c45TreeCreator.util.C45TreeCreatorSettings;

import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 * Komponent settingsów
 * 
 * @author Lukasz
 * 
 */
public class C45TreeCreatorSettingsComponent implements ISettingComponent {
	
	private C45TreeCreatorSettingsPanel panel;
	
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
		return new C45TreeCreatorSettings();
	}

	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new C45TreeCreatorSettingsPanel(settings,dataEngine);
		return panel;
	}

}
