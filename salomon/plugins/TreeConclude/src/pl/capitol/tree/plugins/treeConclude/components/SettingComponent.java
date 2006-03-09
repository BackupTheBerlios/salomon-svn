package pl.capitol.tree.plugins.treeConclude.components;

import java.awt.Component;

import pl.capitol.tree.plugins.treeConclude.panels.SettingsPanel;
import pl.capitol.tree.plugins.treeConclude.util.Settings;
import salomon.platform.IDataEngine;
import salomon.plugin.ISettingComponent;
import salomon.plugin.ISettings;

/**
 * 
 * Komponent definiujacy ustawienia pluginu. Definiuje dwie zmienne:
 * <i>isAlone</i> - czy plugin dzia³a sam i drzewo do testowania 
 * powinien pobraæ z w³asnych ustawieñ czy te¿ ze œrodowiska
 * <i>treeId</i> - zmienna okreœlaj¹ca wybrane drzewo do testów
 * 
 * @author Mateusz Nowakowski
 *
 */
public class SettingComponent implements ISettingComponent {
	
	private SettingsPanel panel;
	
	/**
	 * Ta metoda zwraca settingsy i przekazuje je do doJob() w pluginie.
	 */
	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getSettings()
	 */
	public ISettings getSettings() {
		return panel.getSettings();
	}

	/**
	 * Ta metoda zwraca obiek przekazywany pozniej do getComponent() podczas pokazywania okna z settingsami
	 */
	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getDefaultSettings()
	 */
	public ISettings getDefaultSettings() {
		return new Settings();
	}

	/**
	 * Metoda zwraca komponent graficzny odpowiedzialny za zdefiniowanie ustawieñ pluginu.
	 */
	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings, salomon.platform.IDataEngine)
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new SettingsPanel(settings,dataEngine);
		return panel;
	}

}
