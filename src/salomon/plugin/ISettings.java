/*
 * Created on 2004-05-21
 *
 */

package salomon.plugin;

/**
 * @author nico Represents plugin settings
 */
public interface ISettings
{
	/**
	 * Method parses settings from their string representation.
	 * 
	 * @param stringSettings
	 */
	void parseSettings(String stringSettings);

	/**
	 * 
	 * @return String representation of settings
	 */
	String settingsToString();

}