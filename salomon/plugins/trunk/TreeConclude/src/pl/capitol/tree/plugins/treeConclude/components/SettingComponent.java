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
 * <i>isAlone</i> - czy plugin dzia�a sam i drzewo do testowania 
 * powinien pobra� z w�asnych ustawie� czy te� ze �rodowiska
 * <i>treeId</i> - zmienna okre�laj�ca wybrane drzewo do test�w
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
	 * Metoda zwraca komponent graficzny odpowiedzialny za zdefiniowanie ustawie� pluginu.
	 */
	/* (non-Javadoc)
	 * @see salomon.plugin.ISettingComponent#getComponent(salomon.plugin.ISettings, salomon.platform.IDataEngine)
	 */
	public Component getComponent(ISettings settings, IDataEngine dataEngine) {
		panel =  new SettingsPanel(settings,dataEngine);
		return panel;
	}

}
