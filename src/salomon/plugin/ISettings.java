/*
 * Created on 2004-05-21
 *
 */
package salomon.plugin;


/**
 * @author nico
 * Represents plugin settings 
 */
public interface ISettings
{
	/** 
	 *  Method parses settings from string their string representation.
	 * 
	 * @param stringSettings
	 */
	void parseSettings(String stringSettings);	
	
	/**
	 * 
	 * @return string representation of settings 
	 */
	String getSettings();
}
