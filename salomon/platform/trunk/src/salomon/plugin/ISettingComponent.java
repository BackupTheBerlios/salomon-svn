/*
 * Created on 2004-05-22
 *
 */

package salomon.plugin;

import java.awt.Component;

import salomon.platform.IDataEngine;

/**
 * Interface represents object, which is responsible for showing plugin
 * settings.
 *
 */
public interface ISettingComponent
{
	/**
	 * Returns settings object. It is set basing on user selections in setting
	 * panel
	 * 
	 * @return plugin settings
	 */
	ISettings getSettings();

	/**
	 * Method create default settings for plugin.
	 * 
	 * @return default settings for plugin
	 */
	ISettings getDefaultSettings();

	/**
	 * Fills settings component basing on given settings.
	 * 
	 * @param settings settings object
	 * @return component showing given settings
	 */
	Component getComponent(ISettings settings, IDataEngine dataEngine);
}