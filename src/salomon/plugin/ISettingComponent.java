/*
 * Created on 2004-05-22
 *
 */
package salomon.plugin;

import java.awt.Component;

/**
 * @author nico
 *
 */
public interface ISettingComponent
{
	/** Returns settings object. 
	  * It is set basing user selections in setting panel*/
	ISettings getSettings();

	/** Returns component, which is used by user to set plugin settings */
	Component getComponent();

	/** Fills settings component basing on settings */
	Component getComponent(ISettings settings);
	
}
