/*
 * Created on 2004-05-10
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

package ks.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author nico
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Config
{
	private static final String BUNDLE_NAME = "config";//$NON-NLS-1$
	 
	private static ResourceBundle RESOURCE_BUNDLE = null;
	
	private static Logger _logger = Logger.getLogger(Config.class); 
	
	static {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
		} catch (Exception e) {
			_logger.fatal("", e);			
		}
	}
	
	/**
	 * 
	 */
	private Config()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param key
	 * @return
	 */
	public static String getString(String key)
	{		
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}