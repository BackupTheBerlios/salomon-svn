/*
 * Created on 2004-05-21
 *
 */

package salomon.plugin;

import java.io.Serializable;

/**
 * Represents plugin settings.
 * 
 * @author nico
 */
public interface ISettings extends Serializable
{
	/**
	 * Method parses settings from their string representation. Used to load
	 * plugin settings from data base.
	 * 
	 * @param stringSettings
	 */
	void parseSettings(String stringSettings);

	/**
	 * Method returns String representation of settings. Used to write plugin
	 * settings in data base.
	 * 
	 * @return String representation of settings
	 */
	String settingsToString();

}